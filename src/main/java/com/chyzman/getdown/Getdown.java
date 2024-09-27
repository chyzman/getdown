package com.chyzman.getdown;

import com.chyzman.getdown.component.GetDownPlayerComponent;
import com.chyzman.getdown.network.Crawl;
import io.wispforest.owo.network.OwoNetChannel;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class Getdown implements ModInitializer, EntityComponentInitializer {
    public static final String MODID = "getdown";

    public static final OwoNetChannel CHANNEL = OwoNetChannel.create(id("main"));

    public static final com.chyzman.getdown.GetDownConfig CONFIG = com.chyzman.getdown.GetDownConfig.createAndLoad();

    public static final ComponentKey<GetDownPlayerComponent> GETDOWN_PLAYER = ComponentRegistry.getOrCreate(id("player"), GetDownPlayerComponent.class);

    @Override
    public void onInitialize() {
        CHANNEL.registerServerbound(Crawl.class, (message, access) -> {
            GETDOWN_PLAYER.get(access.player()).crawling(message.crawling());
            GETDOWN_PLAYER.sync(access.player());
        });
    }

    public static Identifier id(String path) {
        return Identifier.of(MODID, path);
    }

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(GETDOWN_PLAYER, GetDownPlayerComponent::new, RespawnCopyStrategy.NEVER_COPY);
    }
}
