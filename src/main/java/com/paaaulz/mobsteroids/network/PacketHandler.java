package com.paaaulz.mobsteroids.network;

import com.paaaulz.mobsteroids.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler
{
    // Creates a new instance of the network channel
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Reference.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    // This is used to give each packet type a seperate ID
    public static int id = 0;

    public static int increaseId()
    {
        id++;
        return id;
    }

}