package com.paaaulz.mobsteroids.capabilities;

import net.minecraft.nbt.*;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

// STORAGE CLASS (SAVES/LOADS DATA TO/FROM NBT)

public class BuffStorage implements Capability.IStorage<IBuffing>
{
    @Nullable
    @Override
    public INBT writeNBT(Capability<IBuffing> capability, IBuffing instance, Direction side)
    {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("growState", instance.getGrowState());
        return nbt;
    }

    @Override
    public void readNBT(Capability<IBuffing> capability, IBuffing instance, Direction side, INBT nbt)
    {
        instance.setGrowState(((CompoundNBT) nbt).getInt("growState"));
    }
}