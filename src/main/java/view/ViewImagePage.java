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


public class ViewImagePage {

    public static void main(String[] args) {
        //rep windows on screen
        //jpanel class is for more complex interfaces
        JFrame mainFrame = new JFrame("Game Maker");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        JButton butt = null;

        mainFrame.setContentPane(mainPanel);
        mainPanel.setLayout(null);
        JLabel lab = null;

        try {
            //mainFrame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("../../../folderImage/soccerField.png")))));
            lab = new JLabel(new ImageIcon(ImageIO.read(new File("../../../folderImage/soccerField.png"))));

        } catch (IOException e) {
            e.printStackTrace();
        }
        lab.setBounds(0,0,1000,600);
        butt = new JButton("asdlfkj");
        butt.setBounds(500,500,50,50);
        mainPanel.add(butt);
        mainPanel.add(lab);
        mainPanel.setComponentZOrder(lab, mainPanel.countComponents() - 1);
        mainFrame.pack();
        mainFrame.setVisible(true);

    }
}
