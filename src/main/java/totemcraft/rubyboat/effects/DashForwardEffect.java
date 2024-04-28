package totemcraft.rubyboat.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import totemapi.rubyboat.effects.DeezNuts;

public class DashForwardEffect extends DeezNuts {

    @Override
    public void onDeath(LivingEntity user, World world, ItemStack stack) {
        user.playSound(SoundEvents.ENTITY_CAMEL_DASH);
        user.addVelocity(Math.cos((user.getYaw() + 90) * (Math.PI / 180)) * 4, 0,  Math.sin((user.getYaw() + 90) * (Math.PI / 180)) * 4);
    }

    @Override
    public String getTooltip() {
        return "Dash Horizontally in the direction you're facing";
    }
}
