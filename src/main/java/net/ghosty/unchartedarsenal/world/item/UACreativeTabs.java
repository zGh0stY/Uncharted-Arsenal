package net.ghosty.unchartedarsenal.world.item;

import net.ghosty.unchartedarsenal.UnchartedArsenal;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class UACreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, UnchartedArsenal.MOD_ID);

    public static final RegistryObject<CreativeModeTab> ITEMS = TABS.register("items",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.unchartedarsenal.items"))
                    .icon(() -> new ItemStack(UAItems.PHARAOH_CURSE.get()))
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .displayItems((params, output) -> {
                        UAItems.ITEMS.getEntries().forEach(it -> {
                            output.accept(it.get());
                        });
                    })
                    .build());
}
