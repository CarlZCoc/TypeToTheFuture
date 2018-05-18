package game;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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

public class LanotypeController implements Runnable, Initializable{

    @FXML AnchorPane anchor = new AnchorPane();
    @FXML GridPane grid = new GridPane();

    private List<StackPane> stack = new ArrayList<StackPane>();
    private List<Circle> circle = new ArrayList<Circle>();
    private List<Text> text = new ArrayList<Text>();

    private String randomChars = "abcdefghijklmnopqrstuvwxyz";

    //Lives
    private Image image = new Image("file:res/Heart.png");
    private ImageView[] lifeImage = new ImageView[3];
    private int lives = 3;

    private int speed = 1000;

    @FXML private Label scoreLabel;
    private int score = 0;
    private int scoreIncrement = 100;

    //Data for balls
    private int rad = 20;

    private Thread lanoThread = new Thread(this, "Lanotype Thread");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Set lines to visible
        grid.setGridLinesVisible(true);

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
        lanoThread.start();

    }//end initialize(URL url, ResourceBundle rb)

    public void run() {

        while(true) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    stack.add(new StackPane());
                    circle.add(new Circle());
                    text.add(new Text());

                    text.get(text.size() - 1).setText(String.valueOf(randomChars.charAt((int)(Math.random() * randomChars.length()))));
                    text.get(text.size() - 1).setFont(new Font("Arial", 30));
                    text.get(text.size() - 1).setFill(Color.YELLOW);
                    text.get(text.size() - 1).setBoundsType(TextBoundsType.VISUAL);

                    circle.get(circle.size() - 1).setRadius(rad);
                    circle.get(circle.size() - 1).setCenterX(75);
                    circle.get(circle.size() - 1).setCenterY(- (rad*2));
                    circle.get(circle.size() - 1).setFill(Color.CHOCOLATE);

                    stack.get(stack.size() - 1).setLayoutX(75 - rad + ((int) (Math.random() * 4)) * 150);
                    stack.get(stack.size() - 1).setLayoutY(- (rad*2));

                    stack.get(stack.size() - 1).getChildren().addAll(circle.get(circle.size() - 1), text.get(text.size() - 1));
                    anchor.getChildren().add(stack.get(stack.size() - 1));

                    TranslateTransition transition = new TranslateTransition();
                    transition.setDuration(Duration.seconds(3));
                    transition.setToY(400 + rad * 2);
                    transition.setNode(stack.get(stack.size() - 1));

                    transition.play();

                    //Temporary list size
                    int tempStackSize = stack.size();

                    grid.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event) {
                            for(int i = 0; i < circle.size(); i++) {
                                //Get bounds of circle relative to scene
                                Bounds bound = circle.get(i).localToScene(circle.get(i).getBoundsInLocal());

                                if((bound.getMinY() >= 250 && bound.getMaxY() <= 300) && event.getText().compareTo(text.get(i).getText()) == 0) {
                                    //Add to score
                                    score += scoreIncrement;
                                    //Increment score addition
                                    scoreIncrement += 20;

                                    //Remove the StackPane, Circle, and Text (From scene too)
                                    anchor.getChildren().remove(stack.get(i));
                                    stack.remove(stack.get(i));
                                    circle.remove(circle.get(i));
                                    text.remove(text.get(i));

                                    //Increase speed
                                    if(speed != 100)
                                        speed -= 20;

                                }else if((bound.getMinY() <= 250 && bound.getMaxY() >= 250) || (bound.getMinY() <= 300 && bound.getMaxY() >= 300) && event.getText().compareTo(text.get(i).getText()) == 0) {
                                    //Add to score
                                    score += scoreIncrement;
                                    score =0;

                                    //Remove the StackPane, Circle, and Text (From scene too)
                                    anchor.getChildren().remove(stack.get(i));
                                    stack.remove(stack.get(i));
                                    circle.remove(circle.get(i));
                                    text.remove(text.get(i));

                                    //Increase speed
                                    if(speed != 100)
                                        speed -= 20;

                                }else {
                                    System.out.println("You failed");
                                    //Decrease score if wrong key is pressed
                                    score -= scoreIncrement;

                                }

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        scoreLabel.setText(String.valueOf(score));

                                    }

                                });

                            }

                        }

                    });

                    //Remove the StackPane, Circle, and Text (If out of screen)
                    for(int i = 0; i < tempStackSize; i++) {
                        Bounds bound = circle.get(i).localToScene(circle.get(i).getBoundsInLocal());

                        if(bound.getMinY() >= 400) {
                            //Remove the StackPane, Circle, and Text (From scene too)
                            anchor.getChildren().remove(stack.get(i));
                            stack.remove(stack.get(i));
                            circle.remove(circle.get(i));
                            text.remove(text.get(i));

                            //Reduce lives
                            lives--;
                            anchor.getChildren().remove(lifeImage[lives]);

                            //Element of list is removed (Decrease i)
                            tempStackSize--;
                            i--;

                            //Game over
                            if(lives == 0)
                                lanoThread.stop();

                        }

                    }

                    System.out.println("Exited for loop");

                }

            });

            try {
                lanoThread.sleep(speed);

            }catch(InterruptedException e) {
                System.out.println("Thread interrupted");

            }

        }

    }//end run()

    @FXML
    private void changeToMinigameSelection(ActionEvent event) throws IOException {
        lanoThread.stop();

        Parent root = FXMLLoader.load(getClass().getResource("template/MinigameSelection.fxml"));
        Scene scene = new Scene(root);

        //Get information from the stage
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        //Update scene on stage
        stage.setScene(scene);
        stage.show();

    }//end changeToMenu(ActionEvent event)

}//end class Lanotype
