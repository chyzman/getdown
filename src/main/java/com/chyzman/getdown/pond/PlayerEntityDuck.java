package com.chyzman.getdown.pond;

public interface PlayerEntityDuck {
    default boolean getdown$isActuallySwimming() {
        throw new UnsupportedOperationException("Implemented by Mixin");
    }
}
