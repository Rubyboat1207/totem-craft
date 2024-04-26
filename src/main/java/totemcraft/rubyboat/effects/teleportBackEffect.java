package totemcraft.rubyboat.effects;

import com.mojang.serialization.DataResult;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtOps;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.explosion.Explosion;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.Optional;
import totemlib.rubyboat.effects.DeezNuts;

public class teleportBackEffect extends DeezNuts{
    @Override
    public void onDeath(LivingEntity user, World world, ItemStack stack) {
        NbtCompound compound = stack.getOrCreateNbt();
        if(compound.get("x") != null && compound.get("y") != null && compound.get("z") != null)
        {
            if(world.getRegistryKey() != World.OVERWORLD)
            {
                user.moveToWorld(world.getServer().getWorld(World.OVERWORLD));
            }
            user.teleport(compound.getInt("x"), compound.getInt("y"), compound.getInt("z"));
        }else
        {
            world.createExplosion(user, user.getX(), user.getY(), user.getZ(), 10, Explosion.DestructionType.BREAK);
        }
    }

    @Override
    public ActionResult onClick(LivingEntity user, World world, BlockPos selectedBlock, ItemStack stack) {
        if(world.getRegistryKey() != World.OVERWORLD) {
            if(world.isClient)
            {
                ((PlayerEntity) user).sendMessage(Text.of("You must set your teleport point in the overworld"), false);
            }
            return ActionResult.PASS;
        }

        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putInt("x", selectedBlock.getX() );
        nbt.putInt("y", selectedBlock.getY() + 1);
        nbt.putInt("z", selectedBlock.getZ());
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
