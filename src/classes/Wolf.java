package classes;

public class Wolf extends Animal
{
    public Wolf(World world, int posX, int posY)
    {
        super(world, "Wolf",0,  9, 5, posX, posY);
    }

    @Override
    public void addNew(World swiat, int poz_x, int poz_y) 
    {
        swiat.organisms.add(new Wolf(swiat, poz_x, poz_y));
    }
    
    @Override
    public void Paint()
    {
        world.board[positionY][positionX].setIcon(icon);
    }
}