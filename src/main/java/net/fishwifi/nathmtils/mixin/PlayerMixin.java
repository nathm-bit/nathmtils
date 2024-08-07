package net.fishwifi.nathmtils.mixin;

import net.fishwifi.nathmtils.tag.TagManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(value = Player.class, priority = 800)
public class PlayerMixin {
    @Inject(method = "getDisplayName", at = @At("RETURN"), cancellable = true)
    private void injectGetDisplayName(CallbackInfoReturnable<Component> cir) {
        Player player = (Player) (Object) this;
        UUID playerUUID = player.getUUID();
        String customTag = TagManager.getTagForPlayer(playerUUID);

        if (customTag != null) {
            Component originalName = cir.getReturnValue();
            MutableComponent modifiedName = Component.literal(customTag + " ").append(originalName);
            cir.setReturnValue(modifiedName);
        }

    }
}