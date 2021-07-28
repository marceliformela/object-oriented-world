package classes;

public class Sheep extends Animal
{
    public Sheep(World world, int posX, int posY)
    {
        super(world, "Sheep",0,  4, 4, posX, posY);
    }

    @Override
    public void addNew(World swiat, int poz_x, int poz_y) 
    {
        swiat.organisms.add(new Sheep(swiat, poz_x, poz_y));
    }
    
    @Override
    public void Paint()
    {
        world.board[positionY][positionX].setIcon(icon);
    }
}