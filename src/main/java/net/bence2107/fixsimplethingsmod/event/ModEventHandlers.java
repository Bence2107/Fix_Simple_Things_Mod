package net.bence2107.fixsimplethingsmod.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.entity.EntityTypeTest;

public class ModEventHandlers {
    private static boolean isConcretePowder(ItemStack stack) {
        return BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath().endsWith("_concrete_powder");
    }

    private static ItemStack getConcreteFromPowder(ItemStack stack) {
        String id = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
        String concreteId = id.replace("_powder", "");

        Item concreteItem = BuiltInRegistries.ITEM
                .get(Identifier.fromNamespaceAndPath("minecraft", concreteId))
                .map(net.minecraft.core.Holder.Reference::value)
                .orElse(Items.AIR);

        return new ItemStack(concreteItem, stack.getCount());
    }

    public static void registerEventHandlers() {
        ServerTickEvents.END_LEVEL_TICK.register(world -> {
            world.getEntities(EntityTypeTest.forClass(ItemEntity.class), item -> isConcretePowder(item.getItem()))
                    .forEach(item -> {
                        BlockPos pos = item.getOnPos();
                        BlockState state = world.getBlockState(pos);

                        if (state.getFluidState().is(FluidTags.WATER)) {
                            ItemStack concrete = getConcreteFromPowder(item.getItem());
                            item.setItem(concrete);
                        }
                    });
        });
    }
}