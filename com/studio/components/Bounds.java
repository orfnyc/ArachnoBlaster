package com.studio.components;

import com.studio.Component; 

enum BoundsType
{
    Box,
    Triangle
}
public abstract class Bounds extends Component
{
    public BoundsType type;

    abstract public float getWidth();
    abstract public float getHeight();

    public static boolean checkCollision(Bounds b1, Bounds b2)
    {
        if(b1.type == b2.type && b1.type == BoundsType.Box)
        {
            return BoxBounds.checkCollision((BoxBounds)b1, (BoxBounds)b2);
        }
        return false;
    }

    public static void resolveCollision(Bounds b1, Bounds b2)
    {
        if (b1.type == BoundsType.Box && b2.type == b1.type)
        {
            BoxBounds box1 = (BoxBounds) b1;
            BoxBounds box2 = (BoxBounds) b2;

            box1.resolveCollision(box2);
        }

    }

    public String toString()
    {
        return "generic bounds";
    }

    public boolean equals(Object other)
    {
        return false;
    }

}
