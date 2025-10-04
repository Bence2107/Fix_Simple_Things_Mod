package net.bence2107.fixsamplethingsmod.block.custom;

import com.mojang.serialization.MapCodec;
import net.bence2107.fixsamplethingsmod.block.ModBlockEntities;
import net.bence2107.fixsamplethingsmod.block.entities.StoneOvenBlockEntity;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class StoneOvenBlock extends AbstractFurnaceBlock {
    public static final MapCodec<StoneOvenBlock> CODEC = createCodec(StoneOvenBlock::new);

    public StoneOvenBlock(Settings settings) {
        super(settings.luminance(state -> state.get(LIT) ? 15 : 0));
    }

    @Override
    protected MapCodec<? extends AbstractFurnaceBlock> getCodec() {
        return CODEC;
    }


    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StoneOvenBlockEntity(pos, state);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);

            if (blockEntity instanceof StoneOvenBlockEntity) {
                player.openHandledScreen((StoneOvenBlockEntity) blockEntity);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    protected void openScreen(World world, BlockPos pos, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof StoneOvenBlockEntity) {
            player.openHandledScreen((StoneOvenBlockEntity) blockEntity);
            player.incrementStat(Stats.INTERACT_WITH_FURNACE);
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (world.isClient) {
            return null;
        }

        return type == ModBlockEntities.STONE_OVEN_BLOCK_ENTITY ?
                (world1, pos, state1, blockEntity) -> {
                    if (blockEntity instanceof StoneOvenBlockEntity stoneOven) {
                        stoneOven.tick((ServerWorld) world1, pos, state1);
                    }
                } : null;
    }


}
