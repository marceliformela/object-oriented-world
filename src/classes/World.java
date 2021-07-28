package classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.Scanner;
import javax.swing.JTextArea;

public class World {
//------------------------------------------------------------------------------
//                                STAŁE KLASY
//------------------------------------------------------------------------------

    static final int mapSize = 20;
    int mapSizeX;
    int mapSizeY;

//------------------------------------------------------------------------------
//                                POLA KLASY
//------------------------------------------------------------------------------
    private int round;
    private boolean endGame;
    public ArrayList<Organism> organisms;
    private Random generator;
    OrganismPanel[][] board;
    JTextArea events;
//------------------------------------------------------------------------------
//                            KONSTRUKTOR KLASY
//------------------------------------------------------------------------------

    public World(OrganismPanel[][] plansza, JTextArea wydarzenia) {
        this.organisms = new ArrayList<>();
        this.board = plansza;
        this.events = wydarzenia;
        generator = new Random();
        round = 0;
        endGame = false;

       // createOrganisms(2, "Wolf");
        //createOrganisms(20, "Guarana");
        //createOrganisms(2, "Sheep");
       // createOrganisms(2, "DeadlyNightshade");
       // createOrganisms(1, "Human");
       // createOrganisms(10, "Grass");

    }

//------------------------------------------------------------------------------
//                               METODY KLASY
//------------------------------------------------------------------------------
    private void createOrganisms(int quantity, String name) {
        int x;
        int y;
        for (int i = 0; i < quantity; i++) {
            do {
                x = generator.nextInt(mapSize);
                y = generator.nextInt(mapSize);
            } while (checkIfEmpty(x, y) != -1);

            switch (name) {
                case "Sheep":
                    organisms.add(new Sheep(this, x, y));
                    break;
                case "Wolf":
                    organisms.add(new Wolf(this, x, y));
                    break;
                case "Fox":
                    organisms.add(new Fox(this, x, y));
                    break;
                case "Turtle":
                    organisms.add(new Turtle(this, x, y));
                    break;
                case "Antelope":
                    organisms.add(new Antelope(this, x, y));
                    break;
                case "Grass":
                    System.out.println("trawa");
                    organisms.add(new Grass(this, x, y));
                    break;
                case "Guarana":
                    organisms.add(new Guarana(this, x, y));
                    break;
                case "Dandelion":
                    organisms.add(new Dandelion(this, x, y));
                    break;
                case "DeadlyNightshade":
                    organisms.add(new DeadlyNightshade(this, x, y));
                    break;
                case "Human":
                    organisms.add(new Human(this, x, y));
                    break;
            }
        }
    }

    public void CreateOrganism(String type, int x, int y) {

        if (checkIfEmpty(x, y) == -1) {
            System.out.println("Dodaje obiekt typu: " + type + " na " + x + " " + y);
            switch (type) {
                case "Sheep":
                    organisms.add(new Sheep(this, x, y));
                    break;
                case "Wolf":
                    organisms.add(new Wolf(this, x, y));
                    break;
                case "Fox":
                    organisms.add(new Fox(this, x, y));
                    break;
                case "Turtle":
                    organisms.add(new Turtle(this, x, y));
                    break;
                case "Antelope":
                   
                    organisms.add(new Antelope(this, x, y));
                    break;
                case "Grass":
                  
                    organisms.add(new Grass(this, x, y));
                    break;
                case "Guarana":
                   
                    organisms.add(new Guarana(this, x, y));
                    break;
                case "Dandelion":
                    organisms.add(new Dandelion(this, x, y));
                    break;
                case "DeadlyNightshade":
                    organisms.add(new DeadlyNightshade(this, x, y));
                    break;
                case "Human":
                    organisms.add(new Human(this, x, y));
                    break;
            }
        }
        DrawWorld();
    }

    public int checkIfEmpty(int poz_x, int poz_y) {
        for (int i = 0; i < organisms.size(); i++) {
            if (organisms.get(i).getPositionX() == poz_x && organisms.get(i).getPositionY() == poz_y) {
                return i;
            }
        }

        return -1;
    }

    public void DrawWorld() {
        for (Organism organizm : organisms) {
            organizm.Paint();
        }
    }

