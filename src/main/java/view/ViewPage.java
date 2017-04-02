import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ViewPage {

    // public static void main(String[] args) {

        // JTextField textField = new JTextField(8);
        // JFrame frame = new JFrame("ViewPage");
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.pack();
        // frame.setVisible(true);
        // //frame.add(new JLabel(new ImageIcon("Path/To/Your/Image.png")));
        //
        // // public ImagePanel() {
        // // try {
        // //     image = ImageIO.read(new File("/Users/leochen/Desktop/SoccerField"));
        // // } catch (IOException ex) {
        // //     System.out.println(ex.getMessage());
        // // }
        // BufferedImage myPicture = ImageIO.read(new File("/Users/leochen/Desktop/SoccerField"));
        // JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        // add(picLabel);



// public class TestImages {

   // *** your image path will be different *****
   private static final String IMG_PATH = "/User/lchen/Desktop/free-soccer-field-vector.png";

   public static void main(String[] args) {
      try {
         BufferedImage img = ImageIO.read(new File(IMG_PATH));
         ImageIcon icon = new ImageIcon(img);
         JLabel label = new JLabel(icon);
         JOptionPane.showMessageDialog(null, label);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
