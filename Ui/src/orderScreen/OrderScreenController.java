package orderScreen;

import Costumer.Customer;
import DtoObjects.DtoConvertor;
import Item.*;
import ItemPair.ItemAmountAndStore;
import Order.Order;
import Store.Store;
import appController.AppController;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import listCells.customerCell.CustomerListViewCell;
import listCells.storeCell.StoreListViewCell;

import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

public class OrderScreenController {

    private AppController appController;
    private Order order = new Order();
    private ObservableMap<Integer ,ItemAmountAndStore> orderItems = FXCollections.observableHashMap();
    private IntegerBinding listSizeBinding = Bindings.size(orderItems);

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
    private TableColumn<Item, Integer> idCol;

    @FXML
    private TableColumn<Item, String> nameCol;

    @FXML
    private TableColumn<Item, Float> priceCol;

    @FXML
    private Label itemNameLabel;

    @FXML
    private TextArea itemAmountTextField;

    @FXML
    private Button clearButton;

    @FXML
    private Button addButton;

    @FXML
    private TableView<ItemAmountAndStore> orderSummaryTable;

    @FXML
    private TableColumn<ItemAmountAndStore, Integer> itemSummaryId;

    @FXML
    private TableColumn<ItemAmountAndStore, String> itemSummaryName;

    @FXML
    private TableColumn<ItemAmountAndStore, Float> itemSummaryAmount;


    @FXML
    void addAction() {
        float amount = Float.parseFloat(itemAmountTextField.getText());
        Item item = itemsTable.getSelectionModel().getSelectedItem();
        ItemAmountAndStore itemToAdd;
        if (dynamicOrderCB.isSelected()){
            itemToAdd = appController.getStoreManager().getCheapestItem(item.getId());
            itemToAdd.setAmount(amount);
        }
        else {
            itemToAdd = new ItemAmountAndStore(DtoConvertor.itemToDtoItem(item), amount, storeCB.getValue());
        }

        if(orderItems.containsKey(item.getId())){
            ItemAmountAndStore currentItem = orderItems.get(item.getId());
            currentItem.setAmount(currentItem.getAmount() + itemToAdd.getAmount());
            orderSummaryTable.getItems()
                    .stream()
                    .filter(e -> e.getItemId() == currentItem.getItemId())
                    .collect(Collectors.toSet())
                    .forEach(e -> e.setAmount(currentItem.getAmount()));
        }
        else {
            orderItems.put(item.getId(), itemToAdd);
            orderSummaryTable.getItems().add(itemToAdd);
        }
        orderSummaryTable.refresh();
        itemNameLabel.setText(" ");
        itemAmountTextField.clear();
        itemsTable.getSelectionModel().clearSelection();
    }

    @FXML
    public void placeOrderAction(){

    }

    @FXML
    void clearAction(ActionEvent event) {
        orderItems.clear();
        orderSummaryTable.getItems().clear();
    }

    @FXML
    public void initialize(){
        //set the columns from the items
        nameCol.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        idCol.setCellValueFactory(new PropertyValueFactory<Item, Integer>("id"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Item, Float>("price"));

        //set column for the summary
        itemSummaryName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemSummaryAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        itemSummaryId.setCellValueFactory(new PropertyValueFactory<>("itemId"));

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

        itemsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Item>() {
            @Override
            public void changed(ObservableValue<? extends Item> observable, Item oldValue, Item newValue) {
                if(newValue != null){
                    itemNameLabel.setText(newValue.getName());
                }
            }
        });

        dynamicOrderCB.disableProperty().bind(listSizeBinding.greaterThan(0));

        //set binding for the amount text field
        BooleanBinding textFieldBindToItemsTable =
                Bindings.createBooleanBinding(() -> {
                    boolean result = itemsTable.getSelectionModel().getSelectedItems().size() != 1;
                    return result;
                }, itemsTable.getSelectionModel().selectedItemProperty());

        itemAmountTextField.disableProperty().bind(textFieldBindToItemsTable);

        //set bindings for the items table
        BooleanBinding datePickerBind =
                Bindings.createBooleanBinding(() -> {
                    return datePicker.getValue() == null;
                }, datePicker.valueProperty());

        BooleanBinding customerBind =
                Bindings.createBooleanBinding(() -> {
                    return customerCB.getValue() == null;
                }, customerCB.valueProperty());

        BooleanBinding storeBind =
                Bindings.createBooleanBinding(() -> {
                    return storeCB.getValue() == null;
                }, storeCB.valueProperty());

        BooleanBinding dynamicOrderBind =
                Bindings.createBooleanBinding(() -> {
                    return dynamicOrderCB.isSelected();
                }, dynamicOrderCB.selectedProperty());

        BooleanBinding andBinding1 = Bindings.and(dynamicOrderBind, storeBind);
        BooleanBinding andBinding2 = Bindings.or(customerBind, datePickerBind);

        itemsTable.disableProperty().bind(Bindings.or(andBinding1, andBinding2));
    }

    public OrderScreenController(){}

    public OrderScreenController(AppController appController) {
        this.appController = appController;
    }

    public AppController getAppController() {
        return appController;
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void setData(AppController appController){

        this.appController = appController;

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
        else if (storeCB.getValue() != null){
            itemsTable.getItems().clear();
            ObservableList<Item> items = FXCollections.observableArrayList(storeCB.getValue().getInventory().values());
            itemsTable.getItems().addAll(items);
        }
    }
}
