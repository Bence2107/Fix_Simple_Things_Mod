package net.bence2107.fixsamplethingsmod.block;

import net.bence2107.fixsamplethingsmod.FixSampleThingsMod;
import net.bence2107.fixsamplethingsmod.block.entities.StoneOvenBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<StoneOvenBlockEntity> STONE_OVEN_BLOCK_ENTITY =
            register(StoneOvenBlockEntity::new, ModBlocks.STONE_OVEN);

    private static <T extends BlockEntity> BlockEntityType<T> register(FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory, Block... blocks) {
        Identifier id = Identifier.of(FixSampleThingsMod.MOD_ID, "stone_oven_block_entity");
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }


    public static void registerBlockEntities() {
        FixSampleThingsMod.LOGGER.info("Registering Block Entities for " + FixSampleThingsMod.MOD_ID);
    }
}
