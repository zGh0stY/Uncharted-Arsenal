package net.ghosty.unchartedarsenal.mixin;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.ghosty.unchartedarsenal.UnchartedArsenal;
import net.ghosty.unchartedarsenal.api.animation.types.MultihitAttackAnimation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.utils.TypeFlexibleHashMap;
import yesman.epicfight.api.utils.TypeFlexibleHashMap.TypeKey;

import java.util.HashMap;

@Mixin(value = Animator.class)
public abstract class MixinAnimator {
    protected final TypeFlexibleHashMap<TypeKey<?>> animationVariables = new TypeFlexibleHashMap<TypeKey<?>>(false);

    @Inject(method = "<init>", at = @At("RETURN"), cancellable = true, remap = false)
    public void constructorHead(CallbackInfo ci) {
        // Put default variables
        this.animationVariables.put(MultihitAttackAnimation.HIT_COUNTS, Maps.newHashMap());
    }

}
