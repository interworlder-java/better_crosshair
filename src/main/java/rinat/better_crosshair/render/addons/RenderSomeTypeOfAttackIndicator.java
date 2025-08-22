package rinat.better_crosshair.render.addons;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import rinat.better_crosshair.config.Config;

public class RenderSomeTypeOfAttackIndicator {
    public static void renderSomeTypeOfAttackIndicator(MinecraftClient client, MatrixStack matrices, VertexConsumerProvider vertexConsumers, DrawContext drawContext) {
        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;

        if (!Config.getConfigData().use_attack_indicator) {
            int color;

            if (Config.getConfigData().crosshair_rainbow) {
                color = AdditionRenderFunctions.getRainbowColor();
            } else {
                color = Config.getConfigData().crosshair_color;
            }

            int size = Config.getConfigData().crosshair_size + 2;
            int gap = Config.getConfigData().crosshair_gap;
            int width = Config.getConfigData().crosshair_width;

            int can_attack_color = Config.getConfigData().crosshair_attack_color;
            boolean use_can_attack_color = Config.getConfigData().use_different_color_then_can_attack_enemy;

            assert client.player != null;

            float f = client.player.getAttackCooldownProgress(0.0F);

            if (f >= 1.0f) {
                if (client.targetedEntity != null && client.targetedEntity.isAlive() && use_can_attack_color) {
                    AdditionRenderFunctions.fill(RenderLayer.getGuiOverlay(), centerX - size, centerY + ((float) width / 2), centerX - gap, centerY - ((float) width / 2), can_attack_color, matrices, vertexConsumers);
                    AdditionRenderFunctions.fill(RenderLayer.getGuiOverlay(), centerX + size, centerY - ((float) width / 2), centerX + gap, centerY + ((float) width / 2), can_attack_color, matrices, vertexConsumers);
                    AdditionRenderFunctions.fill(RenderLayer.getGuiOverlay(), centerX + ((float) width / 2), centerY - size, centerX - ((float) width / 2), centerY - gap, can_attack_color, matrices, vertexConsumers);
                    AdditionRenderFunctions.fill(RenderLayer.getGuiOverlay(), centerX - ((float) width / 2), centerY + size, centerX + ((float) width / 2), centerY + gap, can_attack_color, matrices, vertexConsumers);
                } else {
                    AdditionRenderFunctions.fill(RenderLayer.getGuiOverlay(), centerX - size, centerY + ((float) width / 2), centerX - gap, centerY - ((float) width / 2), color, matrices, vertexConsumers);
                    AdditionRenderFunctions.fill(RenderLayer.getGuiOverlay(), centerX + size, centerY - ((float) width / 2), centerX + gap, centerY + ((float) width / 2), color, matrices, vertexConsumers);
                    AdditionRenderFunctions.fill(RenderLayer.getGuiOverlay(), centerX + ((float) width / 2), centerY - size, centerX - ((float) width / 2), centerY - gap, color, matrices, vertexConsumers);
                    AdditionRenderFunctions.fill(RenderLayer.getGuiOverlay(), centerX - ((float) width / 2), centerY + size, centerX + ((float) width / 2), centerY + gap, color, matrices, vertexConsumers);
                }
            } else {
                float animation = (f + 1) * size; // smooth movement range from 0 to size

                if (client.targetedEntity != null && client.targetedEntity.isAlive() && use_can_attack_color) {
                    AdditionRenderFunctions.fill(RenderLayer.getGuiOverlay(), centerX - (size + (2 * size)) + animation, centerY + ((float) width / 2),
                            centerX - (gap + (2 * size)) + animation, centerY - ((float) width / 2),
                            can_attack_color, matrices, vertexConsumers);
                    AdditionRenderFunctions.fill(RenderLayer.getGuiOverlay(), centerX + (size + (2 * size)) - animation, centerY - ((float) width / 2),
                            centerX + (gap + (2 * size)) - animation, centerY + ((float) width / 2),
                            can_attack_color, matrices, vertexConsumers);
                    AdditionRenderFunctions.fill(RenderLayer.getGuiOverlay(), centerX + ((float) width / 2), centerY - (size + (2 * size)) + animation,
                            centerX - ((float) width / 2), centerY - (gap + (2 * size)) + animation,
                            can_attack_color, matrices, vertexConsumers);
                    AdditionRenderFunctions.fill(RenderLayer.getGuiOverlay(), centerX - ((float) width / 2), centerY + (size + (2 * size)) - animation,
                            centerX + ((float) width / 2), centerY + (gap + (2 * size)) - animation,
                            can_attack_color, matrices, vertexConsumers);
                } else {
                    AdditionRenderFunctions.fill(RenderLayer.getGuiOverlay(), centerX - (size + (2 * size)) + animation, centerY + ((float) width / 2),
                            centerX - (gap + (2 * size)) + animation, centerY - ((float) width / 2),
                            color, matrices, vertexConsumers);
                    AdditionRenderFunctions.fill(RenderLayer.getGuiOverlay(), centerX + (size + (2 * size)) - animation, centerY - ((float) width / 2),
                            centerX + (gap + (2 * size)) - animation, centerY + ((float) width / 2),
                            color, matrices, vertexConsumers);
                    AdditionRenderFunctions.fill(RenderLayer.getGuiOverlay(), centerX + ((float) width / 2), centerY - (size + (2 * size)) + animation,
                            centerX - ((float) width / 2), centerY - (gap + (2 * size)) + animation,
                            color, matrices, vertexConsumers);
                    AdditionRenderFunctions.fill(RenderLayer.getGuiOverlay(), centerX - ((float) width / 2), centerY + (size + (2 * size)) - animation,
                            centerX + ((float) width / 2), centerY + (gap + (2 * size)) - animation,
                            color, matrices, vertexConsumers);
                }
            }
        } else {
            RenderAttackIndicator.renderAttackIndicator(client, drawContext);
        }
    }
}
