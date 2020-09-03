package showStores;

import Store.Store;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

import java.io.IOException;


public class StoreListViewCell extends ListCell<Store> {

    @FXML
    private Label storeNameLabel;

    @FXML
    private Label storeIdLabel;

    @FXML
    private VBox cellVbox;

    private FXMLLoader fxmlLoader;

    @Override
    protected void updateItem(Store store, boolean empty) {
        super.updateItem(store, empty);

        if(empty || store == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/showStores/storeListViewCell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            storeNameLabel.setText(String.valueOf(store.getName()));
            storeIdLabel.setText(String.valueOf(store.getSerialNumber()));

            setText(null);
            setGraphic(cellVbox);
        }

    }
}