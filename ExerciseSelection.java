package main;

import java.awt.*;

public class ExerciseSelection {

    private GameFrame gFrame;

    private String title;

    private int hand;
    private int finger;

    private Rectangle[] handSelection;
    private Rectangle[] fingerSelection;
    private Rectangle back;

    public ExerciseSelection(GameFrame gf) {
        this.gFrame = gf;

        handSelection = new Rectangle[2];
        fingerSelection = new Rectangle[5];

        for(int i = 0; i < handSelection.length; i++) {
            handSelection[i] = new Rectangle();

        }

        for(int j = 0; j < fingerSelection.length; j++) {

        }

        title = "Exercise Selection";

        back = new Rectangle(0, 0, 100, 100);

    }//end constructor ExerciseSelection(GameFrame gf)

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for(int i = 0; i < handSelection.length; i++) {
            g2d.fill(handSelection[i]);

        }

        for(int j = 0; j < fingerSelection.length; j++) {
            g2d.fill(fingerSelection[j]);
        }

    }

    public Rectangle[] getHand() {
        return handSelection;

    }//end getHand()

    public Rectangle[] getFinger() {
        return fingerSelection;

    }//end getFinger()

    public int getHandNum() {
        return hand;

    }//end getHandNum()

    public void setHandNum(int hn) {
        hand = hn;

    }//end setHandNum(int hn)

    public int getFingerNum() {
        return finger;

    }//end getFingerNum()

    public void setFingerNum(int fn) {
        finger = fn;

    }//end setFingerNum(int fn)

}//end class ExerciseSelection
