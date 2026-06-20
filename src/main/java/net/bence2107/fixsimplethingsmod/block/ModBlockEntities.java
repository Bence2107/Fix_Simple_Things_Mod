package net.bence2107.fixsimplethingsmod.block;

import net.bence2107.fixsimplethingsmod.FixSimpleThingsMod;
import net.bence2107.fixsimplethingsmod.block.entities.StoneSmelterBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<StoneSmelterBlockEntity> STONE_SMELTER_BLOCK_ENTITY =
            registerBlockEntity(StoneSmelterBlockEntity::new, ModBlocks.STONE_SMELTER);

    private static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory, Block... blocks) {
        Identifier id = Identifier.of(FixSimpleThingsMod.MOD_ID, "stone_smelter_block_entity");
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }


    public static void registerBlockEntities() {
        FixSimpleThingsMod.LOGGER.info("Registering Block Entities for " + FixSimpleThingsMod.MOD_ID);
    }
}
