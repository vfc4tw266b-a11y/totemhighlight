package com.example.totemhighlight.mixin;

import com.example.totemhighlight.HighlightRenderer;
import com.example.totemhighlight.TotemHighlight;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin {

    @Shadow protected int leftPos;
    @Shadow protected int topPos;
    @Shadow @Final protected AbstractContainerMenu menu;

    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void totemhighlight$highlightTotems(GuiGraphicsExtractor g, int mouseX, int mouseY,
                                                float partialTick, CallbackInfo ci) {
        if (TotemHighlight.intensity() <= 0f) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            return;
        }
        for (Slot slot : this.menu.slots) {
            if (slot.container != mc.player.getInventory()) {
                continue; // nur eigene Inventar-Slots (inkl. Hotbar und Offhand)
            }
            if (!HighlightRenderer.isTotem(slot.getItem())) {
                continue;
            }
            HighlightRenderer.draw(g, this.leftPos + slot.x, this.topPos + slot.y, false);
        }
    }
}
