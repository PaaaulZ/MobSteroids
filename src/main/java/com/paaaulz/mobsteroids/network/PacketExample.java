package com.paaaulz.mobsteroids.network;

import com.paaaulz.mobsteroids.capabilities.BuffingProvider;
import com.paaaulz.mobsteroids.capabilities.IBuffing;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class PacketExample
{

    private static final Logger LOGGER = LogManager.getLogger();
    // Whatever data needs to be sent to the server should be stored in this object.
    private String parameter;
    private int value;
    private int entityId;

    // This constructor is called by you when creating a new packet to be sent to the server
    public PacketExample(String parameter_, int value_, int entityId_)
    {
        this.parameter = parameter_;
        this.value = value_;
        this.entityId = entityId_;
    }

    // Called by the game, not you. The data is written into a PacketBuffer, ready
    // to be sent to the server.
    public static void encode(PacketExample msg, PacketBuffer buf)
    {
        buf.writeString(msg.parameter);
        buf.writeInt(msg.value);
        buf.writeInt(msg.entityId);
        // SERVER THREAD
        LOGGER.info("Server packet: " + msg.parameter + ":" + msg.value);
    }

    // Called by the game, not you. The packet is decoded by the server and a new 
    // PacketExample object is created to store the recieved data!
    public static PacketExample decode(PacketBuffer buf)
    {
        String receivedParameter = buf.readString(255);
        int receivedValue = buf.readInt();
        int receivedEntityId = buf.readInt();

        return new PacketExample(receivedParameter, receivedValue, receivedEntityId);
    }

    // Called by the game, not you. This is where you do stuff with the received data!
    public static class Handler
    {
        public static void handle(final PacketExample message, Supplier<NetworkEvent.Context> ctx)
        {
            ctx.get().enqueueWork(() ->
            {
                // Work that needs to be threadsafe (most work) goes inside here

                ServerPlayerEntity sender = ctx.get().getSender();
                String receivedParameter = message.parameter;
                int receivedValue = message.value;
                int receivedEntityId = message.entityId;

                // Since this part is only called on the server-side, you 
                // can do whatever you need to do on the server here! You've
                // got whatever data you sent, too!


                // RENDER THREAD
                LOGGER.info("Client Packet: " + message.parameter + ":" + message.value);

                if (message.parameter.equals("growState"))
                {
                    World world = Minecraft.getInstance().world;
                    Entity targetEntity = world.getEntityByID(message.entityId);
                    IBuffing buffs = targetEntity.getCapability(BuffingProvider.BUFF_CAP, null).orElseThrow(() -> new IllegalArgumentException("Invalid LazyOptional, must not be empty"));
                    buffs.setGrowState(message.value);
                }

            });

            ctx.get().setPacketHandled(true);
        }
    }

}