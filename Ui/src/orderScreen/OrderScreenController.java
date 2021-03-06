package orderScreen;
import Store.Discount;
import Costumer.Customer;
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
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.converter.FloatStringConverter;
import listCells.customerCell.CustomerListViewCell;
import listCells.storeCell.StoreListViewCell;
import textFieldFilters.FloatFilter;
import textFieldFilters.IntFilter;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class OrderScreenController {

    private AppController appController;
    private Order order = new Order();
    private ObservableMap<Integer ,ItemAmountAndStore> orderItems = FXCollections.observableHashMap();
    private IntegerBinding listSizeBinding = Bindings.size(orderItems);
    private final IntFilter intFilter = new IntFilter();
    private boolean interestedInDiscount = true;
    private Customer customer;
    @FXML private ProgressBar dynamicOrderProgressBar;
    @FXML private Label progressBarPrecentText;
    @FXML private Label progressMsg;
    @FXML private Label zeroAmountLabel;
    @FXML private VBox orderSummeryScreen;
    @FXML private OrderSummeryController orderSummeryScreenController;
    @FXML private SplitPane orderScreenSplitPane;
    @FXML private SplitPane discountScreen;
    @FXML private DiscountScreenController discountScreenController;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<Customer> customerCB;
    @FXML private CheckBox dynamicOrderCB;
    @FXML private ComboBox<Store> storeCB;
    @FXML private TableView<Item> itemsTable;
    @FXML private TableColumn<Item, Integer> idCol;
    @FXML private TableColumn<Item, String> nameCol;
    @FXML private TableColumn<Item, Float> priceCol;
    @FXML private Label itemNameLabel;
    @FXML private TextArea itemAmountTextField;
    @FXML private Button clearButton;
    @FXML private Button addButton;
    @FXML private TableView<ItemAmountAndStore> orderSummaryTable;
    @FXML private TableColumn<ItemAmountAndStore, Integer> itemSummaryId;
    @FXML private TableColumn<ItemAmountAndStore, String> itemSummaryName;
    @FXML private TableColumn<ItemAmountAndStore, Float> itemSummaryAmount;

    public SplitPane getDiscountScreen() {
        return discountScreen;
    }

    public TableView<ItemAmountAndStore> getOrderSummaryTable() {
        return orderSummaryTable;
    }

    public void setOrderSummaryTable(TableView<ItemAmountAndStore> orderSummaryTable) {
        this.orderSummaryTable = orderSummaryTable;
    }

    @FXML
    private void addAction() {
        Item item = itemsTable.getSelectionModel().getSelectedItem();
        if (item != null) {
            float amount = Float.parseFloat(itemAmountTextField.getText());
            if (amount == 0) {
                zeroAmountLabel.setVisible(true);
            } else {
                zeroAmountLabel.setVisible(false);
                ItemAmountAndStore itemToAdd;
                if (dynamicOrderCB.isSelected()) {
                    itemToAdd = new ItemAmountAndStore(item, amount);
                    itemToAdd.setDiscountItemAmount(amount);
                    itemToAdd.setAmount(amount);
                } else {
                    itemToAdd = new ItemAmountAndStore(item, amount, storeCB.getValue());
                }

                if (orderItems.containsKey(item.getId())) {
                    ItemAmountAndStore currentItem = orderItems.get(item.getId());
                    currentItem.setAmount(currentItem.getAmount() + itemToAdd.getAmount());
                    currentItem.setDiscountItemAmount(currentItem.getAmount());
                    orderSummaryTable.getItems()
                            .stream()
                            .filter(e -> e.getItemId() == currentItem.getItemId())
                            .collect(Collectors.toSet())
                            .forEach(e -> e.setAmount(currentItem.getAmount()));
                } else {
                    orderItems.put(item.getId(), itemToAdd);
                    orderSummaryTable.getItems().add(itemToAdd);
                }
                orderSummaryTable.refresh();
                itemNameLabel.setText(" ");
                itemAmountTextField.clear();
                itemsTable.getSelectionModel().clearSelection();
            }
        }
    }

    @FXML
    public void placeOrderAction(){
        if (orderItems.size() != 0) {
            HashMap<Integer, ItemAmountAndStore> itemsToAdd = new HashMap<Integer, ItemAmountAndStore>();
            customer = customerCB.getValue();
            Date orderDate = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            if(dynamicOrderCB.selectedProperty().getValue()){
                Task<Boolean> dynamicOrderTask = new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        int progressJump = 100 / orderItems.size();
                        int currentProgress = 0;
                        for(ItemAmountAndStore item : orderItems.values()){
                            updateMessage("finding the best price for " + item.getItemName());
                            for(int i = currentProgress; i < currentProgress + progressJump; i++){
                                updateProgress(i, 100);
                                TimeUnit.MILLISECONDS.sleep(1000/progressJump);
                            }
                            currentProgress += progressJump;
                            float amount = item.getAmount();
                            itemsToAdd.put(item.getItemId(),appController.getStoreManager().getCheapestItem(item.getItemId()));
                            itemsToAdd.get(item.getItemId()).setDiscountItemAmount(amount);
                            itemsToAdd.get(item.getItemId()).setAmount(amount);
                        }
                        for(int i = currentProgress; i <= 100; i++){
                            updateProgress(i,100);
                            TimeUnit.MILLISECONDS.sleep(50);
                        }
                        return true;
                    }
                };
                dynamicOrderTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        progressBarPrecentText.textProperty().unbind();
                        dynamicOrderProgressBar.progressProperty().unbind();
                        progressMsg.textProperty().unbind();
                        progressBarPrecentText.setText(" ");
                        dynamicOrderProgressBar.progressProperty().setValue(0);
                        progressMsg.setText(" ");
                        finishOrder(orderDate, itemsToAdd);
                    }
                });
                progressBarPrecentText.textProperty().bind(Bindings.concat(
                        Bindings.format(
                                "%.0f",
                                Bindings.multiply(
                                        dynamicOrderTask.progressProperty(),
                                        100)),
                        " %"));
                dynamicOrderProgressBar.progressProperty().bind(dynamicOrderTask.progressProperty());
                progressMsg.textProperty().bind(dynamicOrderTask.messageProperty());
                Thread dynamicOrderThread = new Thread(dynamicOrderTask);
                dynamicOrderThread.start();
            }
            else {
                finishOrder(orderDate, new HashMap<>(orderItems));
            }
        }
    }

    public void finishOrder(Date orderDate, HashMap<Integer, ItemAmountAndStore> itemsToAdd ){
        Order order = appController.getStoreManager().createOrder(customerCB.getValue().getLocation(), orderDate,itemsToAdd , customer);
        ArrayList<Discount> discounts = appController.getStoreManager().getEntitledDiscounts(order);
        appController.getShowItemsController().setData(appController);
        if (discounts.size() != 0 && interestedInDiscount) {
            discountScreenController.setData(discounts, appController, order, discountScreen, customerCB.getValue().getLocation(), orderSummeryScreen, orderSummeryScreenController, orderScreenSplitPane, this);
        } else
            orderScreenSplitPane.setVisible(false);
        orderSummeryScreenController.setData(appController, order, customerCB.getValue().getLocation(), orderSummeryScreen, orderScreenSplitPane, this);
    }

    @FXML
    public void textFieldOnEnter(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.ENTER){
            addAction();
        }
    }

    @FXML
    public void editAmountOnTable(TableColumn.CellEditEvent cellEditEvent){
        ItemAmountAndStore itemAmountAndStore = (ItemAmountAndStore) cellEditEvent.getTableView().getItems().get(cellEditEvent.getTablePosition().getRow());
        if((float)cellEditEvent.getNewValue() < 0){
            orderSummaryTable.getItems().set(cellEditEvent.getTablePosition().getRow(), itemAmountAndStore);
        }
        else if(itemAmountAndStore.getItem() instanceof UnitItem &&  (float)cellEditEvent.getNewValue() % 1 != 0){
            orderSummaryTable.getItems().set(cellEditEvent.getTablePosition().getRow(), itemAmountAndStore);
        }
        else {
            orderSummaryTable.getSelectionModel().selectedItemProperty().getValue().setAmount((Float) cellEditEvent.getNewValue());
        }
    }

    @FXML
    public void clearAction() {
        orderItems.clear();
        orderSummaryTable.getItems().clear();
        orderItems.clear();
        interestedInDiscount = true;
        dynamicOrderCB.setSelected(false);
        datePicker.setValue(null);
        storeCB.setValue(null);
        customerCB.setValue(null);
    }

    @FXML
    public void initialize(){
        zeroAmountLabel.setVisible(false);
        dynamicOrderProgressBar.visibleProperty().bind(dynamicOrderCB.selectedProperty());
        progressBarPrecentText.visibleProperty().bind(dynamicOrderCB.selectedProperty());
        //set the columns from the items
        nameCol.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        idCol.setCellValueFactory(new PropertyValueFactory<Item, Integer>("id"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Item, Float>("price"));
        priceCol.setCellFactory(e -> new TableCell<Item, Float>(){
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                if(empty){
                    setText(null);
                }
                else {
                    setText(appController.getDecimalFormat().format(item));
                }
            }
        });


        //set column for the summary
        itemSummaryName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemSummaryAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        itemSummaryAmount.setCellFactory(e -> new TableCell<ItemAmountAndStore, Float>(){
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                if(empty){
                    setText(null);
                }
                else {
                    setText(appController.getDecimalFormat().format(item));
                }
            }
        });
        itemSummaryId.setCellValueFactory(new PropertyValueFactory<>("itemId"));


        //set combobox selection of store
        storeCB.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Store>() {
            @Override
            public void changed(ObservableValue<? extends Store> observable, Store oldValue, Store newValue) {
                if (newValue != null){
                    HashMap<Integer, Store> newStore = new HashMap<Integer, Store>();
                    newStore.put(newValue.getSerialNumber(), newValue);
                    itemsTable.getItems().clear();
                    ObservableList<Item> items = FXCollections.observableArrayList(newValue.getInventory().values());
                    itemsTable.getItems().addAll(items);
                    order.setStores(newStore);
                }
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
                    if (newValue instanceof UnitItem){
                        itemAmountTextField.setTextFormatter(intFilter.getIntegerTextFormatter());
                    }
                    else {
                        itemAmountTextField.setTextFormatter(new FloatFilter().getTextFormatter());
                    }

                }
            }
        });

        //bind the store combo box so it cant change mid order
        storeCB.disableProperty().bind(Bindings.or(listSizeBinding.greaterThan(0), dynamicOrderCB.selectedProperty()));
        dynamicOrderCB.disableProperty().bind(listSizeBinding.greaterThan(0));

        //set binding for the amount text field
        BooleanBinding textFieldBindToItemsTable =
                Bindings.createBooleanBinding(() -> {
                    return itemsTable.getSelectionModel().getSelectedItems().size() != 1;
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
                    return !dynamicOrderCB.isSelected();
                }, dynamicOrderCB.selectedProperty());

        BooleanBinding storeSelectionBinding = Bindings.and(dynamicOrderBind, storeBind);
        BooleanBinding andBind = Bindings.or(customerBind, datePickerBind);

        itemsTable.disableProperty().bind(Bindings.or(storeSelectionBinding, andBind));
        orderSummaryTable.disableProperty().bind(Bindings.or(storeSelectionBinding, andBind));
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
        storeCB.getItems().clear();
        storeCB.getItems().addAll(appController.getStoreManager().getAllStores().values());
        storeCB.setCellFactory(e -> new StoreListViewCell());
        storeCB.setButtonCell(new StoreListViewCell());

        // sets the comboboxes values
        customerCB.getItems().clear();
        customerCB.getItems().addAll(appController.getStoreManager().getAllCustomers().values());
        customerCB.setCellFactory(e -> new CustomerListViewCell());
        customerCB.setButtonCell(new CustomerListViewCell());
    }

    @FXML
    public void datePickerSelection(){
        if (datePicker.getValue() != null){
            order.setDateOfOrder(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
    }

    @FXML
    public void dynamicOrderCBAction(){
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
    public boolean isInterestedInDiscount() {
        return interestedInDiscount;
    }

    public void setInterestedInDiscount(boolean interestedInDiscount) {
        this.interestedInDiscount = interestedInDiscount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
