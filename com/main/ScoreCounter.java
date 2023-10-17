package com.main;

import java.awt.Graphics2D;

import com.studio.GameObject;
import com.studio.components.Sprite;
import com.studio.stuff.AssetPool;
import com.studio.stuff.Transform;

public class ScoreCounter extends GameObject
{

    public int score;

    public ScoreCounter(String name, Transform transform) 
    {
        super(name, transform);
    }

    public void draw(Graphics2D g2)
    {
        int count = 0;
        int temp = score;
        while(temp > 0)
        {
            int digit = temp%10;
            Sprite s = new Sprite(AssetPool.getSprite("assets/numbers.png").image.getSubimage(digit*8, 0, 8, 16), "");
            s.gameObject = this;
            s.setRelativePos(90-count*10, 0);
            s.draw(g2);
            temp/=10;
            count++;
        }
        while(count < 10)
        {
            Sprite s = new Sprite(AssetPool.getSprite("assets/numbers.png").image.getSubimage(0, 0, 8, 16), "");
            s.gameObject = this;
            s.setRelativePos(90-count*10, 0);
            s.draw(g2);
            count++;
        }
    }
    
}
