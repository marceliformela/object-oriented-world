package classes;

import java.util.Random;

abstract class Animal extends Organism
{
    public Animal(World world, String type,int age,  int strength, int priority, int posX, int posY) 
    {
        super(world, type, age, strength, priority, posX, posY);
        
    }
    
    public void move(int direction)
    {
        switch(direction)
        {
            case KIERUNEK_LEWO:
                if(positionX - 1 >= 0)
                    positionX--;
                actualMove = KIERUNEK_LEWO;
                break;
            case KIERUNEK_GORA:
                if(positionY - 1 >= 0)
                    positionY--;
                actualMove = KIERUNEK_GORA;
                break;
            case KIERUNEK_PRAWO:
                if(positionX + 1 < 20)
                    positionX++;
                actualMove = KIERUNEK_PRAWO;
                break;
            case KIERUNEK_DOL:
                if(positionY + 1 < 20)
                    positionY++;
                actualMove = KIERUNEK_DOL;
                break;
        }
    }
    
    @Override
    public void backMove(int kierunek)
    {
        switch(kierunek)
        {
            case KIERUNEK_PRAWO:
                if(positionX - 1 >= 0)
                    positionX--;
                actualMove = KIERUNEK_LEWO;
                break;
            case KIERUNEK_DOL:
                if(positionY - 1 >= 0)
                    positionY--;
                actualMove = KIERUNEK_GORA;
                break;
            case KIERUNEK_LEWO:
                if(positionX + 1 < 20)
                    positionX++;
                actualMove = KIERUNEK_PRAWO;
                break;
            case KIERUNEK_GORA:
                if(positionY + 1 < 20)
                    positionY++;
                actualMove = KIERUNEK_DOL;
                break;
        }
    }
    
    @Override
    public void Collision(Organism second)
    {
        System.out.println("kolizja");
        if(this.type.equals(second.type))
        {
            backMove(actualMove);
            Random generator = new Random();
            int miejsce_narodzin = generator.nextInt(4);
           // int miejsce_narodzin = KIERUNEK_DOL;
            switch(miejsce_narodzin)
            {
                case KIERUNEK_LEWO:
                    if(positionX - 1 >= 0 && world.checkIfEmpty(positionX - 1, positionY) == -1)
                        addNew(world, positionX - 1, positionY);
                    break;
                case KIERUNEK_PRAWO:
                    if(positionX + 1 < 20 && world.checkIfEmpty(positionX + 1, positionY) == -1)
                        addNew(world, positionX + 1, positionY);
                    break;
                case KIERUNEK_GORA:
                    if(positionY - 1 >= 0 && world.checkIfEmpty(positionX, positionY - 1) == -1)
                        addNew(world, positionX, positionY - 1);
                    break;
                case KIERUNEK_DOL:
                    if(positionY + 1 < 20 && world.checkIfEmpty(positionX, positionY + 1) == -1)
                        addNew(world, positionX, positionY + 1);
                    break;
            }
        }
        else
        {
            if(this.strength > second.strength)
                second.isDead = true;
            else if(this.strength < second.strength)
                this.isDead = true;
            else
            {
                if(this.priority > second.priority)
                    second.isDead = true;
                else
                    this.isDead = true;
            }
        }
    }
    
    @Override
    public void Action(int direction)
    {
        move(direction);
    }
    
    @Override
    public void special()
    {
        
    }
    
}