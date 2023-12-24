package net.ghosty.unchartedarsenal.skill.weaponinnate;

import java.util.List;
import java.util.UUID;

import net.ghosty.unchartedarsenal.UnchartedArsenal;
import net.ghosty.unchartedarsenal.gameasset.UAAnimations;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.skill.SkillDataManager.SkillDataKey;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

public class SunsWrathSkill extends WeaponInnateSkill {
    Logger LOGGER = LogManager.getLogger(UnchartedArsenal.MOD_ID);
    private static final UUID EVENT_UUID = UUID.fromString("c5b6c896-6cc0-44e1-9eb6-601d60751deb");
    public static final SkillDataKey<Integer> STANCE = SkillDataKey.createDataKey(SkillDataManager.ValueType.INTEGER);
    public static final SkillDataKey<Integer> TIMER = SkillDataKey.createDataKey(SkillDataManager.ValueType.INTEGER);

    private final int timerMaxDuration = 200;
    private final int timerGain = 20;

    public SunsWrathSkill(Builder<? extends Skill> builder) {
        super(builder);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getDataManager().registerData(STANCE);
        container.getDataManager().registerData(TIMER);

        container.getExecuter().getEventListener().addEventListener(EventType.DEALT_DAMAGE_EVENT_POST, EVENT_UUID, (event) -> {
            // Player Dealt Damage
            container.getDataManager().setDataSync(TIMER, container.getDataManager().getDataValue(TIMER)+timerGain,((ServerPlayerPatch) container.getExecuter()).getOriginal());
        });
    }

    @Override
    public void onRemoved(SkillContainer container) {
        // Cancel Events once they stop getting used
        container.getExecuter().getEventListener().removeListener(EventType.DEALT_DAMAGE_EVENT_POST, EVENT_UUID);
    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        // Play Sounds, Animations, Etc.. when skill is activated

    }

    @Override
    public void cancelOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        // Reset Variables
        super.cancelOnServer(executer, args);
        executer.getSkill(this).getDataManager().setData(STANCE, 0);
        executer.getSkill(this).getDataManager().setData(TIMER, 0);
    }

    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        if(!container.getExecuter().isLogicalClient()) {
            ServerPlayerPatch executer = (ServerPlayerPatch) container.getExecuter();
            if (container.getDataManager().getDataValue(TIMER) > 0) {
                if (container.getDataManager().getDataValue(STANCE) == 0) {
                    container.getDataManager().setDataSync(STANCE, 1, ((ServerPlayerPatch) container.getExecuter()).getOriginal());

                    executer.modifyLivingMotionByCurrentItem();
                    executer.playSound(SoundEvents.BLAZE_SHOOT, 0.0F, 0.0F);
                }

                if (!container.getExecuter().isLogicalClient()) {
                    container.getDataManager().setDataSync(TIMER, container.getDataManager().getDataValue(TIMER) - 1, ((ServerPlayerPatch) container.getExecuter()).getOriginal());
                }
            }
            else {
                if (container.getDataManager().getDataValue(STANCE) == 1) {
                    container.getDataManager().setDataSync(STANCE, 0, ((ServerPlayerPatch) container.getExecuter()).getOriginal());

                    executer.playAnimationSynchronized(UAAnimations.PHARAOH_IDLE_SWITCH, 0.1F);
                    executer.modifyLivingMotionByCurrentItem();
                    executer.playSound(SoundEvents.FIRE_EXTINGUISH, 0.0F, 0.0F);
                }
            }
        }
    }

    @Override
    public List<Component> getTooltipOnItem(ItemStack itemStack, CapabilityItem cap, PlayerPatch<?> playerCap) {
        List<Component> list = super.getTooltipOnItem(itemStack, cap, playerCap);
        this.generateTooltipforPhase(list, itemStack, cap, playerCap, this.properties.get(0), "TEMP");

        return list;
    }

    @Override
    public WeaponInnateSkill registerPropertiesToAnimation() {
        return this;
    }

    public enum Stance {
        INACTIVE, ACTIVE
    }
}
