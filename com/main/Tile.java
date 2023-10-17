package com.main;

import java.awt.Graphics2D;
import java.awt.Image;

import com.studio.GameObject;
import com.studio.components.BoxBounds;
import com.studio.components.Sprite;
import com.studio.stuff.AssetPool;
import com.studio.stuff.Transform;
import com.studio.stuff.Vector2;
import com.studio.util.Constants;

public class Tile extends GameObject
{
    public Image image;
    public int type;

    private Tile(float xPos, float yPos)
    {
        super("tile", new Transform(new Vector2(xPos, yPos)));
    }

    public Tile(String i, int x, int y, int width, int height, float xPos, float yPos)
    {
        super("tile", new Transform(new Vector2(xPos, yPos)));
        this.addComponent(new Sprite(AssetPool.getSprite(i).image.getSubimage(x*width, y*height, width, height), i));
    }

    public void addCollision()
    {
        if(this.getComponent(BoxBounds.class) == null)
        {
            this.addComponent(new BoxBounds(16, 16, null));
        }
    }

    public void draw(Graphics2D g2)
    {
        super.draw(g2);
    }

    public Tile copy()
    {
        Tile newTile = new Tile(this.transform.position.x/Constants.SCALE, this.transform.position.y/Constants.SCALE);
        newTile.addComponent(this.getComponent(Sprite.class).copy());
        newTile.type = this.type;
        return newTile;
    }

    public Tile makeDuplicate(float xPos, float yPos)
    {
        Tile newTile = new Tile(xPos, yPos);
        newTile.addComponent(this.getComponent(Sprite.class).copy());
        return newTile;
    }
}
