package com.rubyboat.totem_craft.mixin;

import com.rubyboat.totem_craft.TotemCraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnimalEntity.class)
public class AnimalMixin {
    @Inject(at = @At("HEAD"), method = "breed(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/AnimalEntity;)V")
    public void breed(ServerWorld world, AnimalEntity other, CallbackInfo ci) {
        TotemCraft.SpawnItemAtEntity((LivingEntity) ( (Object) this), TotemCraft.GetTotemFromEntity(other));
    }
}
