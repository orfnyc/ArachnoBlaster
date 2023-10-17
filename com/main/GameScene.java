package com.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.studio.GameObject;
import com.studio.Scene;
import com.studio.Window;
import com.studio.components.BoxBounds;
import com.studio.stuff.AssetPool;
import com.studio.stuff.Transform;
import com.studio.stuff.Vector2;
import com.studio.util.Constants;

public class GameScene extends Scene
{
    private Player player;
    private ArrayList<Projectile> projectiles;
    private ArrayList<Enemy> enemies;
    private ArrayList<Powerup> powerups;
    private double cooldown;
    private double maxCooldown;
    private double enemyCooldown;
    private double maxEnemyCooldown;
    private boolean hitThisFrame;
    private LivesCounter livesCounter;
    private ScoreCounter scoreCounter;
    private GameObject exitSign;
    private int level;
    private LevelMap[] levels;
    private double tripleShot;
    private double fasterShooting;
    private boolean cleared;
    private double time;
    private Scanner sc;
    private GameObject background;
    

    public GameScene(String name) 
    {
        super(name);
    }

    @Override
    public void init() 
    {
        background = new GameObject("background", new Transform(new Vector2()));
        background.addComponent(AssetPool.getSprite("assets/background2.png"));
        projectiles = new ArrayList<>();
        enemies = new ArrayList<>();
        powerups = new ArrayList<>();
        levels = new LevelMap[11];
        levels[0] = new LevelMap("assets/level6.txt");
        levels[1] = new LevelMap("assets/level2.txt");
        levels[2] = new LevelMap("assets/level3.txt");
        levels[3] = new LevelMap("assets/level4.txt");
        levels[4] = new LevelMap("assets/level5.txt");
        levels[5] = new LevelMap("assets/level6.txt");
        levels[6] = new LevelMap("assets/level7.txt");
        levels[7] = new LevelMap("assets/level8.txt");
        levels[8] = new LevelMap("assets/level9.txt");
        levels[9] = new LevelMap("assets/level10.txt");
        levels[10] = new LevelMap("assets/level11.txt");

        player = new Player("player", new Transform(new Vector2(levels[level].startX, levels[level].startY)));
        gameObjects.add(player);
        maxCooldown = 0.5;
        maxEnemyCooldown = 0.75;
        livesCounter = new LivesCounter("lives", new Transform(new Vector2(2, 20)));
        gameObjects.add(livesCounter);
        scoreCounter = new ScoreCounter("score", new Transform(new Vector2(2, 36)));
        gameObjects.add(scoreCounter);
        GameObject g = new GameObject("move", new Transform(new Vector2(10, Constants.SCREEN_HEIGHT-48)));
        g.transform.scale = new Vector2(2.0f, 2.0f);
        g.addComponent(AssetPool.getSprite("assets/moveControls.png"));
        gameObjects.add(g);
        g = new GameObject("shoot", new Transform(new Vector2(Constants.SCREEN_WIDTH-48, Constants.SCREEN_HEIGHT-48)));
        g.transform.scale = new Vector2(2.0f, 2.0f);
        g.addComponent(AssetPool.getSprite("assets/shootControls.png"));
        gameObjects.add(g);
        exitSign = new GameObject("exit", new Transform(new Vector2(Constants.SCREEN_WIDTH/2 - 20, levels[level].minY-32)));
        exitSign.transform.scale = new Vector2(2.0f, 2.0f);
        exitSign.addComponent(AssetPool.getSprite("assets/exit.png"));
        time = 0;
    }

