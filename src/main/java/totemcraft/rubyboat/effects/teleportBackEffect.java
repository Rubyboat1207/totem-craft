package totemcraft.rubyboat.effects;

import com.mojang.serialization.DataResult;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LodestoneTrackerComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.explosion.Explosion;
import org.apache.logging.log4j.Logger;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import totemapi.rubyboat.effects.DeezNuts;
import totemcraft.rubyboat.Main;
import totemcraft.rubyboat.itemComponents.TeleportBackComponent;

public class teleportBackEffect extends DeezNuts{
    @Override
    public void onDeath(LivingEntity user, World world, ItemStack stack) {
        TeleportBackComponent teleportBackComponent = stack.get(Main.TELEPORT_BACK_COMPONENT);
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

        stack.set(Main.TELEPORT_BACK_COMPONENT, teleportBackComponent);

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
