package com.rubyboat.totem_craft.effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.rubyboat.totem_craft.TotemCraft;
import com.rubyboat.totemapi.components.TotemEffectType;
import com.rubyboat.totemapi.effects.TotemEffect;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class ReplaceBlockEffect extends TotemEffect {
    public static final MapCodec<ReplaceBlockEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Block.CODEC.fieldOf("replace").forGetter(ReplaceBlockEffect::getReplace),
                    Block.CODEC.fieldOf("replacement").forGetter(ReplaceBlockEffect::getReplacement),
                    Codec.INT.fieldOf("radius").forGetter(ReplaceBlockEffect::getRadius)
            ).apply(instance, ReplaceBlockEffect::new)
    );

    private final Block replace;
    private final Block replacement;
    private final int radius;

    private Block getReplace() {
        return replace;
    }

    private Block getReplacement() {
        return replacement;
    }

    private int getRadius() {
        return radius;
    }

    public ReplaceBlockEffect(Block replace, Block replacement, int radius) {
        this.replace = replace;
        this.replacement = replacement;
        this.radius = radius;
    }

    @Override
    public void onDeath(LivingEntity user, World world, ItemStack stack) {
        BlockPos pos = user.getBlockPos();
        BlockPos offset1 = new BlockPos(pos).add(-radius,-radius,-radius);
        BlockPos offest2 = new BlockPos(pos).add(radius,radius,radius);
        for(BlockPos pos1 : BlockPos.iterate(offset1, offest2)) {
            if(world.getBlockState(pos1).getBlock() == replace) {
                world.setBlockState(pos1, replacement.getDefaultState());
            }
        }
    }

    @Override
    public String getTooltip() {
        return "Replaces all " + replace.getName().getString() + " with " + replacement.getName().getString() + " in a radius of " + radius;
    }

    @Override
    public TotemEffectType getType() {
        return TotemCraft.REPLACE_BLOCK_EFFECT_TYPE;
    }
}
