package net.ghosty.unchartedarsenal.events.engine;

import com.mojang.blaze3d.vertex.PoseStack;

import net.ghosty.unchartedarsenal.world.item.UAItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@OnlyIn(Dist.CLIENT)
public class RenderPharaoh extends RenderItemBase {
    private final ItemStack Secondmodel = new ItemStack(UAItems.PHARAOH_WHEEL.get());

    @Override
    public void renderItemInHand(ItemStack stack, LivingEntityPatch<?> entitypatch, InteractionHand hand, HumanoidArmature armature, OpenMatrix4f[] poses, MultiBufferSource buffer, PoseStack poseStack, int packedLight) {
        OpenMatrix4f modelMatrix = this.getCorrectionMatrix(stack, entitypatch, hand);
        boolean isInMainhand = (hand == InteractionHand.MAIN_HAND);
        Joint holdingHand = armature.toolR;
        OpenMatrix4f jointTransform = poses[holdingHand.getId()];
        Joint holdingHand2 = armature.toolL;
        OpenMatrix4f jointTransform2 = poses[holdingHand2.getId()];
        modelMatrix.mulFront(jointTransform);
        if (!isInMainhand) {
            modelMatrix.translate(1.000f,0, 0);
        }

        poseStack.pushPose();
        this.mulPoseStack(poseStack, modelMatrix);
        ItemDisplayContext transformType = ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, transformType, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, null, 0);

        poseStack.popPose();

        modelMatrix = this.getCorrectionMatrix(Secondmodel, entitypatch, hand);
        modelMatrix.mulFront(jointTransform2);

        poseStack.pushPose();
        this.mulPoseStack(poseStack, modelMatrix);
        Minecraft.getInstance().getItemRenderer().renderStatic(Secondmodel, transformType, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, null, 0);

        poseStack.popPose();
    }
}
