package com.paaaulz.mobsteroids.capabilities;

import net.minecraft.nbt.*;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.io.DataOutput;
import java.io.IOException;

// STORAGE CLASS (SAVES/LOADS DATA TO/FROM NBT)

public class BuffStorage implements Capability.IStorage<IBuffing>
{
    @Nullable
    @Override
    public INBT writeNBT(Capability capability, IBuffing instance, Direction side)
    {
        return new INBT()
        {
            @Override
            public void write(DataOutput output) throws IOException {
                output.write(((IBuffing)instance).getGrowState());
            }

            @Override
            public byte getId() {
                return 0;
            }

            @Override
            public INBTType<?> func_225647_b_() {
                return null;
            }

            @Override
            public INBT copy() {
                return null;
            }

            @Override
            public ITextComponent toFormattedComponent(String indentation, int indentDepth) {
                return null;
            }
        };
    }

    @Override
    public void readNBT(Capability capability, IBuffing instance, Direction side, INBT nbt)
    {
        ((IBuffing)instance).setGrowState(((IntNBT)nbt).getInt());
    }
}