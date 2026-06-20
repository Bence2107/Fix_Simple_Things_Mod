package net.bence2107.fixsimplethingsmod.block;

import net.bence2107.fixsimplethingsmod.FixSimpleThingsMod;
import net.bence2107.fixsimplethingsmod.block.custom.NyliumPathBlock;
import net.bence2107.fixsimplethingsmod.block.custom.StoneSmelterBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block WARPED_NYLIUM_PATH = registerBlock("warped_nylium_path",
            new NyliumPathBlock(copySettings(Blocks.WARPED_NYLIUM, "warped_nylium_path")));
    public static final Block CRIMSON_NYLIUM_PATH = registerBlock("crimson_nylium_path",
            new NyliumPathBlock(copySettings(Blocks.CRIMSON_NYLIUM, "crimson_nylium_path")));
    public static final Block STONE_SMELTER = registerBlock("stone_smelter",
            new StoneSmelterBlock(createSettings("stone_smelter")));

    private static Block registerBlock(String name, Block block){
        registerBlockItem(name,block);
        return Registry.register(Registries.BLOCK, Identifier.of(FixSimpleThingsMod.MOD_ID,name),block);
    }

    private static AbstractBlock.Settings createSettings(String name) {
        return AbstractBlock.Settings.create().registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FixSimpleThingsMod.MOD_ID,name)));
    }

    private static AbstractBlock.Settings copySettings(Block blockFrom, String name) {
        return AbstractBlock.Settings.copy(blockFrom).registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FixSimpleThingsMod.MOD_ID,name)));
    }

    private static void registerBlockItem(String name, Block block){
        Registry.register(Registries.ITEM, Identifier.of(FixSimpleThingsMod.MOD_ID,name),
                new BlockItem(block, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FixSimpleThingsMod.MOD_ID,name)))));
    }

    public static void registerModBlocks() {
        FixSimpleThingsMod.LOGGER.info("Registering Mod Blocks");

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(ModBlocks.WARPED_NYLIUM_PATH);
            fabricItemGroupEntries.add(ModBlocks.CRIMSON_NYLIUM_PATH);
            fabricItemGroupEntries.add(ModBlocks.STONE_SMELTER);
        });
    }
}
