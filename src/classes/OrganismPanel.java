package classes;

import javax.swing.*;
import java.awt.*;

import java.awt.image.BufferedImage;

public class OrganismPanel extends JPanel
{
    static final int size  = 32;
    static final int space = 0;
    private BufferedImage icon = null;
    
    public OrganismPanel(int positionX, int positionY)
    {
        setBackground(Color.gray);
        setBounds((size * positionX) + space, (size * positionY) + space, size, size);
    }
    
    public void setIcon(BufferedImage icon)
    {
        this.icon = icon;
    }
    
    public BufferedImage getIcon()
    {
        return icon;
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(icon, 0, 0, null);
    }

}