package com.rubyboat.totem_craft.effects;

import com.rubyboat.totemapi.effects.TotemEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GiveItemEffect extends TotemEffect {
    ItemStack item;
    public GiveItemEffect(ItemStack item)
    {
        this.item = item;
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
}
