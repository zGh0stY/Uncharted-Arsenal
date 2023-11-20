package net.ghosty.unchartedarsenal.animations;

import net.ghosty.unchartedarsenal.UnchartedArsenal;
import net.ghosty.unchartedarsenal.api.animation.property.MultihitPhaseProperty;
import net.ghosty.unchartedarsenal.api.animation.types.MultihitAttackAnimation;
import net.ghosty.unchartedarsenal.colliders.UAColliders;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import yesman.epicfight.api.animation.property.AnimationEvent.Side;
import yesman.epicfight.api.animation.property.AnimationEvent.TimeStampedEvent;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.animation.types.AttackAnimation.Phase;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.damagesource.StunType;

@Mod.EventBusSubscriber(modid = UnchartedArsenal.MOD_ID , bus = EventBusSubscriber.Bus.MOD)
public class UAAnimations {
    public static StaticAnimation PHARAOH_IDLE;
    public static StaticAnimation PHARAOH_WALK;
    public static StaticAnimation PHARAOH_RUN;
    public static StaticAnimation PHARAOH_IDLE_ACTIVE;
    public static StaticAnimation PHARAOH_WALK_ACTIVE;
    public static StaticAnimation PHARAOH_RUN_ACTIVE;
    public static StaticAnimation PHARAOH_AUTO1;
    public static StaticAnimation PHARAOH_AUTO2;
    public static StaticAnimation PHARAOH_AUTO3;
    public static StaticAnimation PHARAOH_DASH;
    public static StaticAnimation PHARAOH_AIR;

    @SubscribeEvent
    public static void registerAnimations(AnimationRegistryEvent event) {
        event.getRegistryMap().put(UnchartedArsenal.MOD_ID, UAAnimations::build);
    }

    private static void build() {
        HumanoidArmature biped = Armatures.BIPED;

        // --- PHARAOH'S CURSE
        PHARAOH_IDLE = new StaticAnimation(true, "biped/living/pharaoh_idle", biped);
        PHARAOH_WALK = new MovementAnimation(true, "biped/living/pharaoh_walk", biped);
        PHARAOH_RUN = new MovementAnimation(true, "biped/living/pharaoh_run", biped);
        PHARAOH_IDLE_ACTIVE = new StaticAnimation(true, "biped/living/pharaoh_idle_active", biped);
        PHARAOH_WALK_ACTIVE = new MovementAnimation(true, "biped/living/pharaoh_walk_active", biped);
        PHARAOH_RUN_ACTIVE = new MovementAnimation(true, "biped/living/pharaoh_run_active", biped);

        PHARAOH_AIR = new AirSlashAnimation(0.1F, 0.3F, 0.41F, 1F, UAColliders.PHARAOH_CURSE, biped.toolR, "biped/combat/pharaoh_air", biped)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F)
                .addProperty(ActionAnimationProperty.MOVE_VERTICAL, false)
                .addEvents(TimeStampedEvent.create(0.35F, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, Side.CLIENT).params(new Vec3f(0F, 0F, -3F), Armatures.BIPED.rootJoint, 1.1D, 0.55F));
        PHARAOH_DASH = new DashAttackAnimation(0.25F, 0.25F, 0.3F, 0.7F, 1.3F, UAColliders.PHARAOH_CURSE, biped.toolR, "biped/combat/pharaoh_dash", biped)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.3F);
        PHARAOH_AUTO1 = new MultihitAttackAnimation(0.1F, 0.4F, 0.6F, 0.6F, UAColliders.PHARAOH_CURSE, biped.toolR, "biped/combat/pharaoh_auto1", biped)
                .addProperty(MultihitPhaseProperty.MULTI_HIT_RATE, ValueModifier.adder(1))
                .addProperty(MultihitPhaseProperty.MULTI_MAX_HITS, ValueModifier.adder(1))
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F);
        PHARAOH_AUTO2 = new BasicAttackAnimation(0.1F, 0.3F, 0.45F, 0.69F, UAColliders.PHARAOH_CURSE, biped.toolR, "biped/combat/pharaoh_auto2", biped)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F);
        PHARAOH_AUTO3 = new BasicAttackAnimation(0.1F, "biped/combat/pharaoh_auto3", biped,
                new Phase(0F, 0.5F, 0.65F, 0.6F, 0.6F, biped.toolR, UAColliders.PHARAOH_CURSE),
                new Phase(0.6F, 0.4F, 0.9F, 1.4F, 1.4F, biped.rootJoint, UAColliders.PHARAOH_CRASH))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(3))
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT.get())
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F)
                .addEvents(
                        TimeStampedEvent.create(0.55F, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, Side.CLIENT).params(new Vec3f(0F, 0F, -3F), Armatures.BIPED.rootJoint, 1.1D, 0.55F),
                        TimeStampedEvent.create(0.65F, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, Side.CLIENT).params(new Vec3f(1F, 0F, -3F), Armatures.BIPED.rootJoint, 1.1D, 0.55F),
                        TimeStampedEvent.create(0.75F, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, Side.CLIENT).params(new Vec3f(-1F, 0F, -3F), Armatures.BIPED.rootJoint, 1.1D, 0.55F)
                );
        /*
        PHARAOH_AUTO1 = new BasicAttackAnimation(0.1F, 0.4F, 0.6F, 0.05F, UAColliders.PHARAOH_CURSE, biped.toolL, "biped/combat/pharaoh_auto1", biped)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(3))
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT.get())
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F);
        */
        /*
        PHARAOH_AUTO1 = new BasicAttackAnimation(0.15F, "biped/combat/pharaoh_auto1", biped,
                new Phase(0.3F, 0.4F, 1F, 0.5F, 0.45F, biped.toolL, UAColliders.PHARAOH_CURSE),
                new Phase(0.3F, 0.4F, 1.05F, 0.55F, 0.45F, biped.toolL, UAColliders.PHARAOH_CURSE),
                new Phase(0.3F, 0.4F, 1.1F, 0.6F, 0.45F, biped.toolL, UAColliders.PHARAOH_CURSE))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD,1)
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.NONE,2)
                .addProperty(AttackAnimationProperty.EXTRA_COLLIDERS, 3)
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 2F);
        */
    }
}
