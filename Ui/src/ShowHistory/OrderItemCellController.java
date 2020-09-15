package ShowHistory;

import DtoObjects.DtoUnitItem;
import Item.*;
import ItemPair.ItemAmountAndStore;
import Store.Store;
import appController.AppController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class OrderItemCellController extends ListCell<ItemAmountAndStore> {

    @FXML private AnchorPane cellBox;
    @FXML private Label name;
    @FXML private Label ID;
    @FXML private Label sellBy;
    @FXML private Label amount;
    @FXML private Label itemPrice;
    @FXML private Label totalItemPrice;
    @FXML private Label isPartOfDiscount;

    private AppController appController;
    private Store store;
    private FXMLLoader fxmlLoader;

    public OrderItemCellController(AppController appController) {
        this.appController = appController;
    }

    @Override
    protected void updateItem(ItemAmountAndStore item, boolean empty) {
        Object oldItem = getItem();
    /*    if (oldItem != null) {
            amountSold.textProperty().unbind();
        }
*/        super.updateItem(item, empty);

        if(empty || item == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/ShowHistory/orderItemCell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            name.setText(item.getItemName());
            ID.setText(String.valueOf(item.getItemId()));
            if(item.getItem() instanceof DtoUnitItem){
                sellBy.setText("Unit");
            }
            else {
                sellBy.setText("Weight");
            }
            amount.setText(Float.toString(item.getAmount()));
            Float price = appController.getStoreManager().getAllStores().get(item.getStore().getSerialNumber()).getInventory().get(item.getItemId()).getPrice();
            itemPrice.setText(String.valueOf(price));
            totalItemPrice.setText(Float.toString(item.getAmount() * price));
            String partOfDiscount;
            if (item.getIsPartOfDiscount()){
                partOfDiscount = "Yes";
            }
            else{
                partOfDiscount = "No";
            }
            isPartOfDiscount.setText(partOfDiscount);
            setText(null);
            setGraphic(cellBox);
        }

    }
}
