package ShowHistory;

import Item.*;
import ItemPair.ItemAmountAndStore;
import Order.Order;
import Order.StoreOrder;
import Store.Store;
import appController.AppController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import showItems.ItemListCellController;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class DisplaySingleOrderController {
    @FXML
    private ListView<ItemAmountAndStore> itemsListView;

    @FXML private TableView<StoreOrder> storesTable;
    @FXML private TableColumn<StoreOrder,Integer> storeIdCol;
    @FXML private TableColumn<StoreOrder, String> storeNameCol;
    @FXML private TableColumn<StoreOrder, Float> storeDistanceToClientcol;
    @FXML private TableColumn<StoreOrder, Float> storePkkCol;
    @FXML private TableColumn<StoreOrder, Float> storeShippingCostCol;
    private AppController appController;
    private DecimalFormat decimalFormat;

    public ListView<ItemAmountAndStore> getItemsListView() {
        return itemsListView;
    }

    public TableView<StoreOrder> getStoresTable() {
        return storesTable;
    }

    @FXML
    public void initialize(){
        //set the StoreTable
        storeNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        storeIdCol.setCellValueFactory(new PropertyValueFactory<>("storeID"));
        storeDistanceToClientcol.setCellValueFactory(new PropertyValueFactory<>("distance"));
        storePkkCol.setCellValueFactory(new PropertyValueFactory<>("pkk"));
        storeShippingCostCol.setCellValueFactory(new PropertyValueFactory<>("shippingCost"));

       }

    public void setData(AppController appController, HashMap<Integer, ItemAmountAndStore> itemAmountAndStore, Order order, Point costumerLocation) {

        decimalFormat = appController.getStoreManager().getDecimalFormat();

        //set the item list\
        itemsListView.getItems().clear();
        itemsListView.getItems().addAll( itemAmountAndStore.values());
        itemsListView.setCellFactory(e -> new OrderItemCellController(appController));

        //goes through the shippingCostByStore and for every store takes the needed values and puts them in the store table
        order.getShippingCostByStore().forEach((storeID,shippingCost)->{
            Store store = appController.getStoreManager().getAllStores().get(storeID);
            float distance =  Float.parseFloat(decimalFormat.format(appController.getStoreManager().distanceCalculator(costumerLocation, store.getLocation())));
            float decimalShippingCost = Float.parseFloat(decimalFormat.format(shippingCost));
            StoreOrder orderToPresent = new StoreOrder(order.getDateOfOrder(), decimalShippingCost, distance, store, order.getOrderId(),store.getPPK());
            storesTable.getItems().removeIf(currOrder-> currOrder.getStoreID() == orderToPresent.getStoreID());
            storesTable.getItems().add(orderToPresent);

        });


    }
}
