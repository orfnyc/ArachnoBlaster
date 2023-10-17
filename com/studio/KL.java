package com.studio;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KL extends KeyAdapter
{
    private boolean[] keyPressed = new boolean[128];

    @Override
    public void keyPressed(KeyEvent e)
    {
        keyPressed[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        keyPressed[e.getKeyCode()] = false;
    }

    public boolean isKeyPressed(int keyCode)
    {
        return keyPressed[keyCode];
    }

    public String toString()
    {
        return "key listener";
    }

    public boolean equals(Object other)
    {
        return false;
    }
}

