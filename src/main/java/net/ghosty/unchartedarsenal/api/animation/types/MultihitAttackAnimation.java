package net.ghosty.unchartedarsenal.api.animation.types;

import java.util.*;

import javax.annotation.Nullable;

import net.ghosty.unchartedarsenal.UnchartedArsenal;
import net.ghosty.unchartedarsenal.api.animation.property.MultihitPhaseProperty;
import net.ghosty.unchartedarsenal.world.capabilities.entitypatch.IMixinLivingEntityPatch;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.entity.PartEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.animation.types.BasicAttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.api.utils.HitEntityList;
import yesman.epicfight.api.utils.TypeFlexibleHashMap.TypeKey;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.entity.eventlistener.DealtDamageEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

public class MultihitAttackAnimation extends BasicAttackAnimation {
    Logger LOGGER = LogManager.getLogger(UnchartedArsenal.MOD_ID);

    /** Amount of times hurt entities got hit **/
    public static final TypeKey<Map<LivingEntity, Integer>> HIT_COUNTS = new TypeKey<>() {
        public Map<LivingEntity, Integer> defaultValue() {
            return new HashMap<>();
        }
    };

    public MultihitAttackAnimation(float convertTime, float antic, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        this(convertTime, antic, antic, contact, recovery, collider, colliderJoint, path, armature);
    }

    public MultihitAttackAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        super(convertTime, antic, preDelay, contact, recovery, collider, colliderJoint, path, armature);
        this.addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true);
        this.addProperty(ActionAnimationProperty.MOVE_VERTICAL, false);
        this.addProperty(StaticAnimationProperty.POSE_MODIFIER, Animations.ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER);
    }

    public MultihitAttackAnimation(float convertTime, float antic, float contact, float recovery, InteractionHand hand, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        super(convertTime, antic, contact, recovery, hand, collider, colliderJoint, path, armature);
        this.addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true);
        this.addProperty(ActionAnimationProperty.MOVE_VERTICAL, false);
        this.addProperty(StaticAnimationProperty.POSE_MODIFIER, Animations.ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER);
    }

    public MultihitAttackAnimation(float convertTime, String path, Armature armature, Phase... phases) {
        super(convertTime, path, armature, phases);
        this.addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true);
        this.addProperty(ActionAnimationProperty.MOVE_VERTICAL, false);
        this.addProperty(StaticAnimationProperty.POSE_MODIFIER, Animations.ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER);
    }

    @Override
    protected void hurtCollidingEntities(LivingEntityPatch<?> entitypatch, float prevElapsedTime, float elapsedTime, EntityState prevState, EntityState state, Phase phase) {
        LivingEntity entity = entitypatch.getOriginal();
        entitypatch.getArmature().initializeTransform();
        float prevPoseTime = prevState.attacking() ? prevElapsedTime : phase.preDelay;
        float poseTime = state.attacking() ? elapsedTime : phase.contact;
        List<Entity> list = this.getPhaseByTime(elapsedTime).getCollidingEntities(entitypatch, this, prevPoseTime, poseTime, this.getPlaySpeed(entitypatch));

        if (list.size() > 0) {
            HitEntityList hitEntities = new HitEntityList(entitypatch, list, phase.getProperty(AttackPhaseProperty.HIT_PRIORITY).orElse(HitEntityList.Priority.DISTANCE));
            int maxStrikes = this.getMaxStrikes(entitypatch, phase);

            LOGGER.debug(this.getMultiMaxHits(entitypatch, phase));
            LOGGER.debug(this.getMultiHitRate(entitypatch, phase));
            LOGGER.debug(((IMixinLivingEntityPatch)entitypatch).getEntityHitCount(entity));

            while (entitypatch.getCurrenltyHurtEntities().size() < maxStrikes && hitEntities.next()) {
                Entity hitten = hitEntities.getEntity();
                LivingEntity trueEntity = this.getTrueEntity(hitten);

                if (trueEntity != null && trueEntity.isAlive() && !entitypatch.getCurrenltyAttackedEntities().contains(trueEntity) && !entitypatch.isTeammate(hitten)) {
                    if (hitten instanceof LivingEntity || hitten instanceof PartEntity) {
                        if (entity.hasLineOfSight(hitten)) {
                            EpicFightDamageSource source = this.getEpicFightDamageSource(entitypatch, hitten, phase);
                            int prevInvulTime = hitten.invulnerableTime;
                            hitten.invulnerableTime = 0;

                            AttackResult attackResult = entitypatch.attack(source, hitten, phase.hand);
                            hitten.invulnerableTime = prevInvulTime;

                            if (attackResult.resultType.dealtDamage()) {
                                if (entitypatch instanceof ServerPlayerPatch playerpatch) {
                                    playerpatch.getEventListener().triggerEvents(EventType.DEALT_DAMAGE_EVENT_POST, new DealtDamageEvent(playerpatch, trueEntity, source, attackResult.damage));
                                }

                                hitten.level().playSound(null, hitten.getX(), hitten.getY(), hitten.getZ(), this.getHitSound(entitypatch, phase), hitten.getSoundSource(), 1.0F, 1.0F);
                                this.spawnHitParticle((ServerLevel)hitten.level(), entitypatch, hitten, phase);
                            }

                            entitypatch.getCurrenltyAttackedEntities().add(trueEntity);

                            if (attackResult.resultType.shouldCount()) {
                                entitypatch.getCurrenltyHurtEntities().add(trueEntity);
                            }
                        }
                    }
                }
            }
        }
    }

    protected int getMultiMaxHits(LivingEntityPatch<?> entitypatch, Phase phase) {
        return phase.getProperty(MultihitPhaseProperty.MULTI_MAX_HITS)
                .map(valueCorrector -> (int) valueCorrector.getTotalValue(1))
                .orElse(1);
    }

    protected int getMultiHitRate(LivingEntityPatch<?> entitypatch, Phase phase) {
        return phase.getProperty(MultihitPhaseProperty.MULTI_HIT_RATE)
                .map(valueCorrector -> (int) valueCorrector.getTotalValue(1))
                .orElse(1);
    }

}