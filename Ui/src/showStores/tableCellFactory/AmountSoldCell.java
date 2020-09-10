package showStores.tableCellFactory;

import Costumer.Customer;
import Item.Item;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableCell;

import java.io.IOException;

public class AmountSoldCell extends TableCell<Item, Integer> {
    private Item item;

    public AmountSoldCell(Item item){
        this.item = item;
    }

    @Override
    protected void updateItem(Integer val, boolean empty) {
        super.updateItem(val, empty);

        if(empty || val == null) {

            setText(null);
            setGraphic(null);
        }
        else {
            setText(String.valueOf(item.getAmountSold()));
        }

    }
}
