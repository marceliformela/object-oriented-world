package classes;

import java.util.Random;

abstract class Plant extends Organism
{
    public Plant(World world, String type,int age,  int strength, int priority, int posX, int posY)
    {
        super(world, type, age, strength, priority, posX, posY);
    }
    
    public void plantNew(int direction)
    {
        Random generator = new Random();
        int prob = 3;
        int chance = generator.nextInt(101);
        
        if(prob >= chance)
        { 
            switch(direction)
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
    }
    
    @Override
    public void Action(int direction)
    {
        plantNew(direction);
    }
    
    @Override
    public void Collision(Organism second)
    {
        this.isDead = true;
    }
    
    @Override
    public void backMove(int direction)
    {
        
    }
    
    @Override
    public void special()
    {
        
    }
}