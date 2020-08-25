package com.paaaulz.mobsteroids.capabilities;

import com.ibm.icu.impl.ICULocaleService;
import com.paaaulz.mobsteroids.Reference;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BuffingProvider<T> implements ICapabilitySerializable<CompoundNBT>
{
    private Buffing<T>[] capabilities;

    @CapabilityInject(IBuffing.class)
    public static Capability<IBuffing> BUFF_CAP = null;

    private static final Logger LOGGER = LogManager.getLogger();



    public final IBuffing instance = BUFF_CAP.getDefaultInstance();
   
    private final LazyOptional<IBuffing> holder = LazyOptional.of(() -> instance);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        return cap == BUFF_CAP ? holder.cast() : LazyOptional.empty();
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        return cap == BUFF_CAP ? holder.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT()
    {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("growState", instance.getGrowState());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {
        instance.setGrowState(nbt.getInt("growState"));
    }

}


