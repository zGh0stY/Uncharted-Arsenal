package net.ghosty.unchartedarsenal.world.item;

import net.ghosty.unchartedarsenal.UnchartedArsenal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UAItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, UnchartedArsenal.MOD_ID);

    public static final RegistryObject<Item> PHARAOH_CURSE = ITEMS.register("pharaoh", () -> new PharaohCurseItem(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> PHARAOH_WHEEL = ITEMS.register("pharaoh_wheel", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
}
