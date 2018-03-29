package input;

import main.GameFrame;
import main.GamePanel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{

    /*
     * Author: Carl Zhang
     * Date: Monday 19, 2017
     *
     * KeyListener
     *
     */

    private GamePanel gPanel;

    public KeyInput(GamePanel gp){
        this.gPanel = gp;

    }//end constructor KeyInput(Game g)

    public void keyPressed(KeyEvent e) {
        gPanel.keyPressed(e);

    }//end keyPressed(KeyEvent e)

    public void keyReleased(KeyEvent e) {
        gPanel.keyReleased(e);

    }//end keyReleased(KeyEvent e)

}//end class KeyInput