    @Override
    public void update(double dt) 
    {
        livesCounter.numLives = player.lives;
        scoreCounter.score = player.score;
        hitThisFrame = false;
        for(GameObject g : gameObjects)
        {
            g.update(dt);
            if(g.getComponent(BoxBounds.class) != null)
            {
                levels[level].resolveCollisions(g.getComponent(BoxBounds.class));
            }
        }
        for(Projectile p : projectiles)
        {
            p.update(dt);
        }
        for(Enemy e : enemies)
        {
            e.setGoal(player.transform.position.x, player.transform.position.y);
        }
        cooldown -= dt;
        if(cooldown < 0)
        {      
            int x = 0; int y = 0;
            if(Window.getWindow().keyListener.isKeyPressed(KeyEvent.VK_UP))
            {
                y--;
            }
            if(Window.getWindow().keyListener.isKeyPressed(KeyEvent.VK_DOWN))
            {
                y++;
            }
            if(Window.getWindow().keyListener.isKeyPressed(KeyEvent.VK_LEFT))
            {
                x--;
            }
            if(Window.getWindow().keyListener.isKeyPressed(KeyEvent.VK_RIGHT))
            {
                x++;
            }
            if(x != 0 || y != 0)
            {
                Projectile p = new Projectile(null, new Transform(new Vector2(player.transform.position.x/Constants.SCALE + 4, player.transform.position.y/Constants.SCALE + 4)), x, y);
                projectiles.add(p);
                if(tripleShot > 0 && x != 0 && y == 0)
                {
                    p = new Projectile(null, new Transform(new Vector2(player.transform.position.x/Constants.SCALE + 4, player.transform.position.y/Constants.SCALE + 4)), x, 0.2);
                    projectiles.add(p);
                    p = new Projectile(null, new Transform(new Vector2(player.transform.position.x/Constants.SCALE + 4, player.transform.position.y/Constants.SCALE + 4)), x, -0.2);
                    projectiles.add(p);
                }
                else if(tripleShot > 0 && x == 0 && y != 0)
                {
                    p = new Projectile(null, new Transform(new Vector2(player.transform.position.x/Constants.SCALE + 4, player.transform.position.y/Constants.SCALE + 4)), 0.2, y);
                    projectiles.add(p);
                    p = new Projectile(null, new Transform(new Vector2(player.transform.position.x/Constants.SCALE + 4, player.transform.position.y/Constants.SCALE + 4)), -0.2, y);
                    projectiles.add(p);
                }
                else if(tripleShot > 0 && x*y < 0)
                {
                    p = new Projectile(null, new Transform(new Vector2(player.transform.position.x/Constants.SCALE + 4, player.transform.position.y/Constants.SCALE + 4)), x + 0.2, y + 0.2);
                    projectiles.add(p);
                    p = new Projectile(null, new Transform(new Vector2(player.transform.position.x/Constants.SCALE + 4, player.transform.position.y/Constants.SCALE + 4)), x-0.2, y-0.2);
                    projectiles.add(p);
                }
                else if(tripleShot > 0 && x*y > 0)
                {
                    p = new Projectile(null, new Transform(new Vector2(player.transform.position.x/Constants.SCALE + 4, player.transform.position.y/Constants.SCALE + 4)), x + 0.2, y - 0.2);
                    projectiles.add(p);
                    p = new Projectile(null, new Transform(new Vector2(player.transform.position.x/Constants.SCALE + 4, player.transform.position.y/Constants.SCALE + 4)), x-0.2, y+0.2);
                    projectiles.add(p);
                }
                cooldown = maxCooldown;
                if(fasterShooting > 0)
                {
                    cooldown /= 4.0;
                }
            }
        }
        for(int i = projectiles.size()-1; i >= 0; i--)
        {
            if(projectiles.get(i).transform.position.x > Constants.SCREEN_WIDTH*Constants.SCALE
            || projectiles.get(i).transform.position.x < 0 
            || projectiles.get(i).transform.position.y > Constants.SCREEN_HEIGHT*Constants.SCALE
            || projectiles.get(i).transform.position.y < 0
            || levels[level].checkCollision(projectiles.get(i).getComponent(BoxBounds.class)))
            {
                projectiles.remove(i);
            }
        }
        enemyCooldown -= dt;
        if(levels[level].numEnemies > 0 && (enemyCooldown <= 0 || enemies.size() == 0))
        {
            int n = (int)(Math.random() * 10);
            int r = (int)(Math.random() * levels[level].spawnpoints.size());
            Enemy e;
            if(n == 9)
            {
                e = new BigEnemy("", new Transform(new Vector2(levels[level].spawnpoints.get(r).x, levels[level].spawnpoints.get(r).y)));
            }
            else 
            {
                e = new NormalEnemy("", new Transform(new Vector2(levels[level].spawnpoints.get(r).x, levels[level].spawnpoints.get(r).y)));
            }
            enemies.add(e);
            enemyCooldown = maxEnemyCooldown;
        }
        for(Enemy e : enemies)
        {
            e.update(dt);
            for(Enemy f : enemies)
            {
                if(BoxBounds.checkCollision(e.getComponent(BoxBounds.class), f.getComponent(BoxBounds.class)))
                {
                    BoxBounds.resolveCollision(e.getComponent(BoxBounds.class), f.getComponent(BoxBounds.class));
                }
            }  
            levels[level].resolveCollisions(e.getComponent(BoxBounds.class)); 
        }
        for(int i = enemies.size()-1; i >= 0; i--)
        {
            if(!hitThisFrame && BoxBounds.checkCollision(enemies.get(i).getComponent(BoxBounds.class), player.getComponent(BoxBounds.class))) 
            {
                enemies.get(i).markForRemoval = true;
                player.lives--;
                player.transform.position.x = levels[level].startX * Constants.SCALE;
                player.transform.position.y = levels[level].startY * Constants.SCALE;
                hitThisFrame = true;
                tripleShot = 0;
                fasterShooting = 0;
            }
            for(int j = projectiles.size()-1; j >= 0; j--)
            {
                if(!projectiles.get(j).markForRemoval && !enemies.get(i).markForRemoval && BoxBounds.checkCollision(enemies.get(i).getComponent(BoxBounds.class), projectiles.get(j).getComponent(BoxBounds.class)))
                {
                    enemies.get(i).health--;
                    if(enemies.get(i).health <= 0) levels[level].numEnemies--;
                    projectiles.get(j).markForRemoval = true;
                }
            } 
        }
        for(int i = enemies.size()-1; i >= 0; i--)
        {
            
            if(enemies.get(i).markForRemoval) 
            {
                if(enemies.get(i).health <= 0) player.score += enemies.get(i).points;
                int r = (int)(Math.random() * 25);
                if(enemies.get(i).health <= 0 && r==1) powerups.add(new Powerup("", new Transform(new Vector2(enemies.get(i).transform.position.x/Constants.SCALE, enemies.get(i).transform.position.y/Constants.SCALE))));
                enemies.remove(i);
            }
            else if(enemies.get(i).transform.position.x > levels[level].maxX
                     || enemies.get(i).transform.position.y > levels[level].maxY
                     || enemies.get(i).transform.position.x < levels[level].minX
                     || enemies.get(i).transform.position.y < levels[level].minY)
            {
                enemies.remove(i);
            }
        }
        for(int i = projectiles.size()-1; i >= 0; i--)
        {
            if(projectiles.get(i).markForRemoval) projectiles.remove(i);
        }
        if(hitThisFrame) 
        { 
            enemies = new ArrayList<Enemy>();
            projectiles = new ArrayList<>();
            powerups = new ArrayList<>();
            levels[level].numEnemies += 5;
        }
        for(int i = powerups.size()-1; i >= 0; i--)
        {
            powerups.get(i).update(dt);
            if(BoxBounds.checkCollision(player.getComponent(BoxBounds.class), powerups.get(i).getComponent(BoxBounds.class)))
            {
                powerups.remove(i);
                int r = (int)(Math.random() * 20);
                if(r == 0)
                {
                    player.lives++;
                }
                else if(r > 0 && r < 10)
                {
                    fasterShooting = 7.5;
                }
                else if(r > 9 && r < 19)
                {
                    tripleShot = 7.5;
                }
                else 
                {
                    fasterShooting = 7.5;
                    tripleShot = 7.5;
                }
            }
            else if(powerups.get(i).lifeTime <= 0) powerups.remove(i);
        }
        if(tripleShot > 0) tripleShot = Math.max(tripleShot-dt, 0);
        if(fasterShooting > 0) fasterShooting = Math.max(0, fasterShooting-dt);
        if(player.lives <= 0)
        {
            saveScore();
            Window.getWindow().changeScene(2);
        } 
        time += dt;
        if(levels[level].numEnemies <= 0 && enemies.size() == 0) 
        {
            cleared = true;
            for(GameObject g : levels[level].exitpoints)
            {
                if(BoxBounds.checkCollision(g.getComponent(BoxBounds.class), player.getComponent(BoxBounds.class)))
                {
                    player.score += player.lives * 10 * (level + 1);
                    player.score += Math.max(0, (int) (60 - time) * 5);
                    if(level == levels.length-1)
                    {
                        Window.getWindow().changeScene(4);
                    }
                    else
                    {
                        time = 0;
                        level++;
                        player.transform.position.x = levels[level].startX * Constants.SCALE;
                        player.transform.position.y = levels[level].startY * Constants.SCALE;
                        enemies = new ArrayList<>();
                        projectiles = new ArrayList<>();
                        cleared = false;
                        exitSign = new GameObject("exit", new Transform(new Vector2(Constants.SCREEN_WIDTH/2 - 20, levels[level].minY-32)));
                        exitSign.transform.scale = new Vector2(2.0f, 2.0f);
                        exitSign.addComponent(AssetPool.getSprite("assets/exit.png"));
                    }
                }
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) 
    {
        g2.setColor(new Color(0.0f, 0.0f, 0.0f));
        g2.fillRect(0, 0, (int)(Constants.SCREEN_WIDTH * Constants.SCALE), (int)(Constants.SCREEN_HEIGHT * Constants.SCALE));
        background.draw(g2);
        levels[level].draw(g2);
        for(Powerup p : powerups)
        {
            p.draw(g2);
        }
        for(Projectile p : projectiles)
        {
            p.draw(g2);
        }
        for(GameObject g : gameObjects)
        {
            g.draw(g2);
        }
        for(Enemy e : enemies)
        {
            e.draw(g2);
        }
        if(cleared) exitSign.draw(g2);
    }  

    private void saveScore()
    {
        GameOverScene.lastScore = player.score;
        ArrayList<Integer> scores = new ArrayList<>();
        File file = new File("assets/scores.txt");
        try 
        {
            sc = new Scanner(file);
        } 
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        }
        for(int i = 0; i < 5; i++)
        {
            scores.add(sc.nextInt());
        }
        for(int i = 0; i < 5; i++)
        {
            if(player.score > scores.get(i)) 
            { 
                scores.add(i, player.score);
                i+=5;
            }

        }
        file.delete();
        try {
            file.createNewFile();
            FileWriter myWriter = new FileWriter("assets/scores.txt");
            for(int i = 0; i < 5; i++)
            {
                myWriter.write(scores.get(i) + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }   
    }
}