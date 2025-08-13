package rinat.better_crosshair.render.addons;

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

        if (client.options.getAttackIndicator().getValue() == AttackIndicator.CROSSHAIR) {
            float f = client.player.getAttackCooldownProgress(0.0F);
            boolean bl = false;
            if (client.targetedEntity != null && client.targetedEntity instanceof LivingEntity && f >= 1.0F) {
                bl = client.player.getAttackCooldownProgressPerTick() > 5.0F;
                bl &= client.targetedEntity.isAlive();
            }

            int j = context.getScaledWindowHeight() / 2 - 7 + 16;
            int k = context.getScaledWindowWidth() / 2 - 8;
            if (bl) {
                context.drawGuiTexture(RenderLayer::getCrosshair, CROSSHAIR_ATTACK_INDICATOR_FULL_TEXTURE, k, j, 16, 16);
            } else if (f < 1.0F) {
                int l = (int)(f * 17.0F);
                context.drawGuiTexture(RenderLayer::getCrosshair, CROSSHAIR_ATTACK_INDICATOR_BACKGROUND_TEXTURE, k, j, 16, 4);
                context.drawGuiTexture(RenderLayer::getCrosshair, CROSSHAIR_ATTACK_INDICATOR_PROGRESS_TEXTURE, 16, 4, 0, 0, k, j, l, 4);
            }
        }
    }
}
