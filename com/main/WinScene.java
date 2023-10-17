package com.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.util.Scanner;

import com.studio.GameObject;
import com.studio.Scene;
import com.studio.Window;
import com.studio.components.MouseArea;
import com.studio.components.Sprite;
import com.studio.components.SpriteManager;
import com.studio.stuff.AssetPool;
import com.studio.stuff.Transform;
import com.studio.stuff.Vector2;
import com.studio.util.Constants;

public class WinScene extends Scene
{

    public static int lastScore;
    Scanner sc;
    GameObject playAgain;

    public WinScene(String name) 
    {
        super(name);
    }

    @Override
    public void init() 
    {
        GameObject g = new GameObject("background", new Transform(new Vector2()));
        g.addComponent(AssetPool.getSprite("assets/background2.png"));
        gameObjects.add(g);
        GameObject gameOver = new GameObject("", new Transform(new Vector2(Constants.SCREEN_WIDTH/2-188, Constants.SCREEN_HEIGHT/2-104)));
        gameOver.transform.scale = new Vector2(6.0f, 6.0f);
        gameOver.addComponent(AssetPool.getSprite("assets/win.png"));
        gameObjects.add(gameOver);
        File file = new File("assets/scores.txt");
        try 
        {
            sc = new Scanner(file);
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        for(int i = 0; i < 5; i++)
        {
            g = new GameObject("", new Transform(new Vector2(Constants.SCREEN_WIDTH/2-30, Constants.SCREEN_HEIGHT/2 + 20 + 20*i)));
            g.addComponent(new Sprite(AssetPool.getSprite("assets/numbers.png").image.getSubimage((i+1)*8, 0, 8, 16), ""));
            g.addComponent(new Sprite(AssetPool.getSprite("assets/numbers.png").image.getSubimage(0, 8, 2, 2), "dot"));
            g.getComponent("dot", Sprite.class).setRelativePos(10, 12);
            gameObjects.add(g);
            int score = sc.nextInt();
            ScoreDisplay s = new ScoreDisplay("", new Transform(new Vector2(Constants.SCREEN_WIDTH/2-10, Constants.SCREEN_HEIGHT/2 + 20 + 20*i)), score);
            gameObjects.add(s);
            if(score == lastScore)
            {
                g = new GameObject("", new Transform(new Vector2(Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT/2 + 20 + 20*i)));
                g.addComponent(new Sprite(AssetPool.getSprite("assets/yellowLine.png").image, "left"));
                g.addComponent(new Sprite(AssetPool.getSprite("assets/yellowLine.png").image, "right"));
                g.getComponent("left", Sprite.class).setRelativePos(-80, 0);
                g.getComponent("right", Sprite.class).setRelativePos(40, 0);
                gameObjects.add(g);
            }
        }
        playAgain = new GameObject("", new Transform(new Vector2(Constants.SCREEN_WIDTH/2-126, Constants.SCREEN_HEIGHT/2 + 140)));
        playAgain.transform.scale = new Vector2(3.0f, 3.0f);
        playAgain.addComponent(new SpriteManager());
        playAgain.getComponent(SpriteManager.class).addSprite(AssetPool.getSprite("assets/playAgain.png"));
        playAgain.getComponent(SpriteManager.class).addSprite(AssetPool.getSprite("assets/altPlayAgain.png"));
        playAgain.getComponent(SpriteManager.class).setSprite("assets/playAgain.png");
        playAgain.addComponent(new MouseArea("", playAgain.transform.position, new Vector2(252, 52)));
        gameObjects.add(playAgain);
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
        if(playAgain.getComponent(MouseArea.class).touched())
        {
            playAgain.getComponent(SpriteManager.class).setSprite("assets/altPlayAgain.png");
            if(Window.getWindow().mouseListener.mousePressed) 
            {
                Window.getWindow().changeScene(0);
            }
        }
        else
        {
            playAgain.getComponent(SpriteManager.class).setSprite("assets/playAgain.png");
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
