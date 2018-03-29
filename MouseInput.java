package input;

import main.GameFrame;
import main.GamePanel;
import main.SignIn;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput extends MouseAdapter{

    /*
     * Author: Carl Zhang
     * Date: Monday 19, 2017
     *
     * MouseListener
     *
     */

    private GamePanel gPanel;

    public MouseInput(GamePanel gp) {
        gPanel = gp;

    }//end MouseInput(Game game)

    public void mouseClicked(MouseEvent e) {
        gPanel.mouseClicked(e);

    }//end mouseClicked(MouseEvent e)

}