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
import listCells.itemCell.ItemListViewCell;
import listCells.storeCell.StoreListViewCell;

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
    @FXML private TableColumn<Item, Float> itemAmountSoldCol;
    @FXML private TableView<Item> itemsTable;
    @FXML private TableView<StoreOrder> ordersTable;
    @FXML private TableColumn<StoreOrder, Date> orderDateCol;
    @FXML private TableColumn<StoreOrder, Integer> orderIdCol;
    @FXML private TableColumn<StoreOrder, Float> amountOfItemsCol;
    @FXML private TableColumn<StoreOrder, Float> itemsCostCol;
    @FXML private TableColumn<StoreOrder, Float> shippingCostCol;
    @FXML private TableColumn<StoreOrder, Float> totalCostCol;
    @FXML private ListView<Store> storesListView;
    @FXML private ListView<Discount> discountsList;
    @FXML private Label ifYouBuyItemLabel;
    @FXML private ListView<Item> discountItemsList;
    @FXML private Label youGetLabel;
    @FXML private Label forAdditionalLabel;


    public TableView<Item> getItemsTable() {
        return itemsTable;
    }
    public ListView<Store> getStoresListView() {
        return storesListView;
    }

    @FXML
    public void initialize(){
        //set the item table
        itemNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        itemIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        itemPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        itemPriceCol.setCellFactory(tc -> new TableCell<Item, Float>(){
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                if(empty){
                    setText(null);
                }
                else {
                    setText(String.format("%.2f", item));
                }
            }
        });
        itemAmountSoldCol.setCellValueFactory(new PropertyValueFactory<>("amountSold"));
        itemAmountSoldCol.setCellFactory(tc -> new TableCell<Item, Float>(){
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                if(empty){
                    setText(null);
                }
                else {
                    setText(String.format("%.2f", item));
                }
            }
        });

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
        amountOfItemsCol.setCellFactory(tc -> new TableCell<StoreOrder, Float>(){
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                if(empty){
                    setText(null);
                }
                else {
                    setText(String.format("%.2f", item));
                }
            }
        });
        totalCostCol.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        totalCostCol.setCellFactory(tc -> new TableCell<StoreOrder, Float>(){
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                if(empty){
                    setText(null);
                }
                else {
                    setText(String.format("%.2f", item));
                }
            }
        });
        shippingCostCol.setCellValueFactory(new PropertyValueFactory<>("shippingCost"));
        shippingCostCol.setCellFactory(tc -> new TableCell<StoreOrder, Float>(){
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                if(empty){
                    setText(null);
                }
                else {
                    setText(String.format("%.2f", item));
                }
            }
        });
        itemsCostCol.setCellValueFactory(new PropertyValueFactory<>("totalPriceOfItems"));
        itemsCostCol.setCellFactory(tc -> new TableCell<StoreOrder, Float>(){
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                if(empty){
                    setText(null);
                }
                else {
                    setText(String.format("%.2f", item));
                }
            }
        });
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

                    ordersTable.getItems().clear();
                    ordersTable.setItems(FXCollections.observableArrayList(newValue.getAllOrders().values()));
                    itemsTable.scrollTo(0);

                    // this sets the discount information
                    discountsList.getItems().clear();
                    if (newValue.getAllDiscounts() != null){
                        discountsList.getItems().addAll(newValue.getAllDiscounts());
                        discountsList.setCellFactory(e -> new discountCell());
                    }
                }
            }
        });

        discountsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Discount>() {
            @Override
            public void changed(ObservableValue<? extends Discount> observable, Discount oldValue, Discount newValue) {
                if(newValue != null){
                    youGetLabel.setText(newValue.getThenYouGet().getDiscountOperator().name());
                    ifYouBuyItemLabel.setText(appController.getStoreManager().getAllItems().get(newValue.getIfYouBuy().getItemId()).toString());
                    discountItemsList.getItems().clear();
                    for(Offer offer : newValue.getThenYouGet().getAllOffers()){
                        discountItemsList.getItems().add(appController.getStoreManager().getAllItems().get(offer.getItemId()));
                    }
                    discountItemsList.setCellFactory(e ->  new ItemListViewCell());
                }
            }
        });

        discountItemsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Item>() {
            @Override
            public void changed(ObservableValue<? extends Item> observable, Item oldValue, Item newValue) {
                if(newValue != null){
                    forAdditionalLabel.setText(String.valueOf(discountsList.getSelectionModel().getSelectedItem().getThenYouGet().getOfferByID(newValue.getId()).getForAdditional()));
                }
            }
        });
    }
}
