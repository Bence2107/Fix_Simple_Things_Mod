package net.bence2107.fixsimplethingsmod.block.custom;

import com.mojang.serialization.MapCodec;
import net.bence2107.fixsimplethingsmod.block.ModBlockEntities;
import net.bence2107.fixsimplethingsmod.block.entities.StoneSmelterBlockEntity;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.core.particles.ParticleTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StoneSmelterBlock extends AbstractFurnaceBlock {
    public static final MapCodec<StoneSmelterBlock> CODEC = simpleCodec(StoneSmelterBlock::new);
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public StoneSmelterBlock(BlockBehaviour.Properties properties) {
        super(properties.lightLevel(state -> state.getValue(LIT) ? 10 : 0));
    }

    @Override
    protected void openContainer(Level level, BlockPos pos, Player player) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof StoneSmelterBlockEntity stoneSmelter) {
            player.openMenu(stoneSmelter);
            player.awardStat(Stats.INTERACT_WITH_FURNACE);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT, BlockStateProperties.HORIZONTAL_FACING);
    }

    @Override
    public @NotNull BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, ctx.getHorizontalDirection().getOpposite())
                .setValue(LIT, false);
    }

    @Override
    protected @NotNull MapCodec<? extends AbstractFurnaceBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new StoneSmelterBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        if (world.isClientSide()) {
            return null;
        }

        return type == ModBlockEntities.STONE_SMELTER_BLOCK_ENTITY ?
                (world1, pos, state1, blockEntity) -> {
                    if (blockEntity instanceof StoneSmelterBlockEntity stoneOven) {
                        stoneOven.tick((ServerLevel) world1, pos, state1);
                    }
                } : null;
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT)) {
            double x = pos.getX() + 0.5;
            double y = pos.getY() + 0.45;
            double z = pos.getZ() + 0.5;

            double offset = 0.52;
            double spread = random.nextDouble() * 0.3 - 0.15;

            double velocityX = random.nextDouble() * 0.02 - 0.01;
            double velocityY = random.nextDouble() * 0.05;
            double velocityZ = random.nextDouble() * 0.02 - 0.01;

            Direction facing = state.getValue(BlockStateProperties.FACING);
            double frontX = x + facing.getStepX() * offset + (facing.getAxis() == Direction.Axis.Z ? spread : 0);
            double frontY = y + spread;
            double frontZ = z + facing.getStepZ() * offset + (facing.getAxis() == Direction.Axis.X ? spread : 0);

            double velX = velocityX + facing.getStepX() * 0.02;
            double velZ = velocityZ + facing.getStepZ() * 0.02;

            world.addParticle(ParticleTypes.SMOKE, frontX, frontY, frontZ, velX, velocityY, velZ);
            world.addParticle(ParticleTypes.FLAME, frontX, frontY, frontZ, velX, velocityY * 0.5, velZ);
        }
    }
}