package code.control;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class CompetitionController implements Runnable, Initializable{

    private Thread competitionThread;

    @FXML private AnchorPane anchor;
    private ImageView background;

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

    private String paragraph = "Earl of March Secondary School is located somewhere in Canada, Ontario, Ottawa, Kanata, but not on knows where.";

    //Race car animation components
    @FXML private AnchorPane raceAnchor;

    private ImageView raceBackground;
    private ImageView raceBackground2;
    private ImageView finishLine;

    private ImageView userShip = new ImageView(new Image("file:Images/Ships/UserShip.png"));
    private int userWPM = 90;

    private ImageView[] comShip = new ImageView[3];
    private int comWPM[] = {0, 0, 0};

    private final int SHIP_WIDTH = 25;
    private final int SHIP_HEIGHT = 50;
    private final int SHIP_X = 32;
    private final int SHIP_Y = 100;

    public static int place;

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

        //Setting background
        background = new ImageView("file:Images/Backgrounds/competitionBackground.png");
        background.setX(-126);
        background.setY(225);
        background.setFitWidth(600);
        background.setFitHeight(179);
        raceAnchor.getChildren().add(background);

        //Set race track background
        raceBackground = new ImageView(new Image("file:Images/Backgrounds/raceBackground.jpg"));
        raceBackground.setY(-695);
        raceBackground.setPreserveRatio(true);
        raceBackground.setFitWidth(350);
        raceAnchor.getChildren().add(raceBackground);
        raceBackground.toBack();

        raceBackground2 = new ImageView(new Image("file:Images/Backgrounds/raceBackground.jpg"));
        raceBackground2.setY(0);
        raceBackground2.setPreserveRatio(true);
        raceBackground2.setFitWidth(350);
        raceAnchor.getChildren().add(raceBackground2);
        raceBackground2.toBack();

        finishLine = new ImageView(new Image("file:Images/Backgrounds/finishLine.png"));
        finishLine.setY(0);
        finishLine.setPreserveRatio(true);
        finishLine.setFitWidth(350);

        //Car images
        userShip.setX(SHIP_X);
        userShip.setY(SHIP_Y);
        userShip.setFitWidth(SHIP_WIDTH);
        userShip.setFitHeight(SHIP_HEIGHT);
        raceAnchor.getChildren().add(userShip);
        userShip.toFront();

        for(int i = 0; i < comShip.length; i++) {
            comShip[i] = new ImageView(new Image("file:Images/Ships/ComShip.png"));
            comShip[i].setX(SHIP_X + (i + 1) * 86);
            comShip[i].setY(SHIP_Y);
            comShip[i].setFitWidth(SHIP_WIDTH);
            comShip[i].setFitHeight(SHIP_HEIGHT);
            raceAnchor.getChildren().add(comShip[i]);
            comShip[i].toFront();

        }

        //Set original place to last
        place = 4;

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
                moveBackground();

                updateCurrentWPM();

                //Reset Current WPM values
                intervalStartTime = System.currentTimeMillis();
                intervalCharTyped = 0;

                updateTotalWPM();

                //Check for chars typed
                typing();

                //Change position of Computers
                moveCom();

                //Update WPM & CPM every 3 seconds
                competitionThread.sleep(3000);


            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }//end run()

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
                    //Start
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

    private void moveBackground() {
        TranslateTransition translate = new TranslateTransition();
        translate.setDuration(Duration.seconds(3));
        translate.setFromY(0);
        translate.setToY(695);
        translate.setNode(raceBackground);

        TranslateTransition translate2 = new TranslateTransition();
        translate2.setDuration(Duration.seconds(3));
        translate2.setFromY(0);
        translate2.setToY(695);
        translate2.setNode(raceBackground2);

        ParallelTransition parallel = new ParallelTransition(translate, translate2);
        //parallel.setInterpolator();
        parallel.play();

    }//end moveBackground()

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
                if(paragraph.length() == 0) {
                    //endRace();
                    checkPlace();

                    try {
                        changeToCompetitionResult();

                    }catch(IOException e) {
                        System.out.println("IO Exception");

                    }

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

    private void moveCom() {
        for(int i = 0; i < comShip.length; i++) {
            TranslateTransition comTrans = new TranslateTransition();
            comTrans.setDuration(Duration.seconds(3));
            comTrans.setToY(wordValue - comWPM[i]);
            comTrans.setNode(comShip[i]);
            comTrans.play();

            //comShip[i].setY(userShip.getY() + (wordValue - comWPM[i]));

            comWPM[i] =  userWPM + (int) (Math.random() * 11) - 5;


        }

    }

    private void checkPlace() {
        //Check y value of all ships
        for(int i = 0; i < comShip.length; i++) {
            if(wordValue >= comWPM[i]) {
                place--;

            }

        }

    }//end checkPlace()

    private void endRace() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                raceAnchor.getChildren().add(finishLine);
                finishLine.toFront();

            }

        });


                /*

                System.out.println(userShip.getY());

                while(userShip.getY() >= 0) {
                    userShip.setY(userShip.getY() - 1);

                    for(int i = 0; i < comShip.length; i++) {
                        comShip[i].setY(comShip[i].getY() - 1);

                    }

                    */

        try {
            competitionThread.sleep(100);

        }catch(InterruptedException e) {
            System.out.println("Interrupted Exception");

        }

    }//end endRace

    @FXML
    private void changeToCompetitionSelection(ActionEvent event) throws IOException {
        competitionThread.stop();

        Parent root = FXMLLoader.load(getClass().getResource("../FXML/CompetitionSelection.fxml"));
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

        Parent root = FXMLLoader.load(getClass().getResource("../FXML/MainScreen.fxml"));
        Scene scene = new Scene(root);

        //Get information from the stage
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        //Update scene on stage
        stage.setScene(scene);
        stage.show();

    }//end changeToMenu(ActionEvent event) throws IOException

    private void changeToCompetitionResult() throws IOException {
        competitionThread.stop();

        Parent root = FXMLLoader.load(getClass().getResource("../FXML/CompetitionResult.fxml"));
        Scene scene = new Scene(root);

        //Get information from the stage
        Stage stage = (Stage) anchor.getScene().getWindow();

        //Update scene on stage
        stage.setScene(scene);
        stage.show();

    }//end changeToCompetitionResult(ActionEvent event) throws IOException

}//end class CompetitionController