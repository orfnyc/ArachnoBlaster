package com.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.studio.Scene;
import com.studio.Window;
import com.studio.stuff.AssetPool;
import com.studio.util.Constants;

public class LevelEditor extends Scene
{

    private int index;
    private ArrayList<Tile> tiles;
    private Tile mouse;
    private double keyCooldown;
    private float minX, minY, maxX, maxY;
    private HashMap<String, Tile> placedTiles;
    private HashMap<String, Tile> placedCollisions;
    private String mode;
    private Tile collisionBox;
    private boolean clickable;
    private Tile tileBox;

    public LevelEditor(String name) 
    {
        super(name); 
    }

    @Override
    public void init() 
    {   
        collisionBox = new Tile("assets/editorTools.png", 1, 0, 16, 16, 0, 0);
        tileBox = new Tile("assets/editorTools.png", 0, 0, 16, 16, 0, 0);
        minX = minY = Float.MAX_VALUE;
        maxX = maxY = Float.MIN_VALUE;
        mode = "draw";
        keyCooldown = 0.2;
        String file = "assets/walls.png";
        int size = 16;
        BufferedImage image = AssetPool.getSprite(file).image;
        tiles = new ArrayList<>();
        placedTiles = new HashMap<>();
        placedCollisions = new HashMap<>();
        int count = 1;
        for(int r = 0; r <= image.getWidth()-size; r += size)
        {
            for(int c = 0; c <= image.getHeight()-size; c += size)
            {
                Tile t = new Tile(file, r/size, c/size, size, size, r + 25, c + 25);
                t.type = count++;
                tiles.add(t);
            }
        }
        mouse = tiles.get(index).copy();
    }

    @Override
    public void update(double dt) 
    {
        mouse.transform.position.x = (float)((int)Window.getWindow().mouseListener.x/(int)(16*Constants.SCALE) * (int)(16*Constants.SCALE));
        mouse.transform.position.y = (float)((int)Window.getWindow().mouseListener.y/(int)(16*Constants.SCALE) * (int)(16*Constants.SCALE));
        tileBox.transform.position.x = mouse.transform.position.x;
        tileBox.transform.position.y = mouse.transform.position.y;
        if(mode.equals("collision"))
        {
            collisionBox.transform.position.x = mouse.transform.position.x;
            collisionBox.transform.position.y = mouse.transform.position.y;
        }
        
        if(keyCooldown <= 0 && Window.getWindow().keyListener.isKeyPressed(KeyEvent.VK_A))
        {
            keyCooldown = 0.3;
            index -= 1;
            if(index < 0)
            {
                index = tiles.size() - 1;
            }
            mouse = tiles.get(index).copy();
        }
        else if(keyCooldown <= 0 && Window.getWindow().keyListener.isKeyPressed(KeyEvent.VK_D))
        {
            keyCooldown = 0.3;
            index += 1;
            if(index > tiles.size() - 1)
            {
                index = 0;
            }
            mouse = tiles.get(index).copy();
        }
        if(keyCooldown <= 0 && Window.getWindow().keyListener.isKeyPressed(KeyEvent.VK_E))
        {
            keyCooldown = 0.3;
            mode = "eraser";
        }
        else if(keyCooldown <= 0 && Window.getWindow().keyListener.isKeyPressed(KeyEvent.VK_C))
        {
            keyCooldown = 0.3;
            mode = "collision";
        }
        else if(keyCooldown <= 0 && Window.getWindow().keyListener.isKeyPressed(KeyEvent.VK_R))
        {
            keyCooldown = 0.3;
            mode = "draw";
        }
        if(Window.getWindow().keyListener.isKeyPressed(KeyEvent.VK_P))
        {
            save();
        }
        keyCooldown -= dt;
        if(mode.equals("draw") && Window.getWindow().mouseListener.mousePressed)
        {
            placedTiles.put(mouse.transform.position.x + " " + mouse.transform.position.y, mouse.copy());
            if(mouse.transform.position.x > maxX) maxX = mouse.transform.position.x;
            else if(mouse.transform.position.x < minX) minX = mouse.transform.position.x;
            if(mouse.transform.position.y > maxY) maxY = mouse.transform.position.y;
            else if(mouse.transform.position.y < minY) minY = mouse.transform.position.y;
        }
        else if(mode.equals("eraser") && Window.getWindow().mouseListener.mousePressed)
        {
            placedTiles.remove(mouse.transform.position.x + " " + mouse.transform.position.y);
        }
        else if(clickable && mode.equals("collision") && Window.getWindow().mouseListener.mousePressed)
        {
            clickable = false;
            collisionBox.transform.position.x = mouse.transform.position.x;
            collisionBox.transform.position.y = mouse.transform.position.y;
            if(placedCollisions.containsKey(mouse.transform.position.x + " " + mouse.transform.position.y))
            {
                placedCollisions.remove(mouse.transform.position.x + " " + mouse.transform.position.y);
            }
            else
            {
                placedCollisions.put(mouse.transform.position.x + " " + mouse.transform.position.y, collisionBox.copy());
            }
        }
        if(!Window.getWindow().mouseListener.mousePressed)
        {
            clickable = true;
        }
    }

