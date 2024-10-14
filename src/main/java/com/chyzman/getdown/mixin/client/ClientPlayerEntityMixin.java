package com.chyzman.getdown.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.network.ClientPlayerEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.chyzman.getdown.Getdown.GETDOWN_PLAYER;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {

    @ModifyExpressionValue(method = "tickMovement", at = @At(value = "FIELD", target = "Lnet/minecraft/client/input/Input;sneaking:Z", opcode = Opcodes.GETFIELD, ordinal = 0))
    private boolean crawlNOW(boolean original) {
        return original || GETDOWN_PLAYER.get(this).crawling();
    }

    @ModifyExpressionValue(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;canChangeIntoPose(Lnet/minecraft/entity/EntityPose;)Z", ordinal = 0))
    private boolean dontSneakWhileCrawling(boolean original) {
        return original && !GETDOWN_PLAYER.get(this).crawling();
    }

    @Inject(method = "canSprint", at = @At(value = "HEAD"), cancellable = true)
    private void dontSprintWhileCrawling(CallbackInfoReturnable<Boolean> cir) {
        if (GETDOWN_PLAYER.get(this).crawling()) cir.setReturnValue(false);
    }
}
