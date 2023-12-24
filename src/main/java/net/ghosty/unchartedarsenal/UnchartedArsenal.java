package net.ghosty.unchartedarsenal;

import com.mojang.logging.LogUtils;
import net.ghosty.unchartedarsenal.gameasset.UASounds;
import net.ghosty.unchartedarsenal.gameasset.UAParticles;
import net.ghosty.unchartedarsenal.gameasset.UASkills;
import net.ghosty.unchartedarsenal.world.item.UACreativeTabs;
import net.ghosty.unchartedarsenal.world.item.UAItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(UnchartedArsenal.MOD_ID)
public class UnchartedArsenal {
    public static final String MOD_ID = "unchartedarsenal";
    private static final Logger LOGGER = LogUtils.getLogger();

    public UnchartedArsenal() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        UAItems.ITEMS.register(bus);
        UACreativeTabs.TABS.register(bus);
        UAParticles.PARTICLES.register(bus);
        UASounds.SOUNDS.register(bus);
        UASkills.registerSkills();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
}
