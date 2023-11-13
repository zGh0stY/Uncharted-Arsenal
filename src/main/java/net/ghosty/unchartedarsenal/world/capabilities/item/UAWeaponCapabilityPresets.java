package net.ghosty.unchartedarsenal.world.capabilities.item;

import java.util.function.Function;

import com.mojang.datafixers.util.Pair;

import net.ghosty.unchartedarsenal.UnchartedArsenal;
import net.ghosty.unchartedarsenal.animation.UAAnimations;
import net.ghosty.unchartedarsenal.colliders.UAColliders;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.skill.BattojutsuPassive;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

@Mod.EventBusSubscriber(modid = UnchartedArsenal.MOD_ID , bus = EventBusSubscriber.Bus.MOD)
public class UAWeaponCapabilityPresets {
    public static final Function<Item, CapabilityItem.Builder> PHARAOH_CURSE = (item) -> {
        CapabilityItem.Builder builder = WeaponCapability.builder()
                .category(UAWeaponCategories.PHARAOH_CURSE)
                .styleProvider((playerpatch) -> Styles.SHEATH)
                .collider(UAColliders.PHARAOH_CURSE)
                .hitSound(EpicFightSounds.BLADE_HIT.get())
                .swingSound(EpicFightSounds.WHOOSH_BIG.get())
                .canBePlacedOffhand(false)
                .newStyleCombo(Styles.SHEATH, UAAnimations.PHARAOH_AUTO_1)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, UAAnimations.PHARAOH_IDLE)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, UAAnimations.PHARAOH_WALK)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, UAAnimations.PHARAOH_RUN)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_SPEAR)
                .livingMotionModifier(Styles.SHEATH, LivingMotions.IDLE, UAAnimations.PHARAOH_IDLE_ACTIVE)
                .livingMotionModifier(Styles.SHEATH, LivingMotions.WALK, UAAnimations.PHARAOH_WALK_ACTIVE)
                .livingMotionModifier(Styles.SHEATH, LivingMotions.RUN, UAAnimations.PHARAOH_RUN_ACTIVE);
        return builder;
    };

    @SubscribeEvent
    public static void register(WeaponCapabilityPresetRegistryEvent event) {
        event.getTypeEntry().put("pharaoh", PHARAOH_CURSE);
    }
}
