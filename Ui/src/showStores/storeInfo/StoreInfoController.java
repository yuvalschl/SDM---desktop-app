package showStores.storeInfo;

import appController.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import showStores.ShowStoresController;

public class StoreInfoController {

    private ShowStoresController showStoresController;

    @FXML
    private Label storeNameLabel;

    @FXML
    private Label storeIdLabel;

    @FXML
    private Label PPKLabel;

    @FXML
    private Label shippingCostLabel;

    @FXML
    private ListView<String> itemsListView;

    public void setShowStoresController(ShowStoresController showStoresController) {
        this.showStoresController = showStoresController;
    }
}
