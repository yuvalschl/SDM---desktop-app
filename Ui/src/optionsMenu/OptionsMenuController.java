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
import showItems.ShowItemsController;
import showStores.ShowStoresController;

import java.io.IOException;

public class OptionsMenuController {

    private AppController appController;

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    private Button showStoresButton;

    @FXML
    private Button placeOrderButton;

    @FXML
    private Button showItemsButton;

    public Button getShowItemsButton() {
        return showItemsButton;
    }

    public void setShowItemsButton(Button showItemsButton) {
        this.showItemsButton = showItemsButton;
    }

    public Button getShowStoresButton() {
        return showStoresButton;
    }

    public void setShowStoresButton(Button showStoresButton) {
        this.showStoresButton = showStoresButton;
    }

    public Button getPlaceOrderButton() {
        return placeOrderButton;
    }

    public void setPlaceOrderButton(Button placeOrderButton) {
        this.placeOrderButton = placeOrderButton;
    }

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

    @FXML
    public void showItemsAction()throws IOException{

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/showItems/showItems.fxml"));
        fxmlLoader.setController(new ShowItemsController(appController));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 400);
        Main.getPrimaryStage().setScene(scene);
    }
}
