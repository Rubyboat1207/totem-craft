package com.rubyboat.totem_craft.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.rubyboat.totem_craft.TotemCraft;
import com.rubyboat.totemapi.components.TotemEffectType;
import com.rubyboat.totemapi.effects.TotemEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GiveItemEffect extends TotemEffect {
    public static final MapCodec<GiveItemEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
                ItemStack.CODEC.fieldOf("item").forGetter(GiveItemEffect::getItem)
        ).apply(instance, GiveItemEffect::new)
    );
    private final ItemStack item;
    public GiveItemEffect(ItemStack item) {
        this.item = item;
    }

    private ItemStack getItem() {
        return item;
    }

    @Override
    public void onDeath(LivingEntity user, World world, ItemStack stack) {
        if(user.isPlayer())
        {
            ((PlayerEntity) user).getInventory().insertStack(item);
        }
    }

    @Override
    public String getTooltip() {
        return "gives the user " + (item.getCount() == 1 ? "a" : item.getCount()) + " " + item.getName().getString();
    }

    @Override
    public TotemEffectType getType() {
        return TotemCraft.GIVE_ITEM_EFFECT_TYPE;
    }
}
