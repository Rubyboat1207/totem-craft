package com.rubyboat.totem_craft.effects;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.rubyboat.totem_craft.TotemCraft;
import com.rubyboat.totemapi.components.TotemEffectType;
import com.rubyboat.totemapi.effects.TotemEffect;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SculkSpreadEffect extends TotemEffect {
    public final static MapCodec<SculkSpreadEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("charge").forGetter(SculkSpreadEffect::getCharge)
    ).apply(instance, SculkSpreadEffect::new));
    private final int charge;

    public int getCharge() {
        return charge;
    }

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

    @Override
    public TotemEffectType getType() {
        return TotemCraft.SCULK_SPREAD_EFFECT_TYPE;
    }
}
