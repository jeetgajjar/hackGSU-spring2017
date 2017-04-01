package controller;


/******************************************************************************\
 * Copyright (C) 2012-2013 Leap Motion, Inc. All rights reserved.               *
 * Leap Motion proprietary and confidential. Not for distribution.              *
 * Use subject to the terms of the Leap Motion SDK Agreement available at       *
 * https://developer.leapmotion.com/sdk_agreement, or another agreement         *
 * between Leap Motion and you, your company or other organization.             *
 \******************************************************************************/

import java.io.IOException;
import java.lang.Math;
import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiChannel;

class SampleListener extends Listener {
    boolean pinched = false;
    double progress = 40;
    double initialPosition = 0;
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
        // Get the most recent frame and report some basic information
        Frame frame = controller.frame();
        // System.out.println("Frame id: " + frame.id()
        //                  + ", timestamp: " + frame.timestamp()
        //                  + ", hands: " + frame.hands().count()
        //                  + ", fingers: " + frame.fingers().count()
        //                  + ", tools: " + frame.tools().count()
        //                  + ", gestures " + frame.gestures().count());

        // //Get hands
        // for(Hand hand : frame.hands()) {
        //     String handType = hand.isLeft() ? "Left hand" : "Right hand";
        //     System.out.println("  " + handType + ", id: " + hand.id()
        //                      + ", palm position: " + hand.palmPosition());

        //     // Get the hand's normal vector and direction
        //     Vector normal = hand.palmNormal();
        //     Vector direction = hand.direction();

        //     // Calculate the hand's pitch, roll, and yaw angles
        //     System.out.println("  pitch: " + Math.toDegrees(direction.pitch()) + " degrees, "
        //                      + "roll: " + Math.toDegrees(normal.roll()) + " degrees, "
        //                      + "yaw: " + Math.toDegrees(direction.yaw()) + " degrees");

        //     // Get arm bone
        //     Arm arm = hand.arm();
        //     System.out.println("  Arm direction: " + arm.direction()
        //                      + ", wrist position: " + arm.wristPosition()
        //                      + ", elbow position: " + arm.elbowPosition());

        //     // Get fingers
        //     for (Finger finger : hand.fingers()) {
        //         System.out.println("    " + finger.type() + ", id: " + finger.id()
        //                          + ", length: " + finger.length()
        //                          + "mm, width: " + finger.width() + "mm");

        //         //Get Bones
        //         for(Bone.Type boneType : Bone.Type.values()) {
        //             Bone bone = finger.bone(boneType);
        //             System.out.println("      " + bone.type()
        //                              + " bone, start: " + bone.prevJoint()
        //                              + ", end: " + bone.nextJoint()
        //                              + ", direction: " + bone.direction());
        //         }
        //     }
        //}

        // Get tools
        // for(Tool tool : frame.tools()) {
        //     System.out.println("  Tool id: " + tool.id()
        //                      + ", position: " + tool.tipPosition()
        //                      + ", direction: " + tool.direction());
        // }
        HandList hands = frame.hands();
        Hand left = hands.leftmost();
        if (left.isLeft()) {
            FingerList fingers = left.fingers();

            //System.out.println("(" + phLoc.getX() + ", " + phLoc.getY() + ")");

            Bone bone1 = fingers.get(0).bone(Bone.Type.TYPE_DISTAL);
            Vector loc1 = bone1.center();
            Bone bone2 = fingers.get(1).bone(Bone.Type.TYPE_DISTAL);
            Vector loc2 = bone2.center();

            if (loc1.distanceTo(loc2) < 50) {
                pinched = true;
                System.out.print("pinch");
                //System.out.println("PINCH DETECTED AT LOCATION" + loc1);
                if (initialPosition == 0) {
                    initialPosition = loc1.getX();
                }
                progress = (progress + (loc1.getX() - initialPosition)/80) % 40 + 40;
            } else {
                initialPosition = 0;
                pinched = false;
            }

        }

