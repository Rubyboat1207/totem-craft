package com.rubyboat.totem_craft.effects;

import com.mojang.serialization.MapCodec;
import com.rubyboat.totem_craft.TotemCraft;
import com.rubyboat.totemapi.components.TotemEffectType;
import com.rubyboat.totemapi.effects.TotemEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RemoveArrowsEffect extends TotemEffect {
    public static final MapCodec<RemoveArrowsEffect> CODEC = MapCodec.unit(new RemoveArrowsEffect());

    // TODO: make mixin to fix last arrow remaining after death (PersistentProjectileEntity ln. 331)
    @Override
    public void onDeath(LivingEntity user, World world, ItemStack stack) {
        user.setStuckArrowCount(0);
    }

    @Override
    public String getTooltip() {
        return "Removes all arrows from you";
    }

    @Override
    public TotemEffectType getType() {
        return TotemCraft.REMOVE_ARROWS_EFFECT_TYPE;
    }
}
