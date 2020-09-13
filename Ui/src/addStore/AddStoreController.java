package addStore;

import Item.*;
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
import textFieldFilters.FloatFilter;
import textFieldFilters.IntFilter;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AddStoreController {
    private AppController appController;
    private StringProperty storeName = new SimpleStringProperty();
    private IntegerProperty storeId = new SimpleIntegerProperty();
    private IntegerProperty storePPK = new SimpleIntegerProperty();
    private Point storeLocation = new Point();
    private Map<Integer, Item> storeInventory = new HashMap<Integer, Item>();

    @FXML private TextField nameTextField;
    @FXML private TextField idTextField;
    @FXML private Spinner<Integer> xSpinner;
    @FXML private Spinner<Integer> ySpinner;
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

        storeName.bind(nameTextField.textProperty());

        storeId.bind(Bindings.createIntegerBinding(() -> {
            return Integer.parseInt(idTextField.getText());
        }, idTextField.textProperty()));

        storePPK.bind(Bindings.createIntegerBinding(() -> {
            return Integer.parseInt(ppkTextField.getText());
        }, ppkTextField.textProperty()));

        xSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        xSpinner.setEditable(true);
        ySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50));
        ySpinner.setEditable(true);

        xSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            storeLocation.x = newValue;
        });

        ySpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            storeLocation.y = newValue;
        });

        availableItemsItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        availableItemsItemId.setCellValueFactory(new PropertyValueFactory<>("id"));

        storeInventoryItemId.setCellValueFactory(new PropertyValueFactory<>("id"));
        storeInventoryItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        storeInventoryItemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));


        availableItemsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Item>() {
            @Override
            public void changed(ObservableValue<? extends Item> observable, Item oldValue, Item newValue) {
                itemNameLabel.setText(newValue.getName());
            }
        });

    }

    public void setData(AppController appController){
        this.appController = appController;
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
            storeInfoErrorLabel.setText(storeInfoErrorLabel.getText() + "\n" + "store with id " + storeId + " already in the system");
            validInfo = false;
        }

        if(xSpinner.getValue() > 50 || xSpinner.getValue() < 0){
            storeInfoErrorLabel.setText(storeInfoErrorLabel.getText() + "\n" + xSpinner.getValue() + "X location is invalid");
            validInfo = false;
        }

        if(ySpinner.getValue() > 50 || ySpinner.getValue() < 0){
            storeInfoErrorLabel.setText(storeInfoErrorLabel.getText() + "\n" + ySpinner.getValue() + "Y location is invalid");
            validInfo = false;
        }

        if (validInfo){
            appController.getStoreManager().addNewStore(storeId.getValue(), storeName.getValue(), storeLocation, storeInventory, storePPK.getValue());
            storeInfoErrorLabel.setVisible(false);
            xSpinner.getValueFactory().setValue(0);
            ySpinner.getValueFactory().setValue(0);
            nameTextField.clear();
            idTextField.clear();
            ppkTextField.clear();
            appController.getShowStoresComponentController().setData(appController);
            appController.getOptionsMenuComponentController().homeButtonAction();
        }
    }


    @FXML
    void addItemAction(ActionEvent event) {
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
