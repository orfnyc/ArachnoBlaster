package com.main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.studio.GameObject;
import com.studio.components.BoxBounds;
import com.studio.stuff.AssetPool;
import com.studio.stuff.Transform;
import com.studio.stuff.Vector2;
import com.studio.util.Constants;

public class LevelMap
{
    String palette;
    ArrayList<Tile> tiles;
    File file;
    Scanner sc;
    Tile[][] map;
    float minX, minY, maxX, maxY;
    ArrayList<Vector2> spawnpoints;
    ArrayList<GameObject> exitpoints;
    float startX, startY;
    int numEnemies;
 
    public LevelMap(String filePath)
    {
        palette = "assets/walls.png";
        BufferedImage image = AssetPool.getSprite(palette).image;
        int size = 16;
        tiles = new ArrayList<>();
        tiles.add(new Tile("assets/editorTools.png", 1, 1, 16, 16, 0, 0));
        for(int r = 0; r <= image.getWidth()-size; r += size)
        {
            for(int c = 0; c <= image.getHeight()-size; c += size)
            {
                Tile t = new Tile(palette, r/size, c/size, size, size, r, c);
                tiles.add(t);
            }
        }
        file = new File(filePath);
        try 
        {
            sc = new Scanner(file);
        } 
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        }
        startY = sc.nextInt();
        startX = sc.nextInt(); 
        map = new Tile[sc.nextInt()][sc.nextInt()];
        minX = (float)(Constants.SCREEN_WIDTH/2 - 16*map.length/2);
        minY = (float)(Constants.SCREEN_HEIGHT/2 - 16*map[0].length/2);
        maxX = (float)((minX + 16*(map.length-1))*Constants.SCALE);
        maxY = (float)((minY + 16*(map[0].length-1))*Constants.SCALE);
        startX = startX * 16 + minX;
        startY = startY * 16 + minY;
        for(int c = 0; c < map[0].length; c++)
        {
            for(int r = 0; r < map.length; r++)
            {
                int i = sc.nextInt();
                map[r][c] = tiles.get(i).makeDuplicate(minX + r*16, minY + c*16);
                
            }
        }
        for(int c = 0; c < map[0].length; c++)
        {
            for(int r = 0; r < map.length; r++)
            {
                if(sc.nextInt() == 1) map[r][c].addCollision();
            }
        }
        spawnpoints = new ArrayList<Vector2>();
        int spawns = sc.nextInt();
        for(int i = 0; i < spawns; i++)
        {
            float y = sc.nextInt() * 16 + minY;
            float x = sc.nextInt() * 16 + minX;
            spawnpoints.add(new Vector2(x, y));
        }
        exitpoints = new ArrayList<GameObject>();
        int exits = sc.nextInt();
        for(int i = 0; i < exits; i++)
        {
            float y = sc.nextInt() * 16 + minY;
            float x = sc.nextInt() * 16 + minX;
            GameObject g = new GameObject("", new Transform(new Vector2(x, y)));
            g.addComponent(new BoxBounds(16, 16, ""));
            exitpoints.add(g);
        }
        numEnemies = sc.nextInt();
    }

    public boolean checkCollision(BoxBounds b)
    {
        for(Tile[] r : map)
        {
            for(Tile c : r)
            {
                if(c.getComponent(BoxBounds.class) != null && BoxBounds.checkCollision(c.getComponent(BoxBounds.class), b)) return true;
            }
        }
        return false;
    }
    public void resolveCollisions(BoxBounds b)
    {
        for(Tile[] r : map)
        {
            for(Tile c : r)
            {
                if(c.getComponent(BoxBounds.class) != null && BoxBounds.checkCollision(c.getComponent(BoxBounds.class), b))
                {
                    b.resolveCollision(c.getComponent(BoxBounds.class));
                }
            }
        }
    }
    public void draw(Graphics2D g2)
    {
        for(int r = 0; r < map.length; r++)
        {
            for(int c = 0; c < map[0].length; c++)
            {
                map[r][c].draw(g2);
            }
        }
    }
}
