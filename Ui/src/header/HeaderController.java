package header;

import appController.AppController;
import appController.Main;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
        appController.getXmlLoaded().setValue(false);
    }

    @FXML
    void goToMainMenuAction(){
        Main.getPrimaryStage().setScene(Main.getMainMenu());
    }

}
