package net.total.ponder.item;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.total.ponder.Ponder;

public class PonderItems {
    public static final Item FUSHIGI = registerItem("fushigi",
            new FushigiItem(new Item.Settings().maxCount(1)));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(Ponder.MOD_ID, name), item);
    }

    public static void registerPonderingItems(){
        Ponder.LOGGER.info("Orbing all men");
    }
}
