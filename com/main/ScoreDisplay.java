package com.main;

import java.util.ArrayList;

import com.studio.GameObject;
import com.studio.components.Sprite;
import com.studio.stuff.AssetPool;
import com.studio.stuff.Transform;

public class ScoreDisplay extends GameObject
{

    public ScoreDisplay(String name, Transform transform, int score) 
    {
        super(name, transform);
        ArrayList<Integer> digits = toDigits(score);
        for(int i = 0; i < digits.size(); i++)
        {
            Sprite s = new Sprite(AssetPool.getSprite("assets/numbers.png").image.getSubimage(8*digits.get(i),0,8, 16), "");
            s.setRelativePos(10*i, 0);
            this.addComponent(s);
        }
    }

    private ArrayList<Integer> toDigits(int n)
    {
        int temp = n;
        ArrayList<Integer> digits = new ArrayList<>();
        while(temp > 0)
        {
            digits.add(0, temp%10);
            temp/=10;
        }
        return digits;
    }
    

}
