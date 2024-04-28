package totemcraft.rubyboat.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.world.World;
import totemapi.rubyboat.effects.DeezNuts;

public class GiveAllEffects extends DeezNuts {
    final int durationTicks;
    final int amplifier;

    public GiveAllEffects(int durationTicks, int amplifier) {
        this.durationTicks = durationTicks;
        this.amplifier = amplifier;
    }

    @Override
    public void onDeath(LivingEntity user, World world, ItemStack stack) {
        for(var effect : Registries.STATUS_EFFECT.getEntrySet()) {
            user.addStatusEffect(new StatusEffectInstance(Registries.STATUS_EFFECT.getEntry(effect.getValue()), durationTicks, amplifier));
        }
    }

    @Override
    public String getTooltip() {
        return "Gives All Affects for " + (durationTicks / 20) + " seconds at level " + (amplifier + 1);
    }
}
