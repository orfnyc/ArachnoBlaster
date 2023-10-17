package com.studio.stuff;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import com.studio.components.Sprite;

import javax.imageio.ImageIO;

public class AssetPool 
{
    public static Map<String, Sprite> sprites = new HashMap<>();

    public static boolean hasSprite(String pictureFile) 
    {
        return AssetPool.sprites.containsKey(pictureFile);
    }
    
    public static Sprite getSprite(String pictureFile)
    {
        InputStream is = Sprite.class.getResourceAsStream("/" + pictureFile);
        try {
            BufferedImage image = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(AssetPool.hasSprite(pictureFile))
        {
            return AssetPool.sprites.get(pictureFile).copy();
        }
        else
        {
            Sprite sprite = new Sprite(pictureFile);
            AssetPool.addSprite(pictureFile, sprite);
            return AssetPool.sprites.get(pictureFile).copy();
        }
        
    }

    public static void addSprite(String pictureFile, Sprite sprite)
    {
        InputStream is = Sprite.class.getResourceAsStream("/" + pictureFile);
        try {
            BufferedImage image = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(!AssetPool.hasSprite(pictureFile))
        {
            AssetPool.sprites.put(pictureFile, sprite);
        }
        else
        {
            System.out.println("Asset pool already has asset: " + pictureFile);
        }
    }
}
