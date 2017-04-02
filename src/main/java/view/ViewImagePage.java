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


public class ViewImagePage {

    public static void main(String[] args) {
        JLabel player;

        //rep windows on screen
        //jpanel class is for more complex interfaces
        JFrame mainFrame = new JFrame("Game Maker");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            mainFrame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("../../../folderImage/soccerField.png")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainFrame.pack();
        mainFrame.setVisible(true);

    }

}
