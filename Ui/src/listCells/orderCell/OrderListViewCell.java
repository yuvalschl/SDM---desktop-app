package listCells.orderCell;

import Order.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class OrderListViewCell extends ListCell<StoreOrder> {

    @FXML
    private VBox cellVbox;

    @FXML
    private Label orderDateLabel;

    @FXML
    private Label orderIdLabel;

    private FXMLLoader fxmlLoader;

    @Override
    protected void updateItem(StoreOrder order, boolean empty) {
        super.updateItem(order, empty);

        if(empty || order == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/listCells/orderCell/orderListViewCell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            orderDateLabel.setText(String.valueOf(order.getDateOfOrder()));
            orderIdLabel.setText(String.valueOf(order.getOrderId()));

            setText(null);
            setGraphic(cellVbox);
        }

    }
}
