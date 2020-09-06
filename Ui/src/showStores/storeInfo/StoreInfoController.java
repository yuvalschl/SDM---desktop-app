package showStores.storeInfo;

import Item.*;
import Order.StoreOrder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import showStores.ShowStoresController;
import showStores.storeInfo.itemInfo.ItemInfoController;
import showStores.storeInfo.orderInfo.OrderInfoController;

public class StoreInfoController {

    private ShowStoresController showStoresController;

    @FXML
    private ListView<Item> itemsListView;
    @FXML
    private ListView<StoreOrder> ordersListView;
    @FXML
    private VBox itemInfo;
    @FXML
    private VBox orderInfoComponent;
    @FXML
    private ItemInfoController itemInfoController;
    @FXML
    private OrderInfoController orderInfoComponentController;
    @FXML
    private Label storeNameLabel;
    @FXML
    private Label storeIdLabel;
    @FXML
    private Label PPKLabel;
    @FXML
    private Label shippingPaymentLabel;

    @FXML
    public void initialize(){
        itemInfoController.setStoreInfoController(this);

        itemsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Item>() {
            @Override
            public void changed(ObservableValue<? extends Item> observable, Item oldValue, Item newValue) {
                if (newValue != null){
                    String sellBy = newValue instanceof UnitItem ? "Unit" : "Weight";
                    itemInfoController.getItemNameLabel().setText(newValue.getName());
                    itemInfoController.getItmeIdLabel().setText(String.valueOf(newValue.getId()));
                    itemInfoController.getAmountSoldLabel().setText(String.valueOf(newValue.getAmountSold()));
                    itemInfoController.getPricePerUnitLabel().setText(String.valueOf(newValue.getPrice()));
                    itemInfoController.getItemSellByLabel().setText(sellBy);

                }

            }
        });
    }

    public ItemInfoController getItemInfoController() {
        return itemInfoController;
    }

    public void setItemInfoController(ItemInfoController itemInfoController) {
        this.itemInfoController = itemInfoController;
    }

    public OrderInfoController getOrderInfoComponentController() {
        return orderInfoComponentController;
    }

    public void setOrderInfoComponentController(OrderInfoController orderInfoComponentController) {
        this.orderInfoComponentController = orderInfoComponentController;
    }

    public ListView<Item> getItemsListView() {
        return itemsListView;
    }

    public void setItemsListView(ListView<Item> itemsListView) {
        this.itemsListView = itemsListView;
    }

    public ListView<StoreOrder> getOrdersListView() {
        return ordersListView;
    }

    public void setOrdersListView(ListView<StoreOrder> ordersListView) {
        this.ordersListView = ordersListView;
    }

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

    public void setShowStoresController(ShowStoresController showStoresController) {
        this.showStoresController = showStoresController;
    }
}
