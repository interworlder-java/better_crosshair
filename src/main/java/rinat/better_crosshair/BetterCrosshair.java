package rinat.better_crosshair;

import net.fabricmc.api.ModInitializer;
import rinat.better_crosshair.config.Config;

public class BetterCrosshair implements ModInitializer {

    @Override
    public void onInitialize() {
        Config.getConfigData();

    }
}
