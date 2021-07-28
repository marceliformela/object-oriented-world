package classes;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import javax.swing.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Game extends JFrame implements KeyListener, MouseListener {
//------------------------------------------------------------------------------
//                                    STAŁE
//------------------------------------------------------------------------------

    static final int KIERUNEK_LEWO = 0;
    static final int KIERUNEK_GORA = 1;
    static final int KIERUNEK_PRAWO = 2;
    static final int KIERUNEK_DOL = 3;
//------------------------------------------------------------------------------
//                                POLA KLASY
//------------------------------------------------------------------------------

    OrganismPanel[][] orgPanels;
    World world;
    JLabel roundLabel;
    JLabel infoLabel;
    JFileChooser saveMap;
    JFileChooser readMap;
    JTextArea events;
    int round;

    public Game() {
        round = 0;
        init();
        world = new World(this.orgPanels, this.events);
        world.DrawWorld();
        addKeyListener(this);
        addMouseListener(this);

    }

    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Virutal World v2.0");
        setResizable(false);
        setPreferredSize(new Dimension(1080, 720));
        setLayout(null);

        orgPanels = new OrganismPanel[20][20];

        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 20; y++) {
                orgPanels[x][y] = new OrganismPanel(x, y);
                add(orgPanels[x][y]);
            }
        }
        // obslugaMyszy();

        roundLabel = new JLabel("TURA: " + round);
        roundLabel.setBounds(580, 660, 100, 25);
        add(roundLabel);

        drawInterface();

        pack();
        setVisible(true);
    }

    private void drawInterface() {
        JButton newRoundButton = new JButton("Nowa Tura");
        newRoundButton.setBounds(10, 660, 100, 25);

        newRoundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                world.makeAMove();
                revalidate();
                repaint();
                world.DrawWorld();
                round++;
                roundLabel.setText("TURA: " + round);
            }
        });

        newRoundButton.setFocusable(false);
        add(newRoundButton);

        JButton readMapButton = new JButton("Odczyt mapy...");
        readMapButton.setBounds(670, 660, 125, 25);

        readMapButton.addActionListener(new ActionListener() {
            private Component temp;

            @Override
            public void actionPerformed(ActionEvent e) {
                readMap = new JFileChooser("src/");
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Pliki txt", "txt");
                readMap.setFileFilter(filter);
                int returnValue = readMap.showOpenDialog(this.temp);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File mapFromFile = readMap.getSelectedFile();
                    try {
                        world.readMap(mapFromFile);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("Wczytano mape z pliku: " + mapFromFile.getName());
                    round = world.getRound();
                    roundLabel.setText("TURA: " + round);
                    revalidate();
                    repaint();
                }
            }
        });

        readMapButton.setFocusable(false);
        add(readMapButton);

        JButton saveMapButton = new JButton("Zapis mapy...");
        saveMapButton.setBounds(930, 660, 125, 25);

        saveMapButton.addActionListener(new ActionListener() {
            private Component temp;

            @Override
            public void actionPerformed(ActionEvent e) {
                saveMap = new JFileChooser("src/");
                saveMap.setSelectedFile(new File("mojaMapa.txt"));

                int returnValue = saveMap.showSaveDialog(temp);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File mapSavedToFile = saveMap.getSelectedFile();
                    try {
                        world.saveMap(mapSavedToFile);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("Zapisano mape do pliku: " + mapSavedToFile.getName());
                }
            }
        });

        saveMapButton.setFocusable(false);
        add(saveMapButton);

        events = new JTextArea();
        events.setBounds(660, 405, 400, 240);
        events.setFocusable(false);
        add(events);
        revalidate();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent event) {

        Random generator = new Random();
        int checkIfEmptyOrIndex;
        int direction;
        if (event.getKeyCode() != KeyEvent.VK_LEFT
                && event.getKeyCode() != KeyEvent.VK_RIGHT
                && event.getKeyCode() != KeyEvent.VK_UP
                && event.getKeyCode() != KeyEvent.VK_DOWN
                && event.getKeyCode() != KeyEvent.VK_SPACE) {
            return;
        }
        events.setText("");
        for (int i = 0; i < world.organisms.size(); i++) {
            if (world.getRound() - world.organisms.get(i).getAge() > 0 || world.getRound() == 0) {
                if ("Human".equals(world.organisms.get(i).getType())) {
                    if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                        world.organisms.get(i).Action(KIERUNEK_GORA);
                    } else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                        world.organisms.get(i).Action(KIERUNEK_DOL);
                    } else if (event.getKeyCode() == KeyEvent.VK_UP) {
                        world.organisms.get(i).Action(KIERUNEK_LEWO);
                    } else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
                        world.organisms.get(i).Action(KIERUNEK_PRAWO);
                    } else if (event.getKeyCode() == KeyEvent.VK_SPACE) {
                        world.organisms.get(i).special();
                    }
                } else {
                    direction = generator.nextInt(4);
                    world.organisms.get(i).Action(direction);
                }

                checkIfEmptyOrIndex = world.checkIfEmpty(world.organisms.get(i).getPositionX(), world.organisms.get(i).getPositionY());

                if (checkIfEmptyOrIndex != -1 && checkIfEmptyOrIndex != i) {
                    world.organisms.get(i).Collision(world.organisms.get(checkIfEmptyOrIndex));
                }
            }
        }

        world.actualizeOrganisms();
        world.incrementRound();
        world.DrawWorld();
        round = world.getRound();
        roundLabel.setText("TURA: " + round);
        revalidate();
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent event) {

    }

    @Override
    public void keyTyped(KeyEvent event) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent me) {

    }

    @Override
    public void mouseReleased(MouseEvent me) {

        int y = (me.getX() / 32);
        int x = (me.getY() / 32) - 1;
        System.out.println("pressed" + x + " " + y);
        if (x < 0 || y < 0 || x > 19 || y > 19) {
            return;
        }
        if (me.getButton() == MouseEvent.BUTTON3) // Block for triggering popup
        {
            JPopupMenu menu = new JPopupMenu();
            //plants
            JMenuItem GrassItem = new JMenuItem("Grass");
            GrassItem.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {

                    world.CreateOrganism("Grass", x, y);

                    world.DrawWorld();

                }

            });

            JMenuItem GuaranaItem = new JMenuItem("Guarana");
            GuaranaItem.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    // System.out.println("nowa guarana");
                    world.CreateOrganism("Guarana", x, y);
                    world.DrawWorld();

                }

            });

            JMenuItem DandelionItem = new JMenuItem("Dandelion");
            DandelionItem.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {

                    world.CreateOrganism("Dandelion", x, y);
                    world.DrawWorld();

                }

            });
        
            JMenuItem DeadlyNightshadeItem = new JMenuItem("Deadly Nightshade");
            DeadlyNightshadeItem.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {

                    world.CreateOrganism("DeadlyNightshade", x, y);
                    world.DrawWorld();

                }

            });
            //animal
            JMenuItem SheepItem = new JMenuItem("Sheep");
            SheepItem.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {

                    world.CreateOrganism("Sheep", x, y);

                    world.DrawWorld();

                }

            });
            JMenuItem TurtleItem = new JMenuItem("Turtle");
            TurtleItem.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {

                    world.CreateOrganism("Turtle", x, y);

                    world.DrawWorld();

                }

            });
            JMenuItem WolfItem = new JMenuItem("Wolf");
            WolfItem.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {

                    world.CreateOrganism("Wolf", x, y);

                    world.DrawWorld();

                }

            });

            JMenuItem FoxItem = new JMenuItem("Fox");
            FoxItem.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {

                    world.CreateOrganism("Fox", x, y);
                    world.DrawWorld();

                }

            });

            JMenuItem AntelopeItem = new JMenuItem("Antelope");
            AntelopeItem.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {

                    world.CreateOrganism("Antelope", x, y);
                    world.DrawWorld();

                }

            });
            
            
            menu.add(GrassItem);
            menu.add(GuaranaItem);
            menu.add(DandelionItem);
            menu.add(DeadlyNightshadeItem);
            menu.add(FoxItem);
            menu.add(TurtleItem);
            menu.add(AntelopeItem);
            menu.add(WolfItem);
            menu.add(SheepItem);

            menu.show(orgPanels[y][x], y, x);

        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Game newGame = new Game();

            }
        });
    }

}
