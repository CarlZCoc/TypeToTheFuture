package game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.invoke.SwitchPoint;

public class ExerciseSelectionController {

    @FXML
    private void changeToExercise(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("template/Exercise.fxml"));
        Scene scene = new Scene(root);


        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent keyEvent) {

                String c = keyEvent.getCharacter();
                switch (c){
                    case "A":
                        System.out.println("A was pressed");
                        break;
                    case "a":
                        System.out.println("a was pressed");
                        break;

                }
                if (keyEvent.getCode() == KeyCode.A)
                    System.out.println("A WAS NOT PRESSED");

            }

        });

        /*

        //Detect KeyPressed
        scene.setOnKeyPressed(keyEvent -> {
            System.out.println(keyEvent.getText());
            switch (keyEvent.getCode()) {
                case A: ExerciseController.a = true;
                case S: ExerciseController.s = true;
                case D: ExerciseController.d = true;
                case F: ExerciseController.f = true;
                case J: ExerciseController.j = true;
                case K: ExerciseController.k = true;
                case L: ExerciseController.l = true;
                case SEMICOLON: ExerciseController.sC = true;
            }

        });

        //Detect KeyReleased
        scene.setOnKeyReleased(keyEvent -> {
            switch (keyEvent.getCode()) {
                case A: ExerciseController.a = false;
                case S: ExerciseController.s = false;
                case D: ExerciseController.d = false;
                case F: ExerciseController.f = false;
                case J: ExerciseController.j = false;
                case K: ExerciseController.k = false;
                case L: ExerciseController.l = false;
                case SEMICOLON: ExerciseController.sC = false;
            }

        });

        */


        //Get information from the stage
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        //Update scene on stage
        stage.setScene(scene);
        stage.show();

    }//end changeToExercise(ActionEvent event)

}//end class ExerciseSelectionController
