package com.example.totemhighlight;

import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;

/** Markiert Totems in allen Inventar-Fenstern (Inventar, Truhen, Shulker, ...). */
public final class InventoryHighlight {

    private InventoryHighlight() {}

    public static void register() {
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof AbstractContainerScreen<?> containerScreen) {
                ScreenEvents.afterExtract(screen).register(
                        (s, graphics, mouseX, mouseY, tickDelta) -> draw(containerScreen, graphics));
            }
        });
    }

    private static void draw(AbstractContainerScreen<?> screen, GuiGraphicsExtractor g) {
        if (TotemHighlight.intensity() <= 0f) {
            return;
        }
        int left = screen.getLeftPos();
        int top = screen.getTopPos();
        for (Slot slot : screen.getMenu().slots) {
            if (HighlightRenderer.isTotem(slot.getItem())) {
                HighlightRenderer.draw(g, left + slot.x, top + slot.y, false);
            }
        }
    }
}
