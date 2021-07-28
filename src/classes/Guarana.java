package classes;

public class Guarana extends Plant
{
    public Guarana(World world, int poz_x, int poz_y)
    {
        super(world, "Guarana",0,  0, 0, poz_x, poz_y);
    }
    
    @Override
    public void addNew(World swiat, int poz_x, int poz_y)
    {
        swiat.organisms.add(new Guarana(swiat, poz_x, poz_y));
    }
    
    @Override
    public void Paint()
    {
        world.board[positionY][positionX].setIcon(icon);
    }
    
    /**
     *
     * @param second
     */
    @Override
    public void Collision(Organism second)
    {
        second.strength += 3;
        this.isDead = true;
    }

   
}