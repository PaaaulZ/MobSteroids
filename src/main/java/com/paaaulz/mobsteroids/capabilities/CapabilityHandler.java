package com.paaaulz.mobsteroids.capabilities;

import com.paaaulz.mobsteroids.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CapabilityHandler
{
    private static final Logger LOGGER = LogManager.getLogger();
    public static final ResourceLocation BUFF_CAP = new ResourceLocation(Reference.MOD_ID, "buffing");

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<Entity> event)
    {
        Entity entity = event.getObject();
        if (!(entity instanceof AnimalEntity)) return;
        event.addCapability(BUFF_CAP, new BuffingProvider<IBuffing>());
    }
}