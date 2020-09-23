package orderScreen;


import Costumer.Customer;
import ItemPair.ItemAmountAndStore;
import Order.*;
import ShowHistory.DisplaySingleOrderController;
import ShowHistory.OrderItemCellController;
import Store.Store;
import appController.AppController;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.HashMap;

//TODO: add a label that reflects the user choice for a dew seconds and desapear
public class OrderSummeryController {
    private Order order;
    private AppController appController;
    private VBox orderSummeryScreen;
    private SplitPane orderScreen;
    private OrderScreenController orderScreenController;
  // @FXML private DisplaySingleOrderController singleOrderComponentController;//delete thiss
  //  @FXML private Pane singleOrderComponent;//and this
    @FXML private Label allItemsPriceLabel;
    @FXML private  Label totalShippingCostLabel;
    @FXML private  Label totalOrderCostLabel;
    @FXML
    private ListView<ItemAmountAndStore> itemsListView;
    private DecimalFormat decimalFormat;

    @FXML private TableView<StoreOrder> storesTable;
    @FXML private TableColumn<StoreOrder,Integer> storeIdCol;
    @FXML private TableColumn<StoreOrder, String> storeNameCol;
    @FXML private TableColumn<StoreOrder, Float> storeDistanceToClientcol;
    @FXML private TableColumn<StoreOrder, Float> storePkkCol;
    @FXML private TableColumn<StoreOrder, Float> storeShippingCostCol;


    public void setData(AppController appController, Order order, Point costumerLocation, VBox summeryScreen, SplitPane orderScreen, OrderScreenController orderScreenController){
        this.order = order;
        this.appController = appController;
        this.orderSummeryScreen = summeryScreen;
        this.orderScreen = orderScreen;
        this.orderScreenController = orderScreenController;
        decimalFormat = appController.getDecimalFormat();
        setStoresTable(costumerLocation);//sets and puts all the relvent details in the store table
        decimalFormat = appController.getStoreManager().getDecimalFormat();
        //singleOrderComponentController.setData(appController, order.getItemAmountAndStores(), order, costumerLocation);// and this
        orderSummeryScreen.setVisible(true);
        orderScreenController.setInterestedInDiscount(true);
        allItemsPriceLabel.textProperty().set(String.format("%.2f", order.getTotalPriceOfItems()));
        totalShippingCostLabel.textProperty().set(String.format("%.2f",order.getShippingCost()));
        totalOrderCostLabel.textProperty().set(String.format("%.2f", order.getTotalCost()));

        //set the item list

    }

    public void approveOrderAction(javafx.event.ActionEvent actionEvent) throws InterruptedException {
        orderSummeryScreen.setVisible(false);
        orderScreenController.clearAction();
        orderScreen.setVisible(true);
        appController.getStoreManager().placeOrder(order);
        appController.getShowOrdersController().setData(appController);
        appController.getShowStoresComponentController().setData(appController);
        //update the customer details with this order
        updateCustomerDetails();
        appController.getShowCostumerScreenController().updateCustomerToShow();
        appController.getShowItemsController().updateItemsToShow();
       // singleOrderComponentController.getStoresTable().getItems().clear();
    }

    private void updateCustomerDetails(){
        Customer customer = orderScreenController.getCustomer();
        customer.setNumberOfOrdersMade(customer.getNumberOfOrdersMade()+1);
        customer.setTotalShippingCost(customer.getTotalShippingCost()+ order.getShippingCost());
        customer.setTotalOrdersWithoutShippingPrice(customer.getTotalOrdersWithoutShippingPrice()+order.getTotalPriceOfItems());
        float shippingCostAverage = customer.getTotalShippingCost()/customer.getNumberOfOrdersMade();
        float ordersWithoutShippingCostAverage = customer.getTotalOrdersWithoutShippingPrice()/customer.getNumberOfOrdersMade();
        customer.setAverageOrdersShippingPrice(shippingCostAverage);
        customer.setAverageOrdersWithoutShippingPrice(ordersWithoutShippingCostAverage);
        //TODO update show items, items amount
    }
    public void cancelOrderAction(javafx.event.ActionEvent actionEvent) {
        orderSummeryScreen.setVisible(false);
        orderScreenController.clearAction();
        orderScreen.setVisible(true);
    }
    public void  setStoresTable(Point costumerLocation){
        storeNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        storeIdCol.setCellValueFactory(new PropertyValueFactory<>("storeID"));
        storeDistanceToClientcol.setCellValueFactory(new PropertyValueFactory<>("distance"));
        storePkkCol.setCellValueFactory(new PropertyValueFactory<>("pkk"));
        storeShippingCostCol.setCellValueFactory(new PropertyValueFactory<>("shippingCost"));
        storeShippingCostCol.setCellFactory(tc -> new TableCell<StoreOrder, Float>(){
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                if(empty || item == null){
                    setText(null);
                }
                else {
                    setText(String.format("%.2f", item));
                }
            }
        });

        storesTable.getItems().clear();
        order.getShippingCostByStore().forEach((storeID,shippingCost)->{
            Store store = appController.getStoreManager().getAllStores().get(storeID);
            float distance =  Float.parseFloat(decimalFormat.format(appController.getStoreManager().distanceCalculator(costumerLocation, store.getLocation())));
            float decimalShippingCost = Float.parseFloat(decimalFormat.format(shippingCost));
            StoreOrder orderToPresent = new StoreOrder(order.getDateOfOrder(), decimalShippingCost, distance, store, order.getOrderId(),store.getPPK());
            storesTable.getItems().removeIf(currOrder-> currOrder.getStoreID() == orderToPresent.getStoreID());
            storesTable.getItems().add(orderToPresent);

        });
        storesTable.refresh();
    }

    public void displayItems(MouseEvent mouseEvent) {
        itemsListView.getItems().clear();

        if (storesTable.getSelectionModel().getSelectedItem() != null) {
            int storeID = storesTable.getSelectionModel().getSelectedItem().getStoreID();
            itemsListView.getItems().clear();
            HashMap<Integer, ItemAmountAndStore> itemsOfStore = new HashMap<>();
            order.getItemAmountAndStores().forEach((key, itemAmountAndStore)->{
                if(itemAmountAndStore.getStore().getSerialNumber() == storeID) {
                    itemsOfStore.put(key, itemAmountAndStore);
                    itemsListView.getItems().add(itemAmountAndStore);
                }
            });
            itemsListView.setCellFactory(e -> new OrderItemCellController(appController));

        }
    }
}
