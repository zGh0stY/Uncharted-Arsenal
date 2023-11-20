package net.ghosty.unchartedarsenal.world.capabilities.entitypatch;

import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public interface IMixinLivingEntityPatch {
    Integer getEntityHitCount(LivingEntity entity);
    void recordEntityHit(LivingEntity entity);

    public void clearEntityHitCount();
}
