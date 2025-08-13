package rinat.better_crosshair;

import net.fabricmc.api.ModInitializer;
import rinat.better_crosshair.config.Config;

public class BetterCrosshair implements ModInitializer {
    public static final String MOD_ID = "better_crosshair";

    @Override
    public void onInitialize() {
        Config.getConfigData();

    }
}
