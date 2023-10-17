package com.studio;

import java.awt.*;
import java.util.ArrayList;

public abstract class Scene 
{
    private String name;
    public ArrayList<GameObject> gameObjects;

    public Scene(String name) 
    {
        this.name = name; 
        this.gameObjects = new ArrayList<>();
    }

    public void addGameObject(GameObject p)
    {
        gameObjects.add(p);
        for (Component s : p.getAllComponents())
        {
            s.start();
        }
    }

    public abstract void init();
    public abstract void update(double dt);
    public abstract void draw(Graphics2D g2);

    public String toString()
    {
        return gameObjects.toString();
    }

    public boolean equals(Object other)
    {
        return false;
    }
}

