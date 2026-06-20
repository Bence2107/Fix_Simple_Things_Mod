package net.bence2107.fixsimplethingsmod.block.custom;

import com.mojang.serialization.MapCodec;
import net.bence2107.fixsimplethingsmod.block.ModBlockEntities;
import net.bence2107.fixsimplethingsmod.block.entities.StoneSmelterBlockEntity;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class StoneSmelterBlock extends AbstractFurnaceBlock {
    public static final MapCodec<StoneSmelterBlock> CODEC = createCodec(StoneSmelterBlock::new);
    public static final BooleanProperty LIT = Properties.LIT;


    public StoneSmelterBlock(Settings settings) {
        super(settings.luminance(state -> state.get(LIT) ? 10 : 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT, Properties.HORIZONTAL_FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().
                with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite()).
                with(LIT, false);
    }

    @Override
    protected MapCodec<? extends AbstractFurnaceBlock> getCodec() {
        return CODEC;
    }


    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StoneSmelterBlockEntity(pos, state);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);

            if (blockEntity instanceof StoneSmelterBlockEntity) {
                player.openHandledScreen((StoneSmelterBlockEntity) blockEntity);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    protected void openScreen(World world, BlockPos pos, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof StoneSmelterBlockEntity) {
            player.openHandledScreen((StoneSmelterBlockEntity) blockEntity);
            player.incrementStat(Stats.INTERACT_WITH_FURNACE);
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (world.isClient()){
            return null;
        }

        return type == ModBlockEntities.STONE_SMELTER_BLOCK_ENTITY ?
                (world1, pos, state1, blockEntity) -> {
                    if (blockEntity instanceof StoneSmelterBlockEntity stoneOven) {
                        stoneOven.tick((ServerWorld) world1, pos, state1);
                    }
                } : null;
    }
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT)) {
            double x = pos.getX() + 0.5;
            double y = pos.getY() + 0.45;
            double z = pos.getZ() + 0.5;

            double offset = 0.52;
            double spread = random.nextDouble() * 0.3 - 0.15;

            // Add velocity for particle motion
            double velocityX = random.nextDouble() * 0.02 - 0.01;
            double velocityY = random.nextDouble() * 0.05;
            double velocityZ = random.nextDouble() * 0.02 - 0.01;

            // Calculate position and velocity based on facing
            Direction facing = state.get(FACING);
            double frontX = x + facing.getOffsetX() * offset + (facing.getAxis() == Direction.Axis.Z ? spread : 0);
            double frontY = y + spread;
            double frontZ = z + facing.getOffsetZ() * offset + (facing.getAxis() == Direction.Axis.X ? spread : 0);

            double velX = velocityX + facing.getOffsetX() * 0.02;
            double velZ = velocityZ + facing.getOffsetZ() * 0.02;

            // Front face particles
            world.addParticleClient(net.minecraft.particle.ParticleTypes.SMOKE, frontX, frontY, frontZ, velX, velocityY, velZ);
            world.addParticleClient(net.minecraft.particle.ParticleTypes.FLAME, frontX, frontY, frontZ, velX, velocityY * 0.5, velZ);
        }
    }
}
