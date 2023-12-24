package net.ghosty.unchartedarsenal.world.capabilities.item;

import java.util.function.Function;

import net.ghosty.unchartedarsenal.UnchartedArsenal;
import net.ghosty.unchartedarsenal.gameasset.UAAnimations;
import net.ghosty.unchartedarsenal.colliders.UAColliders;
import net.ghosty.unchartedarsenal.gameasset.UASkills;
import net.ghosty.unchartedarsenal.skill.weaponinnate.SunsWrathSkill;
import net.ghosty.unchartedarsenal.world.item.UAItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Interaction;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

@Mod.EventBusSubscriber(modid = UnchartedArsenal.MOD_ID , bus = EventBusSubscriber.Bus.MOD)
public class UAWeaponCapabilityPresets {
    public static final Function<Item, CapabilityItem.Builder> PHARAOH_CURSE = (item) -> {
        CapabilityItem.Builder builder = WeaponCapability.builder()
                .category(UAWeaponCategories.PHARAOH_CURSE)
                .styleProvider((entitypatch) -> {
                    if (entitypatch instanceof PlayerPatch<?>) {
                        if (((PlayerPatch<?>)entitypatch).getSkill(SkillSlots.WEAPON_INNATE).getDataManager().getDataValue(SunsWrathSkill.STANCE) != null) {
                            if (((PlayerPatch<?>)entitypatch).getSkill(SkillSlots.WEAPON_INNATE).getDataManager().getDataValue(SunsWrathSkill.STANCE) == 1) {
                                return Styles.SHEATH;
                            }
                        }
                    }
                    return Styles.TWO_HAND;
                })
                .collider(UAColliders.PHARAOH_CURSE)
                .hitSound(EpicFightSounds.BLADE_HIT.get())
                .swingSound(EpicFightSounds.WHOOSH_BIG.get())
                .canBePlacedOffhand(false)
                .newStyleCombo(Styles.SHEATH, UAAnimations.PHARAOH_AUTO1, UAAnimations.PHARAOH_AUTO2, UAAnimations.PHARAOH_AUTO3, UAAnimations.PHARAOH_DASH, UAAnimations.PHARAOH_AIR)
                .newStyleCombo(Styles.TWO_HAND, UAAnimations.PHARAOH_AUTO1, UAAnimations.PHARAOH_AUTO2, UAAnimations.PHARAOH_AUTO3, UAAnimations.PHARAOH_DASH, UAAnimations.PHARAOH_AIR)
                .innateSkill(Styles.SHEATH,(itemstack) -> UASkills.SUNS_WRATH)
                .innateSkill(Styles.TWO_HAND,(itemstack) -> UASkills.SUNS_WRATH)
                .passiveSkill(UASkills.PHARAOH_PASSIVE)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, UAAnimations.PHARAOH_IDLE)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, UAAnimations.PHARAOH_WALK)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, UAAnimations.PHARAOH_RUN)
                .livingMotionModifier(Styles.SHEATH, LivingMotions.IDLE, UAAnimations.PHARAOH_IDLE_ACTIVE)
                .livingMotionModifier(Styles.SHEATH, LivingMotions.WALK, UAAnimations.PHARAOH_WALK_ACTIVE)
                .livingMotionModifier(Styles.SHEATH, LivingMotions.RUN, UAAnimations.PHARAOH_RUN_ACTIVE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, UAAnimations.PHARAOH_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, UAAnimations.PHARAOH_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, UAAnimations.PHARAOH_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, UAAnimations.PHARAOH_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, UAAnimations.PHARAOH_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.FLY, UAAnimations.PHARAOH_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CREATIVE_FLY, UAAnimations.PHARAOH_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CREATIVE_IDLE, UAAnimations.PHARAOH_IDLE)
            .livingMotionModifier(Styles.SHEATH, LivingMotions.SWIM, UAAnimations.PHARAOH_IDLE_ACTIVE)
            .livingMotionModifier(Styles.SHEATH, LivingMotions.JUMP, UAAnimations.PHARAOH_IDLE_ACTIVE)
            .livingMotionModifier(Styles.SHEATH, LivingMotions.KNEEL, UAAnimations.PHARAOH_IDLE_ACTIVE)
            .livingMotionModifier(Styles.SHEATH, LivingMotions.SNEAK, UAAnimations.PHARAOH_IDLE_ACTIVE)
            .livingMotionModifier(Styles.SHEATH, LivingMotions.SWIM, UAAnimations.PHARAOH_IDLE_ACTIVE)
            .livingMotionModifier(Styles.SHEATH, LivingMotions.FLY, UAAnimations.PHARAOH_IDLE_ACTIVE)
            .livingMotionModifier(Styles.SHEATH, LivingMotions.CREATIVE_FLY, UAAnimations.PHARAOH_IDLE_ACTIVE)
            .livingMotionModifier(Styles.SHEATH, LivingMotions.CREATIVE_IDLE, UAAnimations.PHARAOH_IDLE_ACTIVE);
        return builder;
    };

    public static final Function<Item, CapabilityItem.Builder> CALIBURN = (item) -> {
        CapabilityItem.Builder builder = WeaponCapability.builder()
                .category(UAWeaponCategories.PHARAOH_CURSE)
                .styleProvider((entitypatch) -> {
                    if(entitypatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).is(UAItems.CLARENT.get())) {
                        return Styles.OCHS;
                    }
                    else if (entitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD) {
                        return Styles.TWO_HAND;
                    }
                    return Styles.ONE_HAND;
                })
                .hitSound(EpicFightSounds.BLADE_HIT.get())
                .newStyleCombo(Styles.ONE_HAND, Animations.SWORD_AUTO1, Animations.SWORD_AUTO2, Animations.SWORD_AUTO3, Animations.SWORD_DASH, Animations.SWORD_AIR_SLASH)
                .newStyleCombo(Styles.TWO_HAND, Animations.SWORD_DUAL_AUTO1, Animations.SWORD_DUAL_AUTO2, Animations.SWORD_DUAL_AUTO3, Animations.SWORD_DUAL_DASH, Animations.SWORD_DUAL_AIR_SLASH)
                .newStyleCombo(Styles.OCHS, UAAnimations.CALIBURN_AUTO1)
                .livingMotionModifier(Styles.OCHS, LivingMotions.IDLE, UAAnimations.CALIBURN_IDLE)
                .livingMotionModifier(Styles.OCHS, LivingMotions.WALK, UAAnimations.CALIBURN_WALK)
                .livingMotionModifier(Styles.OCHS, LivingMotions.RUN, UAAnimations.CALIBURN_RUN)
                .weaponCombinationPredicator((entitypatch) -> {
                    return EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD || entitypatch.getOriginal().getItemInHand(InteractionHand.OFF_HAND).is(UAItems.CLARENT.get());
                });
        return builder;
    };

    @SubscribeEvent
    public static void register(WeaponCapabilityPresetRegistryEvent event) {
        event.getTypeEntry().put("pharaoh", PHARAOH_CURSE);
        event.getTypeEntry().put("caliburn", CALIBURN);
    }
}
