package optionsMenu;

import appController.AppController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import orderScreen.OrderScreenController;

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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/orderScreen/orderScreen.fxml"));
        fxmlLoader.setController(new OrderScreenController(appController));
        Stage orderStage = new Stage();
        Parent root = fxmlLoader.load();
        orderStage.setTitle("orderScreen");
        orderStage.setScene(new Scene(root, 600, 400));
        orderStage.show();
    }

}
