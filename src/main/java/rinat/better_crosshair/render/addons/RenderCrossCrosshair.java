package rinat.better_crosshair.render.addons;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import rinat.better_crosshair.config.Config;

public class RenderCrossCrosshair {
    public static void renderCrossCrosshair(MinecraftClient client, MatrixStack matrices, VertexConsumerProvider vertexConsumers) {
        int centerY = client.getWindow().getScaledHeight() / 2;
        int centerX = client.getWindow().getScaledWidth() / 2;

        int color;

        if (Config.getConfigData().crosshair_rainbow) {
            color = AdditionRenderFunctions.getRainbowColor();
        } else {
            color = Config.getConfigData().crosshair_color;
        }

        int can_attack_color = Config.getConfigData().crosshair_attack_color;
        boolean use_can_attack_color = Config.getConfigData().use_different_color_then_can_attack_enemy;

        int size = Config.getConfigData().crosshair_size + 2;
        int gap = Config.getConfigData().crosshair_gap;
        int width = Config.getConfigData().crosshair_width;

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
    }
}
