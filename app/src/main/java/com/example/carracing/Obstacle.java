package com.example.carracing;

public class Obstacle
{
    private int type = 0;
    private int index = 0;

    public Obstacle(int type, int inx)
    {
        this.type = type;
        this.index = inx;
    }

    public int getType()
    {
        return type;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public int getIndex()
    {
        return index;
    }
}
