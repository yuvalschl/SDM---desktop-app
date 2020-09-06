package listCells.customerCell;

import Costumer.Customer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

import java.io.IOException;


public class CustomerListViewCell extends ListCell<Customer> {

    @FXML
    private Label CustomerNameLabel;

    @FXML
    private Label CustomerIdLabel;

    @FXML
    private VBox cellVbox;

    private FXMLLoader fxmlLoader;

    @Override
    protected void updateItem(Customer customer, boolean empty) {
        super.updateItem(customer, empty);

        if(empty || customer == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/listCells/customerCell/customerListViewCell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            CustomerNameLabel.setText(String.valueOf(customer.getName()));
            CustomerIdLabel.setText(String.valueOf(customer.getId()));

            setText(null);
            setGraphic(cellVbox);
        }

    }

}