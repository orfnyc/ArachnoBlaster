package com.studio.stuff;

import com.studio.util.Constants;

public class Transform 
{
    public Vector2 position;
    public Vector2 scale;
    public float rotation;

    public Transform(Vector2 position)
    {
        this.position = position;
        this.position.x *= Constants.SCALE;
        this.position.y *= Constants.SCALE;
        this.scale = new Vector2(1.0f * Constants.SCALE, 1.0f * Constants.SCALE);
        this.rotation = 0.0f;
    }

    public String toString()
    {
        return "Position: (" + position.x + ", " + position.y + ")";
    }

    public boolean equals(Object other)
    {
        return false;
    }

    public Transform copy()
    {
        Transform newTransform = new Transform(position.copy());
        newTransform.scale = this.scale.copy();
        newTransform.rotation = this.rotation;
        return newTransform;
    }
}
