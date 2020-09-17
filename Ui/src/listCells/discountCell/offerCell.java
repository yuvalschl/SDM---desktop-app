package listCells.discountCell;

import Store.Offer;
import StoreManager.StoreManager;
import appController.AppController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class offerCell extends ListCell<Offer> {

    @FXML private VBox cellVbox;

    @FXML private Label offerLabel;
    private FXMLLoader fxmlLoader;
    private StoreManager storeManager;
    public offerCell(StoreManager storeManager){
        this.storeManager = storeManager;
    }

    @Override
    protected void updateItem(Offer offer, boolean empty) {
        super.updateItem(offer, empty);

        if(empty || offer == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/listCells/discountCell/offerListViewCell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            String offerString = offer.getQuantity() +" "+ storeManager.getAllItems().get(offer.getItemId()).getName()+" for "+ offer.getForAdditional();
            offerLabel.setText(offerString);

            setText(null);
            setGraphic(cellVbox);
        }

    }
}
