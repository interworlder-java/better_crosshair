package rinat.better_crosshair.render.addons;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.AttackIndicator;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import org.joml.Matrix4fStack;

public class RenderStandardCrosshair {
    private static MinecraftClient minecraftClient;
    public static void renderStandardCrosshair(MinecraftClient client, DrawContext context, int centerY, int centerX) {
        minecraftClient = client;

        Identifier CROSSHAIR_TEXTURE = Identifier.ofVanilla("hud/crosshair");

        Identifier CROSSHAIR_ATTACK_INDICATOR_FULL_TEXTURE = Identifier.of("minecraft", "hud/crosshair_attack_indicator_full");
        Identifier CROSSHAIR_ATTACK_INDICATOR_BACKGROUND_TEXTURE = Identifier.of("minecraft", "hud/crosshair_attack_indicator_background");
        Identifier CROSSHAIR_ATTACK_INDICATOR_PROGRESS_TEXTURE = Identifier.of("minecraft", "hud/crosshair_attack_indicator_progress");

        GameOptions gameOptions = client.options;
        if (gameOptions.getPerspective().isFirstPerson()) {
            if (client.interactionManager.getCurrentGameMode() != GameMode.SPECTATOR || shouldRenderSpectatorCrosshair(client.crosshairTarget)) {
                if (client.getDebugHud().shouldShowDebugHud() && !client.player.hasReducedDebugInfo() && !(Boolean)gameOptions.getReducedDebugInfo().getValue()) {
                    Camera camera = client.gameRenderer.getCamera();
                    Matrix4fStack matrix4fStack = RenderSystem.getModelViewStack();
                    matrix4fStack.pushMatrix();
                    matrix4fStack.mul(context.getMatrices().peek().getPositionMatrix());
                    matrix4fStack.translate((float)(context.getScaledWindowWidth() / 2), (float)(context.getScaledWindowHeight() / 2), 0.0F);
                    matrix4fStack.rotateX(-camera.getPitch() * ((float)Math.PI / 180F));
                    matrix4fStack.rotateY(camera.getYaw() * ((float)Math.PI / 180F));
                    matrix4fStack.scale(-1.0F, -1.0F, -1.0F);
                    client.getDebugHud().renderDebugCrosshair();
                    matrix4fStack.popMatrix();
                } else {
                    int i = 15;
                    context.drawGuiTexture(RenderLayer::getCrosshair, CROSSHAIR_TEXTURE, (context.getScaledWindowWidth() - 15) / 2, (context.getScaledWindowHeight() - 15) / 2, 15, 15);
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
        }
    }

    private static boolean shouldRenderSpectatorCrosshair(HitResult hitResult) {
        if (hitResult == null) {
            return false;
        }
        if (hitResult.getType() == HitResult.Type.ENTITY) {
            return ((EntityHitResult)hitResult).getEntity() instanceof NamedScreenHandlerFactory;
        }
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            ClientWorld world = minecraftClient.world;
            BlockPos blockPos = ((BlockHitResult)hitResult).getBlockPos();
            return world.getBlockState(blockPos).createScreenHandlerFactory(world, blockPos) != null;
        }
        return false;
    }
}
