package net.ghosty.unchartedarsenal.skill;

import net.ghosty.unchartedarsenal.UnchartedArsenal;
import net.ghosty.unchartedarsenal.skill.weaponinnate.SunsWrathSkill;
import net.ghosty.unchartedarsenal.skill.weaponpassive.PharaohPassive;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.data.reloader.SkillManager;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.passive.PassiveSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;

@Mod.EventBusSubscriber(modid = UnchartedArsenal.MOD_ID, bus= Mod.EventBusSubscriber.Bus.FORGE)
public class UASkills {
    /** Weapon Innate Skills **/
    public static Skill SUNS_WRATH;

    /** Weapon Passive Skills **/
    public static Skill PHARAOH_PASSIVE;

    public static void registerSkills() {
        SkillManager.register(SunsWrathSkill::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(Skill.ActivateType.DURATION), UnchartedArsenal.MOD_ID, "suns_wrath");
        SkillManager.register(PharaohPassive::new, Skill.createBuilder().setCategory(SkillCategories.WEAPON_PASSIVE), UnchartedArsenal.MOD_ID,"pharaoh_passive");
    }

    @SubscribeEvent
    public static void buildSkillEvent(SkillBuildEvent onBuild) {
        SUNS_WRATH = onBuild.build(UnchartedArsenal.MOD_ID, "suns_wrath");
        PHARAOH_PASSIVE = onBuild.build(UnchartedArsenal.MOD_ID, "pharaoh_passive");
    }
}
