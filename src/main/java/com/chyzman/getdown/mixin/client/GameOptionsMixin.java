package com.chyzman.getdown.mixin.client;

import com.chyzman.getdown.client.GetdownClient;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin {
    @Inject(method = "accept", at = @At(value = "FIELD", target = "Lnet/minecraft/client/option/GameOptions;sneakToggled:Lnet/minecraft/client/option/SimpleOption;", shift = At.Shift.AFTER))
    private void loadCrawlToggle(GameOptions.Visitor visitor, CallbackInfo ci) {
        visitor.accept("toggleCrawl", GetdownClient.CRAWL_TOGGLED);
    }
}
