package ru.matt;

import com.mojang.brigadier.Command;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.matt.config.AutoConfig;
import ru.matt.iFace.UpdateOverlay;

public class HitColor implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("hitcolor");
	public static final AutoConfig MOD_CONFIG = me.shedaniel.autoconfig.AutoConfig.register(AutoConfig.class, GsonConfigSerializer::new).get();
	public static final MinecraftClient mc = MinecraftClient.getInstance();

	@Override
	public void onInitializeClient() {
		AutoConfig config = me.shedaniel.autoconfig.AutoConfig.getConfigHolder(AutoConfig.class).getConfig();
		register();
	}
	public void register(){
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
				dispatcher.register(ClientCommandManager.literal("hitcolor")
						.executes(context -> {
							mc.player.sendMessage(Text.translatable("text.command.hitcolor.reloaded"));
							((UpdateOverlay)context.getSource().getClient().gameRenderer.getOverlayTexture()).updateOverlay();
							return Command.SINGLE_SUCCESS;
						})));
	}
}