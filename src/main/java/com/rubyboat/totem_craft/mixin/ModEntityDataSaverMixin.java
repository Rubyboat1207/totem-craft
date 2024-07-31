package com.rubyboat.totem_craft.mixin;

import com.rubyboat.totem_craft.TCDataContainer;
import com.rubyboat.totem_craft.TotemCraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class ModEntityDataSaverMixin implements TCDataContainer {

    @Unique
    NbtCompound persistentData;

    @Inject(method = "writeNbt", at = @At("HEAD"))
    protected void writeNbt(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        if(persistentData != null) {
            nbt.put((TotemCraft.MOD_ID + "_data"), persistentData);
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void readNbt(NbtCompound nbt, CallbackInfo ci) {
        if(nbt.contains((TotemCraft.MOD_ID + "_data"), 10)) {
            persistentData = nbt.getCompound((TotemCraft.MOD_ID + "_data"));
        }
    }

    @Override
    public NbtCompound world_border$getPersistentData() {
        if(persistentData == null) {
            persistentData = new NbtCompound();
        }
        return persistentData;
    }
}
