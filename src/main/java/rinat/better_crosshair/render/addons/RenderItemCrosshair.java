package rinat.better_crosshair.render.addons;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import rinat.better_crosshair.config.Config;

public class RenderItemCrosshair {
    public static void renderItemCrosshair(MinecraftClient client, DrawContext drawContext) {
        int centerY = client.getWindow().getScaledHeight() / 2;
        int centerX = client.getWindow().getScaledWidth() / 2;

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
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
