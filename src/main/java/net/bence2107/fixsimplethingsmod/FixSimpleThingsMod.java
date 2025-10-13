package net.bence2107.fixsimplethingsmod;

import net.bence2107.fixsimplethingsmod.actions.ModActions;
import net.bence2107.fixsimplethingsmod.block.ModBlockEntities;
import net.bence2107.fixsimplethingsmod.block.ModBlocks;
import net.bence2107.fixsimplethingsmod.event.ModEventHandlers;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FixSimpleThingsMod implements ModInitializer {
	public static final String MOD_ID = "fixsimplethingsmod";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModBlocks.registerModBlocks();
		ModActions.registerActions();
		ModBlockEntities.registerBlockEntities();
		ModEventHandlers.registerEventHandlers();
	}
}