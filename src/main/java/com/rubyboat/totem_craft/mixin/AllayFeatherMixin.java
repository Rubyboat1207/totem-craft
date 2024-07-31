package com.rubyboat.totem_craft.mixin;

import com.rubyboat.totem_craft.TotemCraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.entity.passive.ArmadilloEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BrushItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AllayEntity.class)
public class AllayFeatherMixin {

    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    public void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        AllayEntity ent = (AllayEntity) ((Object) this);
        if (player.getStackInHand(hand).getItem() instanceof BrushItem) {
            if(player.isSneaking()) {
                player.getStackInHand(hand).damage(16, player, LivingEntity.getSlotForHand(hand));

                var item = EntityType.ITEM.create(ent.getWorld());

                if(item == null) {
                    return;
                }

                item.setStack(new ItemStack(TotemCraft.allayFeatherItem));
                item.setPos(ent.getX(), ent.getY(), ent.getZ());
                var random = Random.createLocal();
                item.setVelocity(random.nextGaussian() * 0.1, random.nextGaussian() * 0.1, random.nextGaussian() * 0.1);

                ent.getWorld().spawnEntity(item);

                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }
}
