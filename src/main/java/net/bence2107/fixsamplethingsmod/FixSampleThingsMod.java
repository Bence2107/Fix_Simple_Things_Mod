package net.bence2107.fixsamplethingsmod;

import net.bence2107.fixsamplethingsmod.block.ModBlockEntities;
import net.bence2107.fixsamplethingsmod.block.ModBlocks;
import net.bence2107.fixsamplethingsmod.actions.ModBlockActions;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FixSampleThingsMod implements ModInitializer {
	public static final String MOD_ID = "fixsamplethingsmod";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModBlocks.registerModBlocks();
		ModBlockActions.registerActions();
		ModBlockEntities.registerBlockEntities();
	}
}