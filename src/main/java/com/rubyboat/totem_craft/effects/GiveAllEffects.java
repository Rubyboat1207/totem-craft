package com.rubyboat.totem_craft.effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.rubyboat.totem_craft.TotemCraft;
import com.rubyboat.totemapi.components.TotemEffectType;
import com.rubyboat.totemapi.effects.TotemEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.world.World;

public class GiveAllEffects extends TotemEffect {
    public static final MapCodec<GiveAllEffects> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.INT.fieldOf("durationTicks").forGetter(GiveAllEffects::getDurationTicks),
                    Codec.INT.fieldOf("amplifier").forGetter(GiveAllEffects::getAmplifier)
            ).apply(instance, GiveAllEffects::new)
    );
    private final int durationTicks;
    private final int amplifier;

    public int getDurationTicks() {
        return durationTicks;
    }

    public int getAmplifier() {
        return amplifier;
    }

    public GiveAllEffects(int durationTicks, int amplifier) {
        this.durationTicks = durationTicks;
        this.amplifier = amplifier;
    }

    @Override
    public void onDeath(LivingEntity user, World world, ItemStack stack) {
        for(var effect : Registries.STATUS_EFFECT.getEntrySet()) {
            user.addStatusEffect(new StatusEffectInstance(Registries.STATUS_EFFECT.getEntry(effect.getValue()), durationTicks, amplifier));
        }
    }

    @Override
    public String getTooltip() {
        return "Gives All Affects for " + (durationTicks / 20) + " seconds at level " + (amplifier + 1);
    }

    @Override
    public TotemEffectType getType() {
        return TotemCraft.GIVE_ALL_EFFECTS_TYPE;
    }
}
