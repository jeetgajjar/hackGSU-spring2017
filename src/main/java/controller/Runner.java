// package controller;
import java.io.IOException;
import java.lang.Math;
import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField; 
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import java.awt.geom.Area;
import java.awt.Component;
import javax.imageio.ImageIO;
import java.io.File;

public class Runner {
    public static void main(String[] args) throws Exception{
        // Create a sample listener and controller

        JFrame frame = new JFrame("F R A M E");
        // try {
        //     frame.setContentPane(
        //         new JLabel(new ImageIcon(ImageIO.read(new File("../../../folderImage/soccerField.png")))));
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        JPanel panel = new JPanel();   

        // Setting the width and height of frame
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* Creating panel. This is same as a div tag in HTML
         * We can create several panels and add them to specific 
         * positions in a JFrame. Inside panels we can add text 
         * fields, buttons and other components.
         */ 
        // adding panel to frame
        frame.add(panel);
        /* calling user defined method for adding components
         * to the panel.
         */
        placeComponents(panel);

        // Setting the frame visibility to true

        SampleListener listener = new SampleListener(panel);
        Controller controller = new Controller();


        frame.setVisible(true);

        // Have the sample listener receive events from the controller
        controller.addListener(listener);

        if (panel == listener.panel) {
            System.out.println("OK OK OK ");
        }

        // Keep this process running until Enter is pressed
        System.out.println("Press Enter to quit...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Remove the sample listener when done
        controller.removeListener(listener);
    }

    private static void placeComponents(JPanel panel) {

        panel.setLayout(null);

        // Title
        JLabel title = new JLabel("PlayMaker");
        title.setBounds(20,20,200,60);
        title.setFont(new Font(title.getFont().getName(), Font.PLAIN, 40));
        panel.add(title);
        // Names
        JLabel names = new JLabel("Team <Team Name>");
        names.setBounds(20,80,400,60);
        names.setFont(new Font(names.getFont().getName(), Font.PLAIN, 20));

        panel.add(names);

        JButton startButton = new JButton("START");
        startButton.setSize(200,80);
        startButton.setBounds(400, 220, 200, 80);
        panel.add(startButton);
    }
}
