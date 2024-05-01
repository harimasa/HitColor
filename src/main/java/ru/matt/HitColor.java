package ru.matt;

import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.texture.NativeImageBackedTexture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.matt.config.Config;

public class HitColor implements ClientModInitializer {
	public static NativeImageBackedTexture texture = null;
    public static final Logger LOGGER = LoggerFactory.getLogger("hitcolor");

	@Override
	public void onInitializeClient() {

	}
}