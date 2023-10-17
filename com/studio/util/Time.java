package com.studio.util;

public class Time 
{
    public static double timeStarted = System.nanoTime();

    public static double getTime() {return (System.nanoTime() - timeStarted) * 1E-9;}

    public String toString()
    {
        return "" + getTime();
    }

    public boolean equals(Object other)
    {
        return false;
    }
}