package showItems;

import Item.*;
import appController.AppController;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.FloatBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class ItemListCellController extends ListCell<Item> {

    private FXMLLoader fxmlLoader;
    private AppController appController;

    @FXML
    private Label name;

    @FXML
    private Label id;

    @FXML
    private Label sellBy;

    @FXML
    private Label storesSelling;

    @FXML
    private Label avgPrice;

    @FXML
    private Label amountSold;

    @FXML
    private AnchorPane cellBox;

    public ItemListCellController(AppController appController) {
        this.appController = appController;
    }

    @Override
    protected void updateItem(Item item, boolean empty) {
        Object oldItem = getItem();
        if (oldItem != null) {
            amountSold.textProperty().unbind();
        }

        super.updateItem(item, empty);

        if(empty || item == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/showItems/ItemListCell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            name.setText(item.getName());
            id.setText(String.valueOf(item.getId()));
            if(item instanceof UnitItem){
                sellBy.setText("Unit");
            }
            else {
                sellBy.setText("Weight");
            }
            storesSelling.setText(String.valueOf(appController.getStoreManager().NumberOfStoresSellingItem(item)));
            avgPrice.setText(String.valueOf(appController.getStoreManager().getAveragePrice(item)));
            amountSold.setText(String.valueOf(item.getAmountSold()));

            setText(null);
            setGraphic(cellBox);
        }

    }

}
