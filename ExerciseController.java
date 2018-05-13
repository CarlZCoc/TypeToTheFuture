package game;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ExerciseController implements Runnable, Initializable{

    private Thread exerciseThread;

    @FXML private TextField exerciseText;

    //Total WPM variables
    @FXML private Label tWPM;
    private long intervalStartTime;
    private int charTyped;
    private String wordTyped;

    //Current WPM variables
    @FXML private Label cWPM;
    private long startTime;
    private int intervalCharTyped;
    private String intervalWordTyped;

    @FXML private Label readyOne;
    @FXML private Label readyTwo;

    //Check if exercise is over
    private boolean running;

    //Holds index for which number to display in countdown
    private int indexTemp;

    //Booleans for checking if fingers are on keyboard
    private boolean a = false;
    private boolean s = false;
    private boolean d = false;
    private boolean f = false;
    private boolean j = false;
    private boolean k = false;
    private boolean l = false;
    private boolean sC = false;

    //Keys which are too be held
    private HashMap<String, Boolean> keys = new HashMap<>();

    public static int handIndex;
    public static int fingerIndex;

    private String paragraph = "Hello my name is Carl. Hello my name is Aaron. Hello my name is Andrew.";

    public ExerciseController() {
        running = true;

    }//end controller ExerciseController

    public void initialize(URL url, ResourceBundle rb)  {
        //Check which key pressed
        exerciseText.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case A:
                    a = true;
                case S:
                    s = true;
                case D:
                    d = true;
                case F:
                    f = true;
                case J:
                    j = true;
                case K:
                    k = true;
                case L:
                    l = true;
                case SEMICOLON:
                    sC = true;

            }

        });

        //Check when key released (Prevent cheating)
        exerciseText.setOnKeyReleased(keyEvent -> {
            switch (keyEvent.getCode()) {
                case A:
                    a = false;
                case S:
                    s = false;
                case D:
                    d = false;
                case F:
                    f = false;
                case J:
                    j = false;
                case K:
                    k = false;
                case L:
                    l = false;
                case SEMICOLON:
                    sC = false;

            }

        });

        //Typing thread
        exerciseThread = new Thread(this, "Exercise Thread");
        exerciseThread.start();

    }//end initialize()

    public void run() {
        //Choose fingers to place on board
        chooseFingers();
        //Display instructions
        String display = "Please hold down keys ";

        Set set = keys.entrySet();
        Iterator i = set.iterator();

        //Gets name of the keys
        while(i.hasNext()) {
            Map.Entry keyName = (Map.Entry)i.next();
            display += keyName.getKey() + ", ";

        }

        display += " before starting.";

        //Set output display text
        exerciseText.setText(display);

        try {
            //Countdown
            startType();

            //Setting Total WPM values
            startTime = System.currentTimeMillis();
            charTyped = 0;

            //Set Current WPM values
            intervalStartTime = System.currentTimeMillis();
            intervalCharTyped = 0;

            while (running) {
                System.out.println(running);
                //checkKeyPressed();
                //if(checkKeyPressed())
                    //System.out.println("It works!");
                    updateCurrentWPM();

                    //Reset Current WPM values
                    intervalStartTime = System.currentTimeMillis();
                    intervalCharTyped = 0;

                    updateTotalWPM();
                    typing();

                exerciseThread.sleep(1000);

            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }//end run()

    public void checkKeyPressed() {
        Set set = keys.entrySet();
        Iterator i = set.iterator();

        //Gets name of the keys
        while(i.hasNext()) {
            Map.Entry keyName = (Map.Entry)i.next();
            System.out.println((boolean) keyName.getValue());
            if((boolean) keyName.getValue() != true)
                    System.out.println(keyName.getKey() + ", " + keyName.getValue());
                //return false;

        }

        //return true;

    }//end checkKeyPressed()

    //Only run at start (Countdown)
    private void startType() {
            try {
                //Countdown
                for(int i = 3; i > 0; i--) {
                    indexTemp = i;

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            readyOne.setText(String.valueOf(indexTemp));
                            readyTwo.setText(String.valueOf(indexTemp));

                        }
                    });

                    //Wait 1 sec
                    exerciseThread.sleep(1000);

                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        readyOne.setLayoutX(25);
                        readyOne.setFont(Font.font("Regular", FontWeight.BLACK, 36));
                        readyOne.setText("Start");

                        readyTwo.setLayoutX(25);
                        readyTwo.setFont(Font.font("Regular", FontWeight.BLACK, 36));
                        readyTwo.setText("Start");

                    }

                });

            }catch(Exception e) {
                e.printStackTrace();

            }

    }//end startType

    //Select fingers needed to be pressed
    private void chooseFingers() {
        switch(handIndex) {

            case 0:
                switch(fingerIndex) {

                    //Left hand, Index
                    case 0:
                        keys.put("a", a);
                        keys.put("s", s);
                        keys.put("d", d);
                        break;

                    //Left hand, Middle
                    case 1:
                        keys.put("a", a);
                        keys.put("s", s);
                        keys.put("f", f);
                        break;

                    //Left hand, Ring
                    case 2:
                        keys.put("a", a);
                        keys.put("d", d);
                        keys.put("f", f);
                        break;

                    //Left hand, Pinkie
                    case 3:
                        keys.put("s", s);
                        keys.put("d", d);
                        keys.put("f", f);
                        break;

                }
                break;

            case 1:
                switch(fingerIndex) {

                    //Right hand, Index
                    case 0:
                        keys.put("k", k);
                        keys.put("l", l);
                        keys.put(";", sC);
                        break;

                    //Right hand, Middle
                    case 1:
                        keys.put("j", j);
                        keys.put("l", l);
                        keys.put(";", sC);
                        break;

                    //Right hand, Ring
                    case 2:
                        keys.put("j", j);
                        keys.put("k", k);
                        keys.put(";", sC);
                        break;

                    //Right hand, Pinkie
                    case 3:
                        keys.put("j", j);
                        keys.put("k", k);
                        keys.put("l", l);
                        break;

                }

                break;

        }

    }//end chooseFingers()

    private void typing() {
        //Check KeyPressed matches
        exerciseText.setOnKeyPressed(keyEvent -> {
            //Set text
            exerciseText.setText(paragraph);

            if(keyEvent.getText().compareTo(String.valueOf(paragraph.charAt(0))) == 0) {
                //Update String
                paragraph = paragraph.substring(1, paragraph.length());

                exerciseText.setText(paragraph);
                //Increment num of chars typed
                intervalCharTyped += 1;
                charTyped += 1;

                //Stop thread
                if(paragraph.length() == 0) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            exerciseThread.stop();

                        }

                    });
                    /*
                    try {
                        //exerciseThread.wait();

                    }catch(Exception e) {
                        e.printStackTrace();
                    }*/

                }

            }

        });

    }//end typing()

    private void updateCurrentWPM() {
        long timeNow = System.currentTimeMillis();
        double seconds = (timeNow - intervalStartTime) / 1000;
        double minutes = seconds / 60;

        //System.out.println(String.valueOf((int) (intervalCharTyped / minutes)));

        intervalWordTyped = String.valueOf((int) ((intervalCharTyped / 5) / minutes));

        //Update label (Through thread)
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                cWPM.setText(intervalWordTyped);

            }
        });

    }//end updateCurrentWPM()

    private void updateTotalWPM() {
        long timeNow = System.currentTimeMillis();
        double seconds = (timeNow - startTime) / 1000;
        double minutes = seconds / 60;

        wordTyped = String.valueOf((int) ((charTyped / 5) / minutes));

        //Update label (Through thread)
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tWPM.setText(wordTyped);

            }
        });

    }//end updateTotalWPM

    @FXML
    private void changeToExerciseSelection(ActionEvent event) throws IOException {
        exerciseThread.stop();

        Parent root = FXMLLoader.load(getClass().getResource("template/ExerciseSelection.fxml"));
        Scene scene = new Scene(root);

        //Get information from the stage
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        //Update scene on stage
        stage.setScene(scene);
        stage.show();

    }//end changeToExerciseSelection(ActionEvent event)

    @FXML
    private void changeToMenu(ActionEvent event) throws IOException{
        exerciseThread.stop();

        Parent root = FXMLLoader.load(getClass().getResource("template/Menu.fxml"));
        Scene scene = new Scene(root);

        //Get information from the stage
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        //Update scene on stage
        stage.setScene(scene);
        stage.show();

    }//end changeToMenu(ActionEvent event)

    @FXML
    private void changeToExerciseResults(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("template/ExerciseSelection.fxml"));
        Scene scene = new Scene(root);

        //Get information from the stage
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        //Update scene on stage
        stage.setScene(scene);
        stage.show();

    }//end changeToExerciseResults(ActionEvent event)

}//end class ExerciseController
