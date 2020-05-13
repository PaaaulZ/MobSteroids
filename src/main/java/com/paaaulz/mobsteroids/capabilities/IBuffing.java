package com.paaaulz.mobsteroids.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface IBuffing
{
    public int getGrowState();
    public void setGrowState(int growState);
    public void grow(int howMuch);
    public void shrink(int howMuch);
}

