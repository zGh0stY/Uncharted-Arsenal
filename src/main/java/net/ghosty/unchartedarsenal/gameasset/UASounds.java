package net.ghosty.unchartedarsenal.gameasset;

import net.ghosty.unchartedarsenal.UnchartedArsenal;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UASounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, UnchartedArsenal.MOD_ID);

    public static final RegistryObject<SoundEvent> FIRE_GEYSER = registerSound("sfx.fire_geyser");

    private static RegistryObject<SoundEvent> registerSound(String name) {
        ResourceLocation res = new ResourceLocation(UnchartedArsenal.MOD_ID, name);
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(res));
    }
}
