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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExerciseController implements Runnable, Initializable{

    //Typing thread
    private Thread exerciseThread;

    @FXML private TextField exerciseText;

    @FXML private Label cWPM;
    @FXML private Label tWPM;

    @FXML private Label readyOne;
    @FXML private Label readyTwo;
    private Label[] ready = {readyOne, readyTwo};

    //Check if exercise is over
    private boolean running;

    //Booleans for checking if fingers are on keyboard
    public static boolean a = false;
    public static boolean s = false;
    public static boolean d = false;
    public static boolean f = false;
    public static boolean j = false;
    public static boolean k = false;
    public static boolean l = false;
    public static boolean sC = false;

    //Checkpoints for typing speed
    private int startTime;
    private int intervalStartTime;

    public ExerciseController() {
        //Initialize
        exerciseThread = new Thread(this, "Exercise Thread");

        running = true;

    }//end controller ExerciseController

    public void initialize(URL url, ResourceBundle rb)  {
        exerciseThread.start();

    }//end initialize()

    public void run() {
        //Output instructions
        exerciseText.setText("Please hold down keys" + " before starting.");

        try {
            while(running) {
                if(checkKeyPressed())
                    startType();

                exerciseThread.sleep(100);

            }

        }catch(Exception e) {
            e.printStackTrace();

        }

    }//end run()

    public boolean checkKeyPressed() {
        if(a && s && d && f && j && k && l && sC)
            return true;

        return false;

    }//end checkKeyPressed()

    //Only run at start
    private void startType() {
        Platform.runLater(() -> {
            //Countdown
            for(int i = 3; i > 0; i--) {
                //System.out.println(i);
                for(int j = 0; j < 2; j++) {
                    ready[j].setText(String.valueOf(i));

                }

                //Wait 1 second
                try {
                    exerciseThread.sleep(1000);

                }catch(Exception e) {
                    e.printStackTrace();

                }

            }

            //System.out.println("Start");
            readyOne.setText("Start");
            readyTwo.setText("Start");

        });

    }//end startType

    private long updateCurrentWPM() {
        long timeNow = System.currentTimeMillis();

        //Return seconds
        return (timeNow - intervalStartTime) / 1000;

    }//end updateCurrentWPM()

    private long updateTotalWPM() {
        long timeNow = System.currentTimeMillis();

        //Return seconds
        return (timeNow - startTime) / 1000;

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
