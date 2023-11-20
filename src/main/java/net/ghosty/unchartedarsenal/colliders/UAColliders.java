package net.ghosty.unchartedarsenal.colliders;

import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.MultiOBBCollider;
import yesman.epicfight.api.collider.OBBCollider;

public class UAColliders {
    public static final Collider PHARAOH_CURSE = new MultiOBBCollider(4, 0.5D, 1.2D, 0.7D, 0D, 1.8D, 0D);
    public static final Collider PHARAOH_CRASH = new MultiOBBCollider(4, 1D, 1D, 1D, 0D, 0.5D, -3D);
}
