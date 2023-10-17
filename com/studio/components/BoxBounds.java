package com.studio.components;

import com.studio.Component;
import com.studio.stuff.Vector2;
import com.studio.util.Constants;

public class BoxBounds extends Bounds
{
    public float width, height;
    public float halfWidth, halfHeight;
    public Vector2 center;
    public float offsetX, offsetY;

    public BoxBounds(float width, float height, String name)
    {
        type = BoundsType.Box;
        this.name = name;
        this.width = width * Constants.SCALE;
        this.height = height * Constants.SCALE;
        this.halfWidth = this.width / 2;
        this.halfHeight = this.height / 2;
        this.center = new Vector2();
    }

    public void setOffset(float x, float y)
    {
        offsetX = x * Constants.SCALE;
        offsetY = y * Constants.SCALE;
    }

    public void calculateCenter()
    {
        this.center.x = this.gameObject.transform.position.x + this.halfWidth + offsetX;
        this.center.y = this.gameObject.transform.position.y + this.halfHeight + offsetY;
    }

    public static boolean checkCollision(BoxBounds b1, BoxBounds b2)
    {
        b1.calculateCenter();
        b2.calculateCenter();

        float dx = b2.center.x - b1.center.x;
        float dy = b2.center.y - b1.center.y;

        float combinedHalfWidths = b1.halfWidth + b2.halfWidth;
        float combinedHalfHeights = b1.halfHeight + b2.halfHeight;
        if (Math.abs(dx) < combinedHalfWidths)
        {
            return Math.abs(dy) < combinedHalfHeights;
        }

        return false;
    }

    public void resolveCollision(BoxBounds b)
    {
        b.calculateCenter();
        this.calculateCenter();

        float dx = b.center.x - this.center.x;
        float dy = b.center.y - this.center.y;

        float combinedHalfWidths = b.halfWidth + this.halfWidth;
        float combinedHalfHeights = b.halfHeight + this.halfHeight;

        float overlapX = combinedHalfWidths - Math.abs(dx);
        float overlapY = combinedHalfHeights - Math.abs(dy);

        if(overlapX <= overlapY)
        {
            if(dx > 0)
            {
                this.gameObject.transform.position.x -= overlapX;
            }
            else
            {
                this.gameObject.transform.position.x += overlapX;
            }
        }
        if(overlapY <= overlapX)
        {
            if(dy > 0)
            {
                this.gameObject.transform.position.y -= overlapY;
            }
            else
            {
                this.gameObject.transform.position.y += overlapY;
            }
        }
    }

    public static void resolveCollision(BoxBounds b1, BoxBounds b2)
    {
        b1.calculateCenter();
        b2.calculateCenter();

        float dx = b1.center.x - b2.center.x;
        float dy = b1.center.y - b2.center.y;

        float combinedHalfWidths = b1.halfWidth + b2.halfWidth;
        float combinedHalfHeights = b1.halfHeight + b2.halfHeight;

        float overlapX = combinedHalfWidths - Math.abs(dx);
        float overlapY = combinedHalfHeights - Math.abs(dy);

        if(overlapX <= overlapY)
        {
            if(dx > 0)
            {
                b1.gameObject.transform.position.x += overlapX * 0.5;
                b2.gameObject.transform.position.x -= overlapX * 0.5;
            }
            else
            {
                b1.gameObject.transform.position.x -= overlapX * 0.5;
                b2.gameObject.transform.position.x += overlapX * 0.5;
            }
        }
        if(overlapY <= overlapX)
        {
            if(dy > 0)
            {
                b1.gameObject.transform.position.y += overlapY * 0.5;
                b2.gameObject.transform.position.y -= overlapY * 0.5;
            }
            else
            {
                b1.gameObject.transform.position.y -= overlapY * 0.5;
                b2.gameObject.transform.position.y += overlapY * 0.5;
            }
        }
    }

    @Override
    public float getWidth() {
        return this.height;
    }

    @Override
    public float getHeight() {
        return this.width;
    }

    @Override
    public Component copy() 
    {
        return null;
    }

    public String toString()
    {
        return name + ": Width: " + width + " Height: " + height;
    }

    public boolean equals(Object other)
    {
        return false;
    }
}
