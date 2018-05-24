package game;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class CompetitionController implements Runnable, Initializable{

    private Thread competitionThread;

    @FXML private AnchorPane anchor;

    @FXML private Label competitionText;
    @FXML private TextField competitionTextfield;

    @FXML private Button back;
    @FXML private Button menu;

    //Total WPM variables
    @FXML private Label tWPM;
    private long intervalStartTime;
    public static int charTyped;
    public static int wordValue;
    public static double minutes;

    //Current WPM variables
    @FXML private Label cWPM;
    private long startTime;
    private int intervalCharTyped;
    private int intervalWordValue;

    public static List<Integer> charTypedData = new ArrayList<>();

    @FXML private Label readyOne;
    @FXML private Label readyTwo;

    //Holds index for which number to display in countdown
    private int indexTemp;

    private String paragraph = "Hello how are you my name is Carl Zhang.Hello how are you my name is Carl Zhang.Hello how are you my name is Carl Zhang.Hello how are you my name is Carl Zhang.Hello how are you my name is Carl Zhang.Hello how are you my name is Carl Zhang.Hello how are you my name is Carl Zhang.";

    public void initialize(URL url, ResourceBundle rb)  {
        //Prevent going back and menu when typing
        back.addEventFilter(KeyEvent.KEY_PRESSED, k -> {
            if (k.getCode() == KeyCode.SPACE){
                k.consume();

            }

        });

        menu.addEventFilter(KeyEvent.KEY_PRESSED, k -> {
            if (k.getCode() == KeyCode.SPACE){
                k.consume();

            }

        });

        //Typing thread
        competitionThread = new Thread(this, "Competition Thread");
        competitionThread.start();

    }//end initialize()

    public void run() {
        //Set output display text
        competitionText.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        competitionText.setText(paragraph);

        try {
            //Countdown
            startType();

            //Setting Total WPM values
            startTime = System.currentTimeMillis();
            charTyped = 0;

            //Set Current WPM values
            intervalStartTime = System.currentTimeMillis();
            intervalCharTyped = 0;

            while (true) {
                updateCurrentWPM();

                //Reset Current WPM values
                intervalStartTime = System.currentTimeMillis();
                intervalCharTyped = 0;

                updateTotalWPM();
                typing();

                competitionThread.sleep(3000);

            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }//end run()

    //Only run at start (Countdown)
    private void startType() {
        try {
            //Countdown
            for(int i = 5; i > 0; i--) {
                indexTemp = i;

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        readyOne.setText(String.valueOf(indexTemp));
                        readyTwo.setText(String.valueOf(indexTemp));

                    }
                });

                //Wait 1 sec
                competitionThread.sleep(1000);

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

    private void typing() {
        //Check KeyPressed matches
        competitionTextfield.setOnKeyPressed(keyEvent -> {
            //Set text
            competitionText.setText(paragraph);

            if(keyEvent.getText().compareTo(String.valueOf(paragraph.charAt(0))) == 0) {
                //Update String
                paragraph = paragraph.substring(1, paragraph.length());

                competitionText.setText(paragraph);
                //Increment num of chars typed
                intervalCharTyped += 1;
                charTyped += 1;

                //Stop thread
                if(paragraph.length() == 0)
                    try {
                        changeToCompetitionResults();

                    }catch(IOException e) {
                        System.out.println("IO Exception");

                    }

            }

        });

    }//end typing()

    private void updateCurrentWPM() {
        long timeNow = System.currentTimeMillis();
        double seconds = (timeNow - intervalStartTime) / 1000;
        double minutes = seconds / 60;

        double intervalWords = intervalCharTyped / 5.0;
        intervalWordValue = (int) Math.round(intervalWords / minutes);
        charTypedData.add(intervalWordValue);

        //Update label (Through thread)
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                cWPM.setText(String.valueOf(intervalWordValue));

            }
        });

    }//end updateCurrentWPM()

    private void updateTotalWPM() {
        long timeNow = System.currentTimeMillis();
        double seconds = (timeNow - startTime) / 1000;
        minutes = seconds / 60;

        double words = charTyped / 5.0;
        wordValue = (int) Math.round(words / minutes);

        //Update label (Through thread)
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tWPM.setText(String.valueOf(wordValue));

            }
        });

    }//end updateTotalWPM

    @FXML
    private void changeToCompetitionSelection(ActionEvent event) throws IOException {
        competitionThread.stop();

        Parent root = FXMLLoader.load(getClass().getResource("template/CompetitionSelection.fxml"));
        Scene scene = new Scene(root);

        //Get information from the stage
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        //Update scene on stage
        stage.setScene(scene);
        stage.show();

    }//end changeToCompetitionSelection(ActionEvent event) throws IOException

    @FXML
    private void changeToMenu(ActionEvent event) throws IOException {
        competitionThread.stop();

        Parent root = FXMLLoader.load(getClass().getResource("template/Menu.fxml"));
        Scene scene = new Scene(root);

        //Get information from the stage
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        //Update scene on stage
        stage.setScene(scene);
        stage.show();

    }//end changeToMenu(ActionEvent event) throws IOException

    private void changeToCompetitionResults() throws IOException {
        competitionThread.stop();

        Parent root = FXMLLoader.load(getClass().getResource("template/CompetitionResult.fxml"));
        Scene scene = new Scene(root);

        //Get information from the stage
        Stage stage = (Stage) anchor.getScene().getWindow();

        //Update scene on stage
        stage.setScene(scene);
        stage.show();

    }//end changeToCompetitionResults(ActionEvent event) throws IOException

}//end class CompetitionController
