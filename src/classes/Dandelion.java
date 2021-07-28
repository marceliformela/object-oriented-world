package classes;

import java.util.Random;

public class Dandelion extends Plant {

    public Dandelion(World world, int poz_x, int poz_y) {
        super(world, "Dandelion", 0, 0, 0, poz_x, poz_y);
    }

    @Override
    public void Action(int direction) {
        Random generator = new Random();
        int newPlace = 0;
        for (int i = 0; i < 3; i++) {
            newPlace = generator.nextInt(4);
            plantNew(newPlace);
        }
    }

    @Override

    void addNew(World world, int posX, int posY) {
        world.organisms.add(new Dandelion(world, posX, posY));
    }

    @Override
    void Paint() {
        world.board[positionY][positionX].setIcon(icon);
    }
}
