package ru.harimasa.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.harimasa.config.HCScreen;
import ru.harimasa.listener.OverlayReloadListener;

import java.awt.*;

@Mixin(OverlayTexture.class)
public abstract class MixinOverlayTexture implements OverlayReloadListener {
	@Shadow
	@Final
	private NativeImageBackedTexture texture;

	@Inject(method = "<init>", at = @At("TAIL"))
	public void modifyHitColor(CallbackInfo ci) {
		this.reloadOverlay();
		OverlayReloadListener.register(this);
	}

	public void _1_21_7$onOverlayReload() {
		this.reloadOverlay();
	}

	@Unique
	private static int getColorInt(int red, int green, int blue, int alpha) {
		alpha = 255 - alpha;
		return (alpha << 24) + (blue << 16) + (green << 8) + red;
	}

	@Unique
	public void reloadOverlay() {
		NativeImage nativeImage = this.texture.getImage();

		for (int i = 0; i < 16; ++i) {
			for (int j = 0; j < 16; ++j) {
				if (i < 8) {
					Color color = HCScreen.CONFIG.instance().color;
					assert nativeImage != null;
					if (HCScreen.CONFIG.instance().enable) {
						nativeImage.setColor(j, i, getColorInt(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()));
					} else {
						nativeImage.setColor(j, i, -1308622593);
					}
				}
			}
			var a = RenderSystem.getShaderTexture(33985);
			RenderSystem.setShaderTexture(33985, a);
			texture.upload();
			var	b = RenderSystem.getShaderTexture(33984);
			RenderSystem.setShaderTexture(33984, b);
		}
	}
}