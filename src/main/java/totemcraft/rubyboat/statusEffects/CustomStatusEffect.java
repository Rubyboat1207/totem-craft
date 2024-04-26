package totemcraft.rubyboat.statusEffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.text.Text;

public class CustomStatusEffect extends StatusEffect {
    public CustomStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }
}
