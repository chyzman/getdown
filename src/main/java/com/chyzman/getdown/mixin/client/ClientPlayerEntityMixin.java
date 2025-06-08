package com.chyzman.getdown.mixin.client;

import com.chyzman.getdown.pond.PlayerEntityDuck;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isSwimming()Z", ordinal = 2))
    private boolean allowFlyingWhileCrawling(ClientPlayerEntity instance) {
        return ((PlayerEntityDuck)this).getdown$isActuallySwimming();
    }

//    @ModifyExpressionValue(method = "tickMovement", at = @At(value = "FIELD", target = "Lnet/minecraft/client/input/Input;sneaking:Z", opcode = Opcodes.GETFIELD, ordinal = 0))
//    private boolean crawlNOW(boolean original) {
//        return original || GETDOWN_PLAYER.get(this).crawling();
//    }
//
//    @ModifyExpressionValue(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;canChangeIntoPose(Lnet/minecraft/entity/EntityPose;)Z", ordinal = 0))
//    private boolean dontSneakWhileCrawling(boolean original) {
//        return original && !GETDOWN_PLAYER.get(this).crawling();
//    }
//
//    @Inject(method = "canSprint", at = @At(value = "HEAD"), cancellable = true)
//    private void dontSprintWhileCrawling(CallbackInfoReturnable<Boolean> cir) {
//        if (GETDOWN_PLAYER.get(this).crawling()) cir.setReturnValue(false);
//    }
}
