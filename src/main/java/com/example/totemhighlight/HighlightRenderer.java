package com.example.totemhighlight;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class HighlightRenderer {

    private static final int GOLD = 0xFFC933;
    private static final int LIGHT_GOLD = 0xFFF1A6;

    private HighlightRenderer() {}

    /**
     * Zeichnet die Markierung fuer einen 16x16-Item-Slot an Position (x, y).
     * wide = true: breiterer Glow (Hotbar, viel Platz). false: Inventar (Slots liegen enger).
     */
    public static void draw(GuiGraphicsExtractor g, int x, int y, boolean wide) {
        float fade = TotemHighlight.intensity();
        if (fade <= 0f) {
            return;
        }
        // sanftes Pulsieren (ca. 1,4x pro Sekunde)
        float pulse = 0.5f + 0.5f * (float) Math.sin(System.currentTimeMillis() / 110.0);
        float a = fade * (0.72f + 0.28f * pulse);

        // leichte goldene Toenung, Item bleibt gut sichtbar
        g.fill(x, y, x + 16, y + 16, argb(a * 0.20f, GOLD));

        // Glow-Ringe von aussen nach innen, abgerundete Ecken
        if (wide) {
            outline(g, x - 3, y - 3, x + 19, y + 19, argb(a * 0.14f, GOLD));
        }
        outline(g, x - 2, y - 2, x + 18, y + 18, argb(a * 0.32f, GOLD));
        // heller Rahmen direkt um den Slot
        outline(g, x - 1, y - 1, x + 17, y + 17, argb(a, LIGHT_GOLD));
    }

    /** Hotbar-Markierung (wird nach der Vanilla-Hotbar gezeichnet). */
    public static void renderHotbar(GuiGraphicsExtractor g) {
        if (TotemHighlight.intensity() <= 0f) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null || mc.screen instanceof AbstractContainerScreen<?>) {
            return; // im Inventar uebernimmt die Inventar-Markierung
        }

        int width = mc.getWindow().getGuiScaledWidth();
        int height = mc.getWindow().getGuiScaledHeight();
        int y = height - 16 - 3;

        for (int i = 0; i < 9; i++) {
            if (isTotem(player.getInventory().getItem(i))) {
                draw(g, width / 2 - 90 + i * 20 + 2, y, true);
            }
        }
        if (isTotem(player.getOffhandItem())) {
            int x = player.getMainArm() == HumanoidArm.RIGHT
                    ? width / 2 - 91 - 26
                    : width / 2 + 91 + 10;
            draw(g, x, y, true);
        }
    }

    public static boolean isTotem(ItemStack stack) {
        return !stack.isEmpty() && stack.is(Items.TOTEM_OF_UNDYING);
    }

    private static int argb(float alpha, int rgb) {
        int a = Math.round(Math.max(0f, Math.min(1f, alpha)) * 255f);
        return (a << 24) | (rgb & 0xFFFFFF);
    }

    /** 1px-Rahmen mit abgerundeten Ecken (Eckpixel bleiben frei). */
    private static void outline(GuiGraphicsExtractor g, int x1, int y1, int x2, int y2, int color) {
        g.fill(x1 + 1, y1, x2 - 1, y1 + 1, color);     // oben
        g.fill(x1 + 1, y2 - 1, x2 - 1, y2, color);     // unten
        g.fill(x1, y1 + 1, x1 + 1, y2 - 1, color);     // links
        g.fill(x2 - 1, y1 + 1, x2, y2 - 1, color);     // rechts
    }
}