    public void actualizeOrganisms() {
        for (int i = 0; i < organisms.size(); i++) {
            if (organisms.get(i).isDead == true) {
                addEvent("Smierc " + organisms.get(i).getType() + " na (" + organisms.get(i).getPositionX() + "," + organisms.get(i).getPositionY() + ").");
                organisms.remove(i);
            }
        }

        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                board[y][x].setIcon(null);
            }
        } 
    }

    public void makeAMove() {
           for(int i = 0; i < organisms.size();i++){
            organisms.get(i).age++;
        
    }
        Collections.sort(organisms);
     
        events.setText("");
        int ifCollisionorg;
        int direction;

        for (int i = 0; i < organisms.size(); i++) {
            if (round - organisms.get(i).getAge() > 0 || round == 0) {
                direction = generator.nextInt(4);
                
                if (!"Human".equals(organisms.get(i).getType())) {
                    organisms.get(i).Action(direction);
                }

                ifCollisionorg = checkIfEmpty(organisms.get(i).getPositionX(), organisms.get(i).getPositionY());
                if (ifCollisionorg != -1 && ifCollisionorg != i) {
                    organisms.get(i).Collision(organisms.get(ifCollisionorg));
                }
            }
        }
        actualizeOrganisms();

        round++;
    }

    public void restartGame() {
        organisms.clear();
        events.setText("ZRESTARTOWANO GRE\n");
        round = 0;
        endGame = false;
    }

    public void addEvent(String text) {
        events.append(text + "\n");
    }

    public void readMap(File plik) throws FileNotFoundException {
        restartGame();
        actualizeOrganisms();
        DrawWorld();
        char[][] map = new char[mapSize][mapSize];

        Scanner skaner = new Scanner(plik);

        for (int y = 0; skaner.hasNextLine() && y < mapSize; y++) {
            map[y] = skaner.nextLine().toCharArray();
        }

        for (int y = 0; y < mapSize; y++) {
            for (int x = 0; x < mapSize; x++) {
                switch (map[y][x]) {
                    case 'C':
                        organisms.add(new Human(this, x, y));
                        break;
                    case 'W':
                        organisms.add(new Wolf(this, x, y));
                        break;
                    case 'O':
                        organisms.add(new Sheep(this, x, y));
                        break;
                    case 'A':
                        organisms.add(new Antelope(this, x, y));
                        break;
                    case 'L':
                        organisms.add(new Fox(this, x, y));
                        break;
                    case 'Z':
                        organisms.add(new Turtle(this, x, y));
                        break;
                    case 'T':
                        organisms.add(new Grass(this, x, y));
                        break;
                    case 'M':
                        organisms.add(new Dandelion(this, x, y));
                        break;
                    case 'G':
                        organisms.add(new Guarana(this, x, y));
                        break;
                    case 'J':
                        organisms.add(new DeadlyNightshade(this, x, y));
                        break;
                }

            }
        }
        DrawWorld();
    }

    public void saveMap(File plik) throws FileNotFoundException {
        char[][] map = new char[mapSize][mapSize];
        PrintWriter writer = new PrintWriter(plik);

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                map[i][j] = '-';
            }
        }

        for (Organism organism : organisms) {
            switch (organism.getType()) {
                case "Human":
                    map[organism.getPositionY()][organism.getPositionX()] = 'C';
                    break;
                case "Wolf":
                    map[organism.getPositionY()][organism.getPositionX()] = 'W';
                    break;
                case "Sheep":
                    map[organism.getPositionY()][organism.getPositionX()] = 'O';
                    break;
                case "Antelope":
                    map[organism.getPositionY()][organism.getPositionX()] = 'A';
                    break;
                case "Fox":
                    map[organism.getPositionY()][organism.getPositionX()] = 'L';
                    break;
                case "Turtle":
                    map[organism.getPositionY()][organism.getPositionX()] = 'Z';
                    break;
                case "Grass":
                    map[organism.getPositionY()][organism.getPositionX()] = 'T';
                    break;
                case "Dandelion":
                    map[organism.getPositionY()][organism.getPositionX()] = 'M';
                    break;
                case "Guarana":
                    map[organism.getPositionY()][organism.getPositionX()] = 'G';
                    break;
                case "DeadlyNightshade":
                    map[organism.getPositionY()][organism.getPositionX()] = 'J';
                    break;

            }
        }

        for (int y = 0; y < mapSize; y++) {
            for (int x = 0; x < mapSize; x++) {
                writer.write(map[y][x]);
            }
            writer.println();

        }

        writer.flush();
        writer.close();
    }

//------------------------------------------------------------------------------
//                            GETTERY I SETTERY
//------------------------------------------------------------------------------
    public int getRound() {
        return round;
    }

    public void finishGame() {
        endGame = true;
    }

    public int findHuman() {
        for (int i = 0; i < organisms.size(); i++) {
            if (organisms.get(i).getType().equals("Human")) {
                return i;
            }
        }

        return -1;
    }

    public void incrementRound() {
        round++;
    }
}
