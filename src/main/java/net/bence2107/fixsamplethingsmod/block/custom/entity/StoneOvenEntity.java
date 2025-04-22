package net.bence2107.fixsamplethingsmod.block.custom.entity;

import net.bence2107.fixsamplethingsmod.block.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StoneOvenEntity extends BlockEntity {
    private int smeltProgress = 0;
    private final int maxSmeltProgress = 100;

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);

    public DefaultedList<ItemStack> getInventory() {
        return inventory;
    }

    public StoneOvenEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STONE_BLAST_FURNACE, pos, state);
    }




    public static void tick(World world, BlockPos pos, BlockState state, StoneOvenEntity entity) {
        if (!world.isClient) {
            ItemStack input = entity.getInventory().getFirst();
            if (input.getItem() == Items.STONE || input.getItem() == Items.COBBLESTONE) {
                entity.smeltProgress += 2;
                if (entity.smeltProgress >= entity.maxSmeltProgress) {
                    entity.inventory.set(0, ItemStack.EMPTY);
                    entity.inventory.set(1, new ItemStack(
                            input.getItem() == Items.STONE ? Items.SMOOTH_STONE : Items.STONE
                    ));
                    entity.smeltProgress = 0;
                }
            }
        }
    }
}
