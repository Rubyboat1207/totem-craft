package com.rubyboat.totem_craft.effects;

import com.rubyboat.totemapi.effects.TotemEffect;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

public class DistributePillarsEffect extends TotemEffect {
    private BlockState blockState;
    private int radius;
    private int pillars;
    private int minHeight;
    private int maxHeight;
    private TagKey<Block> placeTag;

    public DistributePillarsEffect(BlockState blockstate, int radius, int pillars, int minHeight, int maxHeight, TagKey<Block> placeTag) {
        this.blockState = blockstate;
        this.radius = radius;
        this.pillars = pillars;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.placeTag = placeTag;
    }

    @Override
    public void onDeath(LivingEntity user, World world, ItemStack stack) {
        HashSet<BlockPos> usedPositions = new HashSet<>();
        int reIterationCount = 0;
        final int maxReIterations = 50;
        for(int i = 0; i < pillars; i++) {
            float angle = world.getRandom().nextFloat() * MathHelper.PI * 2;
            int distance = world.getRandom().nextInt(radius);

            BlockPos pos = new BlockPos(Math.round(MathHelper.cos(angle) * distance) + user.getBlockPos().getX(), user.getBlockPos().getY(), Math.round(MathHelper.sin(angle) * distance)  + user.getBlockPos().getZ());

            if(usedPositions.contains(pos)) {
                i--;
                reIterationCount++;

                if(reIterationCount > maxReIterations) {
                    break;
                }
                continue;
            }

            usedPositions.add(pos);

            while(pos.getY() > world.getBottomY()) {
                if(world.getBlockState(pos.down()).isAir()) {
                    pos = pos.down();
                }else {
                    break;
                }
            }

            if(world.getBlockState(pos.down()).isIn(BlockTags.BAMBOO_PLANTABLE_ON)) {
                int height = world.getRandom().nextBetween(minHeight, maxHeight);

                if(height == 0) {
                    continue;
                }

                for(int y = 0; y < height; y++) {
                    if(!world.getBlockState(pos).isAir()) {
                        break;
                    }

                    world.setBlockState(pos, blockState);
                    pos = pos.up();
                }
            }
        }
    }

    @Override
    public String getTooltip() {
        return String.format("Distributes up to %d pillars of %s within a %d block radius", pillars, blockState.getBlock().getName().getString(), radius);
    }
}
