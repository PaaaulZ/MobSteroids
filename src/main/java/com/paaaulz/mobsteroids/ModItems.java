package com.paaaulz.mobsteroids;

import com.paaaulz.mobsteroids.items.ShepherdsCrook;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemTier;

import java.util.List;
import java.util.ArrayList;

public class ModItems
{
    public static final List<Object> ITEMS = new ArrayList<Object>();

    public static final Item SHEPHERDSCROOK = new ShepherdsCrook(ItemTier.WOOD, 0, -2.4F, (new Item.Properties()).group(ItemGroup.TOOLS));
}