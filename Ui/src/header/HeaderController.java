package header;

import appController.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HeaderController {


    private AppController appController;

    @FXML
    private Button loadXmlButton;

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    void loadXmlAction() {
        appController.loadXmlAction();
    }

}
