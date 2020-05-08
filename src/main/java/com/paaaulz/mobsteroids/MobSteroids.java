package com.paaaulz.mobsteroids;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

import java.util.Set;

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
    }



    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {

        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> itemsRegistryEvent)
        {
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
            Entity entity = e.getEntity();
            /*MatrixStack stack = e.getMatrixStack();
            IRenderTypeBuffer buffer = IRenderTypeBuffer.func_228455_a_(Tessellator.getInstance().getBuffer());
            Set<String> tags = entity.getTags();
            AxisAlignedBB oldBB = entity.getBoundingBox();
            stack.func_227862_a_(3.0F, 3.0F, 3.0F);
            AxisAlignedBB currentBB = entity.getBoundingBox();
            AxisAlignedBB expanded = entity.getBoundingBox().expand(currentBB.getXSize()/oldBB.getXSize(),currentBB.getYSize()/oldBB.getYSize(),currentBB.getZSize()/oldBB.getZSize());
            entity.setBoundingBox(expanded);*/
            CompoundNBT compound = entity.serializeNBT();
            LOGGER.info("GROW?: " + compound.getString("grow"));
            //compound.func_229681_c_("test");
            //entity.deserializeNBT(compound);
        }

        @SubscribeEvent
        public static void postRenderLiving(final RenderLivingEvent.Post<CowEntity, CowModel<CowEntity>> e)
        {
            //LOGGER.info("[preRenderLiving] START");
            //GL11.glPopMatrix();
            //LOGGER.info("[preRenderLiving] END");
        }

        /*
        @SubscribeEvent
        public void spawnentity(LivingSpawnEvent.CheckSpawn event) {

            LOGGER.info("spawnentity per favore funziona");

        }

        @SubscribeEvent
        public static void constructing(final EntityEvent.EntityConstructing e)
        {
            LOGGER.info("constructing per favore funziona");
        }

        @SubscribeEvent
        public static void entering(final EntityEvent.EnteringChunk e)
        {
            LOGGER.info("entering per favore funziona");
        }*/
    }

}

