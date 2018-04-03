package future.type;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("readyTemplates/LoginScreen.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 400));
        this.primaryStage = primaryStage;
        this.primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
