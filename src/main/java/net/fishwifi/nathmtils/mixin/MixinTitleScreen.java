package net.fishwifi.nathmtils.mixin;


import net.fishwifi.nathmtils.config.ModConfig;
import net.fishwifi.nathmtils.nathmUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class MixinTitleScreen {

    @Unique
    private static final ModConfig config = nathmUtils.getConfig();

    @Inject(method = "render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)I"))
    private void injectTitle(GuiGraphics guiGraphics, int i, int j, float f, CallbackInfo callbackInfo) {
        TitleScreen titleScreen = (TitleScreen) (Object) this;
        Minecraft mc = Minecraft.getInstance();
        String titleText = config.getTitleScreenText();

        int yPos = titleScreen.height - 20;

        guiGraphics.drawString(mc.font, titleText, 2, yPos, 16777215 | i);
    }

}