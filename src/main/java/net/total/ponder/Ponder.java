package net.total.ponder;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import net.total.ponder.block.PonderOrbs;
import net.total.ponder.statistics.PonderingStat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ponder implements ModInitializer {
	public static final String MOD_ID = "ponder";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	Identifier id = Identifier.of(MOD_ID, "path");

	@Override
	public void onInitialize() {
		PonderOrbs.registerPondering();
		PonderingStat.registerStats();
		LOGGER.info("Hello Fabric world!");
	}
}