package classes;

public class Fox extends Animal {

    public Fox(World world, int posX, int posY) {
        super(world, "Fox", 0, 3, 7, posX, posX);
    }

    @Override
    public void addNew(World swiat, int poz_x, int poz_y) {
        swiat.organisms.add(new Fox(swiat, poz_x, poz_y));
    }

    @Override
    public void Paint() {
        world.board[positionY][positionX].setIcon(icon);
    }

    @Override
    public void Action(int direction) {
        switch (direction) {
            case KIERUNEK_GORA:
                if (positionY - 1 >= 0 && (world.checkIfEmpty(positionX, positionY - 1) == -1 || world.organisms.get(world.checkIfEmpty(positionX, positionY - 1)).strength <= this.strength)) {
                    move(KIERUNEK_GORA);
                }
                break;
            case KIERUNEK_DOL:
                if (positionY + 1 < 20 && (world.checkIfEmpty(positionX, positionY + 1) == -1 || world.organisms.get(world.checkIfEmpty(positionX, positionY + 1)).strength <= this.strength)) {
                    move(KIERUNEK_DOL);
                }
                break;
            case KIERUNEK_PRAWO:
                if (positionX + 1 < 20 && (world.checkIfEmpty(positionX + 1, positionY) == -1 || world.organisms.get(world.checkIfEmpty(positionX + 1, positionY)).strength <= this.strength)) {
                    move(KIERUNEK_PRAWO);
                }
                break;
            case KIERUNEK_LEWO:
                if (positionX - 1 >= 0 && (world.checkIfEmpty(positionX - 1, positionY) == -1 || world.organisms.get(world.checkIfEmpty(positionX - 1, positionY)).strength <= this.strength)) {
                    move(KIERUNEK_LEWO);
                }
                break;
        }
    }
}
