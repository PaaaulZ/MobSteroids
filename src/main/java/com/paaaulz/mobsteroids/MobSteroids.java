package com.paaaulz.mobsteroids;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.paaaulz.mobsteroids.capabilities.*;
import com.paaaulz.mobsteroids.network.PacketExample;
import com.paaaulz.mobsteroids.network.PacketHandler;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;
import java.util.concurrent.Callable;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("mobsteroids")

public class MobSteroids
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public MobSteroids()
    {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
        PacketHandler.INSTANCE.registerMessage(
                PacketHandler.increaseId(),
                PacketExample.class,
                PacketExample::encode,
                PacketExample::decode,
                PacketExample.Handler::handle
        );
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {

        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> itemsRegistryEvent)
        {
            // THIS SHOULD NOT BE HERE
            CapabilityManager.INSTANCE.register(IBuffing.class, new BuffStorage(), Buffing::new);
            //CapabilityManager.INSTANCE.register(IBuffing.class, new BuffStorage(), () -> { return new Buffing<IBuffing>(); });
            LOGGER.info("Registered capabilities");

            itemsRegistryEvent.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
            LOGGER.info("Items registered!");
        }

    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEvents
    {
        @SubscribeEvent
        public static void preRenderLiving(final RenderLivingEvent.Pre<CowEntity, CowModel<CowEntity>> e)
        {
            // Change to fire with other animals too.
            // func_228455_a_ => getImpl()
            // func_227862_a_ => scale?

            //IRenderTypeBuffer buffer = IRenderTypeBuffer.func_228455_a_(Tessellator.getInstance().getBuffer());

            Entity entity = e.getEntity();
            boolean enabled = true;
            if (entity instanceof CowEntity && enabled)
            {
                MatrixStack stack = e.getMatrixStack();
                //AxisAlignedBB oldBB = entity.getBoundingBox();
                IBuffing buffs = entity.getCapability(BuffingProvider.BUFF_CAP, null).orElseThrow(() -> new IllegalArgumentException("Invalid LazyOptional, must not be empty"));
                int currentGrowState = buffs.getGrowState();
                if (currentGrowState > 1)
                {
                    // Grow entity and bounding box
                    stack.func_227862_a_((float) currentGrowState/2, (float) currentGrowState/2, (float) currentGrowState/2);
                    AxisAlignedBB expanded = entity.getBoundingBox().expand(1,1,1);
                    entity.setBoundingBox(expanded);
                }

            }
        }

        @SubscribeEvent
        public static void onSound(final PlaySoundEvent e)
        {
            // if buffed animal change pitch.
        }
    }

}

