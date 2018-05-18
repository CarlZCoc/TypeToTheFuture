package game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MinigameSelectionController {

    @FXML
    private void lanotype(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("template/Lanotype.fxml"));
        Scene scene = new Scene(root);

        //Get information from the stage
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        //Update scene on stage
        stage.setScene(scene);
        stage.show();

    }//end changeToLanotypeController(ActionEvent event)

    @FXML
    private void destructotype(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("template/Destructotype.fxml"));
        Scene scene = new Scene(root);

        //Get information from the stage
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        //Update scene on stage
        stage.setScene(scene);
        stage.show();

    }//end changeToDestructotypeController(ActionEvent event)

    @FXML
    private void changeToMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("template/Menu.fxml"));
        Scene scene = new Scene(root);

        //Get information from the stage
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        //Update scene on stage
        stage.setScene(scene);
        stage.show();

    }//end changeToMenu(ActionEvent event)

}//end class MinigameSelectionController
