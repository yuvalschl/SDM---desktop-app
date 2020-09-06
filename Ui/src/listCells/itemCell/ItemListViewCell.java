package listCells.itemCell;

import Item.Item;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

import java.io.IOException;


public class ItemListViewCell extends ListCell<Item> {

    @FXML
    private VBox cellVbox;

    @FXML
    private Label itemNameLabel;

    @FXML
    private Label itemIdLabel;

    private FXMLLoader fxmlLoader;

    @Override
    protected void updateItem(Item item, boolean empty) {
        super.updateItem(item, empty);

        if(empty || item == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/listCells/itemCell/itemListViewCell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            itemNameLabel.setText(String.valueOf(item.getName()));
            itemIdLabel.setText(String.valueOf(item.getId()));

            setText(null);
            setGraphic(cellVbox);
        }

    }
}