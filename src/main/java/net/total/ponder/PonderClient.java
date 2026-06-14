package net.total.ponder;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.total.ponder.entity.PonderEntities;
import net.total.ponder.entity.client.rendering.EvilPonderOrbRenderer;

public class PonderClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(PonderEntities.EVIL_PONDER_ORB, EvilPonderOrbRenderer::new);
    }
}
