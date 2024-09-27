package com.chyzman.getdown.component;

import com.chyzman.getdown.network.Crawl;
import io.wispforest.endec.Endec;
import io.wispforest.endec.SerializationContext;
import io.wispforest.endec.impl.KeyedEndec;
import io.wispforest.owo.serialization.RegistriesAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

import static com.chyzman.getdown.Getdown.CHANNEL;

public class GetDownPlayerComponent implements AutoSyncedComponent {
    private boolean crawling;

    //region ENDEC STUFF

    public static final KeyedEndec<Boolean> CRAWLING = Endec.BOOLEAN.keyed("Crawling", false);

    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.@NotNull WrapperLookup registryLookup) {
        var context = SerializationContext.attributes(RegistriesAttribute.of((DynamicRegistryManager) registryLookup));
        crawling = tag.get(context, CRAWLING);
    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.@NotNull WrapperLookup registryLookup) {
        var context = SerializationContext.attributes(RegistriesAttribute.of((DynamicRegistryManager) registryLookup));
        tag.put(context, CRAWLING, crawling);
    }

    //endregion

    public GetDownPlayerComponent(PlayerEntity holder) {
    }

    public boolean crawling() {
        return crawling;
    }

    public GetDownPlayerComponent crawling(boolean crawling) {
        var changed = this.crawling != crawling;
        this.crawling = crawling;
        if (changed) CHANNEL.clientHandle().send(new Crawl(crawling));
        return this;
    }
}
