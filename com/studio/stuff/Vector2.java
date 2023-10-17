package com.studio.stuff;

public class Vector2 
{
    public float x, y;
    public Vector2(float x, float y)
    {
        this.x = x;
        this.y = y;
    } 
    
    public Vector2()
    {
        this.x = 0.0f;
        this.y = 0.0f;
    }

    public Vector2 copy()
    {
        Vector2 newVector2 = new Vector2();
        newVector2.x = this.x;
        newVector2.y = this.y;
        return newVector2;
    }

    public String toString()
    {
        return "X: " + x + " Y: " + y;
    }

    public boolean equals(Object other)
    {
        return false;
    }
}