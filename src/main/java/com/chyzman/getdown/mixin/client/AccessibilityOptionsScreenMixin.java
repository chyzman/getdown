package com.chyzman.getdown.mixin.client;

import com.chyzman.getdown.mixin.accessor.GameOptionsAccessor;
import net.minecraft.client.gui.screen.option.AccessibilityOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.chyzman.getdown.client.GetdownClient.injectGetDownOption;

@Mixin(AccessibilityOptionsScreen.class)
public class AccessibilityOptionsScreenMixin {
    @Inject(method = "getOptions", at = @At("RETURN"), cancellable = true)
    private static void injectGetDownOptionsIntoAccessibilityScreen(GameOptions gameOptions, CallbackInfoReturnable<SimpleOption<?>[]> cir) {
        cir.setReturnValue(injectGetDownOption((GameOptionsAccessor) gameOptions, cir.getReturnValue()));
    }
}
