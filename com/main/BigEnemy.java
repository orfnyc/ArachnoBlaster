package com.main;

import java.util.ArrayList;

import com.studio.components.Animation;
import com.studio.components.AnimationManager;
import com.studio.components.BoxBounds;
import com.studio.components.Sprite;
import com.studio.stuff.AssetPool;
import com.studio.stuff.Transform;

public class BigEnemy extends Enemy
{

    public BigEnemy(String name, Transform transform) 
    {
        super(name, transform);
        speed = 16;
        health = 4;
        points = 45;
        this.addComponent(new AnimationManager("spider"));
        ArrayList<Sprite> a = new ArrayList<Sprite>();
        a.add(new Sprite(AssetPool.getSprite("assets/bigSpider.png").image.getSubimage(0, 0, 32, 32), "1"));
        a.add(new Sprite(AssetPool.getSprite("assets/bigSpider.png").image.getSubimage(32, 0, 32, 32), "2"));
        a.add(new Sprite(AssetPool.getSprite("assets/bigSpider.png").image.getSubimage(64, 0, 32, 32), "3"));
        a.add(new Sprite(AssetPool.getSprite("assets/bigSpider.png").image.getSubimage(96, 0, 32, 32), "4"));
        a.add(new Sprite(AssetPool.getSprite("assets/bigSpider.png").image.getSubimage(0, 32, 32, 32), "5"));
        this.getComponent(AnimationManager.class).add(new Animation(a, "scuttle"));
        this.getComponent(AnimationManager.class).setCurrentAnimation("scuttle");
        this.addComponent(new BoxBounds(32, 32, ""));
    }
    
}
