package net.bence2107.fixsimplethingsmod.block;

import net.bence2107.fixsimplethingsmod.FixSimpleThingsMod;
import net.bence2107.fixsimplethingsmod.block.entities.StoneSmelterBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;

public class ModBlockEntities {
    public static final BlockEntityType<StoneSmelterBlockEntity> STONE_SMELTER_BLOCK_ENTITY =
            registerBlockEntity(StoneSmelterBlockEntity::new, ModBlocks.STONE_SMELTER);

    private static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory, Block... blocks) {
        Identifier id = Identifier.fromNamespaceAndPath(FixSimpleThingsMod.MOD_ID, "stone_smelter_block_entity");
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }


    public static void registerBlockEntities() {
        FixSimpleThingsMod.LOGGER.info("Registering Block Entities for " + FixSimpleThingsMod.MOD_ID);
    }
}
