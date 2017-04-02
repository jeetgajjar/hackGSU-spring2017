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

class SampleListener extends Listener {
    double counter = 0;
    Frame mainFrame;
    JFrame viewFrame;
    JButton button;
    public JPanel panel;

    public SampleListener(JPanel pan) {
        //viewFrame = frame;
        //JPanel panel = (JPanel) (viewFrame.getContentPane());
        button = new JButton("CURSOR");
        panel = pan;
        button.setSize(100,40);
        button.setBounds(500, 280, 100, 40);
        panel.add(button);
        panel.revalidate();
        panel.repaint();
    }
    public void onInit(Controller controller) {
        System.out.println("Initialized");
    }

    public void onConnect(Controller controller) {
        System.out.println("Connected");
        controller.enableGesture(Gesture.Type.TYPE_SWIPE);
        controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
        controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
        controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
    }

    public void onDisconnect(Controller controller) {
        //Note: not dispatched when running in a debugger.
        System.out.println("Disconnected");
    }

    public void onExit(Controller controller) {
        System.out.println("Exited");
    }

    public void onFrame(Controller controller) {
        String condition = "start";
        if (condition.equals("start")) {
            start(controller);
        }
        // Get the most recent frame and report some basic information
        Frame frame = controller.frame();
        mainFrame = controller.frame();
        // System.out.println("Frame id: " + frame.id()
        //                  + ", timestamp: " + frame.timestamp()
        //                  + ", hands: " + frame.hands().count()
        //                  + ", fingers: " + frame.fingers().count()
        //                  + ", tools: " + frame.tools().count()
        //                  + ", gestures " + frame.gestures().count());

        // Get tools
        for(Tool tool : frame.tools()) {
        //     System.out.println("  Tool id: " + tool.id()
        //                      + ", position: " + tool.tipPosition()
        //                      + ", direction: " + tool.direction());
        }

        GestureList gestures = frame.gestures();
        for (int i = 0; i < gestures.count(); i++) {
            Gesture gesture = gestures.get(i);

            switch (gesture.type()) {
                case TYPE_CIRCLE:

                    Hand hand = frame.hands().leftmost();
                    if (hand.isRight()) {
                        System.out.println("ya");
                    } else if (hand.isLeft()) {
                        CircleGesture circle = new CircleGesture(gesture);

                        // Calculate clock direction using the angle between circle normal and pointable
                        String clockwiseness;
                        if (circle.pointable().direction().angleTo(circle.normal()) <= Math.PI/2) {
                            // Clockwise if angle is less than 90 degrees
                            clockwiseness = "clockwise";
                        } else {
                            clockwiseness = "counterclockwise";
                        }

                        // Calculate angle swept since last frame
                        double sweptAngle = 0;
                        if (circle.state() != State.STATE_START) {
                            CircleGesture previousUpdate = new CircleGesture(controller.frame(1).gesture(circle.id()));
                            sweptAngle = (circle.progress() - previousUpdate.progress()) * 2 * Math.PI;
                        }

                        // System.out.println("  Circle id: " + circle.id()
                        //            + ", " + circle.state()
                        //            + ", progress: " + circle.progress()
                        //            + ", radius: " + circle.radius()
                        //            + ", angle: " + Math.toDegrees(sweptAngle)
                        //            + ", " + clockwiseness);
                        if (clockwiseness.equals("clockwise")) {
                            if (counter <= 1) {
                                counter += 0.0005;
                            }
                        } else if (clockwiseness.equals("counterclockwise")) {
                            if (counter >= 0) {
                                counter -= 0.001;
                            }
                        }
                        System.out.print("process started ");
                        for (double j = 0; j < counter; j+=0.01) {
                            System.out.print(" %");
                        }
                        System.out.println("%");
                        break;
                        //}
                    }
                    default:
                        System.out.println("Unknown gesture type.");
                        break;

                }


                // case TYPE_SWIPE:
                //     SwipeGesture swipe = new SwipeGesture(gesture);
                //     System.out.println("  Swipe id: " + swipe.id()
                //                + ", " + swipe.state()
                //                + ", position: " + swipe.position()
                //                + ", direction: " + swipe.direction()
                //                + ", speed: " + swipe.speed());
                //     break;
                // case TYPE_SCREEN_TAP:
                //     ScreenTapGesture screenTap = new ScreenTapGesture(gesture);
                //     System.out.println("  Screen Tap id: " + screenTap.id()
                //                + ", " + screenTap.state()
                //                + ", position: " + screenTap.position()
                //                + ", direction: " + screenTap.direction());
                //     break;
                // case TYPE_KEY_TAP:
                //     KeyTapGesture keyTap = new KeyTapGesture(gesture);
                //     System.out.println("  Key Tap id: " + keyTap.id()
                //                + ", " + keyTap.state()
                //                + ", position: " + keyTap.position()
                //                + ", direction: " + keyTap.direction());
                //     break;

        }

        if (!frame.hands().isEmpty() || !gestures.isEmpty()) {
            //System.out.println();
        }
    }

    public void start(Controller controller) {
        Frame frame = controller.frame();
        mainFrame = controller.frame();

        Hand cursorHand = frame.hands().rightmost();
        Vector position = calibrate(cursorHand.palmPosition());
        
        if (cursorHand.isValid()) {
            cursor(position);
        }

    }

    public Vector calibrate(Vector position) {
        double x = position.getX();
        x = x > 150 ? 150 : x;
        x = x < -150? -150 : x;
        double y = position.getY();

        y = y > 350 ? 350 : y;
        y = y < 150? 150 : y;

        position.setX((float)x);
        position.setY((float)y);

        return position;
    }

    public void cursor(Vector position) {
        //position = calibrate(position);
        double x = (position.getX()+150)*10/3;
        double y = 600 - ((position.getY() - 150) * 3);
        System.out.println("X: " + x + "Y: " + y);

        button.setLocation((int)x,(int)y);
        panel.revalidate();
        panel.repaint();


    }
}

public class Runner {
    public static void main(String[] args) {
        // Create a sample listener and controller

        JFrame frame = new JFrame("My First Swing Example");
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
        startButton.setSize(100,40);
        startButton.setBounds(450, 280, 100, 40);
        panel.add(startButton);
    }
}
