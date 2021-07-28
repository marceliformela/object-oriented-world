package classes;

public class Grass extends Plant {

    public Grass(World world, int poz_x, int poz_y) {
        super(world, "Grass", 0, 0, 0, poz_x, poz_y);
    }

    @Override
    void addNew(World world, int posX, int posY) {
        world.organisms.add(new Grass(world, posX, posY));
    }

    @Override
    void Paint() {
        world.board[positionY][positionX].setIcon(icon);
    }
}
