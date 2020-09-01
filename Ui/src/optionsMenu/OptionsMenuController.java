package optionsMenu;

import appController.AppController;
import appController.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import orderScreen.OrderScreenController;
import showStores.ShowStoresController;

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
    public void showStoresAction() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/showStores/showStores.fxml"));
        fxmlLoader.setController(new ShowStoresController(appController));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 400);
        Main.getPrimaryStage().setScene(scene);
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
