package com.paaaulz.mobsteroids.capabilities;

// THIS IS THE DEFAULT IMPLEMENTATION

public class Buffing<T> implements IBuffing
{
    private int growState = 1;

    public void setGrowState(int newGrowState)
    {
        this.growState = newGrowState;
    }
    public int getGrowState()
    {
        return this.growState;
    }
    public void grow(int howMuch) { this.growState += howMuch; }
    public void shrink(int howMuch) { this.growState -= howMuch; }
}
