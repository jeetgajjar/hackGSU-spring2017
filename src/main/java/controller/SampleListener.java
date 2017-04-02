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

public class SampleListener extends Listener {
    Frame mainFrame;
    JFrame viewFrame;
    JLabel mouse;
    public JPanel panel;
    int time = 0;
    int simTime = 0;
    boolean selectedItem = false;
    int startTime = 0;
    int endTime = 0;
    JLabel[] team;
    FieldFrame[] movie;
    JLabel lab;
    



    public SampleListener(JPanel pan) throws Exception {
        //viewFrame = frame;
        //JPanel panel = (JPanel) (viewFrame.getContentPane());
        ImageIcon icon = new ImageIcon("../../../icons/hand.png");
        mouse = new JLabel(icon);
        panel = pan;
        mouse.setBounds(500, 300, 25, 25);
        panel.add(mouse);
        panel.setComponentZOrder(mouse, 0);
        panel.revalidate();
        panel.repaint();

        movie = new FieldFrame[150];
        for (int i = 0; i < movie.length; i++) {
            movie[i] = new FieldFrame();
        }

        team = new Player[5];

        for (int i = 0; i < team.length; i++ ) {
            team[i] = new Player(25, 10 + i*90, i);
        }
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
        mouse(controller);
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
        if (checkPinch(frame)) {
            System.out.print("pinch");
            clickStart();
            grab();
        } else if (selectedItem) {
            selectedItem = false;
            keyFrame(time, time, 1); // TODO USE PREVIOUS TIME AND CHECK PROPER ID
        }
        GestureList gestures = frame.gestures();
        for (int i = 0; i < gestures.count(); i++) {
            Gesture gesture = gestures.get(i);

            //TIME DILATION
            switch (gesture.type()) {
                case TYPE_CIRCLE:

                    Hand hand = frame.hands().leftmost();
                    if (hand.isLeft()) {
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
                            if (simTime <= 299) {
                                simTime += 1;
                            }
                        } else if (clockwiseness.equals("counterclockwise")) {
                            if (simTime >= 0) {
                                simTime -= 1;
                            }
                        }
                        // for (double j = 0; j < counter; j+=0.05) {
                        //     System.out.print(" %");
                        // }
                        // System.out.println("%");
                        //draw(time);
                        int time = simTime / 2;
                        System.out.println("TIME: " + time);
                        FieldFrame current = movie[time];
                        draw(time);

                        panel.revalidate();
                        panel.repaint();
                        System.out.println("end of circle");
                        break;
                        //}
                    }
                    case TYPE_SCREEN_TAP:
                        ScreenTapGesture screenTap = new ScreenTapGesture(gesture);
                        
                        break;

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

    public void grab() {

    }

    public void draw(int newTime) {
        FieldFrame current = movie[newTime];
        // for (int i = 0; i < team.length; i++) {
        //     panel.remove(team[i]);
        //     panel.add(team[i]);
        //     if (!(((Player) team[i]).isSelected())) {
        //         team[i].setLocation(800,10);
        //         System.out.print(team[i].getLocation());
        //         panel.revalidate();
        //         panel.repaint();
        //         System.out.println(team[i].getLocation());

        //         //team[i].setLocation(current.getRoster()[i][0], current.getRoster()[i][1]);
        //     } else {
        //         int x = (int)(mouse.getLocation().getX());
        //         int y = (int)(mouse.getLocation().getY());
        //         team[i].setLocation(x,y);
                
        //     }
        
        // }

        for (Player p: (Player[]) team) {
            if (!(p.isSelected())) {
                int i = p.getID();
                p.setLocation(current.getRoster()[i][0], current.getRoster()[i][1]);
                panel.setComponentZOrder(lab, panel.countComponents()-1);
                panel.revalidate();
                panel.repaint(0,0,1000,600);
                System.out.println(p.getLocation());
            }
        }
        JPanel newPanel = new JPanel();
        for (Player p: (Player[]) team) {
            newPanel.add(p);
            System.out.println("TEST");
        }
        ((JFrame) (panel.getParent())).setContentPane((JPanel) newPanel);
        panel = newPanel;
        panel.revalidate();
        panel.repaint();
        // draw shit TODO
    }


    public boolean checkPinch(Frame frame) {
        Hand right = frame.hands().rightmost();
        if (!(right.isValid())) {
            return false;
        }
        FingerList fingers = right.fingers();

                //System.out.println("(" + phLoc.getX() + ", " + phLoc.getY() + ")");

                Bone bone1 = fingers.get(0).bone(Bone.Type.TYPE_DISTAL);
                Vector loc1 = bone1.center();
                Bone bone2 = fingers.get(1).bone(Bone.Type.TYPE_DISTAL);
                Vector loc2 = bone2.center();

                if (loc1.distanceTo(loc2) < 40) {
                    return true;
                } return false;
    }

    public void clickStart() {
        Area userArea = new Area(mouse.getBounds());
                        Component[] items = panel.getComponents();
                        for (Component c: items) {
                            Area newArea = new Area(c.getBounds());
                            if (mouse != c && newArea.intersects(userArea.getBounds2D())) {
                                if (c instanceof JButton) {
                                    startSim();
                                    System.out.print("Click");
                                }
                            }
                        }
    }

    public void mouse(Controller controller) {
        Frame frame = controller.frame();
        mainFrame = controller.frame();

        Hand cursorHand = frame.hands().rightmost();
        Vector position = calibrate(cursorHand.palmPosition());
        
        if (cursorHand.isValid()) {
            cursor(position);
        }

    }

    public void startSim() {
        // JPanel mainPanel = new JPanel();
        // JButton butt = null;

        
        panel.setLayout(null);
        lab = null;
        panel.removeAll();

        try {
            //mainFrame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("../../../folderImage/soccerField.png")))));
            lab = new JLabel(new ImageIcon(ImageIO.read(new File("../../../folderImage/soccerField.png"))));

        } catch (IOException e) {
            e.printStackTrace();
        }
        lab.setBounds(0,0,1000,600);
        for (JLabel p:team) {
            panel.add(p);
        }
        panel.add(lab);
        panel.add(mouse);
        panel.setComponentZOrder(lab, panel.countComponents() - 1);
        // viewFrame.pack();
        // viewFrame.setVisible(true);
        // viewFrame.invalidate();
        // viewFrame.validate();
        // viewFrame.repaint();
        panel.revalidate();
        panel.repaint();
    }

    public void keyFrame(int startTime, int endTime, int id) {

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
        //System.out.println("X: " + x + "Y: " + y);

        mouse.setLocation((int)x-1,(int)y-1);
        panel.revalidate();
        panel.repaint();


    }
}