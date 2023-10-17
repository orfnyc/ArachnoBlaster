package com.main;

import java.util.ArrayList;

import com.studio.GameObject;
import com.studio.components.Animation;
import com.studio.components.AnimationManager;
import com.studio.components.BoxBounds;
import com.studio.components.Sprite;
import com.studio.stuff.AssetPool;
import com.studio.stuff.Transform;
import com.studio.util.Constants;

public class Projectile extends GameObject
{
    private double xDirection, yDirection;
    public boolean markForRemoval;
    public Projectile(String name, Transform transform, double xDirection, double yDirection) 
    {
        super(name, transform);
        this.addComponent(new AnimationManager("laser"));
        ArrayList<Sprite> a = new ArrayList<Sprite>();
        a.add(new Sprite(AssetPool.getSprite("assets/laser.png").image.getSubimage(0, 0, 8, 8), "1"));
        a.add(new Sprite(AssetPool.getSprite("assets/laser.png").image.getSubimage(8, 0, 8, 8), "2"));
        a.add(new Sprite(AssetPool.getSprite("assets/laser.png").image.getSubimage(16, 0, 8, 8), "3"));
        a.add(new Sprite(AssetPool.getSprite("assets/laser.png").image.getSubimage(24, 0, 8, 8), "4"));
        a.add(new Sprite(AssetPool.getSprite("assets/laser.png").image.getSubimage(0, 8, 8, 8), "5"));
        a.add(new Sprite(AssetPool.getSprite("assets/laser.png").image.getSubimage(8, 8, 8, 8), "6"));
        this.getComponent(AnimationManager.class).add(new Animation(a, "shot"));
        this.getComponent(AnimationManager.class).setCurrentAnimation("shot");
        this.addComponent(new BoxBounds(8, 8, null));
        this.xDirection = xDirection;
        this.yDirection = yDirection;
        markForRemoval = false;
    }

    public void update(double dt)
    {
        super.update(dt);
        this.transform.position.x += 128*xDirection*dt*Constants.SCALE;
        this.transform.position.y += 128*yDirection*dt*Constants.SCALE;
    } 

}
