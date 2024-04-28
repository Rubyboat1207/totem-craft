package totemcraft.rubyboat;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentType;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import totemcraft.rubyboat.effects.*;
import totemcraft.rubyboat.itemComponents.TeleportBackComponent;
import totemcraft.rubyboat.statusEffects.CustomStatusEffect;
import totemcraft.rubyboat.statusEffects.SpitThrower;
import totemapi.rubyboat.TotemItem;
import totemapi.rubyboat.effects.*;

import java.util.ArrayList;
import java.util.function.UnaryOperator;

public class Main implements ModInitializer {
    public static String MOD_ID = "totemcraft";
    //StatusEffects
    public static RegistryEntry<StatusEffect> climbEffect = Registry.registerReference(Registries.STATUS_EFFECT, new Identifier(MOD_ID, "climb_effect"),new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0xfcff38));
    public static RegistryEntry<StatusEffect> spitEffect = Registry.registerReference(Registries.STATUS_EFFECT, new Identifier(MOD_ID, "spit_effect"), new SpitThrower(StatusEffectCategory.BENEFICIAL));
    public static RegistryEntry<StatusEffect> lavaWalker = Registry.registerReference(Registries.STATUS_EFFECT, new Identifier(MOD_ID, "lava_walker"),new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x690237));
    public static RegistryEntry<StatusEffect> waterWalker = Registry.registerReference(Registries.STATUS_EFFECT, new Identifier(MOD_ID, "water_walker"),new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x447096));
    public static final RegistryEntry<StatusEffect> BIG = Registry.registerReference(Registries.STATUS_EFFECT, new Identifier(MOD_ID, "big"), new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 16284963)
            .addAttributeModifier(EntityAttributes.GENERIC_SCALE, "f95554de-cfa9-442b-ab5e-d11558fe642d", 4.0, EntityAttributeModifier.Operation.ADD_VALUE)
            .addAttributeModifier(EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE, "c93ff70c-d50d-414a-beb8-d87b5d32aa5b", 4.0, EntityAttributeModifier.Operation.ADD_VALUE)
            .addAttributeModifier(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE, "f95554de-cfa9-442b-ab5e-d11558fe642d", 4.0, EntityAttributeModifier.Operation.ADD_VALUE));
    public static final RegistryEntry<StatusEffect> SMALL = Registry.registerReference(Registries.STATUS_EFFECT, new Identifier(MOD_ID, "small"), new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 16284963)
            .addAttributeModifier(EntityAttributes.GENERIC_SCALE, "f95554de-cfa9-442b-ab5e-d11558fe642d", -0.75, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            .addAttributeModifier(EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE, "c93ff70c-d50d-414a-beb8-d87b5d32aa5b", -0.75, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            .addAttributeModifier(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE, "f95554de-cfa9-442b-ab5e-d11558fe642d", -0.75, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static DataComponentType<TeleportBackComponent> TELEPORT_BACK_COMPONENT = registerComponent("teleport_back", builder -> builder.codec(TeleportBackComponent.CODEC).packetCodec(TeleportBackComponent.PACKET_CODEC).cache());

    private static <T> DataComponentType<T> registerComponent(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, new Identifier(MOD_ID, id), ((DataComponentType.Builder)builderOperator.apply(DataComponentType.builder())).build());
    }

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


    public static final GameRules.Key<GameRules.IntRule> TOTEM_DROP_CHANCE = GameRuleRegistry.register(
            "totemDropChance",
            GameRules.Category.DROPS,
            GameRuleFactory.createIntRule(50, 0, 100)
    );





    public static ArrayList<StatusEffectInstance> witherskeletonEffects = new ArrayList<>();
    public static TotemItem witherskeletonTotem;

    @Override
    public void onInitialize() {



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
        llamaEffects.add(new StatusEffectInstance(Main.spitEffect, 30*20, 0));
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
        pandaEffects.add(new StatusEffectInstance(StatusEffects.RESISTANCE, 15*20, 1));
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
        striderEffects.add(new StatusEffectInstance(Main.lavaWalker, 4*60*20, 0));
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
        allayEffects.add(new StatusEffectInstance(StatusEffects.LEVITATION, 15*20, 0));
        frogEffects.add(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 30*20, 9));
        frogEffects.add(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 40*20, 9));
        snifferEffects.add(new StatusEffectInstance(BIG, 60*20, 0));
        snifferEffects.add(new StatusEffectInstance(StatusEffects.STRENGTH, 60*20, 2));
        tadpoleEffects.add(new StatusEffectInstance(SMALL, 60*20, 0));
        //witch

        //Totem items
        axolotlTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "axolotl_totem"), axolotlEffects, 6, null);
        batTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "bat_totem"), batEffects, 6, new LaunchEffect(2));
        beeTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "bee_totem"), beeEffects, 6, null);
        blazeTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "blaze_totem"), blazeEffects, 8, null);
        catTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "cat_totem"), catEffects, 18, null);
        caveSpiderTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "cave_spider_totem"), caveSpiderEffects, 10, null);
        chickenTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "chicken_totem"), chickenEffects, 2, null);
        codTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "cod_totem"), fishEffects, 2, new giveItem(new ItemStack(Items.COOKED_COD, 1)));
        cowTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "cow_totem"), cowEffects, 2, null);
        creeperTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "creeper_totem"), creeperEffects, 7, new ExplodeEffect(15));
        dolphinTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "dolphin_totem"), fishEffects, 8, null);
        drownedTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "drowned_totem"), drownedEffects, 8, null);
        elderGuardianTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "elder_guardian_totem"), elderGuardianEffects, 8, null);
        endermanTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "enderman_totem"), endereffects, 8, new RandomTeleport(64));
        endermiteTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "endermite_totem"), endereffects, 8, new teleportBackEffect(), true);
        foxTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "fox_totem"), foxEffects, 8, new giveItem(new ItemStack(Items.SWEET_BERRIES, 16)));
        ghastTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "ghast_totem"), ghasteffects, 20, null);
        glowSquidTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "glow_squid_totem"), glowSquidEffects, 20, null);
        goatTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "goat_totem"), goatEffects, 10, new LaunchEffect(1));
        horseTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "horse_totem"), horseEffects, 10, null);
        hoglinTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "hoglin_totem"), hoglinEffects, 10, null);
        guardianTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "guardian_totem"), guardianEffects, 10, null);
        huskTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "husk_totem"), huskEffects, 10, null);
        ironGolemTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "iron_golem_totem"), ironGolemEffects, 20, null);
        llamaTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "llama_totem"), llamaEffects, 6, null);
        magmaCubeTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "magma_cube_totem"), magmaCubeEffects, 10, null);
        mooshroomTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "mooshroom_totem"), mooshroomEffects, 10, null);
        ocelotTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "ocelot_totem"), catEffects, 10, null);
        pandaTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "panda_totem"), pandaEffects, 10, null);
        parrotTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "parrot_totem"), chickenEffects, 10, new LaunchEffect(1));
        phantomTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "phantom_totem"), chickenEffects, 15, new LaunchEffect(15));
        pigTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "pig_totem"), cowEffects, 8, null);
        piglinTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "piglin_totem"), piglinEffects, 8, null);
        piglinBruteTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "piglin_brute_totem"), piglinBruteEffects, 16, null);
        pillagerTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "pillager_totem"), pillagerEffects, 4, null);
        polarBearTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "polar_bear_totem"), polarBearEffects, 4, null);
        pufferfishTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "pufferfish_totem"), pufferfishEffects, 4, null);
        rabbitTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "rabbit_totem"), rabbitEffects, 4, null);
        ravagerTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "ravager_totem"), ravagerEffects, 20, null);
        sheepTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "sheep_totem"), sheepEffects, 6, null);
        shulkerTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "shulker_totem"), shulkerEffects, 10, null);
        silverfishTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "silverfish_totem"), silverfishEffects, 4, null);
        salmonTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "salmon_totem"), fishEffects, 4, null);
        skeletonTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "skeleton_totem"), skeletonEffects, 8, null);
        snowGolemTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "snow_golem_totem"), snowGolemEffects, 4, new FreezeEffect(50));
        spiderTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "spider_totem"), spiderEffects, 4, new FreezeEffect(50));
        squidTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "squid_totem"), squidEffects, 4, new AOEEffect(16, squidAOEEffects));
        strayTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "stray_totem"), strayEffects, 4, new AOEEffect(16, new StatusEffectInstance(StatusEffects.SLOWNESS, 30*20, 2)));
        striderTotem = new TotemItem(new Item.Settings().fireproof(), new Identifier(MOD_ID, "strider_totem"), striderEffects, 8, null);
        coldStriderTotem = new TotemItem(new Item.Settings().fireproof(), new Identifier(MOD_ID, "cold_strider_totem"), coldStriderEffects, 8, null);
        turtleTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "turtle_totem"), turtleEffects, 8, null);
        tropicalFishTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "tropical_fish_totem"), fishEffects, 8, null);
        vexTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "vex_totem"), vexEffects, 8, new LaunchEffect(20));
        villagerTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "villager_totem"), villagerEffects, 16, new giveItem(new ItemStack(Items.EMERALD_BLOCK, 2)));
        wanderingTraderTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "wandering_trader_totem"), villagerEffects, 8, new AndEffect(new giveItem(new ItemStack(Items.EMERALD_BLOCK, 2)), new RandomTeleport(16)));
        witherTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "wither_totem"), witherEffects, 8, new AndEffect(new ExplodeEffect(10), new AOEEffect(50, new StatusEffectInstance(StatusEffects.WITHER, 15*20, 0))));
        wolfTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "wolf_totem"), wolfEffects, 8, null);
        zoglinTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "zoglin_totem"), zoglinEffects, 4, null);
        zombieTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "zombie_totem"), zombieEffects, 6, null);
        zombieVillagerTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "zombie_villager_totem"), zombieEffects, 6, new giveItem(new ItemStack(Items.EMERALD_BLOCK, 2)));
        zombifiedPiglinTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "zombified_piglin_totem"), zombifiedPiglinEffects, 8, null);
        playerTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "player_totem"), playerEffects, 16, null);
        slimeTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "slime_totem"), slimeEffects, 2, new LaunchEffect(4));
        witherskeletonTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "wither_skeleton_totem"), witherskeletonEffects, 2, null);
        wardenTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "warden_totem"), wardenEffects, 60, new AndEffect(new ReplaceBlockEffect(Blocks.GRASS_BLOCK, Blocks.SCULK, 5), new AndEffect(new ReplaceBlockEffect(Blocks.STONE, Blocks.SCULK, 5), new AndEffect(new ReplaceBlockEffect(Blocks.DIRT, Blocks.SCULK, 5), new SculkSpreadEffect(32)))));
        allayTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "allay_totem"), allayEffects, 10, new giveItem(new ItemStack(Items.COOKIE, 3)));
        frogTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "frog_totem"), frogEffects, 5, new LaunchEffect(3));
        witchTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "witch_totem"), new ArrayList<>(), 20, new GiveAllEffects(20 * 5, 0));
        snifferTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "sniffer_totem"), snifferEffects, 20, null);
        tadpoleTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "tadpole_totem"), tadpoleEffects, 8, null);
        camelTotem = new TotemItem(new Item.Settings(), new Identifier(MOD_ID, "camel_totem"), new ArrayList<>(), 8, new DashForwardEffect());
    }
}
