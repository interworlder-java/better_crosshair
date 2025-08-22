package rinat.better_crosshair.config;

import com.google.gson.Gson;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Config {
    private static final Path config_dir = Paths.get(MinecraftClient.getInstance().runDirectory.getPath() + "/config");
    private static final Path config_file = Paths.get(config_dir + "/better_crosshair.json");
    private static ConfigData config_data;

    public static ConfigData getConfigData() {
        if (config_data != null) return config_data;

        try {
            if (!Files.exists(config_file)) {
                Files.createDirectories(config_dir);
                Files.createFile(config_file);
                config_data = ConfigData.getDefault();
                config_data.save();
                return config_data;
            }
        } catch (IOException e) {
            e.printStackTrace();
            config_data = ConfigData.getDefault();
            return config_data;
        }
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(config_file.toFile());
            config_data = gson.fromJson(reader, ConfigData.class);
        } catch (IOException e) {
            e.printStackTrace();
            config_data = ConfigData.getDefault();
        }
        return config_data;
    }

    public static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent).setTitle(Text.translatable("screen.better_crosshair.config.title"));

        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("config.better_crosshair.category.general"));
        ConfigCategory color = builder.getOrCreateCategory(Text.translatable("config.better_crosshair.category.color"));
        ConfigCategory size = builder.getOrCreateCategory(Text.translatable("config.better_crosshair.category.size"));
        ConfigCategory dot = builder.getOrCreateCategory(Text.translatable("config.better_crosshair.category.dot"));
        ConfigCategory fun = builder.getOrCreateCategory(Text.translatable("config.better_crosshair.category.fun"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        dot.addEntry(entryBuilder.startBooleanToggle(
                                Text.translatable("config.better_crosshair.dot_enabled"),
                                config_data.dot_enable
                        )
                        .setDefaultValue(false)
                        .setSaveConsumer(newValue -> config_data.dot_enable = newValue)
                        .build()
        );

        dot.addEntry(entryBuilder.startIntSlider(
                                Text.translatable("config.better_crosshair.dot_radius"),
                                config_data.dot_radius,
                                1, 30
                        )
                        .setDefaultValue(8)
                        .setSaveConsumer(newValue -> config_data.dot_radius = newValue)
                        .build()
        );

        dot.addEntry(entryBuilder.startAlphaColorField(
                                Text.translatable("config.better_crosshair.dot_color"),
                                config_data.dot_color
                        )
                        .setDefaultValue(-1)
                        .setSaveConsumer(newValue -> config_data.dot_color = newValue)
                        .build()
        );

        general.addEntry(entryBuilder.startBooleanToggle(
                                Text.translatable("config.better_crosshair.enable"),
                                config_data.enable
                        )
                        .setDefaultValue(true)
                        .setSaveConsumer(newValue -> config_data.enable = newValue)
                        .build()
        );

        fun.addEntry(entryBuilder.startBooleanToggle(
                                Text.translatable("config.better_crosshair.use_cross_crosshair"),
                                config_data.use_standard_crosshair
                        )
                        .setDefaultValue(true)
                        .setSaveConsumer(newValue -> config_data.use_standard_crosshair = newValue)
                        .build()
        );

        fun.addEntry(entryBuilder.startBooleanToggle(
                                Text.translatable("config.better_crosshair.spin_crosshair"),
                                config_data.spin_crosshair
                        )
                        .setDefaultValue(false)
                        .setSaveConsumer(newValue -> config_data.spin_crosshair = newValue)
                        .build()
        );

        fun.addEntry(entryBuilder.startBooleanToggle(
                                Text.translatable("config.better_crosshair.use_item_as_crosshair"),
                                config_data.use_item_as_crosshair
                        )
                        .setDefaultValue(false)
                        .setSaveConsumer(newValue -> config_data.use_item_as_crosshair = newValue)
                        .build()
        );

        fun.addEntry(entryBuilder.startStrField(
                                Text.translatable("config.better_crosshair.choose_item"),
                                config_data.choosen_item
                        )
                        .setDefaultValue("potato")
                        .setSaveConsumer(newValue -> config_data.choosen_item = newValue)
                        .build()
        );

        size.addEntry(entryBuilder.startBooleanToggle(
                                Text.translatable("config.better_crosshair.use_attack_indicator"),
                                config_data.use_attack_indicator
                        )
                        .setDefaultValue(false)
                        .setSaveConsumer(newValue -> config_data.use_attack_indicator = newValue)
                        .build()
        );

        size.addEntry(entryBuilder.startIntSlider(
                                Text.translatable("config.better_crosshair.from_center_to_attack_indicator"),
                                config_data.from_center_to_ai,
                                -50, 50
                        )
                        .setDefaultValue(9)
                        .setSaveConsumer(newValue -> config_data.from_center_to_ai = newValue)
                        .build()
        );

        size.addEntry(entryBuilder.startIntSlider(
                                Text.translatable("config.better_crosshair.crosshair_size"),
                                config_data.crosshair_size,
                                2, 50
                        )
                        .setDefaultValue(7)
                        .setSaveConsumer(newValue -> config_data.crosshair_size = newValue)
                        .build()
        );

        size.addEntry(entryBuilder.startIntSlider(
                                Text.translatable("config.better_crosshair.crosshair_gap"),
                                config_data.crosshair_gap,
                                0, 32
                        )
                        .setDefaultValue(2)
                        .setSaveConsumer(newValue -> config_data.crosshair_gap = newValue)
                        .build()
        );

        size.addEntry(entryBuilder.startIntSlider(
                                Text.translatable("config.better_crosshair.crosshair_width"),
                                config_data.crosshair_width,
                                1, 16
                        )
                        .setDefaultValue(2)
                        .setSaveConsumer(newValue -> config_data.crosshair_width = newValue)
                        .build()
        );

        size.addEntry(entryBuilder.startIntSlider(
                                Text.translatable("config.better_crosshair.crosshair_rotation"),
                                config_data.crosshair_rotation,
                                0, 360
                        )
                        .setDefaultValue(0)
                        .setSaveConsumer(newValue -> config_data.crosshair_rotation = newValue)
                        .build()
        );

        color.addEntry(entryBuilder.startAlphaColorField(
                                Text.translatable("config.better_crosshair.crosshair_color"),
                                config_data.crosshair_color
                        )
                        .setDefaultValue(-1)
                        .setSaveConsumer(newValue -> config_data.crosshair_color = newValue)
                        .build()
        );

        color.addEntry(entryBuilder.startBooleanToggle(
                                Text.translatable("config.better_crosshair.use_rainbow_colors"),
                                config_data.crosshair_rainbow
                        )
                        .setDefaultValue(false)
                        .setSaveConsumer(newValue -> config_data.crosshair_rainbow = newValue)
                        .build()
        );

        color.addEntry(entryBuilder.startIntSlider(
                                Text.translatable("config.better_crosshair.rainbow_colors_speed"),
                                config_data.crosshair_rainbow_speed,
                                50, 2000
                        )
                        .setDefaultValue(750)
                        .setSaveConsumer(newValue -> config_data.crosshair_rainbow_speed = newValue)
                        .build()
        );

        color.addEntry(entryBuilder.startBooleanToggle(
                                Text.translatable("config.better_crosshair.use_different_color_then_can_attack_enemy"),
                                config_data.use_different_color_then_can_attack_enemy
                        )
                        .setDefaultValue(true)
                        .setSaveConsumer(newValue -> config_data.use_different_color_then_can_attack_enemy = newValue)
                        .build()
        );

        color.addEntry(entryBuilder.startAlphaColorField(
                                Text.translatable("config.better_crosshair.crosshair_attack_color"),
                                config_data.crosshair_attack_color
                        )
                        .setDefaultValue(0xFFFF0000)
                        .setSaveConsumer(newValue -> config_data.crosshair_attack_color = newValue)
                        .build()
        );

        builder.setSavingRunnable(config_data::save);

        return builder.build();
    }

    public static class ConfigData {
        public boolean enable;

        public int from_center_to_ai;
        public int crosshair_color;
        public int crosshair_attack_color;
        public int crosshair_size;
        public int crosshair_gap;
        public int crosshair_width;
        public int crosshair_rainbow_speed;
        public int crosshair_rotation;

        public boolean use_standard_crosshair;
        public boolean spin_crosshair;
        public boolean use_item_as_crosshair;
        public boolean use_attack_indicator;
        public boolean crosshair_rainbow;
        public boolean dot_enable;
        public boolean use_different_color_then_can_attack_enemy;
        public int dot_color;
        public int dot_radius;

        public String choosen_item;

        public ConfigData(boolean enable,

                          int from_center_to_ai,
                          int crosshair_color,
                          boolean use_standard_crosshair, boolean spin_crosshair,
                          boolean use_item_as_crosshair, boolean crosshair_rainbow,
                          boolean use_attack_indicator, boolean dot_enable,
                          int crosshair_size, int crosshair_gap,
                          int crosshair_width, int crosshair_rainbow_speed,
                          int crosshair_rotation, int crosshair_attack_color,
                          int dot_color, int dot_radius,
                          boolean use_different_color_then_can_attack_enemy,
                          String choosen_item) {
            this.enable = enable;

            this.from_center_to_ai = from_center_to_ai;
            this.crosshair_color = crosshair_color;
            this.crosshair_attack_color = crosshair_attack_color;
            this.use_standard_crosshair = use_standard_crosshair;
            this.crosshair_size = crosshair_size;
            this.crosshair_gap = crosshair_gap;
            this.crosshair_width = crosshair_width;
            this.choosen_item = choosen_item;
            this.spin_crosshair = spin_crosshair;
            this.crosshair_rainbow_speed = crosshair_rainbow_speed;
            this.crosshair_rainbow = crosshair_rainbow;
            this.use_item_as_crosshair = use_item_as_crosshair;
            this.use_attack_indicator = use_attack_indicator;
            this.crosshair_rotation = crosshair_rotation;
            this.use_different_color_then_can_attack_enemy = use_different_color_then_can_attack_enemy;
            this.dot_enable = dot_enable;
            this.dot_color = dot_color;
            this.dot_radius = dot_radius;
        }

        public static ConfigData getDefault() {
            return new ConfigData(
                    true,

                    9,
                    -1,
                    true,
                    false,
                    false,
                    false,
                    false,
                    false,
                    7,
                    2,
                    2,
                    750,
                    0,
                    0xFFFF0000,
                    0xFF00FF00,
                    10,
                    true,
                    "potato");
        }

        public void save() {
            try {
                Gson gson = new Gson();
                FileWriter writer = new FileWriter(config_file.toFile());
                gson.toJson(this, writer);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}