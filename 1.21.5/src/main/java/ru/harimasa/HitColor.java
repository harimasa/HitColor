package ru.harimasa;

import ru.harimasa.config.HCScreen;
import ru.harimasa.listener.OverlayReloadListener;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class HitColor implements ModInitializer {

	@Override
	public void onInitialize() {
		HCScreen.CONFIG.load();
		ClientTickEvents.END_WORLD_TICK.register((client) -> OverlayReloadListener.callEvent());
	}
}