package com.main;

import java.util.ArrayList;

import com.studio.components.Animation;
import com.studio.components.AnimationManager;
import com.studio.components.BoxBounds;
import com.studio.components.Sprite;
import com.studio.stuff.AssetPool;
import com.studio.stuff.Transform;
import com.studio.util.Constants;

public class NormalEnemy extends Enemy
{

    public NormalEnemy(String name, Transform transform) 
    {
        super(name, transform);
        speed = 32;
        health = 1;
        points = 10;
        this.addComponent(new AnimationManager("spider"));
        ArrayList<Sprite> a = new ArrayList<Sprite>();
        a.add(new Sprite(AssetPool.getSprite("assets/smallSpider.png").image.getSubimage(0, 0, 16, 16), "1"));
        a.add(new Sprite(AssetPool.getSprite("assets/smallSpider.png").image.getSubimage(16, 0, 16, 16), "2"));
        a.add(new Sprite(AssetPool.getSprite("assets/smallSpider.png").image.getSubimage(32, 0, 16, 16), "3"));
        a.add(new Sprite(AssetPool.getSprite("assets/smallSpider.png").image.getSubimage(48, 0, 16, 16), "4"));
        this.getComponent(AnimationManager.class).add(new Animation(a, "down"));
        this.getComponent(AnimationManager.class).setCurrentAnimation("down");
        this.addComponent(new BoxBounds(16, 16, ""));
    }  
}
