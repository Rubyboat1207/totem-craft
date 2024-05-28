package com.rubyboat.totem_craft.mixin;

import com.rubyboat.totem_craft.TotemCraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow
    public abstract ItemStack getStackInHand(Hand hand);

    @Shadow public abstract void setHealth(float health);

    @Shadow public abstract boolean clearStatusEffects();

    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);


    @Shadow public abstract boolean damage(DamageSource source, float amount);

    @Shadow public abstract boolean hasStatusEffect(RegistryEntry<StatusEffect> effect);


    @Inject(at = @At("HEAD"), method = "onDeath")
    public void onDeath(DamageSource source, CallbackInfo ci)
    {
        if(!((LivingEntity)(Object)this).getWorld().isClient){
            ServerWorld sw = (ServerWorld) ((LivingEntity)(Object)this).getWorld();
            if(new Random().nextInt(0, 100) < sw.getGameRules().getInt(TotemCraft.TOTEM_DROP_CHANCE)) {
                TotemCraft.SpawnItemAtEntity(((LivingEntity)(Object)this), TotemCraft.GetTotemFromEntity(((LivingEntity)(Object)this)));
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "damage", cancellable = true)
    public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        var fire = ((LivingEntity)(Object)this).getWorld().getDamageSources().onFire();




        if(source == fire && this.hasStatusEffect(TotemCraft.lavaWalker)) {
            cir.setReturnValue(false);
        }
    }


    @Inject(at = @At("HEAD"), method = "canWalkOnFluid", cancellable = true)
    public void canWalkOnFluid(FluidState state, CallbackInfoReturnable<Boolean> cir) {
        if(state.isOf(Fluids.LAVA) && this.hasStatusEffect(TotemCraft.lavaWalker)) {
            cir.setReturnValue(true);
        }
        if(state.isOf(Fluids.WATER) && this.hasStatusEffect(TotemCraft.waterWalker)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(at = @At("HEAD"), method = "isClimbing", cancellable = true)
    public void isClimbing(CallbackInfoReturnable<Boolean> cir)
    {
        if(this.hasStatusEffect(TotemCraft.climbEffect) && ((LivingEntity) ((Object)(this))).horizontalCollision) {
            cir.setReturnValue(true);
        }
    }
}
