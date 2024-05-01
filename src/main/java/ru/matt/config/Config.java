package ru.matt.config;

import com.mojang.blaze3d.platform.GlConst;
import com.mojang.blaze3d.systems.RenderSystem;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.text.Text;
import ru.matt.HitColor;

import java.awt.*;

public class Config {
    public static String color = "#ff0000";
    public static float alpha = 32.8f;
    private static Integer processedColor;

    public static ConfigBuilder getConfigBuilder() {
        ConfigBuilder builder = ConfigBuilder.create().setTitle(Text.translatable("title.hitcolor.config"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ConfigCategory colors = builder.getOrCreateCategory(Text.translatable("category.hitcolor.colors"));
        colors.addEntry(entryBuilder.startColorField(Text.translatable("options.hitcolor.hitcolor"), 0x00ffff).setDefaultValue(0x00ffff).setAlphaMode(true).build());
        builder.transparentBackground();
        return builder;
    }

    public static int getColor() {
        if (processedColor == null) {
            updateColor();
        }
        return processedColor;
    }

    public static void updateColor() {
        Color rgbColor;
        try {
            rgbColor = new Color(Integer.parseInt(color.substring(1), 16));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            if (processedColor == null) {
                processedColor = -1308622593;
            }
            return;
        }
        processedColor = rgbColor.getRed() | rgbColor.getGreen() << 8 | rgbColor.getBlue() << 16 | Math.round(0xFF * (1 - alpha / 100)) << 24;
    }

    public static void updateHitcolor() {
        NativeImage nativeImage = HitColor.texture.getImage();
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                if (i < 8) {
                    nativeImage.setColor(j, i, processedColor);
                    continue;
                }
                int k = (int)((1.0f - (float)j / 15.0f * 0.75f) * 255.0f);
                nativeImage.setColor(j, i, k << 24 | 0xFFFFFF);
            }
        }
        RenderSystem.activeTexture(GlConst.GL_TEXTURE1);
        HitColor.texture.bindTexture();
        nativeImage.upload(0, 0, 0, 0, 0, nativeImage.getWidth(), nativeImage.getHeight(), false, true, false, false);
        RenderSystem.activeTexture(GlConst.GL_TEXTURE0);
    }
}