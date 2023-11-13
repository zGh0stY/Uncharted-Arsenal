package net.ghosty.unchartedarsenal.events.engine;

import net.ghosty.unchartedarsenal.UnchartedArsenal;
import net.ghosty.unchartedarsenal.world.item.UAItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent;

@Mod.EventBusSubscriber(modid = UnchartedArsenal.MOD_ID , bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class UARenderEngine {
    @SubscribeEvent
    public static void registerRenderer(PatchedRenderersEvent.Add event) {
        event.addItemRenderer(UAItems.PHARAOH_CURSE.get(), new RenderPharaoh());
    }
}
