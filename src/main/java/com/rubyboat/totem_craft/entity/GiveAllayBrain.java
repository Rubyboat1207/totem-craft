package com.rubyboat.totem_craft.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.rubyboat.totem_craft.TCDataContainer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.passive.AllayBrain;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class GiveAllayBrain {

    public static Brain<?> create(Brain<AllayEntity> brain) {
        addCoreActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.CORE);
        brain.resetPossibleActivities();
        return brain;
    }

    public static void addCoreActivities(Brain<AllayEntity> brain) {
        brain.setTaskList(Activity.CORE, ImmutableList.of(Pair.of(1, new GiveInventoryToLookTargetTask<>(GiveAllayBrain::getLookTarget, 2.25f, 20))));
    }

    private static Optional<LookTarget> getLookTarget(LivingEntity allay) {
        return Optional.of(new EntityLookTarget(allay.getWorld().getPlayerByUuid(UUID.fromString(((TCDataContainer) allay).world_border$getPersistentData().get("gift_player").asString())), true));
    }
}
