package net.ghosty.unchartedarsenal.skill.weaponpassive;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.ghosty.unchartedarsenal.gameasset.UASkills;
import net.ghosty.unchartedarsenal.skill.weaponinnate.SunsWrathSkill;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.skill.passive.PassiveSkill;

public class PharaohPassive extends PassiveSkill {
    public PharaohPassive(Builder<? extends Skill> builder) {
        super(builder);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean shouldDraw(SkillContainer container) {
        if (container.getExecuter().getSkill(SkillSlots.WEAPON_INNATE).getSkill() instanceof SunsWrathSkill) {
            return container.getExecuter().getSkill(SkillSlots.WEAPON_INNATE).getDataManager().getDataValue(SunsWrathSkill.TIMER) > 0;
        }
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void drawOnGui(BattleModeGui gui, SkillContainer container, GuiGraphics guiGraphics, float x, float y) {
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(0, (float)gui.getSlidingProgression(), 0);
        RenderSystem.setShaderTexture(0, UASkills.SUNS_WRATH.getSkillTexture());
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.blit(UASkills.SUNS_WRATH.getSkillTexture(), (int)x, (int)y, 24, 24, 0, 0, 1, 1, 1, 1);
        guiGraphics.drawString(gui.font, String.valueOf((container.getExecuter().getSkill(SkillSlots.WEAPON_INNATE).getDataManager().getDataValue(SunsWrathSkill.TIMER)/20)+1), x+4, y+13, 16777215,true);
        poseStack.popPose();
    }
}
