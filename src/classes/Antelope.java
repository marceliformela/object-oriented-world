package classes;

import java.util.Random;

public class Antelope extends Animal
{
    public Antelope(World world, int posX, int posY)
    {
        super(world, "Antelope",0,  4, 4, posX, posY);
    }

    @Override
    public void addNew(World swiat, int poz_x, int poz_y) 
    {
        swiat.organisms.add(new Antelope(swiat, poz_x, poz_y));
    }
    
    @Override
    public void Paint()
    {
        world.board[positionY][positionX].setIcon(icon);
    }
    
    @Override
    public void move(int direction)
    {
        switch(direction)
        {
            case KIERUNEK_LEWO:
                if(positionX - 2 >= 0)
                    positionX -= 2;
                actualMove = KIERUNEK_LEWO;
                break;
            case KIERUNEK_GORA:
                if(positionY - 2 >= 0)
                    positionY -= 2;
                actualMove = KIERUNEK_GORA;
                break;
            case KIERUNEK_PRAWO:
                if(positionX + 2 < 20)
                    positionX += 2;
                actualMove = KIERUNEK_PRAWO;
                break;
            case KIERUNEK_DOL:
                if(positionY + 2 < 20)
                    positionY += 2;
                actualMove = KIERUNEK_DOL;
                break;
        }
    }
    
    @Override
    public void backMove(int direction)
    {
        switch(direction)
        {
            case KIERUNEK_PRAWO:
                if(positionX - 2 >= 0)
                    positionX -= 2;
                actualMove = KIERUNEK_LEWO;
                break;
            case KIERUNEK_DOL:
                if(positionY - 2 >= 0)
                    positionY -= 2;
                actualMove = KIERUNEK_GORA;
                break;
            case KIERUNEK_LEWO:
                if(positionX + 2 < 20)
                    positionX += 2;
                actualMove = KIERUNEK_PRAWO;
                break;
            case KIERUNEK_GORA:
                if(positionY + 2 < 20)
                    positionY += 2;
                actualMove = KIERUNEK_DOL;
                break;
        }
    }
    
   
    
    @Override
    public void Collision(Organism second)
    {
        Random generator = new Random();
        int szansa = generator.nextInt(101);
        
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
        else if(szansa <= 50)
        {
            int direction_run = generator.nextInt(4);
            move(direction_run);
        }
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