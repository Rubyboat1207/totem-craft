package com.rubyboat.totem_craft.mixin;

import com.rubyboat.totem_craft.TotemCraft;
import net.minecraft.block.BambooBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BambooBlock.class)
public class BambooMixin {

    @Inject(at = @At("HEAD"), method = "getCollisionShape", cancellable = true)
    public void getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if(context instanceof EntityShapeContext entCtx) {
           Entity entity = entCtx.getEntity();

           if(entity == null) {
               return;
           }

           if(entity instanceof LivingEntity livingEntity) {
               if(livingEntity.hasStatusEffect(TotemCraft.verdance)) {
                   if(!context.isAbove(VoxelShapes.fullCube(), pos, true)) {
                       cir.setReturnValue(VoxelShapes.empty());
                   }
               }
           }
        }
    }
}
