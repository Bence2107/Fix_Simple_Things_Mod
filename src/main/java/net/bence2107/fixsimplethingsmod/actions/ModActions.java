package net.bence2107.fixsimplethingsmod.actions;

import net.bence2107.fixsimplethingsmod.block.ModBlocks;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;

public class ModActions {
    private static void registerShovelAction(Block blockFrom, Block blockTo) {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (!(player instanceof ServerPlayer)) {
                return InteractionResult.PASS;
            }

            BlockPos pos = hitResult.getBlockPos();

            if (world.getBlockState(pos).getBlock() == blockFrom
                    && player.getItemInHand(hand).getItem() instanceof ShovelItem) {

                world.setBlockAndUpdate(pos, blockTo.defaultBlockState());

                world.playSound(null, pos, SoundEvents.GRASS_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);

                player.swing(hand, true);

                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        });
    }

    public static void registerActions() {
        // Shovel Actions:
        registerShovelAction(Blocks.FARMLAND, Blocks.DIRT_PATH);
        registerShovelAction(Blocks.WARPED_NYLIUM, ModBlocks.WARPED_NYLIUM_PATH);
        registerShovelAction(Blocks.CRIMSON_NYLIUM, ModBlocks.CRIMSON_NYLIUM_PATH);
    }
}