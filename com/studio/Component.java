package com.studio;

import java.awt.Graphics2D;

public abstract class Component<T> 
{
    public GameObject gameObject;
    public String name;

    public Component(String name)
    {
        this.name = name;
    }

    public Component()
    {
        this.name = "unnamed";
    }

    public void update(double dt)
    {
        return;
    }

    public void draw(Graphics2D g2)
    {
        return;
    }

    public void setGameObject(GameObject gameObject)
    {
        this.gameObject = gameObject;
    }

    public abstract Component copy();

    public void start()
    {
        return;
    }
    
    public String toString()
    {
        return name;
    }

    public boolean equals(Object other)
    {
        return false;
    }
}
