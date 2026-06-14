package net.total.ponder.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.total.ponder.Ponder;

public class PonderOrbs {
    public static final Block PONDERING_ORB = registerBlock("pondering_orb",
            new OrbBlock(AbstractBlock.Settings.create().strength(1.5f).sounds(BlockSoundGroup.GLASS).luminance(state -> 5)));

    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return  Registry.register(Registries.BLOCK, Identifier.of(Ponder.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block){
        Registry.register(Registries.ITEM, Identifier.of(Ponder.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerPondering(){
        Ponder.LOGGER.info("Orbing all women");
    }

}
