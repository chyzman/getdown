package com.chyzman.getdown;

import com.chyzman.getdown.mixin.accessor.GameOptionsAccessor;
import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Sync;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

@Config(name = "getDown", wrapperName = "GetDownConfig")
public class GetDownConfigModel {

    @Sync(Option.SyncMode.NONE)
    public boolean crawlToggled = true;
}
