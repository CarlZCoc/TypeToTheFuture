package game;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DestructotypeController implements Runnable, Initializable{

    private String[] words = {"Omnipresent", "Hello", "Wassup", "Carl", "Sports"};

    @FXML AnchorPane anchor = new AnchorPane();

    private List<StackPane> stack = new ArrayList<>();
    private List<Rectangle> rect = new ArrayList<>();
    private List<HBox> hBox = new ArrayList<>();
    private List<Text> text = new ArrayList<>();
    private List<Text> text2 = new ArrayList<>();

    private Image image = new Image("file:res/Heart.png");
    private ImageView[] lifeImage = new ImageView[3];
    private int lives = 3;
    private int speed = 3000;

    private Label stageLevel = new Label();
    private int stageNum = 1;
    private int wordsTypedOnStage = 0;

    private int stageX = 175;
    private int stageY = 150;

    @FXML private Label scoreLabel;
    private int score = 0;
    private int scoreIncrement = 100;

    //Data for rectangle
    private int width = 80;
    private int height = 40;

    private Thread destructoThread = new Thread(this, "Lanotype Thread");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Filter space bar
        anchor.addEventFilter(KeyEvent.KEY_PRESSED, k -> {
            if ( k.getCode() == KeyCode.SPACE){
                k.consume();

            }

        });

        //Set location of images (represent lives)
        for(int i = 0; i < lifeImage.length; i++) {
            //Add lives images
            lifeImage[i] = new ImageView(image);
            lifeImage[i].setX(0);
            lifeImage[i].setY(60 * i);
            lifeImage[i].setFitHeight(50);
            lifeImage[i].setFitWidth(50);
            anchor.getChildren().add(lifeImage[i]);

        }

        //Start thread
        destructoThread.start();

    }//end initialize(URL url, ResourceBundle rb)

    public void run() {
        //Display stage 1
        fadeText();

        while(true) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    stack.add(new StackPane());
                    rect.add(new Rectangle());
                    hBox.add(new HBox());
                    text.add(new Text());
                    text2.add(new Text());

                    rect.get(rect.size() - 1).setWidth(width);
                    rect.get(rect.size() - 1).setHeight(height);
                    rect.get(rect.size() - 1).setArcWidth(width/2);
                    rect.get(rect.size() - 1).setArcHeight(height/2);
                    rect.get(rect.size() - 1).setFill(Color.BLACK);

                    text.get(text.size() - 1).setText("");
                    text.get(text.size() - 1).setFill(Color.CORNSILK);
                    text.get(text.size() - 1).setBoundsType(TextBoundsType.VISUAL);

                    text2.get(text.size() - 1).setText(words[(int) (Math.random() * words.length)]);
                    text2.get(text2.size() - 1).setFill(Color.CORNFLOWERBLUE);
                    text2.get(text2.size() - 1).setBoundsType(TextBoundsType.VISUAL);

                    hBox.get(hBox.size() - 1).setAlignment(Pos.CENTER);
                    hBox.get(hBox.size() - 1).getChildren().addAll(text.get(text.size() - 1), text2.get(text2.size() - 1));

                    stack.get(stack.size() - 1).setLayoutX(Math.random() * 600 + 1 - width/2);
                    stack.get(stack.size() - 1).setLayoutY(-height/2);

                    stack.get(stack.size() - 1).getChildren().addAll(rect.get(rect.size() - 1), hBox.get(hBox.size() - 1));
                    anchor.getChildren().add(stack.get(stack.size() - 1));

                    //Bounds of rectangle relatie to scene
                    Bounds bound = rect.get(rect.size() - 1).localToScene(rect.get(rect.size() - 1).getBoundsInLocal());

                    TranslateTransition transition = new TranslateTransition();
                    transition.setDuration(Duration.seconds(10));
                    transition.setToX(300 - bound.getMinX() - width/2);
                    transition.setToY(400 + height);
                    transition.setNode(stack.get(stack.size() - 1));

                    transition.play();

                    //Temporary list size
                    int tempStackSize = stack.size();

                    anchor.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event) {
                            for(int i = 0; i < rect.size(); i++) {
                                if(event.getText().compareTo(String.valueOf(text2.get(i).getText().charAt(0))) == 0) {
                                    text.get(i).setText(text.get(i).getText() + String.valueOf(text2.get(i).getText().charAt(0)));
                                    text2.get(i).setText(text2.get(i).getText().substring(1, text2.get(i).getText().length()));

                                    if(text2.get(i).getText().compareTo("") == 0) {
                                        //Remove the StackPane, Rect, and Text (From scene too)
                                        anchor.getChildren().remove(stack.get(i));
                                        stack.remove(stack.get(i));
                                        rect.remove(rect.get(i));
                                        hBox.remove(hBox.get(i));
                                        text.remove(text.get(i));
                                        text2.remove(text2.get(i));

                                        if(wordsTypedOnStage == 10) {
                                            //Update Stage
                                            stageNum++;
                                            //Reset words typed (Because new stage)
                                            wordsTypedOnStage = 0;
                                            //Increase score increment
                                            scoreIncrement += 100;

                                            //Fading effect
                                            fadeText();

                                            //Increase speed
                                            if(speed != 100)
                                                speed -= 50;

                                        }else {
                                            //Increment words
                                            wordsTypedOnStage++;

                                        }

                                        //Increment score
                                        score += scoreIncrement;

                                    }

                                }

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        //Set score
                                        scoreLabel.setText(String.valueOf(score));

                                    }

                                });

                            }

                        }

                    });

                    //Remove the StackPane, Rect, and Text (If out of screen)
                    for(int i = 0; i < tempStackSize; i++) {
                        Bounds bound2 = rect.get(i).localToScene(rect.get(i).getBoundsInLocal());

                        if(bound2.getMinY() >= 400) {
                            System.out.println(bound2.getMinX());

                            //Remove the StackPane, Rect, and Text (From scene too)
                            anchor.getChildren().remove(stack.get(i));
                            stack.remove(stack.get(i));
                            rect.remove(rect.get(i));
                            text.remove(text.get(i));

                            //Reduce lives
                            lives--;
                            anchor.getChildren().remove(lifeImage[lives]);

                            //Element of list is removed (Decrease i)
                            tempStackSize--;
                            i--;

                            //Game over
                            if(lives == 0)
                                try {
                                    gameEnd();

                                }catch(IOException e) {
                                    System.out.println("IO Exception");

                                }


                        }

                    }

                }

            });

            try {
                destructoThread.sleep(speed);

            }catch(InterruptedException e) {
                System.out.println("Thread interrupted");

            }

        }

    }//end run()

    private void fadeText() {
        stageLevel.setText("Stage " + stageNum);
        stageLevel.setLayoutX(stageX);
        stageLevel.setLayoutY(stageY);
        stageLevel.setFont(new Font("Arial", 72));
        stageLevel.setAlignment(Pos.CENTER);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                anchor.getChildren().add(stageLevel);

            }
        });

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.seconds(2));
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(5.0);
        fadeTransition.setFromValue(5.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setNode(stageLevel);
        fadeTransition.play();

        anchor.getChildren().remove(stageLevel);

    }//end fadeText(int stageNum)

    @FXML
    private void changeToMinigameSelection(ActionEvent event) throws IOException {
        destructoThread.stop();

        Parent root = FXMLLoader.load(getClass().getResource("template/MinigameSelection.fxml"));
        Scene scene = new Scene(root);

        //Get information from the stage
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        //Update scene on stage
        stage.setScene(scene);
        stage.show();

    }//end changeToMinigameSelection(ActionEvent event)

    private void gameEnd() throws IOException {
        destructoThread.stop();

        Parent root = FXMLLoader.load(getClass().getResource("template/MinigameSelection.fxml"));
        Scene scene = new Scene(root);

        //Get information from the stage
        anchor.getScene().getWindow();
        Stage stage = (Stage) anchor.getScene().getWindow();

        //Update scene on stage
        stage.setScene(scene);
        stage.show();

    }

}//end class DestructotypeController