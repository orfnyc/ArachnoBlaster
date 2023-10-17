package com.studio.components;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import com.studio.Component;
import com.studio.util.Constants;

public class Animation extends Component
{

    ArrayList<Sprite> frames;
    public int frameNum;
    public Sprite currentFrame;
    public String name;
    private int relativeX, relativeY;
    float frameTime = 0.1f;
    float frameTimeLeft = 0.1f;

    public void setRelativePos(int x, int y)
    {
        this.relativeX = (int)((float)x*Constants.SCALE); this.relativeY = (int)((float)y*Constants.SCALE);
    }

    public Animation(ArrayList<Sprite> frames, String name)
    {
        this.frames = frames;
        this.name = name;
        this.frameNum = 0;
        this.currentFrame = this.frames.get(0);
    }

    public void addFrame(Sprite s)
    {
        frames.add(s);
    }

    public void draw(Graphics2D g2)
    {
        AffineTransform t = new AffineTransform();
        t.setToIdentity();
        t.translate((double)gameObject.transform.position.x + this.relativeX, (double)gameObject.transform.position.y + this.relativeY);
        t.rotate(gameObject.transform.rotation, currentFrame.width/2*gameObject.transform.scale.x, currentFrame.height/2*gameObject.transform.scale.y);
        t.scale(gameObject.transform.scale.x, gameObject.transform.scale.y);
        g2.drawImage(currentFrame.image, t, null);
    }

    public void update(double dt)
    {
        frameTimeLeft -= dt;
        currentFrame = frames.get(frameNum);
        if(frameTimeLeft <= 0.0f)
        {
            if(frameNum == frames.size()-1)
            {
                frameNum = 0;
            }
            else
            {
                frameNum++;
            }
            frameTimeLeft = frameTime;
        }
    }

    public Animation copy() 
    {
        ArrayList<Sprite> newFrames = new ArrayList<Sprite>();
        for(Sprite s : this.frames)
        {
            newFrames.add(s);
        }
        return new Animation(newFrames, this.name);
    }

    public String toString()
    {
        return frames.toString();
    }

    public boolean equals(Object other)
    {
        return false;
    }
    
}