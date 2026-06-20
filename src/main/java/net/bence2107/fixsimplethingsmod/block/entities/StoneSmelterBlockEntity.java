package net.bence2107.fixsimplethingsmod.block.entities;

import net.bence2107.fixsimplethingsmod.block.ModBlockEntities;
import net.bence2107.fixsimplethingsmod.block.custom.StoneSmelterBlock;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.FuelValues;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;

public class StoneSmelterBlockEntity extends AbstractFurnaceBlockEntity {
    public StoneSmelterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STONE_SMELTER_BLOCK_ENTITY, pos, state, RecipeType.SMELTING);
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.literal("Stone Smelter");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int syncId, Inventory playerInventory) {
        return new FurnaceMenu(syncId, playerInventory, this, this.dataAccess);
    }

    @Override
    protected int getBurnDuration(FuelValues fuelValues, ItemStack stack) {
        return super.getBurnDuration(fuelValues, stack);
    }

    public void tick(ServerLevel world, BlockPos pos, BlockState state) {
        ItemStack inputStack = this.getItem(0);
        boolean isBurning = this.dataAccess.get(AbstractFurnaceBlockEntity.DATA_LIT_TIME) > 0;

        if (state.getBlock() instanceof StoneSmelterBlock && state.getValue(StoneSmelterBlock.LIT) != isBurning) {
            world.setBlock(pos, state.setValue(StoneSmelterBlock.LIT, isBurning), Block.UPDATE_ALL);
        }

        if (!inputStack.isEmpty() && !isStoneItem(inputStack)) {
            return;
        }

        AbstractFurnaceBlockEntity.serverTick(world, pos, state, this);
    }

    private boolean isStoneItem(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        String itemId = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();

        return itemId.contains("stone") ||
                itemId.contains("cobble") ||
                itemId.contains("basalt") ||
                itemId.contains("netherrack") ||
                itemId.contains("sandstone") ||
                itemId.contains("quartz_block") ||
                itemId.contains("calcite");
    }
}