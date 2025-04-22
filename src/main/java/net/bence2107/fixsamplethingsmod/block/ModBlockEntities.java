package net.bence2107.fixsamplethingsmod.block;

import net.bence2107.fixsamplethingsmod.FixSampleThingsMod;
import net.bence2107.fixsamplethingsmod.block.custom.entity.StoneOvenEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class ModBlockEntities {
    public static BlockEntityType<StoneOvenEntity> STONE_BLAST_FURNACE;

    public static void registerBlockEntities() {
        STONE_BLAST_FURNACE = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(FixSampleThingsMod.MOD_ID, "stone_oven"),
                FabricBlockEntityTypeBuilder.create(StoneOvenEntity::new, ModBlocks.STONE_OVEN).build()
        );
    }
}

