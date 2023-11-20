package net.ghosty.unchartedarsenal.world.capabilities.entitypatch;

import net.minecraft.world.entity.LivingEntity;

public interface IMixinLivingEntityPatch {
    int getEntityHitCount(LivingEntity entity);
    void recordEntityHit(LivingEntity entity);
}
