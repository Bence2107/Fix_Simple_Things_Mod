package net.bence2107.fixsimplethingsmod.block;

import net.bence2107.fixsimplethingsmod.FixSimpleThingsMod;
import net.bence2107.fixsimplethingsmod.block.custom.NyliumPathBlock;
import net.bence2107.fixsimplethingsmod.block.custom.StoneSmelterBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;

public class ModBlocks {
    public static final Block WARPED_NYLIUM_PATH = registerBlock("warped_nylium_path",
            new NyliumPathBlock(copySettings(Blocks.WARPED_NYLIUM, "warped_nylium_path")));
    public static final Block CRIMSON_NYLIUM_PATH = registerBlock("crimson_nylium_path",
            new NyliumPathBlock(copySettings(Blocks.CRIMSON_NYLIUM, "crimson_nylium_path")));
    public static final Block STONE_SMELTER = registerBlock("stone_smelter",
            new StoneSmelterBlock(createSettings("stone_smelter")));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(BuiltInRegistries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(FixSimpleThingsMod.MOD_ID, name), block);
    }

    private static BlockBehaviour.Properties createSettings(String name) {
        return BlockBehaviour.Properties.of()
                .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(FixSimpleThingsMod.MOD_ID, name)));
    }

    private static BlockBehaviour.Properties copySettings(Block blockFrom, String name) {
        return BlockBehaviour.Properties.ofFullCopy(blockFrom)
                .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(FixSimpleThingsMod.MOD_ID, name)));
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(FixSimpleThingsMod.MOD_ID, name),
                new BlockItem(block, new Item.Properties()
                        .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(FixSimpleThingsMod.MOD_ID, name)))));
    }

    public static void registerModBlocks() {
        FixSimpleThingsMod.LOGGER.info("Registering Mod Blocks");

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.accept(ModBlocks.WARPED_NYLIUM_PATH);
            fabricItemGroupEntries.accept(ModBlocks.CRIMSON_NYLIUM_PATH);
            fabricItemGroupEntries.accept(ModBlocks.STONE_SMELTER);
        });
    }
}