package showStores;

import Item.Item;
import Order.*;
import Store.*;
import appController.AppController;
import com.sun.org.apache.bcel.internal.generic.LADD;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import listCells.discountCell.discountCell;
import listCells.discountCell.offerCell;
import listCells.storeCell.StoreListViewCell;
import showStores.tableCellFactory.AmountSoldCell;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ShowStoresController {

    private ObservableMap<Integer, Store> storesObservableMap;
    private AppController appController;

    @FXML private TableColumn<Item, String> itemNameCol;
    @FXML private TableColumn<Item, Integer> itemIdCol;
    @FXML private TableColumn<Item, Float> itemPriceCol;
    @FXML private TableColumn<Item, Integer> itemAmountSoldCol;
    @FXML private TableView<Item> itemsTable;
    @FXML private TableView<StoreOrder> ordersTable;
    @FXML private TableColumn<StoreOrder, Date> orderDateCol;
    @FXML private TableColumn<StoreOrder, Integer> orderIdCol;
    @FXML private TableColumn<StoreOrder, Integer> amountOfItemsCol;
    @FXML private TableColumn<StoreOrder, Float> itemsCostCol;
    @FXML private TableColumn<StoreOrder, Float> shippingCostCol;
    @FXML private TableColumn<StoreOrder, Float> totalCostCol;
    @FXML private ListView<Store> storesListView;
    @FXML private ListView<Discount> discountNameListView;
    @FXML private Label ifYouBuyItemLabel;
    @FXML private Label youGetLabel;
    @FXML private ListView<Offer> youGetListView;
    public TableView<Item> getItemsTable() {
        return itemsTable;
    }
    public ListView<Store> getStoresListView() {
        return storesListView;
    }

    public void setData(AppController appController){
        this.appController = appController;
        storesListView.getItems().clear();
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

        orderDateCol.setCellValueFactory(new PropertyValueFactory<>("dateOfOrder"));
        orderDateCol.setCellFactory(column -> {
                    TableCell<StoreOrder, Date> cell = new TableCell<StoreOrder, Date>() {
                        private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                        @Override
                        protected void updateItem(Date item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setText(null);
                            } else {
                                setText(format.format(item));
                            }
                        }
                    };

                    return cell;
                });

        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        amountOfItemsCol.setCellValueFactory(new PropertyValueFactory<>("amountOfItems"));
        totalCostCol.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        shippingCostCol.setCellValueFactory(new PropertyValueFactory<>("shippingCost"));
        itemsCostCol.setCellValueFactory(new PropertyValueFactory<>("totalPriceOfItems"));

        storesListView.getItems().clear();
        storesListView.getItems().addAll(appController.getStoreManager().getAllStores().values());
        storesListView.setCellFactory(e -> new StoreListViewCell());

        // this set the on click for the list view
        storesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Store>() {
            @Override
            public void changed(ObservableValue<? extends Store> observable, Store oldValue, Store newValue) {
                //set the items table
                if(newValue != null){
                    itemsTable.getItems().clear();
                    itemsTable.getItems().addAll(newValue.getInventory().values());
                    itemsTable.scrollTo(0);

                    // this sets the discount information
                    discountNameListView.getItems().clear();
                    discountNameListView.getItems().addAll(newValue.getAllDiscounts());
                    discountNameListView.setCellFactory(e -> new discountCell());

                    ordersTable.getItems().clear();
                    ordersTable.setItems(FXCollections.observableArrayList(newValue.getAllOrders().values()));
                    itemsTable.scrollTo(0);
                }







            }
        });
        discountNameListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Discount>() {
            @Override
            public void changed(ObservableValue<? extends Discount> observable, Discount oldValue, Discount newValue) {
                youGetListView.getItems().clear();
                if (newValue != null) {
                    youGetListView.getItems().addAll(newValue.getThenYouGet().getAllOffers());
                    youGetListView.setCellFactory(e -> new offerCell(appController.getStoreManager()));

                    ifYouBuyItemLabel.textProperty().set(appController.getStoreManager().getAllItems().get(newValue.getIfYouBuy().getItemId()).getName());
                    if (newValue.getThenYouGet().getDiscountOperator() == DiscountOperator.oneOf)
                        youGetLabel.textProperty().set("You get one of:");
                    else
                        youGetLabel.textProperty().set("You get all these items:");

                }
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
