package rinat.better_crosshair.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.AttackIndicator;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.GameMode;
import rinat.better_crosshair.config.Config;

public class DrawCrosshair {
    public static void drawCrosshair() {
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
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
                        long time = System.currentTimeMillis();
                        float angle = (time % 3600) / 10.0f; // cycles every 36 seconds
                        matrices.push();
                        if (Config.getConfigData().spin_crosshair) {
                            matrices.translate(centerX, centerY, 5);
                            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(angle));
                            matrices.translate(-centerX, -centerY, 5);
                        }

                        int rotation = Config.getConfigData().crosshair_rotation;

                        matrices.translate(centerX, centerY, 5);
                        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(rotation));
                        matrices.translate(-centerX, -centerY, 5);

                        if (!Config.getConfigData().use_standard_crosshair) {
                            if (!Config.getConfigData().use_standard_crosshair) {
                                if (Config.getConfigData().use_item_as_crosshair) {
                                    assert client.player != null;
                                    if (client.player.getMainHandStack().isEmpty()) {
                                        Identifier id = Identifier.of(Config.getConfigData().choosen_item);
                                        ItemStack stack = Registries.ITEM.get(id).getDefaultStack();
                                        drawContext.drawItem(stack, centerX - 8, centerY - 8);
                                    } else {
                                        drawContext.drawItem(client.player.getMainHandStack(), centerX - 8, centerY - 8);
                                    }
                                } else {
                                    Identifier id = Identifier.of(Config.getConfigData().choosen_item);
                                    ItemStack stack = Registries.ITEM.get(id).getDefaultStack();
                                    drawContext.drawItem(stack, centerX - 8, centerY - 8);
                                }
                                // Make sure the correct shader for your chosen vertex format is set!
                                // You can find all the shaders in the ShaderProgramKeys class.
                                RenderSystem.setShader(GameRenderer::getPositionColorProgram);
                                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

                            }
                        } else if (client.options.getAttackIndicator().getValue() != AttackIndicator.CROSSHAIR) {
                            int color;

                            if (Config.getConfigData().crosshair_rainbow) {
                                color = AdditionRenderFunctions.getRainbowColor();
                            } else {
                                color = Config.getConfigData().crosshair_color;
                            }

                            int size = Config.getConfigData().crosshair_size + 2;
                            int gap = Config.getConfigData().crosshair_gap;
                            int width = Config.getConfigData().crosshair_width;

                            AdditionRenderFunctions.fill(RenderLayer.getGuiOverlay(), centerX - size, centerY + ((float) width / 2), centerX - gap, centerY - ((float) width / 2), color, matrices, vertexConsumers);
                            AdditionRenderFunctions.fill(RenderLayer.getGuiOverlay(), centerX + size, centerY - ((float) width / 2), centerX + gap, centerY + ((float) width / 2), color, matrices, vertexConsumers);
                            AdditionRenderFunctions.fill(RenderLayer.getGuiOverlay(), centerX + ((float) width / 2), centerY - size, centerX - ((float) width / 2), centerY - gap, color, matrices, vertexConsumers);
                            AdditionRenderFunctions.fill(RenderLayer.getGuiOverlay(), centerX - ((float) width / 2), centerY + size, centerX + ((float) width / 2), centerY + gap, color, matrices, vertexConsumers);
                        }

                        if (!Config.getConfigData().use_attack_indicator) matrices.pop();

                        if (client.options.getAttackIndicator().getValue() == AttackIndicator.CROSSHAIR) {
                            RenderSomeTypeOfAttackIndicator.renderSomeTypeOfAttackIndicator(client, matrices, vertexConsumers, drawContext);
                        }

                        AdditionRenderFunctions.drawCircle(matrices, centerX, centerY, 0.7, 0xFF00BB00);
                    }
                }
            }
        });
    }
}
