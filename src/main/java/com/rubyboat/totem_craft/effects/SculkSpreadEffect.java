package com.rubyboat.totem_craft.effects;

import com.rubyboat.totemapi.effects.TotemEffect;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SculkSpreadEffect extends TotemEffect {

    int charge;

    public SculkSpreadEffect(int charge) {
        this.charge = charge;
    }

    @Override
    public void onDeath(LivingEntity user, World world, ItemStack stack) {
        world.setBlockState(user.getBlockPos().down(), Blocks.SCULK_CATALYST.getDefaultState());
        SculkSpreadManager.create().spread(new BlockPos(user.getBlockPos()), charge);
    }

    @Override
    public String getTooltip() {
        return "Spreads Sculk with a charge of " + charge;
    }
}
