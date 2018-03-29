package main;

import fileIO.IO;

import input.KeyInput;
import input.MouseInput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable, ActionListener{

    private GameFrame gFrame;

    private KeyInput keyInput;
    private MouseInput mouseInput;

    private Thread game;
    private boolean running;

    private SignIn signIn;
    private Login login;
    private Create create;

    private Menu menu;
    private ExerciseSelection exerciseSelection;
    private Exercise exercise;
    private Pvp pvp;
    private MiniGameSelection miniGameSelection;
    private MiniGame miniGame;

    private GameFrame.GameState tempState;

    public GamePanel(GameFrame gf) {
        this.gFrame = gf;

        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(new KeyInput(this));
        this.addMouseListener(new MouseInput(this));

        signIn = new SignIn(gFrame);
        login = new Login(gFrame);
        create = new Create(gFrame);

        menu = new Menu(gFrame);

        exerciseSelection = new ExerciseSelection(gFrame);
        exercise = new Exercise(gFrame);
        pvp = new Pvp(gFrame);
        miniGameSelection = new MiniGameSelection(gFrame);
        miniGame = new MiniGame(gFrame);

        tempState = this.gFrame.getState();

        this.setSize(this.gFrame.getWidth(), this.gFrame.getHeight());
        this.setBackground(Color.CYAN);
        this.setVisible(true);

        running = true;
        this.start();

    }//end constructor GamePanel

    public void start() {
        if(running) return;
        running = true;
        game = new Thread(this, "Game");
        game.start();

    }//end start()

    public void run() {
        System.out.println("hi");
        double nsPerTick = 1000000000.0/60.0;
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double unprocessed = 0.0;
        int fps = 0;
        int tps = 0;

        boolean canRender;

        while(running) {
            long now = System.nanoTime();
            unprocessed += (now - lastTime) / nsPerTick;
            lastTime = now;

            if(unprocessed >= 1.0) {
                tick();
                unprocessed++;
                tps++;
                canRender = true;

            }else {
                canRender = false;

            }

            try {
                Thread.sleep(1);

            }catch(InterruptedException e) {
                e.printStackTrace();

            }

            if(canRender) {
                fps++;

            }

            if(System.currentTimeMillis() - 1000 > timer) {
                timer += 1000;
                System.out.printf("FPS: %d | TPS: %d/n", fps, tps);
                fps = 0;
                tps = 0;

            }

        }

    }//end run

    public void end() {
        if(!running) return;
        running = false;

    }//end end()

    public void tick() {


    }//end tick()

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        render(g);

    }//end paintComponent(Graphics g)

    public void render(Graphics g) {
        switch(gFrame.getState()) {
            case SignIn: signIn.render(g);
                break;
            case Login: login.render(g);
                break;
            case Create: create.render(g);
                break;

            case Menu: menu.render(g);
                break;

            case ExerciseSelection:
                break;

            case Exercise:
                break;

            case Pvp:
                break;

            case MiniGame:
                break;

            case MiniGameSelection:
                break;

        }

    }//end render()

    public void actionPerformed(ActionEvent e) {
        if(gFrame.getState() != tempState) {
            gFrame.switchState();

        }else if(gFrame.getState() == GameFrame.GameState.SignIn) {

        }else if(gFrame.getState() == GameFrame.GameState.Menu) {

        }

        removeAll();
        revalidate();

        //repaint the GameState
        repaint();

    }//end actionPerformed(ActionEvent e)

    public void mouseClicked(MouseEvent e) {
        if(gFrame.getState() == GameFrame.GameState.SignIn) {
            gFrame.getPanel().removeAll();
            gFrame.getPanel().revalidate();
            gFrame.getPanel().repaint();

            if(signIn.getLogin().contains(e.getX(), e.getY())) {
                //change State to Login
                gFrame.setState(GameFrame.GameState.Login);

                //add components
                gFrame.getPanel().add(create.getUsername());
                gFrame.getPanel().add(create.getPassword());

            }else if(signIn.getCreate().contains(e.getX(), e.getY())) {
                //change State to Create
                gFrame.setState(GameFrame.GameState.Create);

                //add components
                gFrame.getPanel().add(create.getUsername());
                gFrame.getPanel().add(create.getPassword());

        }else if(gFrame.getState() == GameFrame.GameState.Login) {
                //Back or Enter clicked
                if(login.getBack().contains(e.getX(), e.getY())) {
                    //set State to SignIn no login
                    gFrame.setState(GameFrame.GameState.SignIn);

                }else if(login.getEnter().contains(e.getX(), e.getY())) {
                    boolean validUserPass = true;
                    String user = login.getUsername().getText();
                    String pass = login.getPassword().getText();

                    //check if there is a value
                    if(user.compareTo("") != 0 && pass.compareTo("") != 0) {


                    }else {
                        validUserPass = false;

                    }

                    if(validUserPass) {
                        //set State to SignIn
                        gFrame.setState(GameFrame.GameState.Menu);

                    }

                }

            }

        }else if(gFrame.getState() == GameFrame.GameState.Create) {
            //Back or Enter clicked
            if (create.getBack().contains(e.getX(), e.getY())) {
                //set State to SignIn no login
                gFrame.setState(GameFrame.GameState.SignIn);

            }else if (create.getEnter().contains(e.getX(), e.getY())) {
                boolean validUserPass = true;
                String user = create.getUsername().getText();
                String pass = create.getPassword().getText();

                //check if there is a value
                if (user.compareTo("") != 0 && pass.compareTo("") != 0) {
                    boolean passIO = true;

                    try {
                        //checks if username already exists
                        IO.openInputFile("data//Info.txt");

                        String line = IO.readLine();

                        while(line != null) {
                            int last = line.indexOf("%");

                            if(user.compareTo(line.substring(0, last)) == 0) {
                                JOptionPane.showMessageDialog(null, "This Username Already Exists", "Username Taken", JOptionPane.OK_CANCEL_OPTION);

                                passIO = false;
                                break;

                            }

                            line = IO.readLine();

                        }//end while

                        IO.closeInputFile();

                        if(passIO) {
                            //putting new info into file
                            IO.appendOutputFile("data//Info.txt");
                            IO.println(user + "%" + pass);
                            IO.closeOutputFile();

                        }

                    } catch (IOException e2) {
                        System.out.println("Error: No Create Data File");

                    }//end try/catch

                }else {
                    validUserPass = false;

                }

                //set State to SignIn if valid user/pass is entered
                if (validUserPass) {
                    //set State to SignIn
                    gFrame.setState(GameFrame.GameState.SignIn);

                }

            }

        }else if(gFrame.getState() == GameFrame.GameState.Menu) {
            if(menu.getExercise().contains(e.getX(), e.getY())) {
                //switch State to ExerciseSelection
                gFrame.setState(GameFrame.GameState.ExerciseSelection);

            }else if(menu.getPvp().contains(e.getX(), e.getY())) {
                //switch State to Pvp
                gFrame.setState(GameFrame.GameState.Pvp);

            }else if(menu.getMiniGame().contains(e.getX(), e.getY())) {
                //switch State to MiniGame
                gFrame.setState(GameFrame.GameState.MiniGameSelection);

            }

        }else if(gFrame.getState() == GameFrame.GameState.ExerciseSelection) {
            //select hand
            for(int i = 0; i < 2; i++) {
                if(exerciseSelection.getHand()[i].contains(e.getX(), e.getY())) {
                    exerciseSelection.setHandNum(i);

                }

            }

            //select finger
            for(int i = 0; i < 5; i++) {
                if(exerciseSelection.getFinger()[i].contains(e.getX(), e.getY())) {
                    exerciseSelection.setFingerNum(i);

                }

            }

        }else if(gFrame.getState() == GameFrame.GameState.Exercise) {

        }else if(gFrame.getState() == GameFrame.GameState.Pvp) {

        }else if(gFrame.getState() == GameFrame.GameState.MiniGameSelection) {

        }else if(gFrame.getState() == GameFrame.GameState.MiniGame) {

        }

        repaint();

    }//end mouseCLicked(MouseEvent e)

    public void keyPressed(KeyEvent e) {


    }//end keyPressed(KeyEvent e)

    public void keyReleased(KeyEvent e) {


    }//end keyReleased(KeyEvent e)

}//end class GamePanel
