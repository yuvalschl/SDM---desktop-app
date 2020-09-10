package showStores;

import Item.Item;
import Order.*;
import Store.Store;
import appController.AppController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import listCells.storeCell.StoreListViewCell;

import java.util.ArrayList;
import java.util.Date;

public class ShowStoresController {

    private ObservableMap<Integer, Store> storesObservableMap;
    private AppController appController;

    @FXML private TableColumn<Item, String> itemNameCol;
    @FXML private TableColumn<Item, Integer> itemIdCol;
    @FXML private TableColumn<Item, Float> itemPriceCol;
    @FXML private TableColumn<Item, Integer> itemAmountSoldCol;
    @FXML private TableView<Item> itemsTable;
    @FXML private TableView<StoreOrder> ordersTable;
    @FXML private TableColumn<Order, Date> orderDateCol;
    @FXML private TableColumn<StoreOrder, Integer> orderIdCol;
    @FXML private TableColumn<StoreOrder, Integer> amountOfItemsCol;
    @FXML private TableColumn<StoreOrder, Float> itemsCostCol;
    @FXML private TableColumn<StoreOrder, Float> shippingCostCol;
    @FXML private TableColumn<StoreOrder, Float> totalCostCol;
    @FXML private ListView<Store> storesListView;


    public void setData(AppController appController){
        this.appController = appController;
        //set listener to the map of stores so the list view updates dynamically
        storesObservableMap = FXCollections.observableMap(appController.getStoreManager().getAllStores());
        storesObservableMap.addListener(new MapChangeListener<Integer, Store>() {
            @Override
            public void onChanged(Change<? extends Integer, ? extends Store> change) {
                storesListView.getItems().add(change.getValueAdded());
                appController.getStoreManager().getAllStores().put(change.getKey(), change.getValueAdded());
            }
        });

        //set the item table
        itemNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        itemIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        itemPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        itemAmountSoldCol.setCellValueFactory(new PropertyValueFactory<>("amountSold"));

        //set the order table
        orderDateCol.setCellValueFactory(new PropertyValueFactory<>("dateOfOrder"));
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        amountOfItemsCol.setCellValueFactory(new PropertyValueFactory<>("amountOfItemsCol"));
        totalCostCol.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        shippingCostCol.setCellValueFactory(new PropertyValueFactory<>("shippingCost"));
        itemsCostCol.setCellValueFactory(new PropertyValueFactory<>("totalPriceOfItems"));

        storesListView.getItems().addAll(appController.getStoreManager().getAllStores().values());
        storesListView.setCellFactory(e -> new StoreListViewCell());

        // this set the on click for the list view
        storesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Store>() {
            @Override
            public void changed(ObservableValue<? extends Store> observable, Store oldValue, Store newValue) {
                //set the items table
                itemsTable.getItems().clear();
                itemsTable.getItems().addAll(newValue.getInventory().values());
                itemsTable.scrollTo(0);

                //set the order list view
                //TODO: this may not work, check when place order is done
                ordersTable.getItems().clear();
                ordersTable.setItems(FXCollections.observableArrayList(newValue.getAllOrders()));
                itemsTable.scrollTo(0);


            }
        });
    }
}




/*
package showStores;

import Order.*;
import Store.Store;
import appController.AppController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import listCells.storeCell.StoreListViewCell;
import listCells.itemCell.ItemListViewCell;
import listCells.orderCell.OrderListViewCell;
import showStores.storeInfo.StoreInfoController;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class ShowStoresController implements Initializable {

    private AppController appController;
    private ObservableMap<Integer, Store> storesObservableMap;

    @FXML
    private SplitPane storeInfo;
    @FXML
    private StoreInfoController storeInfoComponentController;
    @FXML
    private ListView<Store> storesLV;

    public AppController getAppController() {
        return appController;
    }

    public ShowStoresController(AppController appController) {
        this.appController = appController;
    }





        //set the stores list view
        for(Map.Entry<Integer, Store> store : appController.getStoreManager().getAllStores().entrySet()){
            storesLV.getItems().add(store.getValue());
        }
        storesLV.setCellFactory(storeListView -> new StoreListViewCell());
    }
}
*/
