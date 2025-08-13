package rinat.better_crosshair.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.util.Identifier;
import rinat.better_crosshair.BetterCrosshair;
import rinat.better_crosshair.render.DrawCrosshair;

public class BetterCrosshairClient implements ClientModInitializer {
    private static final Identifier HUD_LAYER = Identifier.of(BetterCrosshair.MOD_ID, "hud-layer");

    @Override
    public void onInitializeClient() {
        HudLayerRegistrationCallback.EVENT.register
                (layeredDrawer -> layeredDrawer.attachLayerBefore(IdentifiedLayer.CROSSHAIR, HUD_LAYER, DrawCrosshair::drawCrosshair));
    }
}
