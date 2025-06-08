package com.chyzman.getdown.mixin.accessor;

import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GameOptions.class)
public interface GameOptionsAccessor {
    @Accessor("sprintToggled")
    SimpleOption<Boolean> getdown$getSprintToggledRef();

    @Accessor("TOGGLE_KEY_TEXT")
    static Text getdown$getToggleKeyText() {
        throw new UnsupportedOperationException("Implemented by Mixin");
    }

    @Accessor("HOLD_KEY_TEXT")
    static Text getdown$getHoldKeyText() {
        throw new UnsupportedOperationException("Implemented by Mixin");
    }
}
