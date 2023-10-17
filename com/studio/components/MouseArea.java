package com.studio.components;

import java.awt.Graphics2D;

import com.studio.Component;
import com.studio.Window;
import com.studio.stuff.Vector2;
import com.studio.util.Constants;

public class MouseArea extends Component
{
    private Vector2 position, dimensions;
    private boolean touched;

    public MouseArea(String name, Vector2 positon, Vector2 dimensions)
    {
        super(name);
        this.position = positon;
        this.dimensions = dimensions;
        this.dimensions.x *= Constants.SCALE;
        this.dimensions.y *= Constants.SCALE;

    }

    public boolean touched() {return touched;}

    @Override
    public void update(double dt)
    {
        if (Window.getWindow().mouseListener.x > position.x &&
        Window.getWindow().mouseListener.x < position.x + dimensions.x &&
        Window.getWindow().mouseListener.y > position.y &&
        Window.getWindow().mouseListener.y < position.y + dimensions.y)
        {
            touched = true;
        }
        else
        {
            touched = false;
        }
    }

    @Override
    public void draw(Graphics2D g2)
    {
        return;
    }

    @Override
    public MouseArea copy() 
    {
        return null;
    }

    public String toString()
    {
        return "MouseArea X: " + position.x + " to " + (float)(position.x + dimensions.x) + "; Y: " + position.y + " to " + (float)(dimensions.y + position.y);
    }

    public boolean equals(Object other)
    {
        return false;
    }
    
}
