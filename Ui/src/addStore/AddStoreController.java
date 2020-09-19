package addStore;

import Item.*;
import Store.Store;
import appController.AppController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import textFieldFilters.FloatFilter;
import textFieldFilters.IntFilter;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddStoreController {
    private AppController appController;
    private StringProperty storeName = new SimpleStringProperty();
    private IntegerProperty storeId = new SimpleIntegerProperty();
    private IntegerProperty storePPK = new SimpleIntegerProperty();
    private IntegerProperty storeX = new SimpleIntegerProperty();
    private IntegerProperty storeY = new SimpleIntegerProperty();
    private Map<Integer, Item> storeInventory = new HashMap<Integer, Item>();

    @FXML private TextField nameTextField;
    @FXML private TextField idTextField;
    @FXML private TextField xTextField;
    @FXML private TextField yTextField;
    @FXML private TextField ppkTextField;
    @FXML private TableView<Item>  availableItemsTable;
    @FXML private TableColumn<Item, Integer> availableItemsItemId;
    @FXML private TableColumn<Item, String> availableItemsItemName;
    @FXML private Label itemNameLabel;
    @FXML private Button addItemButton;
    @FXML private TableView<Item> storeInventoryTable;
    @FXML private TableColumn<Item, Integer> storeInventoryItemId;
    @FXML private TableColumn<Item, String> storeInventoryItemName;
    @FXML private TableColumn<Item, Float> storeInventoryItemPrice;
    @FXML private Button addStoreButton;
    @FXML private TextField itemPriceField;
    @FXML private Label storeInfoErrorLabel;
    @FXML private Label itemInInventoryErrorLabel;
    @FXML private Label noItemsInInventoryErrorLabel;

    @FXML
    public void initialize(){
        idTextField.setTextFormatter(new IntFilter().getIntegerTextFormatter());
        idTextField.textProperty().set(" ");
        ppkTextField.setTextFormatter(new IntFilter().getIntegerTextFormatter());
        ppkTextField.textProperty().set(" ");
        itemPriceField.setTextFormatter(new FloatFilter().getTextFormatter());
        idTextField.textProperty().set(" ");
        xTextField.setTextFormatter(new IntFilter().getIntegerTextFormatter());
        yTextField.setTextFormatter(new IntFilter().getIntegerTextFormatter());

        storeName.bind(nameTextField.textProperty());

        storeId.bind(Bindings.createIntegerBinding(() -> {
            return Integer.parseInt(idTextField.getText());
        }, idTextField.textProperty()));

        storePPK.bind(Bindings.createIntegerBinding(() -> {
            return Integer.parseInt(ppkTextField.getText());
        }, ppkTextField.textProperty()));

        storeX.bind(Bindings.createIntegerBinding(() -> {
            return Integer.parseInt(xTextField.getText());
        }, xTextField.textProperty()));

        storeY.bind(Bindings.createIntegerBinding(() -> {
            return Integer.parseInt(yTextField.getText());
        }, yTextField.textProperty()));


        availableItemsItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        availableItemsItemId.setCellValueFactory(new PropertyValueFactory<>("id"));

        storeInventoryItemId.setCellValueFactory(new PropertyValueFactory<>("id"));
        storeInventoryItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        storeInventoryItemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));


        availableItemsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Item>() {
            @Override
            public void changed(ObservableValue<? extends Item> observable, Item oldValue, Item newValue) {
                itemNameLabel.setText(newValue.getName());
                itemPriceField.clear();
            }
        });
    }

    @FXML
    public void textFieldOnEnter(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.ENTER){
            addItemAction();
        }
    }

    public void setData(AppController appController){
        this.appController = appController;
        availableItemsTable.getItems().clear();
        availableItemsTable.getItems().addAll(appController.getStoreManager().getAllItems().values());
    }

    @FXML
    void addStoreAction(ActionEvent event) {
        boolean validInfo = true;
        noItemsInInventoryErrorLabel.setText(" ");
        if(storeInventoryTable.getItems().isEmpty()){
            noItemsInInventoryErrorLabel.setText("add item to the store inventory");
            validInfo = false;
        }

        if(appController.getStoreManager().getAllStores().containsKey(storeId.getValue())){
            storeInfoErrorLabel.setText(storeInfoErrorLabel.getText() + "\n" + "store with id " + storeId.get() + " already in the system");
            validInfo = false;
        }

        if(Integer.parseInt(xTextField.getText()) > 50 ||Integer.parseInt(xTextField.getText()) < 0){
            storeInfoErrorLabel.setText(storeInfoErrorLabel.getText() + "\n" + xTextField.getText() + " X location is invalid");
            validInfo = false;
        }

        if(Integer.parseInt(yTextField.getText()) > 50 ||Integer.parseInt(yTextField.getText()) < 0){
            storeInfoErrorLabel.setText(storeInfoErrorLabel.getText() + "\n" + yTextField.getText() + " Y location is invalid");
            validInfo = false;
        }

        if (validInfo){
            Store newStore = new Store(storeName.getValue(), new Point(storeX.get(), storeY.get()), storeInventory, storePPK.getValue(),storeId.getValue());
            appController.getStoreManager().addNewStore(newStore);
            storeInfoErrorLabel.setVisible(false);
            nameTextField.clear();
            idTextField.clear();
            ppkTextField.clear();
            xTextField.clear();
            yTextField.clear();
            itemPriceField.clear();
            appController.getOptionsMenuComponentController().homeButtonAction();
            updateAppControllerToNewStore(newStore);
        }
    }

    private void updateAppControllerToNewStore(Store store){
        appController.getShowStoresComponentController().getStoresListView().getItems().add(store);
        appController.getOrderScreenComponentController().setData(appController);
        appController.getShowItemsController().updateItemsToShow();
        try {
            appController.getHomeComponentController().getMapGridController().setSize(appController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void addItemAction() {
        Item itemToAdd = availableItemsTable.getSelectionModel().getSelectedItem();
        float price = Float.parseFloat(itemPriceField.getText());
        if(storeInventory.containsKey(itemToAdd.getId())){
            itemInInventoryErrorLabel.setText("Item already in inventory");
        }
        else {
            itemInInventoryErrorLabel.setVisible(false);
            if(itemToAdd instanceof UnitItem){
                storeInventoryTable.getItems().add(new UnitItem(itemToAdd, price));
                storeInventory.put(itemToAdd.getId(), new UnitItem(itemToAdd, price));
            }
            else {
                storeInventoryTable.getItems().add(new WeightItem(itemToAdd, price));
                storeInventory.put(itemToAdd.getId(), new WeightItem(itemToAdd, price));
            }

        }

    }

}
