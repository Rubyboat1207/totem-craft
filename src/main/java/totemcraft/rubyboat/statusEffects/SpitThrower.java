package totemcraft.rubyboat.statusEffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class SpitThrower extends StatusEffect {

    public SpitThrower(StatusEffectCategory category) {
        super(category, 0xFFFFF);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(entity.isPlayer())
        {
            ((PlayerEntity) entity).sendMessage(Text.of("Pretend this is llama spit"), true);
        }
        World world = entity.getWorld();

        if (!world.isClient) {
            ArrowEntity llamaSpit = new ArrowEntity(world, entity.getX(), entity.getY() + 2, entity.getZ(), Items.ARROW.getDefaultStack());
            llamaSpit.setVelocity(entity, entity.getPitch(), entity.getYaw(), 0.0F, 1.5F, 1.0F);
            llamaSpit.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
            world.spawnEntity(llamaSpit);
        }
        return true;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % 5 == 0;
    }
}
