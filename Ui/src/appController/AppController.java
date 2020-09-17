package appController;

import Home.HomeController;
import ShowCustomer.ShowCustomerController;
import ShowHistory.ShowOrdersController;
import StoreManager.StoreManager;
import addStore.AddStoreController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;
import optionsMenu.OptionsMenuController;
import orderScreen.OrderScreenController;
import showItems.ShowItemsController;
import showStores.ShowStoresController;

import java.io.IOException;

public class AppController {
    private StoreManager storeManager;

    @FXML private VBox optionsMenuComponent;
    @FXML private OptionsMenuController optionsMenuComponentController;
    @FXML private SplitPane showStoresComponent;
    @FXML private ShowStoresController showStoresComponentController;
    @FXML private SplitPane ShowOrdersScreenComponent;
    @FXML private ShowOrdersController ShowOrdersScreenComponentController;
    @FXML private Pane homeComponent;
    @FXML private HomeController homeComponentController;
    @FXML private StackPane orderScreen;
    @FXML private OrderScreenController orderScreenController;
    @FXML private ShowItemsController showItemsController;
    @FXML private VBox showItems;
    @FXML private GridPane addStoreComponent;
    @FXML private AddStoreController addStoreComponentController;
    @FXML private VBox showCostumerScreen;
    @FXML private ShowCustomerController showCostumerScreenController;
    private BooleanProperty xmlLoaded = new SimpleBooleanProperty(true);

    @FXML
    public void initialize() throws IOException {
        optionsMenuComponentController.setAppController(this);
        homeComponentController.setAppController(this);
        ShowOrdersScreenComponentController.setAppController(this);
        showCostumerScreenController.setAppController(this);
        optionsMenuComponentController.getShowItemsButton().disableProperty().bind(getXmlLoaded());
        optionsMenuComponentController.getPlaceOrderButton().disableProperty().bind(getXmlLoaded());
        optionsMenuComponentController.getShowStoresButton().disableProperty().bind(getXmlLoaded());
        optionsMenuComponentController.getAddStoreButton().disableProperty().bind(getXmlLoaded());
        optionsMenuComponentController.getShowOrderHistory().disableProperty().bind(getXmlLoaded());
        optionsMenuComponentController.getShowCustomersBtn().disableProperty().bind(getXmlLoaded());
    }


    public VBox getShowCostumerScreen() { return showCostumerScreen; }

    public void setShowCostumerScreen(VBox showCostumerScreen) { this.showCostumerScreen = showCostumerScreen; }

    public ShowCustomerController getShowCostumerScreenController() { return showCostumerScreenController; }

    public void setShowCostumerScreenController(ShowCustomerController showCostumerScreenController) { this.showCostumerScreenController = showCostumerScreenController; }

    public HomeController getHomeComponentController() {
        return homeComponentController;
    }

    public VBox getShowItems() {
        return showItems;
    }

    public SplitPane getShowOrdersScreen() {
        return ShowOrdersScreenComponent;
    }

    public void setShowOrdersScreen(SplitPane showOrdersScreen) {
        ShowOrdersScreenComponent = showOrdersScreen;
    }

    public ShowOrdersController getShowOrdersController() {
        return ShowOrdersScreenComponentController;
    }

    public void setShowOrdersController(ShowOrdersController showOrdersController) {
        this.ShowOrdersScreenComponentController = showOrdersController;
    }

    public void setShowItems(VBox showItems) {
        this.showItems = showItems;
    }

    public ShowItemsController getShowItemsController() {
        return showItemsController;
    }

    public void setShowItemsController(ShowItemsController showItemsController) {
        this.showItemsController = showItemsController;
    }

    public StackPane getOrderScreenComponent() {
        return orderScreen;
    }

    public void setOrderScreenComponent(StackPane orderScreenComponent) {
        this.orderScreen = orderScreenComponent;
    }

    public OrderScreenController getOrderScreenComponentController() {
        return orderScreenController;
    }

    public void setOrderScreenComponentController(OrderScreenController orderScreenComponentController) {
        this.orderScreenController = orderScreenComponentController;
    }

    public SplitPane getShowStoresComponent() {
        return showStoresComponent;
    }

    public ShowStoresController getShowStoresComponentController() {
        return showStoresComponentController;
    }

    public void setShowStoresComponent(SplitPane showStoresComponent) {
        this.showStoresComponent = showStoresComponent;
    }

    public GridPane getAddStoreComponent() {
        return addStoreComponent;
    }

    public void setAddStoreComponent(GridPane addStoreComponent) {
        this.addStoreComponent = addStoreComponent;
    }

    public AddStoreController getAddStoreComponentController() {
        return addStoreComponentController;
    }

    public void setAddStoreComponentController(AddStoreController addStoreComponentController) {
        this.addStoreComponentController = addStoreComponentController;
    }

    public Pane getHomeComponent() {
        return homeComponent;
    }

    public OptionsMenuController getOptionsMenuComponentController() {
        return optionsMenuComponentController;
    }

    public void setOptionsMenuComponentController(OptionsMenuController optionsMenuComponentController) {
        this.optionsMenuComponentController = optionsMenuComponentController;
    }

    public void setHomeComponent(VBox homeComponent) {
        this.homeComponent = homeComponent;
    }

    public BooleanProperty getXmlLoaded() {
        return xmlLoaded;
    }

    public StoreManager getStoreManager() {
        return storeManager;
    }


    public void setStoreManager(StoreManager storeManager) {
        this.storeManager = storeManager;
    }

}
