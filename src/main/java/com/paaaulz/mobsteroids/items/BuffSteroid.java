package com.paaaulz.mobsteroids.items;

import com.paaaulz.mobsteroids.ModItems;
import com.paaaulz.mobsteroids.Reference;
import com.paaaulz.mobsteroids.capabilities.BuffingProvider;
import com.paaaulz.mobsteroids.capabilities.IBuffing;
import com.paaaulz.mobsteroids.network.PacketExample;
import com.paaaulz.mobsteroids.network.PacketHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

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
        //target.playSound(SoundEvents.ENTITY_COW_HURT,6.0F,0.5F);
        LazyOptional<IBuffing> cap = target.getCapability(BuffingProvider.BUFF_CAP, null);
        IBuffing buffs = cap.orElseThrow(() -> new IllegalArgumentException("Invalid LazyOptional, must not be empty"));
        buffs.grow(1);
        // send update packet to player
        Supplier<ServerPlayerEntity> attackerToSupplier = new Supplier<ServerPlayerEntity>() {
            @Override
            public ServerPlayerEntity get() {
                return (ServerPlayerEntity) attacker;
            }
        };
        PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(attackerToSupplier), new PacketExample("growState", buffs.getGrowState(), target.getEntityId()));
        return true;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand)
    {
        IBuffing buffs = target.getCapability(BuffingProvider.BUFF_CAP, null).orElseThrow(() -> new IllegalArgumentException("Invalid LazyOptional, must not be empty"));

        LOGGER.info("GROW STATE: " + buffs.getGrowState());

        return true;
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack)
    {
        // Set item name
        return new TranslationTextComponent("Buff Steroid");
    }

}
