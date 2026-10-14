package com.example.totemhighlight;

public final class TotemHighlight {

    /** Gesamtdauer der Markierung nach einem Pop (Millisekunden). */
    public static final long DURATION_MS = 2000;

    /** Die letzten Millisekunden, in denen die Markierung ausblendet. */
    public static final long FADE_MS = 600;

    private static long start = -1;

    private TotemHighlight() {}

    public static void trigger() {
        start = System.currentTimeMillis();
    }

    /** 0.0 = nicht aktiv, 1.0 = volle Staerke; blendet am Ende weich aus. */
    public static float intensity() {
        if (start < 0) {
            return 0f;
        }
        long remaining = DURATION_MS - (System.currentTimeMillis() - start);
        if (remaining <= 0) {
            return 0f;
        }
        return Math.min(1f, remaining / (float) FADE_MS);
    }
}
