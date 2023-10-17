package com.studio;

import javax.swing.JFrame;
import java.awt.*;

import com.main.*;
import com.requirements.Requirements;
import com.studio.util.*;
import java.awt.event.KeyEvent;

public class Window extends JFrame implements Runnable
{
    private static Window window = null;
    private static boolean isRunning = true;

    public ML mouseListener;
    public KL keyListener;

    private Scene currentScene;
    private Image doubleBufferImage;
    private Graphics doubleBufferGraphics;

    public Window()
    {
        Requirements.meetRequirements();
        this.mouseListener = new ML();
        this.keyListener = new KL();
        this.setSize((int)(Constants.SCREEN_WIDTH * Constants.SCALE), (int)(Constants.SCREEN_HEIGHT * Constants.SCALE));
        this.setTitle(Constants.TITLE);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        this.addKeyListener(keyListener);
        this.setLocationRelativeTo(null);
        changeScene(3);
    }

    public void changeScene(int scene)
    {
        switch(scene)
        {
            case 0:
                currentScene = new GameScene("world");
                currentScene.init();
                break;
            case 1: 
                currentScene = new LevelEditor("");
                currentScene.init();
                break;
            case 2:
                currentScene = new GameOverScene("game over");
                currentScene.init();
                break;
            case 3:
                currentScene = new StartScene("start");
                currentScene.init();
                break;
            case 4:
                currentScene = new WinScene("win");
                currentScene.init();
                break;
            default:
                System.out.println("Bad Scene");
                break;
        }
    }

    public Scene getCurrentScene()
    {
        return currentScene;
    }

    public static Window getWindow()
    {
        if (Window.window == null)
        {
            Window.window = new Window();
        }
        return Window.window;
    }

    public void update(double dt)
    {
        currentScene.update(dt);
        draw(getGraphics());
        if(keyListener.isKeyPressed(KeyEvent.VK_Y)) isRunning = false;
    }

    public void draw(Graphics g)
    {
        if(doubleBufferImage == null)
        {
            doubleBufferImage = createImage(getWidth(), getHeight());
            doubleBufferGraphics  = doubleBufferImage.getGraphics();
        }
        renderOffScreen(doubleBufferGraphics);
        g.drawImage(doubleBufferImage, 0, 0, getWidth(), getHeight(), null);
    }

    public void renderOffScreen(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        currentScene.draw(g2);
    }

    @Override
    public void run()
    {
        double lastFrameTime = 0.0;
        try
        {
            while(isRunning)
            {
                double time = Time.getTime();
                double deltaTime = time - lastFrameTime;
                lastFrameTime = time;
                update(deltaTime);
            }
            dispose();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public String toString()
    {
        return "Window";
    }

    public boolean equals(Object other)
    {
        return false;
    }
}
