package com.main;


import com.studio.GameObject;
import com.studio.stuff.Transform;
import com.studio.util.Constants;

public class Enemy extends GameObject
{
    public float goalX;
    public float goalY;
    public int speed;
    public boolean markForRemoval;
    public int health;
    public int points;

    public Enemy(String name, Transform transform) 
    {
        super(name, transform); 
    }

    public void setGoal(float x, float y)
    {
        this.goalX = x; this.goalY = y;
    }

    public void update(double dt)
    {
        super.update(dt);
        if(Math.abs(this.transform.position.x - goalX) < speed*dt*Constants.SCALE) this.transform.position.x = goalX;
        else if(this.transform.position.x > goalX) this.transform.position.x -= speed*dt*Constants.SCALE;
        else if(this.transform.position.x < goalX) this.transform.position.x += speed*dt*Constants.SCALE;

        if(Math.abs(this.transform.position.y - goalY) < speed*dt*Constants.SCALE) this.transform.position.y = goalY;
        else if(this.transform.position.y > goalY) this.transform.position.y -= speed*dt*Constants.SCALE;
        else if(this.transform.position.y < goalY) this.transform.position.y += speed*dt*Constants.SCALE;

        if(health <= 0) markForRemoval = true;
    }

   
}
