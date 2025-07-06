package ru.harimasa.config;

import dev.isxander.yacl3.config.v2.api.SerialEntry;

import java.awt.*;

public class HConfig {

    @SerialEntry
    public boolean enable = true;

    @SerialEntry
    public Color color = new Color(255,0,0);
}