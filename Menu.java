package main;

import javax.swing.*;
import java.awt.*;

public class Menu {

    private GameFrame gFrame;

    private Rectangle exercise;
    private Rectangle pvp;
    private Rectangle miniGame;

    public Menu(GameFrame gf) {
        this.gFrame = gf;

        exercise = new Rectangle(0, 00, 100, 100);
        pvp = new Rectangle(0, 150, 100, 100);
        miniGame = new Rectangle(0, 300, 100, 100);

    }//end constructor Menu(GameFrame gf)

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.fill(exercise);
        g2d.fill(pvp);
        g2d.fill(miniGame);

    }//end render(Graphics g)

    public Rectangle getExercise() {
        return exercise;

    }//end getExercise()

    public Rectangle getPvp() {
        return pvp;

    }//end getPvp()

    public Rectangle getMiniGame() {
        return miniGame;

    }//end getMiniGame()


}//end class Menu