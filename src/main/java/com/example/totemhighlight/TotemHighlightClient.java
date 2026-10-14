package com.example.totemhighlight;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.resources.Identifier;

public class TotemHighlightClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HudElementRegistry.attachElementAfter(
                VanillaHudElements.HOTBAR,
                Identifier.fromNamespaceAndPath("totemhighlight", "hotbar_highlight"),
                (graphics, deltaTracker) -> HighlightRenderer.renderHotbar(graphics));
    }
}
