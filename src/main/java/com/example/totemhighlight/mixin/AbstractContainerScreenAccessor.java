package com.example.totemhighlight.mixin;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/** Macht die Fensterposition des Inventars lesbar (leftPos/topPos sind sonst geschuetzt). */
@Mixin(AbstractContainerScreen.class)
public interface AbstractContainerScreenAccessor {

    @Accessor("leftPos")
    int totemhighlight$getLeftPos();

    @Accessor("topPos")
    int totemhighlight$getTopPos();
}
