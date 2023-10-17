package com.studio.components;

import java.awt.Graphics2D;
import java.util.HashMap;

import com.studio.Component;

public class SpriteManager extends Component
{
    HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
    Sprite currentSprite;

    public void addSprite(Sprite s)
    {
        s.setGameObject(this.gameObject);
        sprites.put(s.name, s);
    }

    public void setSprite(String s)
    {
        if(sprites.containsKey(s))
        {
            currentSprite = sprites.get(s);
        }
    }

    public void draw(Graphics2D g2)
    {
        currentSprite.draw(g2);
    }

    @Override
    public Component copy() 
    {
        return null;
    }
}
