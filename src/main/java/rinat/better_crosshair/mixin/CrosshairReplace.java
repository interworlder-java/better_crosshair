package rinat.better_crosshair.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class CrosshairReplace {
    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    private void cancelDefaultCrosshair(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        ci.cancel(); // This prevents the default crosshair from being drawn
    }
}
