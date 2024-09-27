package com.chyzman.getdown.mixin.common;

import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.chyzman.getdown.Getdown.GETDOWN_PLAYER;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "updatePose", at = @At("HEAD"), cancellable = true)
    private void crawlNOW(CallbackInfo ci) {
        if (GETDOWN_PLAYER.get(this).crawling()) {
            ((PlayerEntity)(Object)this).setPose(EntityPose.SWIMMING);
            ci.cancel();
        }
    }
}
