package ru.femboypig;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import ru.femboypig.config.HCScreen;
import ru.femboypig.listener.OverlayReloadListener;

public class HitColor implements ModInitializer {

	@Override
	public void onInitialize() {
		HCScreen.CONFIG.load();
		ClientTickEvents.END_WORLD_TICK.register((client) -> {
			OverlayReloadListener.callEvent();
		});
	}
}