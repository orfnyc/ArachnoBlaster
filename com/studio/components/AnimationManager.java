package com.studio.components;

import java.awt.Graphics2D;
import java.util.HashMap;

import com.studio.Component;

public class AnimationManager extends Component
{
    String name;
    HashMap<String, Animation> animations = new HashMap<String, Animation>();
    Animation currentAnimation;

    public AnimationManager(String name)
    {
        this.name = name;
    }
    
    public void add(Animation a)
    {
        animations.put(a.name, a);
        a.setGameObject(this.gameObject);
    }

    public void setCurrentAnimation(String name)
    {
        if(animations.containsKey(name))
        {
            currentAnimation = animations.get(name);
        }
    }

    public void update(double dt)
    {
        currentAnimation.update(dt);
    }

    public void draw(Graphics2D g2)
    {
        currentAnimation.draw(g2);
    }
    
    @Override
    public Component copy() 
    {
        return null;
    }

    public String toString()
    {
        return animations.toString();
    }

    public boolean equals(Object other)
    {
        return false;
    }
}
