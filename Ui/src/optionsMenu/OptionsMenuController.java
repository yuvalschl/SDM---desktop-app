package optionsMenu;

import appController.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class OptionsMenuController {

    private AppController appController;

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML private Button showStoresButton;
    @FXML private Button placeOrderButton;
    @FXML private Button showItemsButton;
    @FXML private Button addStoreButton;
    @FXML private Button showOrderHistory;
    @FXML private  Button homeButton;
    @FXML private  Button showCustomersBtn;



    public Button getShowOrderHistory() { return showOrderHistory; }

    public void setShowOrderHistory(Button showOrderHistory) { this.showOrderHistory = showOrderHistory; }

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

    public Button getShowCustomersBtn() { return showCustomersBtn;}

    public void setShowCustomersBtn(Button showCustomersBtn) { this.showCustomersBtn = showCustomersBtn;}

    @FXML
    public void showStoresAction() throws IOException {
        disablePanes();
        appController.getShowStoresComponentController().getItemsTable().getSelectionModel().clearSelection();
        appController.getShowStoresComponentController().getItemsTable().refresh();
        appController.getShowStoresComponentController().getStoresListView().getSelectionModel().clearSelection();
        appController.getShowStoresComponent().setVisible(true);
    }
    @FXML
    public void homeButtonAction(){
        disablePanes();
        appController.getHomeComponent().setVisible(true);

    }

    public Button getAddStoreButton() {
        return addStoreButton;
    }

    public void setAddStoreButton(Button addStoreButton) {
        this.addStoreButton = addStoreButton;
    }

    @FXML
    void addStoreAction() {
        disablePanes();
        appController.getAddStoreComponent().setVisible(true);
    }

    @FXML
    public void placeOrderAction() throws IOException {
        disablePanes();
        appController.getOrderScreenComponentController().getDiscountScreen().setVisible(false);
        appController.getOrderScreenComponent().setVisible(true);
    }

    @FXML
    public void showItemsAction()throws IOException{
        disablePanes();
        appController.getShowItems().setVisible(true);
    }

    @FXML
    public void showOrderHistoryAction(ActionEvent actionEvent) {
        disablePanes();
        appController.getShowOrdersScreen().setVisible(true);

    }

    private void disablePanes(){
        StackPane stackPane = (StackPane)appController.getOrderScreenComponent().getParent();
        stackPane.getChildren().forEach(c -> c.setVisible(false));
    }


    public void showCustomersBtnAction(ActionEvent actionEvent) {
        disablePanes();
        appController.getShowCostumerScreen().setVisible(true);
    }
}
