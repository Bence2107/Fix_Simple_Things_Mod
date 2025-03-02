package net.bence2107.fixsamplethingsmod.block.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.DirtPathBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class NyliumPathBlock extends DirtPathBlock {
    private static final VoxelShape SHAPE = VoxelShapes.cuboid(0.0, 0.0, 0.0, 1.0, 0.9375, 1.0);

    public NyliumPathBlock(Settings settings) {
        super(settings);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}