    @Override
    public void draw(Graphics2D g2) 
    {
        g2.setColor(new Color(1.0f, 1.0f, 1.0f));
        g2.fillRect(0, 0, (int)(Constants.SCREEN_WIDTH * Constants.SCALE), (int)(Constants.SCREEN_HEIGHT * Constants.SCALE));
        for (Map.Entry<String, Tile> entry : placedTiles.entrySet()) 
        {
            entry.getValue().draw(g2);
        }
        if(mode.equals("collision"))
        {
            for (Map.Entry<String, Tile> entry : placedCollisions.entrySet())
            {
                entry.getValue().draw(g2);
            }
            collisionBox.draw(g2);
        }
        if(mode.equals("draw")) 
        {
            mouse.draw(g2);
            tileBox.draw(g2);
        }
        for(Tile t : tiles) t.draw(g2);
    }

    public void save()
    {
        int minXa = (int)minX / (int)(16*Constants.SCALE);
        int maxXa = (int)maxX / (int)(16*Constants.SCALE);
        int minYa = (int)minY / (int)(16*Constants.SCALE);
        int maxYa = (int)maxY / (int)(16*Constants.SCALE);
        int width = (int) (maxXa - minXa) + 1;
        int length = (int) (maxYa - minYa) + 1;
        Scanner sc = new Scanner(System.in);
        System.out.println("File name: ");
        String name = sc.nextLine();
        try {
            File myObj = new File("assets/" + name + ".txt");
            if (myObj.createNewFile()) {
              System.out.println("File created: " + myObj.getName());
            } else {
              System.out.println("File already exists.");
            }
            FileWriter myWriter = new FileWriter("assets/" + name + ".txt");
            myWriter.write(width + " " + length + "\n");
            for(int r = 0; r < length; r++)
            {
                for(int c = 0; c < width; c++)
                {
                    if(placedTiles.containsKey((float)((minXa+c)*16*Constants.SCALE) + " " + (float)((minYa+r)*16*Constants.SCALE)))
                    {
                        myWriter.write(placedTiles.get((float)((minXa+c)*16*Constants.SCALE) + " " + (float)((minYa+r)*16*Constants.SCALE)).type + " ");
                    }
                    else
                    {
                        myWriter.write(0 + " ");
                    }
                }
                myWriter.write("\n");
            }
            for(int r = 0; r < length; r++)
            {
                for(int c = 0; c < width; c++)
                {
                    if(placedCollisions.containsKey((float)((minXa+c)*16*Constants.SCALE) + " " + (float)((minYa+r)*16*Constants.SCALE)))
                    {
                        myWriter.write(1 + " ");
                    }
                    else
                    {
                        myWriter.write(0 + " ");
                    }
                }
                myWriter.write("\n");
            }
            myWriter.close();
          } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
        sc.close();
        this.init();
    }
    
}

