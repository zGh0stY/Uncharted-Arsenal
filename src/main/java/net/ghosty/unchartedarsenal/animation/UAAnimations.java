package net.ghosty.unchartedarsenal.animation;

import java.util.List;
import java.util.Random;
import java.util.Set;

import net.ghosty.unchartedarsenal.UnchartedArsenal;
import net.ghosty.unchartedarsenal.colliders.UAColliders;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.Keyframe;
import yesman.epicfight.api.animation.TransformSheet;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationEvent.Side;
import yesman.epicfight.api.animation.property.AnimationEvent.TimePeriodEvent;
import yesman.epicfight.api.animation.property.AnimationEvent.TimeStampedEvent;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.animation.types.AttackAnimation.Phase;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.api.utils.LevelUtil;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.SourceTags;
import yesman.epicfight.world.damagesource.StunType;

@Mod.EventBusSubscriber(modid = UnchartedArsenal.MOD_ID , bus = EventBusSubscriber.Bus.MOD)
public class UAAnimations {
    public static StaticAnimation PHARAOH_IDLE;
    public static MovementAnimation PHARAOH_WALK;
    public static MovementAnimation PHARAOH_RUN;
    public static StaticAnimation PHARAOH_IDLE_ACTIVE;
    public static MovementAnimation PHARAOH_WALK_ACTIVE;
    public static MovementAnimation PHARAOH_RUN_ACTIVE;
    public static StaticAnimation PHARAOH_AUTO1;

    @SubscribeEvent
    public static void registerAnimations(AnimationRegistryEvent event) {
        event.getRegistryMap().put(UnchartedArsenal.MOD_ID, UAAnimations::build);
    }

    private static void build() {
        HumanoidArmature biped = Armatures.BIPED;

        // --- PHARAOH'S CURSE
        PHARAOH_IDLE = new StaticAnimation(0.1f,true, "biped/living/pharaoh_idle", biped);
        PHARAOH_WALK = new MovementAnimation(0.1f,true, "biped/living/pharaoh_walk", biped);
        PHARAOH_RUN = new MovementAnimation(0.1f,true, "biped/living/pharaoh_run", biped);
        PHARAOH_IDLE_ACTIVE = new StaticAnimation(0.1f,true, "biped/living/pharaoh_idle_active", biped);
        PHARAOH_WALK_ACTIVE = new MovementAnimation(0.1f,true, "biped/living/pharaoh_walk_active", biped);
        PHARAOH_RUN_ACTIVE = new MovementAnimation(0.1f,true, "biped/living/pharaoh_run_active", biped);

        PHARAOH_AUTO1 = new BasicMultipleAttackAnimation(0.1F, "biped/combat/pharaoh_auto1", biped,
                new Phase(0F, 0.4F, 0.6F, 0.05F, Float.MAX_VALUE, biped.toolL, UAColliders.PHARAOH_CURSE))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(3))
                .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT.get())
                .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F);
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
