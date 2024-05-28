package com.rubyboat.totem_craft.effects;

import com.rubyboat.totemapi.effects.TotemEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RemoveArrowsEffect extends TotemEffect {

    // TODO: make mixin to fix last arrow remaining after death (PersistentProjectileEntity ln. 331)
    @Override
    public void onDeath(LivingEntity user, World world, ItemStack stack) {
        user.setStuckArrowCount(0);
    }

    @Override
    public String getTooltip() {
        return "Removes all arrows from you";
    }
}
