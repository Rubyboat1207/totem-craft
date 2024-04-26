package totemcraft.rubyboat.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlimeBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.fluid.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import totemcraft.rubyboat.Main;

import java.util.Random;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow
    public abstract ItemStack getStackInHand(Hand hand);

    @Shadow public abstract void setHealth(float health);

    @Shadow public abstract boolean clearStatusEffects();

    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);

    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);


    @Shadow public abstract boolean damage(DamageSource source, float amount);

    @Inject(at = @At("HEAD"), method = "onDeath")
    public void onDeath(DamageSource source, CallbackInfo ci)
    {
        if(!((LivingEntity)(Object)this).getWorld().isClient){
            ServerWorld sw = (ServerWorld) ((LivingEntity)(Object)this).getWorld();
            if(new Random().nextInt(0, 100) < sw.getGameRules().getInt(Main.TOTEM_DROP_CHANCE)) {
                SpawnItemAtEntity(((LivingEntity)(Object)this), GetTotemFromEntity(((LivingEntity)(Object)this)));
            }
        }
    }
    @Inject(at = @At("HEAD"), method = "damage", cancellable = true)
    public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if(source.isFire() && this.hasStatusEffect(Main.lavaWalker)) {
            cir.setReturnValue(false);
        }
    }


    @Inject(at = @At("HEAD"), method = "canWalkOnFluid", cancellable = true)
    public void canWalkOnFluid(FluidState state, CallbackInfoReturnable<Boolean> cir) {
        if(state.isOf(Fluids.LAVA) && this.hasStatusEffect(Main.lavaWalker)) {
            cir.setReturnValue(true);
        }
        if(state.isOf(Fluids.WATER) && this.hasStatusEffect(Main.waterWalker)) {
            cir.setReturnValue(true);
        }
    }

    public void SpawnItemAtEntity(LivingEntity livingEntity, ItemStack stack)
    {
        if(stack != null)
        {
            World world = livingEntity.getWorld();
            Entity itemEntity = new ItemEntity(world, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), stack);
            world.spawnEntity(itemEntity);
        }
    }

    public ItemStack GetTotemFromEntity(Entity ent)
    {
        //instanceof patch
        if(ent instanceof ZombieVillagerEntity) { return new ItemStack(Main.zombieVillagerTotem, 1); }
        if(ent instanceof ZombifiedPiglinEntity) { return new ItemStack(Main.zombifiedPiglinTotem, 1); }
        if(ent instanceof MooshroomEntity) { return new ItemStack(Main.mooshroomTotem, 1); }
        if(ent instanceof StrayEntity) { return new ItemStack(Main.strayTotem, 1); }
        if(ent instanceof WardenEntity) { return new ItemStack(Main.wardenTotem, 1); }


        if(ent instanceof CowEntity) { return new ItemStack(Main.cowTotem, 1); }
        if(ent instanceof AxolotlEntity) { return new ItemStack(Main.axolotlTotem, 1); }
        if(ent instanceof BatEntity) { return new ItemStack(Main.batTotem, 1); }
        if(ent instanceof BeeEntity) { return new ItemStack(Main.beeTotem, 1); }
        if(ent instanceof BlazeEntity) { return new ItemStack(Main.blazeTotem, 1); }
        if(ent instanceof CatEntity) { return new ItemStack(Main.catTotem, 1); }
        if(ent instanceof ChickenEntity) { return new ItemStack(Main.chickenTotem, 1); }
        if(ent instanceof CaveSpiderEntity) { return new ItemStack(Main.caveSpiderTotem, 1); }
        if(ent instanceof CodEntity) { return new ItemStack(Main.codTotem, 1); }
        if(ent instanceof CreeperEntity) { return new ItemStack(Main.creeperTotem, 1); }
        if(ent instanceof DrownedEntity) { return new ItemStack(Main.drownedTotem, 1); }
        if(ent instanceof DolphinEntity) { return new ItemStack(Main.dolphinTotem, 1); }
        if(ent instanceof EndermanEntity) { return new ItemStack(Main.endermanTotem, 1); }
        if(ent instanceof ElderGuardianEntity) { return new ItemStack(Main.elderGuardianTotem, 1); }
        if(ent instanceof EndermiteEntity) { return new ItemStack(Main.endermiteTotem, 1); }
        if(ent instanceof FoxEntity) { return new ItemStack(Main.foxTotem, 1); }
        if(ent instanceof GhastEntity) { return new ItemStack(Main.ghastTotem, 1); }
        if(ent instanceof GlowSquidEntity) { return new ItemStack(Main.glowSquidTotem, 1); }
        if(ent instanceof SquidEntity) { return new ItemStack(Main.squidTotem, 1); }
        if(ent instanceof TraderLlamaEntity) { return new ItemStack(Main.llamaTotem, 1); }
        if(ent instanceof GoatEntity) { return new ItemStack(Main.goatTotem, 1); }
        if(ent instanceof GuardianEntity) { return new ItemStack(Main.guardianTotem, 1); }
        if(ent instanceof HoglinEntity) { return new ItemStack(Main.hoglinTotem, 1); }
        if(ent instanceof HorseEntity) { return new ItemStack(Main.horseTotem, 1); }
        if(ent instanceof HuskEntity) { return new ItemStack(Main.huskTotem, 1); }
        if(ent instanceof SkeletonEntity) { return new ItemStack(Main.skeletonTotem, 1); }
        if(ent instanceof IronGolemEntity) { return new ItemStack(Main.ironGolemTotem, 1); }
        if(ent instanceof LlamaEntity) { return new ItemStack(Main.llamaTotem, 1); }
        if(ent instanceof MagmaCubeEntity) { return new ItemStack(Main.magmaCubeTotem, 1); }
        if(ent instanceof OcelotEntity) { return new ItemStack(Main.ocelotTotem, 1); }
        if(ent instanceof PandaEntity) { return new ItemStack(Main.pandaTotem, 1); }
        if(ent instanceof ParrotEntity) { return new ItemStack(Main.parrotTotem, 1); }
        if(ent instanceof PhantomEntity) { return new ItemStack(Main.phantomTotem, 1); }
        if(ent instanceof PigEntity) { return new ItemStack(Main.pigTotem, 1); }
        if(ent instanceof PiglinEntity) { return new ItemStack(Main.piglinTotem, 1); }
        if(ent instanceof PiglinBruteEntity) { return new ItemStack(Main.piglinBruteTotem, 1); }
        if(ent instanceof PillagerEntity) { return new ItemStack(Main.pillagerTotem, 1); }
        if(ent instanceof PolarBearEntity) { return new ItemStack(Main.polarBearTotem, 1); }
        if(ent instanceof PufferfishEntity) { return new ItemStack(Main.pufferfishTotem, 1); }
        if(ent instanceof RabbitEntity) { return new ItemStack(Main.rabbitTotem, 1); }
        if(ent instanceof RavagerEntity) { return new ItemStack(Main.ravagerTotem, 1); }
        if(ent instanceof SheepEntity) { return new ItemStack(Main.sheepTotem, 1); }
        if(ent instanceof ShulkerEntity) { return new ItemStack(Main.shulkerTotem, 1); }
        if(ent instanceof SalmonEntity) { return new ItemStack(Main.salmonTotem, 1); }
        if(ent instanceof SilverfishEntity) { return new ItemStack(Main.silverfishTotem, 1); }
        if(ent instanceof SpiderEntity) { return new ItemStack(Main.spiderTotem, 1); }
        if(ent instanceof SnowGolemEntity) { return new ItemStack(Main.snowGolemTotem, 1); }
        if(ent instanceof SlimeEntity) { return new ItemStack(Main.slimeTotem, 1); }
        if(ent instanceof StriderEntity) { return ((StriderEntity) ent).isCold() ? new ItemStack(Main.coldStriderTotem, 1) : new ItemStack(Main.striderTotem, 1); }
        if(ent instanceof TropicalFishEntity) { return new ItemStack(Main.tropicalFishTotem, 1); }
        if(ent instanceof TurtleEntity) { return new ItemStack(Main.turtleTotem, 1); }
        if(ent instanceof VexEntity) { return new ItemStack(Main.vexTotem, 1); }
        if(ent instanceof VillagerEntity) { return new ItemStack(Main.villagerTotem, 1); }
        if(ent instanceof WitherEntity) { return new ItemStack(Main.witherTotem, 1); }
        if(ent instanceof WolfEntity) { return new ItemStack(Main.wolfTotem, 1); }
        if(ent instanceof ZoglinEntity) { return new ItemStack(Main.zoglinTotem, 1); }
        if(ent instanceof ZombieEntity) { return new ItemStack(Main.zombieTotem, 1); }
        if(ent instanceof WitherSkeletonEntity) { return new ItemStack(Main.witherskeletonTotem, 1); }
        if(ent instanceof WanderingTraderEntity) { return new ItemStack(Main.wanderingTraderTotem, 1); }
        if(ent.isPlayer()) { return new ItemStack(Main.playerTotem, 1); }

        return null;
    }

    @Inject(at = @At("HEAD"), method = "isClimbing", cancellable = true)
    public void isClimbing(CallbackInfoReturnable<Boolean> cir)
    {
        if(this.hasStatusEffect(Main.climbEffect) && ((LivingEntity)(Object)this).horizontalCollision)
        {
            cir.setReturnValue(true);
        }
    }
}
