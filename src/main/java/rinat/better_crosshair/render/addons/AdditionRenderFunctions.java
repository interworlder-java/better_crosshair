package rinat.better_crosshair.render.addons;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;
import rinat.better_crosshair.config.Config;

public class AdditionRenderFunctions {

    public static int getRainbowColor() {
        float hue = (System.currentTimeMillis() % 1000L) / (float) Config.getConfigData().crosshair_rainbow_speed; // Cycle every 1 second
        int rgb = java.awt.Color.HSBtoRGB(hue, 1.0f, 1.0f); // Full saturation and brightness
        return 0xFF000000 | rgb; // Add alpha channel (opaque)
    }

    public static void fill(RenderLayer layer, float x1, float y1, float x2, float y2, int color, MatrixStack matrices, VertexConsumerProvider vertexConsumers) {
        int z = 5;

        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        if (x1 > x2) {
            float temp = x1;
            x1 = x2;
            x2 = temp;
        }
        if (y1 > y2) {
            float temp = y1;
            y1 = y2;
            y2 = temp;
        }

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(layer);
        vertexConsumer.vertex(matrix4f, x1, y1, (float)z).color(color);
        vertexConsumer.vertex(matrix4f, x1, y2, (float)z).color(color);
        vertexConsumer.vertex(matrix4f, x2, y2, (float)z).color(color);
        vertexConsumer.vertex(matrix4f, x2, y1, (float)z).color(color);
    }

    public static void drawCircle(MatrixStack matrices, float centerX, float centerY, double radius, int color) {
        Matrix4f mat = matrices.peek().getPositionMatrix();
        Tessellator tes = Tessellator.getInstance();

        BufferBuilder buf = tes.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
        buf.vertex(mat, centerX, centerY, 0).color(color);

        int i;
        for (i = 0; i < 360; i += 2){
            float x = (float) (Math.cos(Math.toRadians(i)) * radius + centerX);
            float y = (float) (Math.sin(Math.toRadians(i)) * radius + centerY);
            buf.vertex(mat, x, y, 0).color(color);
        }

        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        RenderSystem.setShaderColor(1, 1, 1, 1);

        BufferRenderer.drawWithGlobalProgram(buf.end());

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    public static void rotateCrosshair(MinecraftClient client, MatrixStack matrices) {
        int centerY = client.getWindow().getScaledHeight() / 2;
        int centerX = client.getWindow().getScaledWidth() / 2;

        int rotation = Config.getConfigData().crosshair_rotation;

        matrices.translate(centerX, centerY, 5);
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(rotation));
        matrices.translate(-centerX, -centerY, 5);
    }




    public static void spinCrosshair(MinecraftClient client, MatrixStack matrices) {
        int centerY = client.getWindow().getScaledHeight() / 2;
        int centerX = client.getWindow().getScaledWidth() / 2;

        long time = System.currentTimeMillis();
        float angle = (time % 3600) / 10.0f; // cycles every 36 seconds

        if (Config.getConfigData().spin_crosshair) {
            matrices.translate(centerX, centerY, 5);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(angle));
            matrices.translate(-centerX, -centerY, 5);
        }
    }
}
