package com.chyzman.getdown.network;

import com.chyzman.getdown.Getdown;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

@SuppressWarnings("UnstableApiUsage")
public record CrawlState(boolean crawling) implements CustomPayload {
    private static final Identifier ID = Getdown.id("crawl");

    public static final CustomPayload.Id<CrawlState> PACKET_ID = new CustomPayload.Id<>(ID);

    public static final PacketCodec<ByteBuf, CrawlState> PACKET_CODEC = PacketCodecs.BOOL.xmap(CrawlState::new, CrawlState::crawling);

    public static AttachmentType<CrawlState> TYPE = AttachmentRegistry.create(
        ID,
        builder -> builder
            .initializer(() -> new CrawlState(false))
            .syncWith(PACKET_CODEC, AttachmentSyncPredicate.all())
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }

    public static boolean isCrawling(PlayerEntity player) {
        return player.getAttachedOrCreate(TYPE).crawling();
    }

    public static boolean setCrawling(PlayerEntity player, boolean crawling) {
        boolean changed = player.getAttachedOrCreate(TYPE).crawling() != crawling;
        if (changed) player.setAttached(TYPE, new CrawlState(crawling));
        return changed;
    }
}
