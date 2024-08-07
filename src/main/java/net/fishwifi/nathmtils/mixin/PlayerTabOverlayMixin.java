package net.fishwifi.nathmtils.mixin;

import net.fishwifi.nathmtils.tag.TagManager;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(value = PlayerTabOverlay.class, priority = 800)
public class PlayerTabOverlayMixin {
    @Inject(at = @At("RETURN"), method = "getNameForDisplay", cancellable = true)
    private void modifyTablistDisplayName(PlayerInfo playerInfo, CallbackInfoReturnable<Component> info) {
        UUID playerUUID = playerInfo.getProfile().getId();
        String customTag = TagManager.getTagForPlayer(playerUUID);

        if (customTag != null) {
            Component originalName = info.getReturnValue();
            MutableComponent modifiedName = Component.literal(customTag + " ").append(originalName);
            info.setReturnValue(modifiedName);
        }
    }
}
