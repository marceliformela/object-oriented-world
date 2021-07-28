package classes;

import java.util.Random;

public class Turtle extends Animal
{
    private Random generator;
    
    public Turtle(World world, int posX, int posY)
    {
        super(world, "Turtle",0, 2, 1, posX, posY);
    }

    @Override
    public void addNew(World swiat, int poz_x, int poz_y) 
    {
        swiat.organisms.add(new Turtle(swiat, poz_x, poz_y));
    }
    
    @Override
    public void Paint()
    {
        world.board[positionY][positionX].setIcon(icon);
    }
    
    @Override
    public void Action(int direction)
    {
        generator = new Random();
        int chance = generator.nextInt(101);
        
        if(chance <= 25)
            move(direction);
    }
    
    @Override
    public void Collision(Organism second)
    {
        if(this.type.equals(second.type))
        {
            backMove(actualMove);
            int miejsce_narodzin = generator.nextInt(4);
            
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
        else if(second.strength < 5)
            second.backMove(second.actualMove);
        else
        {
            if(this.strength > second.strength)
                second.isDead = true;
            else if(this.strength < second.strength)
                this.isDead = true;
            else if(this.strength == second.strength)
            {
                if(this.priority > second.priority)
                    second.isDead = true;
                else
                    this.isDead = true;
            }
        }
    }

}