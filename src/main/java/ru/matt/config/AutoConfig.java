package ru.matt.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

import static ru.matt.HitColor.MOD_CONFIG;

@Config(name = "hitcolor")
public class AutoConfig implements ConfigData {

    @Comment("Hex-Code Color")
    @ConfigEntry.Category("Color")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public String color = "#ff0000";

    @Comment("Alpha")
    @ConfigEntry.Category("Color")
    @ConfigEntry.BoundedDiscrete(max = 100)
    public int alpha = 32;

    public static String color() {
        return MOD_CONFIG.color;
    }
    public static int alpha() {
        return MOD_CONFIG.alpha;
    }
}