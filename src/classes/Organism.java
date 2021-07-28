package classes;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

abstract class Organism implements Comparable<Organism>
{   
   static final int KIERUNEK_LEWO = 0;
    static final int KIERUNEK_GORA = 1;
    static final int KIERUNEK_PRAWO = 2;
    static final int KIERUNEK_DOL = 3;

    protected World world;
    protected String type;
  
    protected BufferedImage icon;
    protected int strength;
    protected int priority;
    protected int age;
    protected int positionX;
    protected int positionY;
    protected boolean isDead;
    protected int actualMove;
    public Organism(World world, String type, int age, int strength, int priority, int posX, int posY)
    {
        this.world = world;
        this.type = type;
        this.strength = strength;
        this.priority = priority;
        this.positionX = posX;
        this.positionY = posY;
        this.age = age;
        this.isDead = false;
        
        //ŁADOWANIE OBRAZKA
        String fileName = "src/icons/" + type + ".png";
        
        try
        {
            icon = ImageIO.read(new File(fileName));
        }catch(IOException e)
        {
            System.err.println(fileName + " nie istnieje.");
        }
        System.out.println(positionX + " " + positionY);
        world.addEvent("Nowy " + type + " na (" + positionX + "," + positionY + ").");
    }
    
    abstract void Collision(Organism attacked);
    
    abstract void Action(int direction);
    
    abstract void addNew(World world, int posX, int posY);
    
    abstract void backMove(int direction);
    
    abstract void Paint();
    
    abstract void special();
    
    @Override
    public int compareTo(Organism second)
    {
        if(this.getPriority() == second.getPriority())
            if(this.getAge() < second.getAge())
                return 1;
        else
            if(this.getPriority() > second.getPriority())
                return 1;
        
        return 0;
    }
  
    public int getPositionX() 
    {
        return positionX;
    }

    public int getPositionY() 
    {
        return positionY;
    }
    
    public int getAge()
    {
        return age;
    }
    
    public int getStrength()
    {
        return strength;
    }
    
    public int getPriority()
    {
        return priority;
    }
    
    public String getType()
    {
        return type;
    }

}