        int num = ((int) progress);
        if (pinched == true) {
            int channel = 0; // 0 is a piano, 9 is percussion, other channels are for other instruments

            int volume = 80; // between 0 et 127
            int duration = 200; // in milliseconds

            try {
                Synthesizer synth = MidiSystem.getSynthesizer();
                synth.open();
                MidiChannel[] channels = synth.getChannels();

                // --------------------------------------
                // Play a few notes.
                // The two arguments to the noteOn() method are:
                // "MIDI note number" (pitch of the note),
                // and "velocity" (i.e., volume, or intensity).
                // Each of these arguments is between 0 and 127.
                channels[channel].noteOn( num, volume ); // C note
                Thread.sleep( duration );
                channels[channel].noteOff( num );

                synth.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        GestureList gestures = frame.gestures();
        //     for (int i = 0; i < gestures.count(); i++) {
        //         Gesture gesture = gestures.get(i);

        //         switch (gesture.type()) {
        //             // case TYPE_CIRCLE:

        //             //     CircleGesture circle = new CircleGesture(gesture);
        //             //     if (hands.count() == 1) {
        //             //         Hand hand = hands.get(1);
        //             //         if (hand.isLeft()) {
        //             //             System.out.print("+++++++++++ LEFT HAND +++++++++++++++++++++++");
        //             //         } else if (hand.isRight()) {
        //             //             System.out.print("                           RIGHT HAND");
        //             //         }
        //             //     }

        //             //     // Calculate clock direction using the angle between circle normal and pointable
        //             //     String clockwiseness;
        //             //     if (circle.pointable().direction().angleTo(circle.normal()) <= Math.PI/2) {
        //             //         // Clockwise if angle is less than 90 degrees
        //             //         clockwiseness = "clockwise";
        //             //     } else {
        //             //         clockwiseness = "counterclockwise";
        //             //     }

        //             //     // Calculate angle swept since last frame
        //             //     double sweptAngle = 0;
        //             //     if (circle.state() != State.STATE_START) {
        //             //         CircleGesture previousUpdate = new CircleGesture(controller.frame(1).gesture(circle.id()));
        //             //         sweptAngle = (circle.progress() - previousUpdate.progress()) * 2 * Math.PI;
        //             //     }

        //             //     System.out.println("  Circle id: " + circle.id()
        //             //                + ", " + circle.state()
        //             //                + ", progress: " + circle.progress()
        //             //                + ", radius: " + circle.radius()
        //             //                + ", angle: " + Math.toDegrees(sweptAngle)
        //             //                + ", " + clockwiseness);
        //             //     break;
        //             // case TYPE_SWIPE:
        //             //     if (gesture.hands().leftmost().isLeft()) { System.out.print("LEFT HAND +++++++"); }
        //             //     SwipeGesture swipe = new SwipeGesture(gesture);
        //             //     System.out.println("  Swipe id: " + swipe.id()
        //             //                + ", " + swipe.state()
        //             //                + ", position: " + swipe.position()
        //             //                + ", direction: " + swipe.direction()
        //             //                + ", speed: " + swipe.speed());
        //             //     break;
        //             // // case TYPE_SCREEN_TAP:
        //             // //     ScreenTapGesture screenTap = new ScreenTapGesture(gesture);
        //             // //     System.out.println("  Screen Tap id: " + screenTap.id()
        //             // //                + ", " + screenTap.state()
        //             // //                + ", position: " + screenTap.position()
        //             // //                + ", direction: " + screenTap.direction());
        //             // //     break;
        //             // // case TYPE_KEY_TAP:
        //             // //     KeyTapGesture keyTap = new KeyTapGesture(gesture);
        //             // //     System.out.println("  Key Tap id: " + keyTap.id()
        //             // //                + ", " + keyTap.state()
        //             // //                + ", position: " + keyTap.position()
        //             // //                + ", direction: " + keyTap.direction());
        //             // //     break;
        //             // default:
        //             //     System.out.println("Unknown gesture type.");
        //             //     break;
        //         }
        //     }

        //     // if (!frame.hands().isEmpty() || !gestures.isEmpty()) {
        //     //     System.out.println();
        //     // }
    }
}

class SlideWhistle {
    public static void main(String[] args) {
        // Create a sample listener and controller
        SampleListener listener = new SampleListener();
        Controller controller = new Controller();

        // Have the sample listener receive events from the controller
        controller.addListener(listener);

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
}