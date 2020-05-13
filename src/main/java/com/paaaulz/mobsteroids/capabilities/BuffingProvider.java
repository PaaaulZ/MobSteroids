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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BuffingProvider<T> implements ICapabilitySerializable<CompoundNBT>
{
    private Buffing<T>[] capabilities;

    @CapabilityInject(IBuffing.class)
    public static Capability<IBuffing> BUFF_CAP = null;



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
        INBT test = BUFF_CAP.getStorage().writeNBT(BUFF_CAP, this.instance, null);
        CompoundNBT compound = new CompoundNBT();
        compound.put(Reference.MOD_ID, test);
        //return BUFF_CAP.getStorage().writeNBT(BUFF_CAP, this.instance, null);
        CompoundNBT serializedNBT = new CompoundNBT();
        /*for (IBuffing cap : capabilities) {
            if (cap instanceof IBuffing)
            {
                ICapabilitySerializable serializableCap = (ICapabilitySerializable) cap;
                serializedNBT.put("buffing", serializableCap.serializeNBT());
            }
        }*/
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {
        BUFF_CAP.getStorage().readNBT(BUFF_CAP, this.instance, null, nbt);
    }
}


