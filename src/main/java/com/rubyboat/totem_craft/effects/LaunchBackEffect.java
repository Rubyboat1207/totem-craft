package com.rubyboat.totem_craft.effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.rubyboat.totem_craft.TotemCraft;
import com.rubyboat.totemapi.components.TotemEffectType;
import com.rubyboat.totemapi.effects.TotemEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

public class LaunchBackEffect extends TotemEffect {
    private static final float DEFAULT_UPWARDS_FORCE = 0.9f;
    public static final MapCodec<LaunchBackEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.FLOAT.fieldOf("force").forGetter(LaunchBackEffect::getForce),
                    Codec.FLOAT.optionalFieldOf("upwards_force", DEFAULT_UPWARDS_FORCE).forGetter(LaunchBackEffect::getForce)
            ).apply(instance, LaunchBackEffect::new)
    );
    private final float force;
    private final float upwardsForce;

    public LaunchBackEffect(float force, float upwardsForce) {
        this.force = force;
        this.upwardsForce = upwardsForce;
    }
    public LaunchBackEffect(float force) {
        this.force = force;
        this.upwardsForce = DEFAULT_UPWARDS_FORCE;
    }

    public float getForce() {
        return force;
    }

    @Override
    public void onDeath(LivingEntity user, World world, ItemStack stack) {
        world.getOtherEntities(user, user.getBoundingBox().expand(3), entity -> entity instanceof LivingEntity).forEach(entity -> {
            LivingEntity livingEntity = (LivingEntity) entity;
            double x = livingEntity.getX() - user.getX();
            double z = livingEntity.getZ() - user.getZ();
            double distance = Math.sqrt(x * x + z * z);
            double forceX = x / distance * force;
            double forceZ = z / distance * force;
            livingEntity.addVelocity(forceX, upwardsForce, forceZ);
        });
        if(world.isClient) return;
        var pos = user.getPos();
        world.addParticle(ParticleTypes.GUST_EMITTER_LARGE, pos.getX(), pos.getY(), pos.getZ(), 0, 0, 0);
        world.addParticle(ParticleTypes.GUST_EMITTER_SMALL, pos.getX(), pos.getY(), pos.getZ(), 0, 0, 0);
    }

    @Override
    public String getTooltip() {
        return "Launch all nearby entities away from you";
    }

    @Override
    public TotemEffectType getType() {
        return TotemCraft.LAUNCH_BACK_EFFECT_TYPE;
    }
}
