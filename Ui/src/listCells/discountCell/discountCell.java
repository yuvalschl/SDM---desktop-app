package listCells.discountCell;

import Item.Item;
import Store.Discount;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class discountCell extends ListCell<Discount>{


        @FXML
        private VBox cellVbox;

        @FXML
        private Label discountNameLabel;


        private FXMLLoader fxmlLoader;

        @Override
        protected void updateItem(Discount discount, boolean empty) {
            super.updateItem(discount, empty);

            if(empty || discount == null) {

                setText(null);
                setGraphic(null);

            } else {
                if (fxmlLoader == null) {
                    fxmlLoader = new FXMLLoader(getClass().getResource("/listCells/discountCell/discountListViewCell.fxml"));
                    fxmlLoader.setController(this);

                    try {
                        fxmlLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                discountNameLabel.setText(String.valueOf(discount.getName()));

                setText(null);
                setGraphic(cellVbox);
            }

        }
}

