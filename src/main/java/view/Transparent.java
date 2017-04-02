import java.io.File;
import java.io.IOException;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JLayer;
import javax.swing.plaf.LayerUI;
import java.awt.*;
import javax.swing.*;

class Transparent extends JFrame

{
JPanel p1,p2;

    public Transparent()
    {
        createAndShowGUI();
    }

    private void createAndShowGUI()
    {
        // Set title and default close operation
        setTitle("Transparent Panel");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        try {
            // Set a background for JFrame
            // setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("../../../folderImage/soccerField.png")))));
            this.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("../../../folderImage/soccerField.png")))));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // Set some layout, say FlowLayout
        setLayout(new FlowLayout());

        // Create a JPanel
        p1=new JPanel();

        // Set the background, black with 125 as alpha value
        // This is less transparent
        p1.setBackground(new Color(0,0,0,125));

        // Create another JPanel
        p2=new JPanel();

        // This is more transparent than the previous
        // one
        p2.setBackground(new Color(0,0,0,65));

        // Set some size to the panels
        p1.setPreferredSize(new Dimension(250,150));
        p2.setPreferredSize(new Dimension(250,150));

        // Add the panels to the JFrame
        add(p1);
        add(p2);

        // Set the size of the JFrame and
        // make it visible
        setSize(600,400);
        setVisible(true);
    }


    public static void main(String args[])
    {
        // Run in the EDT
        SwingUtilities.invokeLater(new Runnable(){
            public void run()
            {
                new TransparentPanel();
            }
        });
    }
}
