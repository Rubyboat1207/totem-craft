package com.rubyboat.totem_craft.effects;

import com.rubyboat.totem_craft.TotemCraft;
import com.rubyboat.totem_craft.itemComponents.TeleportBackComponent;
import com.rubyboat.totemapi.effects.TotemEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.Logger;

import java.util.EnumSet;

public class teleportBackEffect extends TotemEffect  {
    @Override
    public void onDeath(LivingEntity user, World world, ItemStack stack) {
        TeleportBackComponent teleportBackComponent = stack.get(TotemCraft.TELEPORT_BACK_COMPONENT);
        if(world.isClient) {
            return;
        }
        if(teleportBackComponent != null)
        {
            var server = world.getServer();
            if(server == null) {
                return;
            }
            var dest = server.getWorld(teleportBackComponent.position().dimension());
            if(dest != null) {
                var pos = teleportBackComponent.position().pos();
                user.teleport(dest, pos.getX(), pos.getY(), pos.getZ(), EnumSet.noneOf(PositionFlag.class), 0.0f, 0.0f);
            }

        }else
        {
            world.createExplosion(user, user.getX(), user.getY(), user.getZ(), 10, World.ExplosionSourceType.MOB);
        }
    }

    @Override
    public ActionResult onClick(LivingEntity user, World world, BlockPos selectedBlock, ItemStack stack) {
        TeleportBackComponent teleportBackComponent = new TeleportBackComponent(GlobalPos.create(world.getRegistryKey(), user.getBlockPos()));

        stack.set(TotemCraft.TELEPORT_BACK_COMPONENT, teleportBackComponent);

        if(user.isPlayer() && world.isClient)
        {
            ((PlayerEntity) user).sendMessage(Text.of("if you die you'll teleport here"), false);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public String getTooltip() {
        return "click a block in the overworld to return to on death";
    }
}
