package ru.femboypig.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.gui.controllers.ColorController;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.awt.*;

public class HCScreen {
    public static final ConfigClassHandler<HConfig> CONFIG = ConfigClassHandler.createBuilder(HConfig.class)
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("hitcolor.json"))
                    .build())
            .build();

    @SuppressWarnings("deprecation")
    public static Screen configScreen(Screen parent) {
        return YetAnotherConfigLib.create(CONFIG, ((defaults, config, builder) -> builder
                .title(Text.translatable("hitcolor.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("hitcolor.title"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("hitcolor.title"))
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.translatable("hitcolor.enable"))
                                        .description(OptionDescription.of(Text.translatable("hitcolor.enable")))
                                        .binding(defaults.enable, () -> config.enable, newVal -> config.enable = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Color>createBuilder()
                                        .name(Text.translatable("hitcolor.color"))
                                        .binding(defaults.color, () -> config.color, value -> config.color = value)
                                        .customController(opt -> new ColorController(opt, true))
                                        .build())
                                .build())
                        .build())
        )).generateScreen(parent);
    }
}