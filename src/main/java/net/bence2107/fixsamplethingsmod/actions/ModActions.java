package net.bence2107.fixsamplethingsmod.actions;

import net.bence2107.fixsamplethingsmod.block.ModBlocks;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ShovelItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public class ModActions {
    private static void registerShowelAction(Block blockFrom, Block blockTo ) {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (!(player instanceof ServerPlayerEntity)) {
                return ActionResult.PASS;
            }
            BlockPos pos = hitResult.getBlockPos();
            if (world.getBlockState(pos).getBlock() == blockFrom && player.getStackInHand(hand).getItem() instanceof ShovelItem) {

                world.setBlockState(pos, blockTo.getDefaultState());

                world.playSound(null,pos, SoundEvents.BLOCK_GRASS_PLACE, SoundCategory.BLOCKS);

                player.swingHand(hand,true);

                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        });
    }

    public static void registerActions(){
        //Showel Actions:
        registerShowelAction(Blocks.FARMLAND,Blocks.DIRT_PATH);
        registerShowelAction(Blocks.WARPED_NYLIUM, ModBlocks.WARPED_NYLIUM_PATH);
        registerShowelAction(Blocks.CRIMSON_NYLIUM,ModBlocks.CRIMSON_NYLIUM_PATH);
    }
}
