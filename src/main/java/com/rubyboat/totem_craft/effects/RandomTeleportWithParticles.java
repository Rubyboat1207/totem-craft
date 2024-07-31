package com.rubyboat.totem_craft.effects;

import com.mojang.serialization.MapCodec;
import com.rubyboat.totem_craft.TotemCraft;
import com.rubyboat.totemapi.components.TotemEffectType;
import com.rubyboat.totemapi.effects.RandomTeleportEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RandomTeleportWithParticles extends RandomTeleportEffect {
    public RandomTeleportWithParticles(float radius) {
        super(radius);
    }

    @Override
    public void onDeath(LivingEntity user, World world, ItemStack stack) {
        Vec3d pos = user.getPos();
        super.onDeath(user, world, stack);
        Vec3d newPos = user.getPos();

        double distance = pos.distanceTo(newPos);

        double particleIncrement = distance / 10;

        for(double i = 0; i < distance; i += particleIncrement) {
            Vec3d particlePos = pos.lerp(newPos, i / distance);
            world.addParticle(ParticleTypes.END_ROD, particlePos.getX(), particlePos.getY(), particlePos.getZ(), 0, 0, 0);
        }
    }

    @Override
    public TotemEffectType getType() {
        return TotemCraft.RANDOM_TELEPORT_WITH_PARTICLES;
    }
}
