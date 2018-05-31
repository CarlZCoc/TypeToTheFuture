package code.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CompetitionSelectionController implements Initializable{

    private @FXML Button menu;
    private ImageView menuButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menuButton = new ImageView(new Image("file:Images/BackButton.png"));
        menuButton.setFitWidth(50);
        menuButton.setFitHeight(50);
        menu.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
        menu.setGraphic(menuButton);

    }//end initialize(URL location, ResourceBundle resources)

    @FXML
    private void changeToMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/MainScreen.fxml"));
        Scene scene = new Scene(root);

        //Get information from the stage
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        //Update scene on stage
        stage.setScene(scene);
        stage.show();

    }//end changeToMenu(ActionEvent event) throws IOException

    @FXML
    private void changeToCompetition(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Competition.fxml"));
        Scene scene = new Scene(root);

        //Get information from the stage
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        //Update scene on stage
        stage.setScene(scene);
        stage.show();

    }//end changeToCompetition(ActionEvent event) throws IOException

    @FXML
    private void changeToLeaderboard(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Leaderboard.fxml"));
        Scene scene = new Scene(root);

        //Get information from the stage
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        //Update scene on stage
        stage.setScene(scene);
        stage.show();

    }//end changeToLeaderboard(ActionEvent event) throws IOException

}//end class CompetitionSelectionController
