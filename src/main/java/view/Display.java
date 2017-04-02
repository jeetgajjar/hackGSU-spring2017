import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField; 
import java.awt.Font;
public class Display {
    
    public static void main(String[] args) {    
        // Creating instance of JFrame
        JFrame frame = new JFrame("My First Swing Example");
        // Setting the width and height of frame
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* Creating panel. This is same as a div tag in HTML
         * We can create several panels and add them to specific 
         * positions in a JFrame. Inside panels we can add text 
         * fields, buttons and other components.
         */
        JPanel panel = new JPanel();    
        // adding panel to frame
        frame.add(panel);
        /* calling user defined method for adding components
         * to the panel.
         */
        placeComponents(panel);

        // Setting the frame visibility to true
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {

        panel.setLayout(null);

        // Title
        JLabel title = new JLabel("PlayMaker");
        title.setBounds(20,20,200,60);
        title.setFont(new Font(title.getFont().getName(), Font.PLAIN, 40));
        panel.add(title);
        // Names
        JLabel names = new JLabel("Team SWAGMONEY DAB LIT");
        names.setBounds(20,80,400,60);
        names.setFont(new Font(names.getFont().getName(), Font.PLAIN, 20));

        panel.add(names);

        JButton startButton = new JButton("START");
        startButton.setSize(100,40);
        startButton.setBounds(450, 280, 100, 40);
        panel.add(startButton);
    }

}