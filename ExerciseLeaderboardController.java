package game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ExerciseLeaderboardController {

    @FXML ScrollPane scroll = new ScrollPane();

    @FXML Label userOne, userTwo, userThree, userFour, userFive;
    @FXML Label scoreOne, scoreTwo, scoreThree, scoreFour, scoreFive;

    @FXML Label[] users = {userOne, userTwo, userThree, userFour, userFive};
    @FXML Label[] scores = {scoreOne, scoreTwo, scoreThree, scoreFour, scoreFive};

    public ExerciseLeaderboardController() {
        for(int i = 0 ; i < users.length; i++) {
            //users[i].setText("HI");

        }

        for(int j = 0; j < scores.length; j++) {
            //scores[j].setText("HI");

        }

    }//end controller ExerciseLeaderboardController()

    @FXML
    private void changeToExerciseSelection(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("template/ExerciseSelection.fxml"));
        Scene scene = new Scene(root);

        //Get information from the stage
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        //Update scene on stage
        stage.setScene(scene);
        stage.show();

    }//end changeToExerciseSelection(ActionEvent event)

}//end class exerciseLeaderboardController
