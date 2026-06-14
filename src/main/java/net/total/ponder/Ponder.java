package net.total.ponder;

import net.fabricmc.api.ModInitializer;

import net.total.ponder.block.PonderOrbs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ponder implements ModInitializer {
	public static final String MOD_ID = "ponder";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		PonderOrbs.registerPondering();
		LOGGER.info("Hello Fabric world!");
	}
}