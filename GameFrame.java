package main;

import javax.swing.*;


public class GameFrame {

    private static GameFrame gFrame;
    private GamePanel gPanel;

    private GameState gameState;

    private static JFrame frame = new JFrame();;

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;

    public GameFrame() {
        gPanel = new GamePanel(this);

        //set GameState
        gameState = GameState.SignIn;

    }//end constructor GameFrame()

    public static void main(String[] args) {
        gFrame = new GameFrame();

        frame.add(gFrame.getPanel());

        frame.setTitle("Type to the Future");
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //frame.pack();
        frame.setVisible(true);

    }//end main

    public enum GameState {
        SignIn,
        Login,
        Create,

        Menu,
        ExerciseSelection,
        Exercise,
        Pvp,
        MiniGameSelection,
        MiniGame,

    }//end enum GameState()

    public void switchState() {
        switch(gameState) {
            case SignIn: gameState = GameState.SignIn;
                break;

            case Login: gameState = GameState.Login;
                break;

            case Create: gameState = GameState.Login;
                break;

            case Menu: gameState = GameState.Menu;
                break;

        }

    }//end switchState()

    public static int getWidth() {
        return WIDTH;

    }//end getWidth()

    public static int getHeight() {
        return HEIGHT;

    }//end getHeight()

    public GamePanel getPanel() {
        return gPanel;

    }//end getPanel()

    public GameState getState() {
        return gameState;

    }//end getState()

    public void setState(GameState gs) {
        gameState = gs;

    }//end setState(GameState gs)

}// end class GameFrame