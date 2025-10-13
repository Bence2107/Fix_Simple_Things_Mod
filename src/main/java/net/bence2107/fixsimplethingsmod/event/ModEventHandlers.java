package net.bence2107.fixsimplethingsmod.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.registry.Registries;

public class ModEventHandlers {
    private static boolean isConcretePowder(ItemStack stack) {
        return Registries.ITEM.getId(stack.getItem()).getPath().endsWith("_concrete_powder");
    }

    private static ItemStack getConcreteFromPowder(ItemStack stack) {
        String id = Registries.ITEM.getId(stack.getItem()).getPath();
        String concreteId = id.replace("_powder", "");
        Item concreteItem = Registries.ITEM.get(Identifier.of("minecraft", concreteId));
        return new ItemStack(concreteItem, stack.getCount());
    }

    public static void registerEventHandlers() {
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            if (world == null) return;

            for (Entity entity : world.iterateEntities()) {
                if (!(entity instanceof ItemEntity item)) continue;

                ItemStack stack = item.getStack();
                if (isConcretePowder(stack)) {
                    BlockPos pos = item.getBlockPos();
                    BlockState state = world.getBlockState(pos);

                    if (state.getFluidState().isIn(FluidTags.WATER)) {
                       ItemStack concrete = getConcreteFromPowder(stack);
                       item.setStack(concrete);
                    }
                }
            }
        });
    }
}
