package ShowHistory;

import Item.*;
import ItemPair.ItemAmountAndStore;
import Order.Order;
import Order.StoreOrder;
import Store.Store;
import appController.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import showItems.ItemListCellController;

import java.awt.*;
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
    public void setData(AppController appController, ArrayList<ItemAmountAndStore> itemAmountAndStore, Order order, Point costumerLocation) {
        this.appController = appController;
        //set the item list\
        itemsListView.getItems().clear();
        itemsListView.getItems().addAll( itemAmountAndStore);
        itemsListView.setCellFactory(e -> new OrderItemCellController(appController));
        //set the StoreTable
        storeNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        storeIdCol.setCellValueFactory(new PropertyValueFactory<>("storeID"));
        storeDistanceToClientcol.setCellValueFactory(new PropertyValueFactory<>("distance"));
        storePkkCol.setCellValueFactory(new PropertyValueFactory<>("pkk"));
        storeShippingCostCol.setCellValueFactory(new PropertyValueFactory<>("shippingCost"));
        order.getShippingCostByStore().forEach((storeID,shippingCost)->{//goes through the shippingCostByStore and for every store takes the needed values and puts them in the store table
            Store store = appController.getStoreManager().getAllStores().get(storeID);
            float distance = appController.getStoreManager().distanceCalculator(costumerLocation, store.getLocation());
            StoreOrder orderToPresent = new StoreOrder(order.getDateOfOrder(), shippingCost, distance, store, order.getOrderId(),store.getPPK());
            storesTable.getItems().removeIf(currOrder-> currOrder.getStoreID() == orderToPresent.getStoreID());
            storesTable.getItems().add(orderToPresent);
        });


    }
}
