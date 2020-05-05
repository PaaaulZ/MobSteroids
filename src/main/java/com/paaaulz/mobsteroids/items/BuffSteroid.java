package com.paaaulz.mobsteroids.items;

import com.paaaulz.mobsteroids.ModItems;
import com.paaaulz.mobsteroids.Reference;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

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
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand)
    {
        if (target instanceof AnimalEntity)
        {
            // Is it an animal?
            //GL11.glScalef(5F,5F,5F);
        }
        return false;
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack)
    {
        // Set item name
        return new TranslationTextComponent("Buff Steroid");
    }
}
