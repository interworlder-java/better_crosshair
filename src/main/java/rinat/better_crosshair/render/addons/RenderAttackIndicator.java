package rinat.better_crosshair.render.addons;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.AttackIndicator;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import rinat.better_crosshair.config.Config;

public class RenderAttackIndicator {
    public static void renderAttackIndicator(MinecraftClient client, DrawContext context) {
        Identifier CROSSHAIR_ATTACK_INDICATOR_FULL_TEXTURE = Identifier.of("minecraft", "hud/crosshair_attack_indicator_full");
        Identifier CROSSHAIR_ATTACK_INDICATOR_BACKGROUND_TEXTURE = Identifier.of("minecraft", "hud/crosshair_attack_indicator_background");
        Identifier CROSSHAIR_ATTACK_INDICATOR_PROGRESS_TEXTURE = Identifier.of("minecraft", "hud/crosshair_attack_indicator_progress");

        if (client.options.getAttackIndicator().getValue() != AttackIndicator.CROSSHAIR) {
            return;
        }

        float cooldownProgress = client.player.getAttackCooldownProgress(0.0f);
        boolean showFull = false;

        if (client.targetedEntity instanceof LivingEntity target && cooldownProgress >= 1.0f) {
            showFull = client.player.getAttackCooldownProgressPerTick() > 5.0f && target.isAlive();
        }

        int x = context.getScaledWindowWidth() / 2 - 8;
        int y = context.getScaledWindowHeight() / 2 + Config.getConfigData().from_center_to_ai; // Below crosshair

        if (showFull) {
            context.drawGuiTexture(RenderLayer::getCrosshair, CROSSHAIR_ATTACK_INDICATOR_FULL_TEXTURE, x, y, 16, 16);
        } else if (cooldownProgress < 1.0f) {
            int width = (int)(cooldownProgress * 17.0f);
            context.drawGuiTexture(RenderLayer::getCrosshair, CROSSHAIR_ATTACK_INDICATOR_BACKGROUND_TEXTURE, x, y, 16, 4);
            context.drawGuiTexture(RenderLayer::getCrosshair, CROSSHAIR_ATTACK_INDICATOR_PROGRESS_TEXTURE, 16, 4, 0, 0, x, y, width, 4);
        }
    }
}
