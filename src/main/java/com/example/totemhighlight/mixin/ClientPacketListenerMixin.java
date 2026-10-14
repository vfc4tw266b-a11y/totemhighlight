package com.example.totemhighlight.mixin;

import com.example.totemhighlight.TotemHighlight;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin {

    /** Entity-Event 35 = Totem der Unsterblichkeit wurde ausgeloest. */
    private static final int TOTEM_EVENT = 35;

    @Inject(method = "handleEntityEvent", at = @At("HEAD"))
    private void totemhighlight$onEntityEvent(ClientboundEntityEventPacket packet, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        if (!mc.isSameThread() || mc.level == null || mc.player == null) {
            return;
        }
        if (packet.getEventId() == TOTEM_EVENT && packet.getEntity(mc.level) == mc.player) {
            TotemHighlight.trigger();
        }
    }
}
