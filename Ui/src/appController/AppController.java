package appController;

import Home.HomeController;
import StoreManager.StoreManager;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import optionsMenu.OptionsMenuController;
import orderScreen.OrderScreenController;
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

    @FXML
    public void initialize() throws IOException {
/*        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/showStores/showStores.fxml"));
        fxmlLoader.setController(new ShowStoresController());
        fxmlLoader.load();*/
/*        FXMLLoader fxmlLoader1 = new FXMLLoader((getClass().getResource("/Home/home.fxml")));
        fxmlLoader1.setController(new HomeController());
        fxmlLoader1.load();*/
        optionsMenuComponentController.setAppController(this);
        homeComponentController.setAppController(this);
        //orderScreenComponentController.setAppController(this);
    }



    private BooleanProperty xmlLoaded = new SimpleBooleanProperty(true);

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
