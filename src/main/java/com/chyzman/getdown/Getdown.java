package com.chyzman.getdown;

import com.chyzman.getdown.network.CrawlState;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class Getdown implements ModInitializer {
    public static final String MODID = "getdown";

    @Override
    public void onInitialize() {
        PayloadTypeRegistry.playC2S().register(CrawlState.PACKET_ID, CrawlState.PACKET_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(CrawlState.PACKET_ID, (payload, context) -> CrawlState.setCrawling(context.player(), payload.crawling()));
    }

    public static Identifier id(String path) {
        return Identifier.of(MODID, path);
    }
}
