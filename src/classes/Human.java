package classes;

import java.util.Random;

public class Human extends Animal {

    private boolean specialSkillActivated;
    private int roundOfActivation;

    public Human(World world, int posX, int posY) {
        super(world, "Human", 0, 5, 4, posX, posX);
        specialSkillActivated = false;
    }

    @Override
    public void addNew(World swiat, int poz_x, int poz_y) {
        throw new UnsupportedOperationException("Czlowiek nie moze miec dzieci");
    }

    @Override
    public void Paint() {
        world.board[positionY][positionX].setIcon(icon);
    }

    @Override
    public void Collision(Organism second) {
        if (specialSkillActivated == false) {
            if (this.strength > second.strength) {
                second.isDead = true;
            } else if (this.strength < second.strength) {
                world.finishGame();
                this.isDead = true;
            } else if (this.priority > second.priority) {
                second.isDead = true;
            } else {
                this.isDead = true;
            }
        } else if (second.type.equals("Wolf")
                || second.type.equals("Sheep")
                || second.type.equals("Turtle")
                || second.type.equals("Fox")
                || second.type.equals("Antelope")) {
            Random generator = new Random();
            int run = generator.nextInt(4);
            second.Action(run);
        }
    }

    @Override
    public void Action(int direction) {
        move(direction);

        if (specialSkillActivated == true && world.getRound() - roundOfActivation == 5) {
            world.addEvent("***TARCZA ALZURA DEZAKTYWOWANA***");
            specialSkillActivated = false;
        }
    }

    @Override
    public void special() {
        if (world.getRound() - roundOfActivation >= 10 || world.getRound() == age) {
            world.addEvent("***TARCZA ALZURA AKTYWOWANA***");
            specialSkillActivated = true;
            roundOfActivation = world.getRound();
        }
    }
}
