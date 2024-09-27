package com.chyzman.getdown.client;

import com.chyzman.getdown.mixin.accessor.GameOptionsAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Arrays;

import static com.chyzman.getdown.Getdown.*;

public class GetdownClient implements ClientModInitializer {
    public static final KeyBinding CRAWL_KEYBIND = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.getdown.crawl", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Z, KeyBinding.GAMEPLAY_CATEGORY));

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            var player = client.player;
            if (player == null) return;
            var component = GETDOWN_PLAYER.get(player);
            if (CONFIG.crawlToggled()) {
                if (CRAWL_KEYBIND.wasPressed()) {
                    component.crawling(!component.crawling());
                }
            } else {
                if (CRAWL_KEYBIND.isPressed() != component.crawling()) {
                    component.crawling(CRAWL_KEYBIND.isPressed());
                }
            }
        });
    }

    public static SimpleOption<?>[] injectGetDownOption(GameOptionsAccessor gameOptions, SimpleOption<?>[] options) {
        var list = new ArrayList<>(Arrays.asList(options));
        var sprintToggleIndex = list.indexOf(gameOptions.getdown$getSprintToggledRef());
        list.add(sprintToggleIndex + 1, new SimpleOption<>(
                "key.getdown.crawl",
                SimpleOption.emptyTooltip(),
                (optionText, value) -> value ? GameOptionsAccessor.getdown$getToggleKeyText() : GameOptionsAccessor.getdown$getHoldKeyText(),
                SimpleOption.BOOLEAN,
                CONFIG.crawlToggled(),
                CONFIG::crawlToggled
        ));
        return list.toArray(new SimpleOption[0]);
    }
}
