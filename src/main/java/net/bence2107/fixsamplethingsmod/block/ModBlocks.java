package net.bence2107.fixsamplethingsmod.block;

import net.bence2107.fixsamplethingsmod.FixSampleThingsMod;
import net.bence2107.fixsamplethingsmod.block.custom.NyliumPathBlock;
import net.bence2107.fixsamplethingsmod.block.custom.StoneOvenBlock;
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
            new NyliumPathBlock(AbstractBlock.Settings.copy(Blocks.WARPED_NYLIUM).registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FixSampleThingsMod.MOD_ID,"warped_nylium_path")))));
    public static final Block CRIMSON_NYLIUM_PATH = registerBlock("crimson_nylium_path",
            new NyliumPathBlock(AbstractBlock.Settings.copy(Blocks.CRIMSON_NYLIUM).registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FixSampleThingsMod.MOD_ID,"crimson_nylium_path")))));
    public static final Block STONE_OVEN = registerBlock("stone_oven",
            new StoneOvenBlock(AbstractBlock.Settings.copy(Blocks.FURNACE).registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FixSampleThingsMod.MOD_ID,"stone_oven")))));

    private static Block registerBlock(String name, Block block){
        registerBlockItem(name,block);
        return Registry.register(Registries.BLOCK, Identifier.of(FixSampleThingsMod.MOD_ID,name),block);
    }

    private static void registerBlockItem(String name, Block block){
        Registry.register(Registries.ITEM, Identifier.of(FixSampleThingsMod.MOD_ID,name),
                new BlockItem(block, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FixSampleThingsMod.MOD_ID,name)))));
    }

    public static void registerModBlocks() {
        FixSampleThingsMod.LOGGER.info("Registering Mod Blocks");

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(ModBlocks.WARPED_NYLIUM_PATH);
            fabricItemGroupEntries.add(ModBlocks.CRIMSON_NYLIUM_PATH);
            fabricItemGroupEntries.add(ModBlocks.STONE_OVEN);
        });
    }
}
