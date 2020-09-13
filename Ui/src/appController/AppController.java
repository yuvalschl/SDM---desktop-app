package appController;

import Home.HomeController;
import StoreManager.StoreManager;
import addStore.AddStoreController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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
    @FXML private AnchorPane homeComponent;
    @FXML private HomeController homeComponentController;
    @FXML private SplitPane orderScreen;
    @FXML private OrderScreenController orderScreenController;
    @FXML private ShowItemsController showItemsController;
    @FXML private VBox showItems;
    @FXML private AnchorPane addStoreComponent;
    @FXML private AddStoreController addStoreComponentController;
    private BooleanProperty xmlLoaded = new SimpleBooleanProperty(true);

    @FXML
    public void initialize() throws IOException {
        optionsMenuComponentController.setAppController(this);
        homeComponentController.setAppController(this);
        optionsMenuComponentController.getShowItemsButton().disableProperty().bind(getXmlLoaded());
        optionsMenuComponentController.getPlaceOrderButton().disableProperty().bind(getXmlLoaded());
        optionsMenuComponentController.getShowStoresButton().disableProperty().bind(getXmlLoaded());
    }

    public VBox getShowItems() {
        return showItems;
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

    public SplitPane getOrderScreenComponent() {
        return orderScreen;
    }

    public void setOrderScreenComponent(SplitPane orderScreenComponent) {
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

    public AnchorPane getAddStoreComponent() {
        return addStoreComponent;
    }

    public void setAddStoreComponent(AnchorPane addStoreComponent) {
        this.addStoreComponent = addStoreComponent;
    }

    public AddStoreController getAddStoreComponentController() {
        return addStoreComponentController;
    }

    public void setAddStoreComponentController(AddStoreController addStoreComponentController) {
        this.addStoreComponentController = addStoreComponentController;
    }

    public AnchorPane getHomeComponent() {
        return homeComponent;
    }

    public OptionsMenuController getOptionsMenuComponentController() {
        return optionsMenuComponentController;
    }

    public void setOptionsMenuComponentController(OptionsMenuController optionsMenuComponentController) {
        this.optionsMenuComponentController = optionsMenuComponentController;
    }

    public void setHomeComponent(AnchorPane homeComponent) {
        this.homeComponent = homeComponent;
    }

    public BooleanProperty getXmlLoaded() {
        return xmlLoaded;
    }

    public StoreManager getStoreManager() {
        return storeManager;
    }

    public void setStoreManager(StoreManager convertJaxbClassToStoreManager) {
        this.storeManager = convertJaxbClassToStoreManager;
    }
}
