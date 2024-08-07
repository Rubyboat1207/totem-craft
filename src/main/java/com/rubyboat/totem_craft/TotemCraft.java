package com.rubyboat.totem_craft;

import com.rubyboat.totem_craft.effects.*;
import com.rubyboat.totem_craft.itemComponents.TeleportBackComponent;
import com.rubyboat.totem_craft.statusEffects.CustomStatusEffect;
import com.rubyboat.totem_craft.statusEffects.SpitThrower;
import com.rubyboat.totemapi.TotemItem;
import com.rubyboat.totemapi.components.TotemComponent;
import com.rubyboat.totemapi.components.TotemEffectType;
import com.rubyboat.totemapi.effects.*;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.function.UnaryOperator;

public class TotemCraft implements ModInitializer {
	public static String MOD_ID = "totemcraft";
	//StatusEffects
	public static RegistryEntry<StatusEffect> climbEffect = Registry.registerReference(Registries.STATUS_EFFECT, identifierOf( "climb_effect"),new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0xfcff38));
	public static RegistryEntry<StatusEffect> spitEffect = Registry.registerReference(Registries.STATUS_EFFECT, identifierOf( "spit_effect"), new SpitThrower(StatusEffectCategory.BENEFICIAL));
	public static RegistryEntry<StatusEffect> lavaWalker = Registry.registerReference(Registries.STATUS_EFFECT, identifierOf( "lava_walker"),new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x690237));
	public static RegistryEntry<StatusEffect> waterWalker = Registry.registerReference(Registries.STATUS_EFFECT, identifierOf( "water_walker"),new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x447096));
	public static RegistryEntry<StatusEffect> verdance = Registry.registerReference(Registries.STATUS_EFFECT, identifierOf( "verdance"),new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x32CD32));
	public static final RegistryEntry<StatusEffect> BIG = Registry.registerReference(Registries.STATUS_EFFECT, identifierOf( "big"), new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 16284963)
			.addAttributeModifier(EntityAttributes.GENERIC_SCALE, identifierOf("big_effect_size"), 4.0, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE, identifierOf("big_effect_interaction"), 4.0, EntityAttributeModifier.Operation.ADD_VALUE)
			.addAttributeModifier(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE, identifierOf("big_effect_entity_interaction"), 4.0, EntityAttributeModifier.Operation.ADD_VALUE));
	public static final RegistryEntry<StatusEffect> SMALL = Registry.registerReference(Registries.STATUS_EFFECT, identifierOf( "small"), new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 16284963)
			.addAttributeModifier(EntityAttributes.GENERIC_SCALE, identifierOf("small_effect_size"), -0.75, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
			.addAttributeModifier(EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE, identifierOf("small_effect_size_interaction"), -0.75, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
			.addAttributeModifier(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE, identifierOf("small_effect_size_entity_interaction"), -0.75, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

	public static ComponentType<TeleportBackComponent> TELEPORT_BACK_COMPONENT = registerComponent("teleport_back", builder -> builder.codec(TeleportBackComponent.CODEC).packetCodec(TeleportBackComponent.PACKET_CODEC).cache());

	private static <T> ComponentType<T> registerComponent(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
		return (ComponentType)Registry.register(Registries.DATA_COMPONENT_TYPE, identifierOf(id), ((ComponentType.Builder)builderOperator.apply(ComponentType.builder())).build());
	}

	public static RegistryKey<LootTable> ALLAY_TOTEM_LOOT_TABLE = RegistryKey.of(RegistryKeys.LOOT_TABLE, identifierOf("allay_totem_effect"));

	//totems
	public static ArrayList<StatusEffectInstance> cowEffects = new ArrayList<>();
	public static TotemItem cowTotem;
	public static ArrayList<StatusEffectInstance> axolotlEffects = new ArrayList<>();
	public static TotemItem axolotlTotem;
	public static ArrayList<StatusEffectInstance> batEffects = new ArrayList<>();
	public static TotemItem batTotem;
	public static ArrayList<StatusEffectInstance> beeEffects = new ArrayList<>();
	public static TotemItem beeTotem;
	public static ArrayList<StatusEffectInstance> blazeEffects = new ArrayList<>();
	public static TotemItem blazeTotem;
	public static ArrayList<StatusEffectInstance> catEffects = new ArrayList<>();
	public static TotemItem catTotem;
	public static ArrayList<StatusEffectInstance> caveSpiderEffects = new ArrayList<>();
	public static TotemItem caveSpiderTotem;
	public static ArrayList<StatusEffectInstance> chickenEffects = new ArrayList<>();
	public static TotemItem chickenTotem;
	public static ArrayList<StatusEffectInstance> fishEffects = new ArrayList<>();
	public static TotemItem codTotem;
	public static ArrayList<StatusEffectInstance> creeperEffects = new ArrayList<>();
	public static TotemItem creeperTotem;
	public static TotemItem dolphinTotem;
	public static TotemItem drownedTotem;
	public static ArrayList<StatusEffectInstance> drownedEffects = new ArrayList<>();
	public static TotemItem elderGuardianTotem;
	public static ArrayList<StatusEffectInstance> elderGuardianEffects = new ArrayList<>();
	public static TotemItem endermanTotem;
	public static ArrayList<StatusEffectInstance> endereffects = new ArrayList<>();
	public static TotemItem endermiteTotem;
	public static TotemItem foxTotem;
	public static ArrayList<StatusEffectInstance> foxEffects = new ArrayList<>();
	public static TotemItem ghastTotem;
	public static ArrayList<StatusEffectInstance> ghasteffects = new ArrayList<>();
	public static TotemItem glowSquidTotem;
	public static ArrayList<StatusEffectInstance> glowSquidEffects = new ArrayList<>();
	public static TotemItem goatTotem;
	public static ArrayList<StatusEffectInstance> goatEffects = new ArrayList<>();
	public static TotemItem guardianTotem;
	public static ArrayList<StatusEffectInstance> guardianEffects = new ArrayList<>();
	public static TotemItem hoglinTotem;
	public static ArrayList<StatusEffectInstance> hoglinEffects = new ArrayList<>();
	public static TotemItem horseTotem;
	public static ArrayList<StatusEffectInstance> horseEffects = new ArrayList<>();
	public static TotemItem huskTotem;
	public static ArrayList<StatusEffectInstance> huskEffects = new ArrayList<>();
	public static TotemItem ironGolemTotem;
	public static ArrayList<StatusEffectInstance> ironGolemEffects = new ArrayList<>();
	public static TotemItem llamaTotem;
	public static ArrayList<StatusEffectInstance> llamaEffects = new ArrayList<>();
	public static TotemItem magmaCubeTotem;
	public static ArrayList<StatusEffectInstance> magmaCubeEffects = new ArrayList<>();
	public static TotemItem mooshroomTotem;
	public static ArrayList<StatusEffectInstance> mooshroomEffects = new ArrayList<>();
	public static TotemItem ocelotTotem;
	public static TotemItem pandaTotem;
	public static ArrayList<StatusEffectInstance> pandaEffects = new ArrayList<>();
	public static TotemItem parrotTotem;
	public static TotemItem phantomTotem;
	public static TotemItem pigTotem;
	public static TotemItem piglinTotem;
	public static ArrayList<StatusEffectInstance> piglinEffects = new ArrayList<>();
	public static TotemItem piglinBruteTotem;
	public static ArrayList<StatusEffectInstance> piglinBruteEffects = new ArrayList<>();
	public static TotemItem pillagerTotem;
	public static ArrayList<StatusEffectInstance> pillagerEffects = new ArrayList<>();
	public static TotemItem playerTotem;
	public static ArrayList<StatusEffectInstance> playerEffects = new ArrayList<>();
	public static TotemItem polarBearTotem;
	public static ArrayList<StatusEffectInstance> polarBearEffects = new ArrayList<>();
	public static TotemItem pufferfishTotem;
	public static ArrayList<StatusEffectInstance> pufferfishEffects = new ArrayList<>();
	public static TotemItem rabbitTotem;
	public static ArrayList<StatusEffectInstance> rabbitEffects = new ArrayList<>();
	public static TotemItem ravagerTotem;
	public static ArrayList<StatusEffectInstance> ravagerEffects = new ArrayList<>();
	public static TotemItem salmonTotem;
	public static TotemItem sheepTotem;
	public static ArrayList<StatusEffectInstance> sheepEffects = new ArrayList<>();
	public static TotemItem shulkerTotem;
	public static ArrayList<StatusEffectInstance> shulkerEffects = new ArrayList<>();
	public static TotemItem silverfishTotem;
	public static ArrayList<StatusEffectInstance> silverfishEffects = new ArrayList<>();
	public static TotemItem skeletonTotem;
	public static ArrayList<StatusEffectInstance> skeletonEffects = new ArrayList<>();
	public static TotemItem snowGolemTotem;
	public static ArrayList<StatusEffectInstance> snowGolemEffects = new ArrayList<>();
	public static TotemItem spiderTotem;
	public static ArrayList<StatusEffectInstance> slimeEffects = new ArrayList<>();
	public static TotemItem slimeTotem;
	public static ArrayList<StatusEffectInstance> spiderEffects = new ArrayList<>();
	public static TotemItem squidTotem;
	public static ArrayList<StatusEffectInstance> squidEffects = new ArrayList<>();
	public static ArrayList<StatusEffectInstance> squidAOEEffects = new ArrayList<>();
	public static TotemItem strayTotem;
	public static ArrayList<StatusEffectInstance> strayEffects = new ArrayList<>();
	public static TotemItem striderTotem;
	public static ArrayList<StatusEffectInstance> striderEffects = new ArrayList<>();
	public static TotemItem coldStriderTotem;
	public static ArrayList<StatusEffectInstance> coldStriderEffects = new ArrayList<>();
	public static TotemItem tropicalFishTotem;
	public static TotemItem turtleTotem;
	public static ArrayList<StatusEffectInstance> turtleEffects = new ArrayList<>();
	public static TotemItem vexTotem;
	public static ArrayList<StatusEffectInstance> vexEffects = new ArrayList<>();
	public static TotemItem villagerTotem;
	public static ArrayList<StatusEffectInstance> villagerEffects = new ArrayList<>();
	public static TotemItem wanderingTraderTotem;
	public static ArrayList<StatusEffectInstance> wanderingTraderEffects = new ArrayList<>();
	public static TotemItem witchTotem;
	public static TotemItem witherTotem;
	public static ArrayList<StatusEffectInstance> witherEffects = new ArrayList<>();
	public static TotemItem wolfTotem;
	public static ArrayList<StatusEffectInstance> wolfEffects = new ArrayList<>();
	public static TotemItem zoglinTotem;
	public static ArrayList<StatusEffectInstance> zoglinEffects = new ArrayList<>();
	public static TotemItem zombieTotem;
	public static ArrayList<StatusEffectInstance> zombieEffects = new ArrayList<>();
	public static TotemItem zombieVillagerTotem;
	public static TotemItem zombifiedPiglinTotem;
	public static ArrayList<StatusEffectInstance> zombifiedPiglinEffects = new ArrayList<>();
	public static TotemItem wardenTotem;
	public static ArrayList<StatusEffectInstance> wardenEffects = new ArrayList<>();
	public static TotemItem allayTotem;
	public static ArrayList<StatusEffectInstance> allayEffects = new ArrayList<>();
	public static TotemItem frogTotem;
	public static ArrayList<StatusEffectInstance> frogEffects = new ArrayList<>();
	public static TotemItem snifferTotem;
	public static ArrayList<StatusEffectInstance> snifferEffects = new ArrayList<>();
	public static TotemItem tadpoleTotem;
	public static ArrayList<StatusEffectInstance> tadpoleEffects = new ArrayList<>();
	public static TotemItem camelTotem;
	public static ArrayList<StatusEffectInstance> armadilloEffects = new ArrayList<>();
	public static TotemItem armadilloTotem;

	public static Item allayFeatherItem = new Item(new Item.Settings().maxCount(16));

	public static final TotemEffectType DASH_FORWARD_EFFECT_TYPE = new TotemEffectType(DashForwardEffect.CODEC);
	public static final TotemEffectType DISTRIBUTE_PILLARS_EFFECT_TYPE = new TotemEffectType(DistributePillarsEffect.CODEC);
	public static final TotemEffectType GIVE_ALL_EFFECTS_TYPE = new TotemEffectType(GiveAllEffects.CODEC);
	public static final TotemEffectType GIVE_ITEM_EFFECT_TYPE = new TotemEffectType(GiveItemEffect.CODEC);
	public static final TotemEffectType REMOVE_ARMOR_EFFECT_TYPE = new TotemEffectType(RemoveArmorEffect.CODEC);
	public static final TotemEffectType REMOVE_ARROWS_EFFECT_TYPE = new TotemEffectType(RemoveArrowsEffect.CODEC);
	public static final TotemEffectType REPLACE_BLOCK_EFFECT_TYPE = new TotemEffectType(ReplaceBlockEffect.CODEC);
	public static final TotemEffectType SCULK_SPREAD_EFFECT_TYPE = new TotemEffectType(SculkSpreadEffect.CODEC);
	public static final TotemEffectType TELEPORT_BACK_EFFECT_TYPE = new TotemEffectType(TeleportBackEffect.CODEC);
	public static final TotemEffectType RANDOM_TELEPORT_WITH_PARTICLES = new TotemEffectType(RandomTeleportEffect.CODEC);
	public static final TotemEffectType GIFTING_ALLAY_EFFECT_TYPE = new TotemEffectType(GiftingAllayEffect.CODEC);

	public static final GameRules.Key<GameRules.IntRule> TOTEM_DROP_CHANCE = GameRuleRegistry.register(
			"totemDropChance",
			GameRules.Category.DROPS,
			GameRuleFactory.createIntRule(50, 0, 100)
	);





	public static ArrayList<StatusEffectInstance> witherskeletonEffects = new ArrayList<>();
	public static TotemItem witherskeletonTotem;

	@Override
	public void onInitialize() {
		GiftingAllayEffect.addDebugCommand();


		//c Effects
		cowEffects.add(new StatusEffectInstance(StatusEffects.REGENERATION, 60, 0));
		axolotlEffects.add(new StatusEffectInstance(StatusEffects.SLOWNESS, 10*20, 0));
		axolotlEffects.add(new StatusEffectInstance(StatusEffects.INVISIBILITY, 15*20, 0));
		axolotlEffects.add(new StatusEffectInstance(StatusEffects.REGENERATION, 20*20, 2));
		batEffects.add(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 20*10, 0));
		beeEffects.add(new StatusEffectInstance(StatusEffects.LEVITATION, 5*20, 0));
		blazeEffects.add(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 10*20, 0));
		blazeEffects.add(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 10*20, 0));
		blazeEffects.add(new StatusEffectInstance(StatusEffects.ABSORPTION, 30*20, 2));
		catEffects.add(new StatusEffectInstance(StatusEffects.SPEED, 20*20, 2));
		catEffects.add(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 5*20, 0));
		caveSpiderEffects.add(new StatusEffectInstance(climbEffect, 60*20, 0));
		chickenEffects.add(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 30*20, 0));
		fishEffects.add(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 10*20, 0));
		fishEffects.add(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 10*20, 1));
		creeperEffects.add(new StatusEffectInstance(StatusEffects.RESISTANCE, 20*20, 1));
		drownedEffects.addAll(fishEffects);
		drownedEffects.add(new StatusEffectInstance(StatusEffects.RESISTANCE, 10*20, 1));
		elderGuardianEffects.add(new StatusEffectInstance(StatusEffects.HASTE, 90*20, 2));
		elderGuardianEffects.add(new StatusEffectInstance(StatusEffects.ABSORPTION, 120*20, 2));
		elderGuardianEffects.add(new StatusEffectInstance(StatusEffects.RESISTANCE, 60*20, 1));
		endereffects.add(new StatusEffectInstance(StatusEffects.ABSORPTION, 30*20, 0));
		foxEffects.add(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 30*20, 1));
		foxEffects.add(new StatusEffectInstance(StatusEffects.SPEED, 30*20, 1));
		ghasteffects.add(new StatusEffectInstance(StatusEffects.LEVITATION, 10*20, 1));
		ghasteffects.add(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 30*20, 1));
		glowSquidEffects.add(new StatusEffectInstance(StatusEffects.GLOWING, 60*20, 1));
		goatEffects.add(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 45*20, 2));
		guardianEffects.add(new StatusEffectInstance(StatusEffects.HASTE, 45*20, 2));
		hoglinEffects.add(new StatusEffectInstance(StatusEffects.RESISTANCE, 30*20, 1));
		horseEffects.add(new StatusEffectInstance(StatusEffects.SPEED, 60*20, 1));
		horseEffects.add(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 60*20, 1));
		huskEffects.add(new StatusEffectInstance(StatusEffects.ABSORPTION, 60*20, 4));
		huskEffects.add(new StatusEffectInstance(StatusEffects.HUNGER, 60*20, 2));
		ironGolemEffects.add(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 60*20, 1));
		ironGolemEffects.add(new StatusEffectInstance(StatusEffects.RESISTANCE, 60*20, 1));
		ironGolemEffects.add(new StatusEffectInstance(StatusEffects.REGENERATION, 60*20, 0));
		llamaEffects.add(new StatusEffectInstance(TotemCraft.spitEffect, 30*20, 0));
		magmaCubeEffects.add(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 30*20, 0));
		magmaCubeEffects.add(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 60*20, 0));
		mooshroomEffects.add(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 2*20, 0));
		mooshroomEffects.add(new StatusEffectInstance(StatusEffects.BLINDNESS, 6*20, 0));
		mooshroomEffects.add(new StatusEffectInstance(StatusEffects.SATURATION, 20, 0));
		mooshroomEffects.add(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 4*20, 0));
		mooshroomEffects.add(new StatusEffectInstance(StatusEffects.POISON, 10*20, 0));
		mooshroomEffects.add(new StatusEffectInstance(StatusEffects.REGENERATION, 6*20, 0));
		mooshroomEffects.add(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 4*20, 0));
		mooshroomEffects.add(new StatusEffectInstance(StatusEffects.WEAKNESS, 7*20, 0));
		mooshroomEffects.add(new StatusEffectInstance(StatusEffects.WITHER, 6*20, 0));
		pandaEffects.add(new StatusEffectInstance(StatusEffects.SLOWNESS, 30*20, 0));
		pandaEffects.add(new StatusEffectInstance(verdance, 20*20, 0));
		piglinEffects.add(new StatusEffectInstance(StatusEffects.STRENGTH, 15*20, 0));
		piglinBruteEffects.add(new StatusEffectInstance(StatusEffects.STRENGTH, 15*20, 2));
		pillagerEffects.add(new StatusEffectInstance(StatusEffects.BAD_OMEN, 99999999*20, 4));
		polarBearEffects.add(new StatusEffectInstance(StatusEffects.SLOWNESS, 30*20, 0));
		polarBearEffects.add(new StatusEffectInstance(StatusEffects.REGENERATION, 30*20, 1));
		pufferfishEffects.addAll(fishEffects);
		pufferfishEffects.add(new StatusEffectInstance(StatusEffects.REGENERATION, 30*20, 0));
		rabbitEffects.add(new StatusEffectInstance(StatusEffects.SPEED, 30*20, 2));
		rabbitEffects.add(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 30*20, 5));
		ravagerEffects.add(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 2*60*20, 3));
		ravagerEffects.add(new StatusEffectInstance(StatusEffects.RESISTANCE, 2*60*20, 0));
		sheepEffects.add(new StatusEffectInstance(StatusEffects.SATURATION, 10, 0));
		shulkerEffects.add(new StatusEffectInstance(StatusEffects.LEVITATION, 30*20, 0));
		shulkerEffects.add(new StatusEffectInstance(StatusEffects.REGENERATION, 60*20, 1));
		silverfishEffects.add(new StatusEffectInstance(StatusEffects.SPEED, 30*20, 3));
		skeletonEffects.add(new StatusEffectInstance(StatusEffects.SATURATION, 10*20, 2));
		skeletonEffects.add(new StatusEffectInstance(StatusEffects.ABSORPTION, 30*20, 2));
		snowGolemEffects.add(new StatusEffectInstance(StatusEffects.REGENERATION, 30*20, 2));
		snowGolemEffects.add(new StatusEffectInstance(StatusEffects.STRENGTH, 30*20, 2));
		spiderEffects.add(new StatusEffectInstance(climbEffect, 30*20, 0));
		squidAOEEffects.add(new StatusEffectInstance(StatusEffects.BLINDNESS, 30*20, 0));
		squidAOEEffects.add(new StatusEffectInstance(StatusEffects.SLOWNESS, 30*20, 0));
		strayEffects.add(new StatusEffectInstance(StatusEffects.SLOWNESS, 30*20, 0));
		strayEffects.add(new StatusEffectInstance(StatusEffects.RESISTANCE, 30*20, 1));
		striderEffects.add(new StatusEffectInstance(TotemCraft.lavaWalker, 4*60*20, 0));
		turtleEffects.add(new StatusEffectInstance(StatusEffects.RESISTANCE, 60*20, 1));
		turtleEffects.add(new StatusEffectInstance(StatusEffects.SLOWNESS, 60*20, 1));
		turtleEffects.add(new StatusEffectInstance(StatusEffects.CONDUIT_POWER, 60*20, 1));
		vexEffects.add(new StatusEffectInstance(StatusEffects.LEVITATION, 10*20, 1));
		vexEffects.add(new StatusEffectInstance(StatusEffects.REGENERATION, 30*20, 0));
		villagerEffects.add(new StatusEffectInstance(StatusEffects.HERO_OF_THE_VILLAGE, 60*20, 2));
		wanderingTraderEffects.addAll(villagerEffects);
		wanderingTraderEffects.add(new StatusEffectInstance(StatusEffects.INVISIBILITY, 12*60*20, 0));
		wanderingTraderEffects.addAll(villagerEffects);
		wanderingTraderEffects.add(new StatusEffectInstance(StatusEffects.INVISIBILITY, 12*60*20, 0));
		wolfEffects.add(new StatusEffectInstance(StatusEffects.REGENERATION, 30*20, 0));
		wolfEffects.add(new StatusEffectInstance(StatusEffects.WEAKNESS, 30*20, 0));
		zoglinEffects.add(new StatusEffectInstance(StatusEffects.STRENGTH, 15*20, 4));
		zombieEffects.add(new StatusEffectInstance(StatusEffects.REGENERATION, 15*20, 0));
		zombifiedPiglinEffects.add(new StatusEffectInstance(StatusEffects.STRENGTH, 15*20, 0));
		zombifiedPiglinEffects.add(new StatusEffectInstance(StatusEffects.SPEED, 15*20, 0));
		playerEffects.add(new StatusEffectInstance(StatusEffects.SPEED, 30*20, 1));
		playerEffects.add(new StatusEffectInstance(StatusEffects.STRENGTH, 15*20, 1));
		slimeEffects.add(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 15*20, 1));
		witherskeletonEffects.add(new StatusEffectInstance(StatusEffects.SPEED, 15*20, 1));
		coldStriderEffects.add(new StatusEffectInstance(waterWalker, 4*60*20, 0));
		wardenEffects.add(new StatusEffectInstance(StatusEffects.STRENGTH, 60*20, 9));
		wardenEffects.add(new StatusEffectInstance(StatusEffects.SLOWNESS, 60*20, 0));
		wardenEffects.add(new StatusEffectInstance(StatusEffects.DARKNESS, 60*20, 0));
		wardenEffects.add(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 3*60*20, 4));
		wardenEffects.add(new StatusEffectInstance(StatusEffects.REGENERATION, 3*60*20, 3));
		allayEffects.add(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 15*20, 0));
		frogEffects.add(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 30*20, 9));
		frogEffects.add(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 40*20, 9));
		snifferEffects.add(new StatusEffectInstance(BIG, 60*20, 0));
		snifferEffects.add(new StatusEffectInstance(StatusEffects.STRENGTH, 60*20, 2));
		tadpoleEffects.add(new StatusEffectInstance(SMALL, 60*20, 0));
		armadilloEffects.add(new StatusEffectInstance(StatusEffects.RESISTANCE, 60*20, 1));
		//witch

		//Totem items
		axolotlTotem = new TotemItem(new Item.Settings(), identifierOf( "axolotl_totem"), axolotlEffects, 6, new AndEffect(new RemoveArmorEffect(), new RemoveArrowsEffect()));
		batTotem = new TotemItem(new Item.Settings(), identifierOf( "bat_totem"), batEffects, 6, new LaunchEffect(2));
		beeTotem = new TotemItem(new Item.Settings(), identifierOf( "bee_totem"), beeEffects, 6, null);
		blazeTotem = new TotemItem(new Item.Settings(), identifierOf( "blaze_totem"), blazeEffects, 8, null);
		catTotem = new TotemItem(new Item.Settings(), identifierOf( "cat_totem"), catEffects, 10, new RandomTeleportWithParticles(16));
		caveSpiderTotem = new TotemItem(new Item.Settings(), identifierOf( "cave_spider_totem"), caveSpiderEffects, 10, null);
		chickenTotem = new TotemItem(new Item.Settings(), identifierOf( "chicken_totem"), chickenEffects, 2, null);
		codTotem = new TotemItem(new Item.Settings(), identifierOf( "cod_totem"), fishEffects, 2, new GiveItemEffect(new ItemStack(Items.COOKED_COD, 1)));
		cowTotem = new TotemItem(new Item.Settings(), identifierOf( "cow_totem"), cowEffects, 2, null);
		creeperTotem = new TotemItem(new Item.Settings(), identifierOf( "creeper_totem"), creeperEffects, 7, new ExplodeEffect(15));
		dolphinTotem = new TotemItem(new Item.Settings(), identifierOf( "dolphin_totem"), fishEffects, 8, null);
		drownedTotem = new TotemItem(new Item.Settings(), identifierOf( "drowned_totem"), drownedEffects, 8, null);
		elderGuardianTotem = new TotemItem(new Item.Settings(), identifierOf( "elder_guardian_totem"), elderGuardianEffects, 8, null);
		endermanTotem = new TotemItem(new Item.Settings(), identifierOf( "enderman_totem"), endereffects, 8, new RandomTeleportEffect(64));
		endermiteTotem = new TotemItem(new Item.Settings(), identifierOf( "endermite_totem"), endereffects, 8, new TeleportBackEffect(), true);
		foxTotem = new TotemItem(new Item.Settings(), identifierOf( "fox_totem"), foxEffects, 8, new GiveItemEffect(new ItemStack(Items.SWEET_BERRIES, 16)));
		ghastTotem = new TotemItem(new Item.Settings(), identifierOf( "ghast_totem"), ghasteffects, 20, null);
		glowSquidTotem = new TotemItem(new Item.Settings(), identifierOf( "glow_squid_totem"), glowSquidEffects, 20, null);
		goatTotem = new TotemItem(new Item.Settings(), identifierOf( "goat_totem"), goatEffects, 10, new LaunchEffect(1));
		horseTotem = new TotemItem(new Item.Settings(), identifierOf( "horse_totem"), horseEffects, 10, null);
		hoglinTotem = new TotemItem(new Item.Settings(), identifierOf( "hoglin_totem"), hoglinEffects, 10, null);
		guardianTotem = new TotemItem(new Item.Settings(), identifierOf( "guardian_totem"), guardianEffects, 10, null);
		huskTotem = new TotemItem(new Item.Settings(), identifierOf( "husk_totem"), huskEffects, 10, null);
		ironGolemTotem = new TotemItem(new Item.Settings(), identifierOf( "iron_golem_totem"), ironGolemEffects, 20, null);
		llamaTotem = new TotemItem(new Item.Settings(), identifierOf( "llama_totem"), llamaEffects, 6, null);
		magmaCubeTotem = new TotemItem(new Item.Settings(), identifierOf( "magma_cube_totem"), magmaCubeEffects, 10, null);
		mooshroomTotem = new TotemItem(new Item.Settings(), identifierOf( "mooshroom_totem"), mooshroomEffects, 10, null);
		ocelotTotem = new TotemItem(new Item.Settings(), identifierOf( "ocelot_totem"), catEffects, 10, null);
		pandaTotem = new TotemItem(new Item.Settings(), identifierOf( "panda_totem"), pandaEffects, 10, new DistributePillarsEffect(Blocks.BAMBOO.getDefaultState(), 10, 50, 0, 15, BlockTags.BAMBOO_PLANTABLE_ON));
		parrotTotem = new TotemItem(new Item.Settings(), identifierOf( "parrot_totem"), chickenEffects, 10, new LaunchEffect(1));
		phantomTotem = new TotemItem(new Item.Settings(), identifierOf( "phantom_totem"), chickenEffects, 15, new LaunchEffect(15));
		pigTotem = new TotemItem(new Item.Settings(), identifierOf( "pig_totem"), cowEffects, 8, null);
		piglinTotem = new TotemItem(new Item.Settings(), identifierOf( "piglin_totem"), piglinEffects, 8, null);
		piglinBruteTotem = new TotemItem(new Item.Settings(), identifierOf( "piglin_brute_totem"), piglinBruteEffects, 16, null);
		pillagerTotem = new TotemItem(new Item.Settings(), identifierOf( "pillager_totem"), pillagerEffects, 4, null);
		polarBearTotem = new TotemItem(new Item.Settings(), identifierOf( "polar_bear_totem"), polarBearEffects, 4, null);
		pufferfishTotem = new TotemItem(new Item.Settings(), identifierOf( "pufferfish_totem"), pufferfishEffects, 4, null);
		rabbitTotem = new TotemItem(new Item.Settings(), identifierOf( "rabbit_totem"), rabbitEffects, 4, null);
		ravagerTotem = new TotemItem(new Item.Settings(), identifierOf( "ravager_totem"), ravagerEffects, 20, null);
		sheepTotem = new TotemItem(new Item.Settings(), identifierOf( "sheep_totem"), sheepEffects, 6, null);
		shulkerTotem = new TotemItem(new Item.Settings(), identifierOf( "shulker_totem"), shulkerEffects, 10, null);
		silverfishTotem = new TotemItem(new Item.Settings(), identifierOf( "silverfish_totem"), silverfishEffects, 4, null);
		salmonTotem = new TotemItem(new Item.Settings(), identifierOf( "salmon_totem"), fishEffects, 4, null);
		skeletonTotem = new TotemItem(new Item.Settings(), identifierOf( "skeleton_totem"), skeletonEffects, 8, null);
		snowGolemTotem = new TotemItem(new Item.Settings(), identifierOf( "snow_golem_totem"), snowGolemEffects, 4, new FreezeEffect(50));
		spiderTotem = new TotemItem(new Item.Settings(), identifierOf( "spider_totem"), spiderEffects, 4, new FreezeEffect(50));
		squidTotem = new TotemItem(new Item.Settings(), identifierOf( "squid_totem"), squidEffects, 4, new AOEEffect(16, squidAOEEffects));
		strayTotem = new TotemItem(new Item.Settings(), identifierOf( "stray_totem"), strayEffects, 4, new AOEEffect(16, new StatusEffectInstance(StatusEffects.SLOWNESS, 30*20, 2)));
		striderTotem = new TotemItem(new Item.Settings().fireproof(), identifierOf( "strider_totem"), striderEffects, 8, null);
		coldStriderTotem = new TotemItem(new Item.Settings().fireproof(), identifierOf( "cold_strider_totem"), coldStriderEffects, 8, null);
		turtleTotem = new TotemItem(new Item.Settings(), identifierOf( "turtle_totem"), turtleEffects, 8, null);
		tropicalFishTotem = new TotemItem(new Item.Settings(), identifierOf( "tropical_fish_totem"), fishEffects, 8, null);
		vexTotem = new TotemItem(new Item.Settings(), identifierOf( "vex_totem"), vexEffects, 8, new LaunchEffect(20));
		villagerTotem = new TotemItem(new Item.Settings(), identifierOf( "villager_totem"), villagerEffects, 16, new GiveItemEffect(new ItemStack(Items.EMERALD_BLOCK, 2)));
		wanderingTraderTotem = new TotemItem(new Item.Settings(), identifierOf( "wandering_trader_totem"), villagerEffects, 8, new AndEffect(new GiveItemEffect(new ItemStack(Items.EMERALD_BLOCK, 2)), new RandomTeleportEffect(16)));
		witherTotem = new TotemItem(new Item.Settings(), identifierOf( "wither_totem"), witherEffects, 8, new AndEffect(new ExplodeEffect(10), new AOEEffect(50, new StatusEffectInstance(StatusEffects.WITHER, 15*20, 0))));
		wolfTotem = new TotemItem(new Item.Settings(), identifierOf( "wolf_totem"), wolfEffects, 8, null);
		zoglinTotem = new TotemItem(new Item.Settings(), identifierOf( "zoglin_totem"), zoglinEffects, 4, null);
		zombieTotem = new TotemItem(new Item.Settings(), identifierOf( "zombie_totem"), zombieEffects, 6, null);
		zombieVillagerTotem = new TotemItem(new Item.Settings(), identifierOf( "zombie_villager_totem"), zombieEffects, 6, new GiveItemEffect(new ItemStack(Items.EMERALD_BLOCK, 2)));
		zombifiedPiglinTotem = new TotemItem(new Item.Settings(), identifierOf( "zombified_piglin_totem"), zombifiedPiglinEffects, 8, null);
		playerTotem = new TotemItem(new Item.Settings(), identifierOf( "player_totem"), playerEffects, 16, null);
		slimeTotem = new TotemItem(new Item.Settings(), identifierOf( "slime_totem"), slimeEffects, 2, new LaunchEffect(4));
		witherskeletonTotem = new TotemItem(new Item.Settings(), identifierOf( "wither_skeleton_totem"), witherskeletonEffects, 2, null);
		wardenTotem = new TotemItem(new Item.Settings(), identifierOf( "warden_totem"), wardenEffects, 60, new AndEffect(new ReplaceBlockEffect(Blocks.GRASS_BLOCK, Blocks.SCULK, 5), new AndEffect(new ReplaceBlockEffect(Blocks.STONE, Blocks.SCULK, 5), new AndEffect(new ReplaceBlockEffect(Blocks.DIRT, Blocks.SCULK, 5), new SculkSpreadEffect(32)))));
		allayTotem = new TotemItem(new Item.Settings(), identifierOf( "allay_totem"), allayEffects, 10, new GiftingAllayEffect(ALLAY_TOTEM_LOOT_TABLE));
		frogTotem = new TotemItem(new Item.Settings(), identifierOf( "frog_totem"), frogEffects, 5, new LaunchEffect(3));
		witchTotem = new TotemItem(new Item.Settings(), identifierOf( "witch_totem"), new ArrayList<>(), 20, new GiveAllEffects(20 * 5, 0));
		snifferTotem = new TotemItem(new Item.Settings(), identifierOf( "sniffer_totem"), snifferEffects, 20, null);
		tadpoleTotem = new TotemItem(new Item.Settings(), identifierOf( "tadpole_totem"), tadpoleEffects, 8, null);
		camelTotem = new TotemItem(new Item.Settings(), identifierOf( "camel_totem"), new ArrayList<>(), 8, new DashForwardEffect(4));
		armadilloTotem = new TotemItem(new Item.Settings(), identifierOf( "armadillo_totem"), armadilloEffects, 20, null);

		Registry.register(Registries.ITEM, identifierOf( "allay_feather"), allayFeatherItem);


		Registry.register(TotemComponent.EFFECTS, identifierOf( "dash_forward"), DASH_FORWARD_EFFECT_TYPE);
		Registry.register(TotemComponent.EFFECTS, identifierOf( "distribute_pillars"), DISTRIBUTE_PILLARS_EFFECT_TYPE);
		Registry.register(TotemComponent.EFFECTS, identifierOf( "give_all_effects"), GIVE_ALL_EFFECTS_TYPE);
		Registry.register(TotemComponent.EFFECTS, identifierOf( "give_item"), GIVE_ITEM_EFFECT_TYPE);
		Registry.register(TotemComponent.EFFECTS, identifierOf( "remove_armor"), REMOVE_ARMOR_EFFECT_TYPE);
		Registry.register(TotemComponent.EFFECTS, identifierOf( "remove_arrows"), REMOVE_ARROWS_EFFECT_TYPE);
		Registry.register(TotemComponent.EFFECTS, identifierOf( "replace_block"), REPLACE_BLOCK_EFFECT_TYPE);
		Registry.register(TotemComponent.EFFECTS, identifierOf( "sculk_spread"), SCULK_SPREAD_EFFECT_TYPE);
		Registry.register(TotemComponent.EFFECTS, identifierOf( "teleport_back"), TELEPORT_BACK_EFFECT_TYPE);
		Registry.register(TotemComponent.EFFECTS, identifierOf( "random_teleport_with_particles"), RANDOM_TELEPORT_WITH_PARTICLES);
		Registry.register(TotemComponent.EFFECTS, identifierOf( "gift_allay"), GIFTING_ALLAY_EFFECT_TYPE);
	}

	public static ItemStack GetTotemFromEntity(Entity ent)
	{
		//instanceof patch
		if(ent instanceof ZombieVillagerEntity) { return new ItemStack(TotemCraft.zombieVillagerTotem, 1); }
		if(ent instanceof ZombifiedPiglinEntity) { return new ItemStack(TotemCraft.zombifiedPiglinTotem, 1); }
		if(ent instanceof MooshroomEntity) { return new ItemStack(TotemCraft.mooshroomTotem, 1); }
		if(ent instanceof StrayEntity) { return new ItemStack(TotemCraft.strayTotem, 1); }
		if(ent instanceof WardenEntity) { return new ItemStack(TotemCraft.wardenTotem, 1); }


		if(ent instanceof CowEntity) { return new ItemStack(TotemCraft.cowTotem, 1); }
		if(ent instanceof AxolotlEntity) { return new ItemStack(TotemCraft.axolotlTotem, 1); }
		if(ent instanceof BatEntity) { return new ItemStack(TotemCraft.batTotem, 1); }
		if(ent instanceof BeeEntity) { return new ItemStack(TotemCraft.beeTotem, 1); }
		if(ent instanceof BlazeEntity) { return new ItemStack(TotemCraft.blazeTotem, 1); }
		if(ent instanceof CatEntity) { return new ItemStack(TotemCraft.catTotem, 1); }
		if(ent instanceof ChickenEntity) { return new ItemStack(TotemCraft.chickenTotem, 1); }
		if(ent instanceof CaveSpiderEntity) { return new ItemStack(TotemCraft.caveSpiderTotem, 1); }
		if(ent instanceof CodEntity) { return new ItemStack(TotemCraft.codTotem, 1); }
		if(ent instanceof CreeperEntity) { return new ItemStack(TotemCraft.creeperTotem, 1); }
		if(ent instanceof DrownedEntity) { return new ItemStack(TotemCraft.drownedTotem, 1); }
		if(ent instanceof DolphinEntity) { return new ItemStack(TotemCraft.dolphinTotem, 1); }
		if(ent instanceof EndermanEntity) { return new ItemStack(TotemCraft.endermanTotem, 1); }
		if(ent instanceof ElderGuardianEntity) { return new ItemStack(TotemCraft.elderGuardianTotem, 1); }
		if(ent instanceof EndermiteEntity) { return new ItemStack(TotemCraft.endermiteTotem, 1); }
		if(ent instanceof FoxEntity) { return new ItemStack(TotemCraft.foxTotem, 1); }
		if(ent instanceof GhastEntity) { return new ItemStack(TotemCraft.ghastTotem, 1); }
		if(ent instanceof GlowSquidEntity) { return new ItemStack(TotemCraft.glowSquidTotem, 1); }
		if(ent instanceof SquidEntity) { return new ItemStack(TotemCraft.squidTotem, 1); }
		if(ent instanceof TraderLlamaEntity) { return new ItemStack(TotemCraft.llamaTotem, 1); }
		if(ent instanceof GoatEntity) { return new ItemStack(TotemCraft.goatTotem, 1); }
		if(ent instanceof GuardianEntity) { return new ItemStack(TotemCraft.guardianTotem, 1); }
		if(ent instanceof HoglinEntity) { return new ItemStack(TotemCraft.hoglinTotem, 1); }
		if(ent instanceof HorseEntity) { return new ItemStack(TotemCraft.horseTotem, 1); }
		if(ent instanceof HuskEntity) { return new ItemStack(TotemCraft.huskTotem, 1); }
		if(ent instanceof SkeletonEntity) { return new ItemStack(TotemCraft.skeletonTotem, 1); }
		if(ent instanceof IronGolemEntity) { return new ItemStack(TotemCraft.ironGolemTotem, 1); }
		if(ent instanceof LlamaEntity) { return new ItemStack(TotemCraft.llamaTotem, 1); }
		if(ent instanceof MagmaCubeEntity) { return new ItemStack(TotemCraft.magmaCubeTotem, 1); }
		if(ent instanceof OcelotEntity) { return new ItemStack(TotemCraft.ocelotTotem, 1); }
		if(ent instanceof PandaEntity) { return new ItemStack(TotemCraft.pandaTotem, 1); }
		if(ent instanceof ParrotEntity) { return new ItemStack(TotemCraft.parrotTotem, 1); }
		if(ent instanceof PhantomEntity) { return new ItemStack(TotemCraft.phantomTotem, 1); }
		if(ent instanceof PigEntity) { return new ItemStack(TotemCraft.pigTotem, 1); }
		if(ent instanceof PiglinEntity) { return new ItemStack(TotemCraft.piglinTotem, 1); }
		if(ent instanceof PiglinBruteEntity) { return new ItemStack(TotemCraft.piglinBruteTotem, 1); }
		if(ent instanceof PillagerEntity) { return new ItemStack(TotemCraft.pillagerTotem, 1); }
		if(ent instanceof PolarBearEntity) { return new ItemStack(TotemCraft.polarBearTotem, 1); }
		if(ent instanceof PufferfishEntity) { return new ItemStack(TotemCraft.pufferfishTotem, 1); }
		if(ent instanceof RabbitEntity) { return new ItemStack(TotemCraft.rabbitTotem, 1); }
		if(ent instanceof RavagerEntity) { return new ItemStack(TotemCraft.ravagerTotem, 1); }
		if(ent instanceof SheepEntity) { return new ItemStack(TotemCraft.sheepTotem, 1); }
		if(ent instanceof ShulkerEntity) { return new ItemStack(TotemCraft.shulkerTotem, 1); }
		if(ent instanceof SalmonEntity) { return new ItemStack(TotemCraft.salmonTotem, 1); }
		if(ent instanceof SilverfishEntity) { return new ItemStack(TotemCraft.silverfishTotem, 1); }
		if(ent instanceof SpiderEntity) { return new ItemStack(TotemCraft.spiderTotem, 1); }
		if(ent instanceof SnowGolemEntity) { return new ItemStack(TotemCraft.snowGolemTotem, 1); }
		if(ent instanceof SlimeEntity) { return new ItemStack(TotemCraft.slimeTotem, 1); }
		if(ent instanceof StriderEntity) { return ((StriderEntity) ent).isCold() ? new ItemStack(TotemCraft.coldStriderTotem, 1) : new ItemStack(TotemCraft.striderTotem, 1); }
		if(ent instanceof TropicalFishEntity) { return new ItemStack(TotemCraft.tropicalFishTotem, 1); }
		if(ent instanceof TurtleEntity) { return new ItemStack(TotemCraft.turtleTotem, 1); }
		if(ent instanceof VexEntity) { return new ItemStack(TotemCraft.vexTotem, 1); }
		if(ent instanceof VillagerEntity) { return new ItemStack(TotemCraft.villagerTotem, 1); }
		if(ent instanceof WitherEntity) { return new ItemStack(TotemCraft.witherTotem, 1); }
		if(ent instanceof WolfEntity) { return new ItemStack(TotemCraft.wolfTotem, 1); }
		if(ent instanceof ZoglinEntity) { return new ItemStack(TotemCraft.zoglinTotem, 1); }
		if(ent instanceof ZombieEntity) { return new ItemStack(TotemCraft.zombieTotem, 1); }
		if(ent instanceof WitherSkeletonEntity) { return new ItemStack(TotemCraft.witherskeletonTotem, 1); }
		if(ent instanceof WanderingTraderEntity) { return new ItemStack(TotemCraft.wanderingTraderTotem, 1); }
		if(ent instanceof WitchEntity) { return new ItemStack(TotemCraft.witchTotem, 1); }
		if(ent instanceof FrogEntity) { return new ItemStack(TotemCraft.frogTotem, 1); }
		if(ent instanceof SnifferEntity) { return new ItemStack(TotemCraft.snifferTotem, 1); }
		if(ent instanceof ArmadilloEntity) { return new ItemStack(TotemCraft.armadilloTotem, 1); }
		if(ent.isPlayer()) { return new ItemStack(TotemCraft.playerTotem, 1); }

		return null;
	}

	@Unique
	public static void SpawnItemAtEntity(LivingEntity livingEntity, @Nullable ItemStack stack)
	{
		if(stack != null)
		{
			World world = livingEntity.getWorld();
			Entity itemEntity = new ItemEntity(world, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), stack);
			world.spawnEntity(itemEntity);
		}
	}

	public static Identifier identifierOf(String path) {
		return Identifier.of(MOD_ID, path);
	}
}