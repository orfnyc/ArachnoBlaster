package com.main;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.studio.GameObject;
import com.studio.Window;
import com.studio.components.Animation;
import com.studio.components.AnimationManager;
import com.studio.components.BoxBounds;
import com.studio.components.Sprite;
import com.studio.stuff.AssetPool;
import com.studio.stuff.Transform;
import com.studio.util.Constants;

public class Player extends GameObject
{
    public int lives;
    public int score;
    private String lastDirection;

    public Player(String name, Transform transform) 
    {
        super(name, transform);
        this.addComponent(new AnimationManager("player"));
        ArrayList<Sprite> a = new ArrayList<Sprite>();
        a.add(new Sprite(AssetPool.getSprite("assets/player.png").image.getSubimage(0, 0, 16, 16), "1"));
        a.add(new Sprite(AssetPool.getSprite("assets/player.png").image.getSubimage(16, 0, 16, 16), "2"));
        a.add(new Sprite(AssetPool.getSprite("assets/player.png").image.getSubimage(32, 0, 16, 16), "3"));
        a.add(new Sprite(AssetPool.getSprite("assets/player.png").image.getSubimage(48, 0, 16, 16), "4"));
        this.getComponent(AnimationManager.class).add(new Animation(a, "right"));
        a = new ArrayList<Sprite>();
        a.add(new Sprite(AssetPool.getSprite("assets/player.png").image.getSubimage(0, 16, 16, 16), "1"));
        a.add(new Sprite(AssetPool.getSprite("assets/player.png").image.getSubimage(16, 16, 16, 16), "2"));
        a.add(new Sprite(AssetPool.getSprite("assets/player.png").image.getSubimage(32, 16, 16, 16), "3"));
        a.add(new Sprite(AssetPool.getSprite("assets/player.png").image.getSubimage(48, 16, 16, 16), "4"));
        this.getComponent(AnimationManager.class).add(new Animation(a, "left"));
        a = new ArrayList<Sprite>();
        a.add(new Sprite(AssetPool.getSprite("assets/player.png").image.getSubimage(0, 16, 16, 16), "1"));
        this.getComponent(AnimationManager.class).add(new Animation(a, "leftStationary"));
        a = new ArrayList<Sprite>();
        a.add(new Sprite(AssetPool.getSprite("assets/player.png").image.getSubimage(0, 0, 16, 16), "1"));
        this.getComponent(AnimationManager.class).add(new Animation(a, "rightStationary"));
        lastDirection = "left";
        this.getComponent(AnimationManager.class).setCurrentAnimation("leftStationary");
        this.addComponent(new BoxBounds(16, 16, null));
        lives = 3;
    }

    @Override
    public void update(double dt) 
    {
        super.update(dt);
        int dx =0, dy = 0;
        if(Window.getWindow().keyListener.isKeyPressed(KeyEvent.VK_W))
        {
            this.transform.position.y -= 64*dt*Constants.SCALE;
            dy -= 1;
        }
        if(Window.getWindow().keyListener.isKeyPressed(KeyEvent.VK_S))
        {
            this.transform.position.y += 64*dt*Constants.SCALE;
            dy += 1;
        }
        if(Window.getWindow().keyListener.isKeyPressed(KeyEvent.VK_A))
        {
            this.transform.position.x -= 64*dt*Constants.SCALE;
            dx -= 1;
        }
        if(Window.getWindow().keyListener.isKeyPressed(KeyEvent.VK_D))
        {
            this.transform.position.x += 64*dt*Constants.SCALE;
            dx += 1;
        }
        if(dx > 0)
        {
            this.getComponent(AnimationManager.class).setCurrentAnimation("right");
            lastDirection = "right";
        }
        else if(dx < 0)
        {
            this.getComponent(AnimationManager.class).setCurrentAnimation("left");
            lastDirection = "left";
        }
        else if(dy != 0)
        {
            if(lastDirection.equals("left"))
            {
                this.getComponent(AnimationManager.class).setCurrentAnimation("left");
            }
            else
            {
                this.getComponent(AnimationManager.class).setCurrentAnimation("right");
            }
        }
        else if(dx == 0 && dy == 0) 
        {
            if(lastDirection.equals("left"))
            {
                this.getComponent(AnimationManager.class).setCurrentAnimation("leftStationary");
            }
            else
            {
                this.getComponent(AnimationManager.class).setCurrentAnimation("rightStationary");
            }
        }
    }  
}