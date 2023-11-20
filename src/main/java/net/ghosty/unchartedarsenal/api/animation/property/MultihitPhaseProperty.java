package net.ghosty.unchartedarsenal.api.animation.property;

import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.utils.math.ValueModifier;

public class MultihitPhaseProperty<T> extends AttackPhaseProperty<T> {
    public static final AttackPhaseProperty<ValueModifier> MULTI_MAX_HITS = new AttackPhaseProperty<ValueModifier> ();
    public static final AttackPhaseProperty<ValueModifier> MULTI_HIT_RATE = new AttackPhaseProperty<ValueModifier> ();
}
