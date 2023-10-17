package com.main;

import java.awt.Color;
import java.awt.Graphics2D;

import com.studio.GameObject;
import com.studio.Scene;
import com.studio.Window;
import com.studio.components.MouseArea;
import com.studio.components.SpriteManager;
import com.studio.stuff.AssetPool;
import com.studio.stuff.Transform;
import com.studio.stuff.Vector2;
import com.studio.util.Constants;

public class StartScene extends Scene
{
    private GameObject play;
    public StartScene(String name) 
    {
        super(name);
    }

    @Override
    public void init() 
    {
        GameObject g = new GameObject("background", new Transform(new Vector2()));
        g.addComponent(AssetPool.getSprite("assets/background2.png"));
        gameObjects.add(g);
        g = new GameObject("title", new Transform(new Vector2(Constants.SCREEN_WIDTH/2-180, Constants.SCREEN_HEIGHT/2-120)));
        g.addComponent(AssetPool.getSprite("assets/title.png"));
        gameObjects.add(g);
        play = new GameObject("", new Transform(new Vector2(Constants.SCREEN_WIDTH/2-58, Constants.SCREEN_HEIGHT/2 + 26)));
        play.transform.scale = new Vector2(3.0f, 3.0f);
        play.addComponent(new SpriteManager());
        play.getComponent(SpriteManager.class).addSprite(AssetPool.getSprite("assets/play.png"));
        play.getComponent(SpriteManager.class).addSprite(AssetPool.getSprite("assets/playAlt.png"));
        play.getComponent(SpriteManager.class).setSprite("assets/play.png");
        play.addComponent(new MouseArea("", play.transform.position, new Vector2(252, 52)));
        gameObjects.add(play);

        g = new GameObject("background", new Transform(new Vector2()));
        g.addComponent(AssetPool.getSprite("assets/background1.png"));
        gameObjects.add(g);
    }

    @Override
    public void update(double dt) 
    {
        for(GameObject g : gameObjects)
        {
             g.update(dt);
        }
        if(play.getComponent(MouseArea.class).touched())
        {
            play.getComponent(SpriteManager.class).setSprite("assets/playAlt.png");
            if(Window.getWindow().mouseListener.mousePressed) 
            {
                Window.getWindow().changeScene(0);
            }
        }
        else
        {
            play.getComponent(SpriteManager.class).setSprite("assets/play.png");
        }
    }

    @Override
    public void draw(Graphics2D g2) 
    {
        g2.setColor(new Color(0.0f, 0.0f, 0.0f));
        g2.fillRect(0, 0, (int)(Constants.SCREEN_WIDTH * Constants.SCALE), (int)(Constants.SCREEN_HEIGHT * Constants.SCALE));
        for(GameObject g : gameObjects)
        {
            g.draw(g2);
        }
    }
    
}
