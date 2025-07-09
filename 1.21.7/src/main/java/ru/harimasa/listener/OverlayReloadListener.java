package ru.harimasa.listener;

import java.util.ArrayList;
import java.util.List;

public interface OverlayReloadListener {
    List<OverlayReloadListener> listeners = new ArrayList<>();
    void _1_21_7$onOverlayReload();

    static void register(OverlayReloadListener listener) {
        listeners.add(listener);
    }

    static void callEvent() {
        for (OverlayReloadListener listener : listeners) {
            listener._1_21_7$onOverlayReload();
        }
    }
}