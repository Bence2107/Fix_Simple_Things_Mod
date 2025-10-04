package net.bence2107.fixsamplethingsmod.block.entities;

import net.bence2107.fixsamplethingsmod.block.ModBlockEntities;
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

public class StoneOvenBlockEntity extends AbstractFurnaceBlockEntity {

    public StoneOvenBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STONE_OVEN_BLOCK_ENTITY, pos, state, RecipeType.SMELTING);
    }

    @Override
    protected Text getContainerName() {
       return Text.of("Stone Oven");
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

        if (!inputStack.isEmpty() && !isStoneItem(inputStack)) {
            return;
        }

        this.propertyDelegate.set(3, 75);

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
                itemId.contains("deepslate") ||
                itemId.contains("basalt") ||
                itemId.contains("netherrack") ||
                itemId.contains("sandstone") ||
                itemId.contains("terracotta") ||
                itemId.contains("clay") ||
                itemId.contains("brick") ||
                itemId.contains("quartz_block") ||
                itemId.contains("granite") ||
                itemId.contains("diorite") ||
                itemId.contains("andesite") ||
                itemId.contains("tuff") ||
                itemId.contains("calcite") ||
                itemId.contains("dripstone");
    }


}
