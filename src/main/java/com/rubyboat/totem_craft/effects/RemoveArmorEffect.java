package com.rubyboat.totem_craft.effects;

import com.rubyboat.totemapi.effects.TotemEffect;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class RemoveArmorEffect extends TotemEffect {
    @Override
    public void onDeath(LivingEntity user, World world, ItemStack stack) {
        if(user instanceof PlayerEntity player) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (slot.getType() == EquipmentSlot.Type.ARMOR) {
                    ItemStack armorItem = player.getEquippedStack(slot);
                    if (!armorItem.isEmpty()) {
                        player.getInventory().insertStack(armorItem);
                        player.equipStack(slot, ItemStack.EMPTY);
                    }
                }
            }
            player.sendMessage(Text.literal("Your armor has been removed and put into your inventory!"), false);
        }
    }

    @Override
    public String getTooltip() {
        return "Takes off your armor.";
    }
}
