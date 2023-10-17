package com.main;

import java.awt.Graphics2D;

import com.studio.GameObject;
import com.studio.components.Sprite;
import com.studio.stuff.AssetPool;
import com.studio.stuff.Transform;

public class LivesCounter extends GameObject
{

    public int numLives;

    public LivesCounter(String name, Transform transform) 
    {
        super(name, transform);
    }

    public void draw(Graphics2D g2)
    {
        for(int i = 0; i < numLives; i++)
        {
            Sprite s = AssetPool.getSprite("assets/heart.png");
            s.gameObject = this;
            s.setRelativePos(18*i, 0);
            s.draw(g2);
        }
    }
    
}
