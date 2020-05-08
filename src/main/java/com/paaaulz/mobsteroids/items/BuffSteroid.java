package com.paaaulz.mobsteroids.items;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.paaaulz.mobsteroids.MobSteroids;
import com.paaaulz.mobsteroids.ModItems;
import com.paaaulz.mobsteroids.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.command.arguments.NBTCompoundTagArgument;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.NBTTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

import java.io.DataOutput;

public class BuffSteroid extends Item
{
    private static final Logger LOGGER = LogManager.getLogger();
    String name = "buff_steroid";
    public BuffSteroid(Item.Properties properties)
    {
        super(properties);
        setRegistryName(Reference.MOD_ID, name);
        ModItems.ITEMS.add(this);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        //LOGGER.info("PRE_HIT_TAGS: " + target.getTags());
        //target.getTags().add("diventagrossa");
        //LOGGER.info("POST_HIT_TAGS: " + target.getTags());
        return true;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand)
    {
        //LOGGER.info("ASKING_TAGS: " + target.getTags());
        CompoundNBT compound = target.serializeNBT();
        compound.putString("grow","yes");
        target.deserializeNBT(compound);
        return true;
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack)
    {
        // Set item name
        return new TranslationTextComponent("Buff Steroid");
    }

}
