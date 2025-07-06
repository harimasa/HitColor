package ru.harimasa.mixin;

import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.harimasa.config.HCScreen;
import ru.harimasa.listener.OverlayReloadListener;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

	public void onOverlayReload() {
		this.reloadOverlay();
	}

	private static int getColorInt(int red, int green, int blue, int alpha) {
		alpha = 255 - alpha;
		return (alpha << 24) + (blue << 16) + (green << 8) + red;
	}

	public void reloadOverlay() {
		NativeImage nativeImage = this.texture.getImage();
		Class<?> clazz = NativeImage.class;
		Method setColorNotPrivate = null;
		try {
			setColorNotPrivate = clazz.getDeclaredMethod("setColor", int.class, int.class, int.class);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
		setColorNotPrivate.setAccessible(true);

		for (int i = 0; i < 16; ++i) {
			for (int j = 0; j < 16; ++j) {
				if (i < 8) {
					Color color = HCScreen.CONFIG.instance().color;
					assert nativeImage != null;
					if (HCScreen.CONFIG.instance().enable) {
						try {
							setColorNotPrivate.invoke(nativeImage, j, i, getColorInt(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()));
						} catch (IllegalAccessException | InvocationTargetException e) {
							throw new RuntimeException(e);
						}
					} else {
						try {
							setColorNotPrivate.invoke(nativeImage,j, i, -1308622593);
						} catch (IllegalAccessException | InvocationTargetException e) {
							throw new RuntimeException(e);
						}
					}
				}
			}
			GlStateManager._activeTexture(33985);
			texture.upload();
			GlStateManager._activeTexture(33984);
		}
	}
}