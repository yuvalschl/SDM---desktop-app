package orderScreen;

import Costumer.Customer;
import Item.Item;
import Order.Order;
import Store.Store;
import appController.AppController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import listCells.customerCell.CustomerListViewCell;
import listCells.storeCell.StoreListViewCell;

import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

public class OrderScreenController {

    private AppController appController;
    private Order order = new Order();

    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<Customer> customerCB;
    @FXML
    private CheckBox dynamicOrderCB;
    @FXML
    private ComboBox<Store> storeCB;
    @FXML
    private TableView<Item> itemsTable;
    @FXML
    private Button clearButton;
    @FXML
    private Button addButton;
    @FXML
    private TableColumn<Item, Integer> idCol;
    @FXML
    private TableColumn<Item, String> nameCol;
    @FXML
    private TableColumn<Item, Float> priceCol;

    public OrderScreenController(AppController appController) {
        this.appController = appController;
    }

    public AppController getAppController() {
        return appController;
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    public void initialize(){
        //set the columns from the items
        nameCol.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        idCol.setCellValueFactory(new PropertyValueFactory<Item, Integer>("id"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Item, Float>("price"));
        // sets the comboboxes values
        storeCB.getItems().addAll(appController.getStoreManager().getAllStores().values());
        storeCB.setCellFactory(e -> new StoreListViewCell());
        //TODO check why the color is changing after selection
        storeCB.setButtonCell(new StoreListViewCell());
        // sets the comboboxes values
        customerCB.getItems().addAll(appController.getStoreManager().getAllCustomers().values());
        customerCB.setCellFactory(e -> new CustomerListViewCell());
        //TODO check why the color is changing after selection
        customerCB.setButtonCell(new CustomerListViewCell());
        //set combobox selection of store
        storeCB.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Store>() {
            @Override
            public void changed(ObservableValue<? extends Store> observable, Store oldValue, Store newValue) {
                HashMap<Integer, Store> newStore = new HashMap<Integer, Store>();
                newStore.put(newValue.getSerialNumber(), newValue);
                itemsTable.getItems().clear();
                ObservableList<Item> items = FXCollections.observableArrayList(newValue.getInventory().values());
                itemsTable.getItems().addAll(items);
                order.setStores(newStore);
            }
        });
        //set combobox selection of customer
        customerCB.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customer>() {
            @Override
            public void changed(ObservableValue<? extends Customer> observable, Customer oldValue, Customer newValue) {
                order.setCustomer(newValue);
            }
        });
    }

    @FXML
    public void datePickerSelection(){
        order.setDateOfOrder(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    @FXML
    public void dynamicOrderCBAction(){
        storeCB.setDisable(dynamicOrderCB.isSelected());
        order.setStores(null);
        //if dynamic order is selected then all items are displayed
        if(dynamicOrderCB.isSelected()){
            itemsTable.getItems().clear();
            ObservableList<Item> items = FXCollections.observableArrayList(appController.getStoreManager().getAllItems().values());
            itemsTable.getItems().addAll(items);
        }
        else {
            itemsTable.getItems().clear();
            ObservableList<Item> items = FXCollections.observableArrayList(storeCB.getValue().getInventory().values());
            itemsTable.getItems().addAll(items);
        }
    }


    @FXML
    void addAction() {

    }

    @FXML
    void clearAction() {

    }

}
