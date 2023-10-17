package com.main;

import com.studio.GameObject;
import com.studio.components.BoxBounds;
import com.studio.stuff.AssetPool;
import com.studio.stuff.Transform;

public class Powerup extends GameObject
{
    public double lifeTime;
    public Powerup(String name, Transform transform) 
    {
        super(name, transform);
        this.addComponent(AssetPool.getSprite("assets/powerup.png"));
        this.addComponent(new BoxBounds(16, 16, "powerup"));
        lifeTime = 5;
    }

    public void update(double dt)
    {
        super.update(dt);
        lifeTime -= dt;
    }
    
}
