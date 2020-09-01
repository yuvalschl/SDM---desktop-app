package header;

import appController.AppController;
import appController.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import showStores.ShowStoresController;

public class HeaderController {


    private AppController appController;

    @FXML
    private Button loadXmlButton;
    @FXML
    private Button mainMenuButton;

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    void loadXmlAction() {
        appController.loadXmlAction();
    }

    @FXML
    void goToMainMenuAction(){
        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/showStores/showStores.fxml"));
        //fxmlLoader.setController(new ShowStoresController(appController));
        //Parent root = fxmlLoader.load();
        //Scene scene = new Scene(root, 600, 400);
        Main.getPrimaryStage().setScene(Main.getMainMenu());
    }

}
