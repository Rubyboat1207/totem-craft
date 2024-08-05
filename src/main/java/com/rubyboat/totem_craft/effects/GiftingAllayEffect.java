package com.rubyboat.totem_craft.effects;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.rubyboat.totem_craft.TCDataContainer;
import com.rubyboat.totem_craft.TotemCraft;
import com.rubyboat.totemapi.components.TotemEffectType;
import com.rubyboat.totemapi.effects.TotemEffect;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Optional;

public class GiftingAllayEffect extends TotemEffect {
    public static MapCodec<GiftingAllayEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.either(ItemStack.CODEC, Identifier.CODEC).fieldOf("item").forGetter(GiftingAllayEffect::getItemOrLootTable)
    ).apply(instance, GiftingAllayEffect::new));
    private final Optional<ItemStack> item;
    private final Optional<Identifier> lootTableKey;

    private Either<ItemStack, Identifier> getItemOrLootTable() {
        if(item.isPresent()) {
            return Either.left(item.get());
        }
        if(lootTableKey.isPresent()) {
            return Either.right(lootTableKey.get());
        }
        return Either.left(ItemStack.EMPTY);
    }

    public GiftingAllayEffect(ItemStack stack) {
        this.item = Optional.of(stack);
        this.lootTableKey = Optional.empty();
    }

    public GiftingAllayEffect(RegistryKey<LootTable> lootTable) {
        this.item = Optional.empty();
        this.lootTableKey = Optional.of(lootTable.getValue());
    }

    private GiftingAllayEffect(Either<ItemStack, Identifier> item) {
        this.item = item.left();
        this.lootTableKey = item.right();
    }

    public ItemStack getItem(ServerWorld world, PlayerEntity player) {
        if(item.isPresent()) {
            return item.get().copy();
        }
        if(lootTableKey.isPresent()) {
            LootTable lootTable = world.getServer().getReloadableRegistries().getLootTable(RegistryKey.of(RegistryKeys.LOOT_TABLE, lootTableKey.get()));

            LootContextParameterSet lootContextParameterSet = new LootContextParameterSet.Builder(world).luck(player.getLuck()).build(LootContextTypes.EMPTY);

            var stacks = lootTable.generateLoot(lootContextParameterSet);

            if(stacks.isEmpty()) {
                return ItemStack.EMPTY;
            }
            return lootTable.generateLoot(lootContextParameterSet).getFirst();
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void onDeath(LivingEntity user, World world, ItemStack stack) {
        if(!(user instanceof PlayerEntity player)) {
            return;
        }
        if(world.isClient()) {
            return;
        }
        spawnAllay(player, getItem((ServerWorld) world, player));
    }

    @Override
    public String getTooltip() {
        if(item.isPresent()) {
            var stack = item.get();
            return "Spawns an allay that gives you " + stack.getCount()  + " " + stack.getName().getString() + "s";
        }
        return "Spawns an allay that gives you an item";
    }

    public static void spawnAllay(PlayerEntity target, ItemStack item) {
        AllayEntity allay = EntityType.ALLAY.create(target.getWorld());
        if(allay == null) {
            return;
        }
        var hit = target.raycast(5, 0, true);

        allay.setPos(hit.getPos().x, hit.getPos().y, hit.getPos().z);

        var data = ((TCDataContainer)allay).world_border$getPersistentData();

        data.putString("gift_player", target.getUuid().toString());
        data.putBoolean("disappear", true);

        allay.getInventory().setStack(0, item);
        allay.setStackInHand(allay.getActiveHand(), item.copyWithCount(1));

        target.getWorld().spawnEntity(allay);
    }

    public static void addDebugCommand() {
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("gift_allay")
                .executes(ctx -> {
                    spawnAllay(ctx.getSource().getPlayer(), ctx.getSource().getPlayer().getMainHandStack().copy());
                    return 1;
                }))));
    }

    @Override
    public TotemEffectType getType() {
        return TotemCraft.GIFTING_ALLAY_EFFECT_TYPE;
    }
}
