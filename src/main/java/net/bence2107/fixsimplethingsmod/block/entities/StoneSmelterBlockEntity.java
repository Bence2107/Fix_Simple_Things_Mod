package net.bence2107.fixsimplethingsmod.block.entities;

import net.bence2107.fixsimplethingsmod.block.ModBlockEntities;
import net.bence2107.fixsimplethingsmod.block.custom.StoneSmelterBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.FuelRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class StoneSmelterBlockEntity extends AbstractFurnaceBlockEntity {

    public StoneSmelterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STONE_SMELTER_BLOCK_ENTITY, pos, state, RecipeType.SMELTING);
    }

    @Override
    protected Text getContainerName() {
       return Text.of("Stone Smelter");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
       return new FurnaceScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    protected int getFuelTime(FuelRegistry fuelRegistry, ItemStack stack) {
        return super.getFuelTime(fuelRegistry, stack);
    }

    public void tick(ServerWorld world, BlockPos pos, BlockState state) {
        ItemStack inputStack = this.getStack(0);
        boolean isBurning = this.propertyDelegate.get(0) > 0;

        if (state.getBlock() instanceof StoneSmelterBlock && state.get(StoneSmelterBlock.LIT) != isBurning) {
            world.setBlockState(pos, state.with(StoneSmelterBlock.LIT, isBurning), Block.NOTIFY_ALL);
        }

        if (!inputStack.isEmpty() && !isStoneItem(inputStack)) {
            return;
        }

        this.propertyDelegate.set(3, 135);

        AbstractFurnaceBlockEntity.tick(world, pos, state, this);
    }

    private boolean isStoneItem(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        String itemId = Registries.ITEM.getId(stack.getItem()).toString();

        // List of allowed stone-related items
        return itemId.contains("stone") ||
                itemId.contains("cobble") ||
                itemId.contains("basalt") ||
                itemId.contains("netherrack") ||
                itemId.contains("sandstone") ||
                itemId.contains("quartz_block") ||
                itemId.contains("calcite");
    }
}
