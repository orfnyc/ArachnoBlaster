package com.studio.components;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.*;

import javax.imageio.ImageIO;

import com.studio.Component;
import com.studio.stuff.AssetPool;
import com.studio.util.Constants;

import java.awt.geom.AffineTransform;

public class Sprite extends Component
{
    public BufferedImage image;
    public String pictureFile;
    public int width, height;
    public int relativeX, relativeY;

    public boolean isSubsprite = false;
    public int row, column, index;


    public BufferedImage image() {return image;};

    public void setRelativePos(int x, int y) {relativeX =(int)((float)x*Constants.SCALE); relativeY = (int)((float)y*Constants.SCALE);}

    public Sprite(String pictureFile)
    {
        super(pictureFile);
        this.pictureFile = pictureFile;
        try
        {
            InputStream is = Sprite.class.getResourceAsStream("/" + pictureFile);
            if(AssetPool.hasSprite(pictureFile))
            {
                System.out.println("Asset already exists: " + pictureFile);
                System.exit(-1);
            }
            this.image = ImageIO.read(is);
            this.width = image.getWidth(null);
            this.height = image.getHeight(null);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public Sprite (BufferedImage image, String name)
    {
        this.name = name;
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    public Sprite(BufferedImage image, int row, int column, int index, String name)
    {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.row = row;
        this.column = column;
        this.index = index;
        this.isSubsprite = true;
        this.name = name;
    }

    @Override
    public void draw(Graphics2D g2)
    {
        AffineTransform t = new AffineTransform();
        t.setToIdentity();
        t.translate((double)gameObject.transform.position.x + this.relativeX, (double)gameObject.transform.position.y + this.relativeY);
        t.rotate(gameObject.transform.rotation, width/2*gameObject.transform.scale.x, height/2*gameObject.transform.scale.y);
        t.scale(gameObject.transform.scale.x, gameObject.transform.scale.y);
        g2.drawImage(image, t, null);

    }

    @Override
    public Sprite copy() 
    {
        if(!isSubsprite)
            return new Sprite(this.image, this.name);
        else
            return new Sprite(this.image, this.row, this.column, this.index, this.name);
    }

    public String toString()
    {
        return "Sprite Name: " + name;
    }
    
    public boolean equals(Object other)
    {
        return false;
    }
}
