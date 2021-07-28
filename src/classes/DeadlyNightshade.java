package classes;

public class DeadlyNightshade extends Plant
{
    public DeadlyNightshade(World swiat, int poz_x, int poz_y)
    {
        super(swiat, "DeadlyNightshade",0, 99, 0, poz_x, poz_y);
    }
    
    @Override
    public void addNew(World swiat, int poz_x, int poz_y)
    {
        swiat.organisms.add(new DeadlyNightshade(swiat, poz_x, poz_y));
    }
    
    @Override
    public void Paint()
    {
        world.board[positionY][positionX].setIcon(icon);
    }
    
    @Override
    public void Collision(Organism second)
    {
        this.isDead = true;
        second.isDead = true;
    }
    
}