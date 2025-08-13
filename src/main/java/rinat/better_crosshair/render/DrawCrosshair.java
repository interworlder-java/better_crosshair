package rinat.better_crosshair.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.AttackIndicator;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.world.GameMode;
import rinat.better_crosshair.config.Config;
import rinat.better_crosshair.render.addons.*;

public class DrawCrosshair {
    public static void drawCrosshair(DrawContext drawContext, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();

        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;

        if (!Config.getConfigData().enable) {
            RenderStandardCrosshair.renderStandardCrosshair(client, drawContext, centerY, centerX);
        } else {
            VertexConsumerProvider.Immediate vertexConsumers = client.getBufferBuilders().getEntityVertexConsumers();

            if (client.options.getPerspective().isFirstPerson()) {
                assert client.interactionManager != null;
                if (client.interactionManager.getCurrentGameMode() != GameMode.SPECTATOR) {
                    MatrixStack matrices = drawContext.getMatrices();

                    matrices.push();

                    AdditionRenderFunctions.spinCrosshair(client, matrices);

                    AdditionRenderFunctions.rotateCrosshair(client, matrices);

                    if (!Config.getConfigData().use_standard_crosshair) {
                        RenderItemCrosshair.renderItemCrosshair(client, drawContext);
                    } else if (Config.getConfigData().use_attack_indicator) {
                        RenderCrossCrosshair.renderCrossCrosshair(client, matrices, vertexConsumers);
                    }

                    matrices.pop();

                    if (client.options.getAttackIndicator().getValue() == AttackIndicator.CROSSHAIR) {
                        RenderSomeTypeOfAttackIndicator.renderSomeTypeOfAttackIndicator(client, matrices, vertexConsumers, drawContext);
                    }

                    if (Config.getConfigData().dot_enable) {
                        float radius = (float) Config.getConfigData().dot_radius / 10;
                        int color = Config.getConfigData().dot_color;

                        AdditionRenderFunctions.drawCircle(matrices, centerX, centerY, radius, color, vertexConsumers);
                    }
                }
            }
        }
    }
}
