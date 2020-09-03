package showStores.storeInfo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
    private Label shippingPaymentLabel;
    @FXML
    private ListView<String> itemsListView;
/*    private StringProperty storeName;
    private StringProperty PPK;
    private StringProperty storeId;
    private StringProperty shippingCost;*/

    public Label getStoreNameLabel() {
        return storeNameLabel;
    }

    public void setStoreNameLabel(Label storeNameLabel) {
        this.storeNameLabel = storeNameLabel;
    }

    public Label getStoreIdLabel() {
        return storeIdLabel;
    }

    public void setStoreIdLabel(Label storeIdLabel) {
        this.storeIdLabel = storeIdLabel;
    }

    public Label getPPKLabel() {
        return PPKLabel;
    }

    public void setPPKLabel(Label PPKLabel) {
        this.PPKLabel = PPKLabel;
    }

    public Label getShippingPaymentLabel() {
        return shippingPaymentLabel;
    }

    public void setShippingPaymentLabel(Label shippingPaymentLabel) {
        this.shippingPaymentLabel = shippingPaymentLabel;
    }

    public ListView<String> getItemsListView() {
        return itemsListView;
    }

/*    @FXML
    public void initialize(){
        storeName = new SimpleStringProperty();
        PPK = new SimpleStringProperty();
        storeId = new SimpleStringProperty();
        shippingCost = new SimpleStringProperty();
    }*/



    public void setShowStoresController(ShowStoresController showStoresController) {
        this.showStoresController = showStoresController;
    }
}
