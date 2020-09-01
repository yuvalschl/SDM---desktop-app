package appController;

import appController.AppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage primaryStage;
    private static Scene mainMenu;

    @Override
    public void start(Stage startPrimaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("app.fxml"));
        startPrimaryStage.setTitle("SDM");
        startPrimaryStage.setScene(new Scene(root, 600, 400));
        primaryStage = startPrimaryStage;
        mainMenu = startPrimaryStage.getScene();
        startPrimaryStage.show();
    }

    public static Scene getMainMenu() {
        return mainMenu;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
