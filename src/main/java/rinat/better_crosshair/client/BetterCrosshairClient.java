package rinat.better_crosshair.client;

import net.fabricmc.api.ClientModInitializer;
import rinat.better_crosshair.render.DrawCrosshair;

public class BetterCrosshairClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        DrawCrosshair.drawCrosshair();
    }
}
