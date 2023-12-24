package net.ghosty.unchartedarsenal.events.engine;

import net.ghosty.unchartedarsenal.UnchartedArsenal;
import net.ghosty.unchartedarsenal.gameasset.UAParticles;
import net.ghosty.unchartedarsenal.particle.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid=UnchartedArsenal.MOD_ID, value= Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onParticleRegistry(final RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(UAParticles.FIRE_GEYSER.get(), FireGeyserParticle.Provider::new);
        event.registerSpriteSet(UAParticles.FIRE_GEYSER_START.get(), FireGeyserStartParticle.Provider::new);
        event.registerSpriteSet(UAParticles.FIRE_GEYSER_END.get(), FireGeyserEndParticle.Provider::new);
        event.registerSpriteSet(UAParticles.FALLING_FLAME.get(), FallingFlameParticle.Provider::new);
    }
}
