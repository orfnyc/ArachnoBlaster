package com.studio;

import java.util.ArrayList;
import java.util.List;

import com.studio.stuff.*;

import java.awt.Graphics2D;

public class GameObject {
    public List<Component> components;
    public Transform transform;
    public String name;

    public Transform transform() {return transform;}
    public void transform(Transform transform) {this.transform = transform;}

    public GameObject(String name, Transform transform)
    {
        this.name = name;
        this.transform = transform;
        this.components = new ArrayList<>();
    }

    public void update(double dt)
    {
        for (Component c : components)
        {
            c.update(dt);
        }
    }

    public <T extends Component> T getComponent(Class<T> componentClass)
    {
        for (Component<T> c : components)
        {
            if(componentClass.isAssignableFrom(c.getClass()))
            {
                try 
                {
                    return componentClass.cast(c);
                }
                catch (ClassCastException e)
                {
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
        }
        return null;
    }

    public <T extends Component> T getComponent(String name, Class<T> componentClass)
    {
        for(Component<T> c : components)
        {
            if(c.name.equals(name) && componentClass.isAssignableFrom(c.getClass()))
            {
                try
                {
                    return componentClass.cast(c);
                }
                catch (ClassCastException e)
                {
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
        }
        return null;
    }

    public void addComponent(Component c)
    {
        components.add(c);
        c.setGameObject(this);
    }

    public <T extends Component> void removeComponent(Class<T> componentClass)
    {
        for (Component c : components)
        {
            if(componentClass.isAssignableFrom(c.getClass()))
            {
                components.remove(c);
                return;
            }
        }
    }

    public void draw(Graphics2D g2)
    {
        for (Component c : components)
        {
            c.draw(g2);
        }
    }

    public GameObject copy()
    {
        GameObject newGameObject = new GameObject("generated", transform.copy());
        for (Component c : components)
        {
            Component copy = c.copy();
            if (copy != null)
            {
                newGameObject.addComponent((copy));
            }
        }
        return newGameObject;
    }

    public List<Component> getAllComponents()
    {
        return components;
    }

    public String toString() 
    {
        String r = name + "\n" + "{";
        for(int i = 0; i < components.size() - 1; i++)
        {
            r += components.get(i).toString() + ", ";
        }
        r += components.get(components.size()-1).toString() + "}";
        return r;
    }
    
    public boolean equals(Object other)
    {
        return false;
    }
}
