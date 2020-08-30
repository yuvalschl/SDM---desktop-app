package optionsMenu;

import appController.AppController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class OptionsMenuController {

    private AppController appController;

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    private Button showStores;
    @FXML
    private Button placeOrder;

    @FXML
    public void showStoresAction() {
        appController.showStoresAction();
    }



    @FXML
    public void placeOrderAction() throws IOException {
        Stage orderStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/orderScreen/orderScreen.fxml"));
        orderStage.setTitle("orderScreen");
        orderStage.setScene(new Scene(root, 600, 400));

        orderStage.show();
    }

}
