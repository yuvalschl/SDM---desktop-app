package listCells.orderCell;

import Order.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class OrderHistoryListViewCell extends ListCell<Order> {

    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    @FXML
    private VBox cellVbox;

    @FXML
    private Label orderDateLabel;

    @FXML
    private Label orderIdLabel;

    private FXMLLoader fxmlLoader;

    @Override
    protected void updateItem(Order order, boolean empty) {
        super.updateItem(order, empty);

        if(empty || order == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/listCells/orderCell/orderHistoryListViewCell.fxml"));//this might be the problem1!!!!!
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            orderDateLabel.setText(String.valueOf(format.format(order.getDateOfOrder())));
            orderIdLabel.setText(String.valueOf(order.getOrderId()));

            setText(null);
            setGraphic(cellVbox);
        }

    }
}
