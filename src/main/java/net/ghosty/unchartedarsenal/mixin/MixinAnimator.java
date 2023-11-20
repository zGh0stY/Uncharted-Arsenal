package net.ghosty.unchartedarsenal.mixin;

import com.google.common.collect.Lists;
import net.ghosty.unchartedarsenal.api.animation.types.MultihitAttackAnimation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.Animator;

import java.util.HashMap;

@Mixin(value = Animator.class)
public abstract class MixinAnimator extends Animator {

    @Inject(method = "<init>", at = @At("HEAD"))
    public void constructorHead(CallbackInfo ci) {
        // Put default variables
        this.animationVariables.put(MultihitAttackAnimation.HIT_COUNTS, new HashMap<>());
    }

}
