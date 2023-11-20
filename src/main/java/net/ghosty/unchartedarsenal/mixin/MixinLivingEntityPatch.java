package net.ghosty.unchartedarsenal.mixin;

import net.ghosty.unchartedarsenal.api.animation.types.MultihitAttackAnimation;
import net.ghosty.unchartedarsenal.world.capabilities.entitypatch.IMixinLivingEntityPatch;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.Map;

@Mixin(value = ServerPlayerPatch.class)
//public abstract class MixinLivingEntityPatch extends PlayerPatch<ServerPlayer> implements IMixinLivingEntityPatch {
public abstract class MixinLivingEntityPatch<T extends LivingEntity> extends LivingEntityPatch<T> implements IMixinLivingEntityPatch {

    @Override
    public int getEntityHitCount(LivingEntity entity) {
        return this.getAnimator().getAnimationVariables(MultihitAttackAnimation.HIT_COUNTS).getOrDefault(entity, 0);
    }

    @Override
    public void recordEntityHit(LivingEntity entity) {
        Map<LivingEntity, Integer> hitCounts = this.getAnimator().getAnimationVariables(MultihitAttackAnimation.HIT_COUNTS);
        hitCounts.put(entity, hitCounts.getOrDefault(entity, 0) + 1);
    }
}
