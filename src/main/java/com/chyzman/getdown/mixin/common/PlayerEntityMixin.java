package com.chyzman.getdown.mixin.common;

import com.chyzman.getdown.network.CrawlState;
import com.chyzman.getdown.pond.PlayerEntityDuck;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity implements PlayerEntityDuck {
    @Unique private boolean ignoreCrawling = false;

    public PlayerEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

//    @ModifyExpressionValue(method = "updatePose", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isSwimming()Z"))
//    private boolean crawlNOW(boolean original) {
//        return original || GETDOWN_PLAYER.get(this).crawling();
//    }

    @ModifyExpressionValue(
        method = "isSwimming",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/PlayerLikeEntity;isSwimming()Z")
    )
    private boolean makeCrawlingHappen(boolean original) {
        if (ignoreCrawling) return original;
        return original || CrawlState.isCrawling((PlayerEntity) (Object) this);
    }

    @ModifyExpressionValue(
        method = "isSwimming",
        at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerAbilities;flying:Z")
    )
    private boolean allowCrawlingWhileFlying(boolean original) {
        if (ignoreCrawling) return original;
        return original && !CrawlState.isCrawling((PlayerEntity) (Object) this);
    }

    @Redirect(
        method = "travel",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isSwimming()Z")
    )
    private boolean dontSinkWhileFlyCrawling(PlayerEntity instance) {
        return ((PlayerEntityDuck) instance).getdown$isActuallySwimming();
    }

    @Override
    public boolean getdown$isActuallySwimming() {
        var previous = ignoreCrawling;
        ignoreCrawling = true;
        boolean result = isSwimming();
        ignoreCrawling = previous;
        return result;
    }
}
