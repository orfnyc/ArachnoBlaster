package com.main;

import com.studio.*;

public class Main 
{
    public static void main(String[] args)
    {
        Window window = Window.getWindow();
        Thread mainThread = new Thread(window);
        mainThread.start();
    }
}
