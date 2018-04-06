package game.main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class SignInController {
    /*Stage primaryStage;

    public SignInController(Stage primaryStage) {
        this.primaryStage = primaryStage;

    }//end constructor SignInController(Stage primaryStage)*/

    @FXML
    public void changeToMenu(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("template/Menu.fxml"));

        Scene scene = new Scene(root);

        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }//end changeToMenu()

    private void changeToCreate() {

    }//end changeToCreate()


    private void handle(ActionEvent event) {


    }//end handle(ActionEvent event)

}//end SignInController
