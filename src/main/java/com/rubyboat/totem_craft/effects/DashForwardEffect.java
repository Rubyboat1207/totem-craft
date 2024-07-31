package com.rubyboat.totem_craft.effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.rubyboat.totem_craft.TotemCraft;
import com.rubyboat.totemapi.components.TotemEffectType;
import com.rubyboat.totemapi.effects.TotemEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class DashForwardEffect extends TotemEffect {
    public static final MapCodec<DashForwardEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.FLOAT.fieldOf("speed").forGetter(DashForwardEffect::getSpeed)
            ).apply(instance, DashForwardEffect::new)
    );
    private final float speed;

    public DashForwardEffect(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

    @Override
    public void onDeath(LivingEntity user, World world, ItemStack stack) {
        user.playSound(SoundEvents.ENTITY_CAMEL_DASH);
        user.addVelocity(Math.cos((user.getYaw() + 90) * (Math.PI / 180)) * speed, 0,  Math.sin((user.getYaw() + 90) * (Math.PI / 180)) * speed);
    }

    @Override
    public String getTooltip() {
        return "Dash Horizontally in the direction you're facing";
    }

    @Override
    public TotemEffectType getType() {
        return TotemCraft.DASH_FORWARD_EFFECT_TYPE;
    }
}
