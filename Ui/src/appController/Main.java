package appController;

import StoreManager.StoreManager;
import appController.AppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.security.auth.login.Configuration;

public class Main extends Application {

    private static Stage primaryStage;
    private static Scene mainMenu;
    private Configuration configuration;

    @Override
    public void start(Stage startPrimaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("app.fxml"));
        startPrimaryStage.setTitle("SDM");
        startPrimaryStage.setScene(new Scene(root, 1200, 800));
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

    public static void main(String[] args) { launch(args);}
}
