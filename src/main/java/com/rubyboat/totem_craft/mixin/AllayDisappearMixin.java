package com.rubyboat.totem_craft.mixin;

import com.rubyboat.totem_craft.TCDataContainer;
import com.rubyboat.totem_craft.entity.GiveAllayBrain;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.InventoryOwner;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(AllayEntity.class)
public abstract class AllayDisappearMixin extends LivingEntity {

    protected AllayDisappearMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow public abstract Brain<AllayEntity> getBrain();

    @Shadow public abstract SimpleInventory getInventory();
    boolean gavebrain = false;
    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        var data = ((TCDataContainer)this).world_border$getPersistentData();
        var allay = (AllayEntity)(Object)this;
        if(data.getBoolean("disappear")) {
            var player = allay.getWorld().getPlayerByUuid(UUID.fromString(data.getString("gift_player")));
            if(player == null || player.isDead()) {
                this.remove(RemovalReason.DISCARDED);
            }
            if(!gavebrain) {
                this.brain = GiveAllayBrain.create(this.getBrain());
                gavebrain = true;
            }

            if(this.getInventory().getStack(0).isEmpty()) {
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }
}
