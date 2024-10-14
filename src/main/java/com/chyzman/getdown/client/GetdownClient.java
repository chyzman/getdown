package com.chyzman.getdown.client;

import com.chyzman.getdown.mixin.accessor.GameOptionsAccessor;
import com.chyzman.getdown.network.Crawl;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.option.StickyKeyBinding;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Arrays;

import static com.chyzman.getdown.Getdown.*;

public class GetdownClient implements ClientModInitializer {
    public static final KeyBinding CRAWL_KEYBIND = KeyBindingHelper.registerKeyBinding(new StickyKeyBinding("key.crawl",GLFW.GLFW_KEY_Z,  KeyBinding.MOVEMENT_CATEGORY, CONFIG::crawlToggled));

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            var player = client.player;
            if (player == null) return;
            var component = GETDOWN_PLAYER.get(player);
            var crawlPressed = CRAWL_KEYBIND.isPressed();
            if (component.crawling(crawlPressed)) CHANNEL.clientHandle().send(new Crawl(component.crawling()));
        });
    }

    public static SimpleOption<?>[] injectGetDownOption(GameOptionsAccessor gameOptions, SimpleOption<?>[] options) {
        var list = new ArrayList<>(Arrays.asList(options));
        var sprintToggleIndex = list.indexOf(gameOptions.getdown$getSprintToggledRef());
        list.add(sprintToggleIndex + 1, new SimpleOption<>(
                "key.crawl",
                SimpleOption.emptyTooltip(),
                (optionText, value) -> value ? GameOptionsAccessor.getdown$getToggleKeyText() : GameOptionsAccessor.getdown$getHoldKeyText(),
                SimpleOption.BOOLEAN,
                CONFIG.crawlToggled(),
                CONFIG::crawlToggled
        ));
        return list.toArray(new SimpleOption[0]);
    }
}
