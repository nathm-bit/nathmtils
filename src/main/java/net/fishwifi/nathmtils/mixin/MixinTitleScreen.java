package net.fishwifi.nathmtils.mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fishwifi.nathmtils.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Optional;

@Mixin(TitleScreen.class)
public class MixinTitleScreen {

    @Inject(method = "render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)I"))
    private void injectTitle(GuiGraphics guiGraphics, int i, int j, float f, CallbackInfo callbackInfo) {
        TitleScreen titleScreen = (TitleScreen) (Object) this;
        Minecraft mc = Minecraft.getInstance();

        String titleText = ModConfig.customText;

        int yPos = titleScreen.height - 20; // Adjusted position for the single line

        guiGraphics.drawString(mc.font, titleText, 2, yPos, 16777215 | i);
    }

}