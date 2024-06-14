package ru.matt.api;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import ru.matt.config.AutoConfig;

@Environment(EnvType.CLIENT)
public class ModMenuInit implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> me.shedaniel.autoconfig.AutoConfig.getConfigScreen(AutoConfig.class, parent).get();
    }
}