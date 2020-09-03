package showStores.storeInfo.itemInfo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import showStores.storeInfo.StoreInfoController;

public class ItemInfoController {

    private StoreInfoController storeInfoController;

    @FXML
    private Label itemNameLabel;

    @FXML
    private Label itmeIdLabel;

    @FXML
    private Label itemSellByLabel;

    @FXML
    private Label pricePerUnitLabel;

    @FXML
    private Label amountSoldLabel;

    public void setStoreInfoController(StoreInfoController storeInfoController) {
        this.storeInfoController = storeInfoController;
    }

    public Label getItemNameLabel() {
        return itemNameLabel;
    }

    public void setItemNameLabel(Label itemNameLabel) {
        this.itemNameLabel = itemNameLabel;
    }

    public Label getItmeIdLabel() {
        return itmeIdLabel;
    }

    public void setItmeIdLabel(Label itmeIdLabel) {
        this.itmeIdLabel = itmeIdLabel;
    }

    public Label getItemSellByLabel() {
        return itemSellByLabel;
    }

    public void setItemSellByLabel(Label itemSellByLabel) {
        this.itemSellByLabel = itemSellByLabel;
    }

    public Label getPricePerUnitLabel() {
        return pricePerUnitLabel;
    }

    public void setPricePerUnitLabel(Label pricePerUnitLabel) {
        this.pricePerUnitLabel = pricePerUnitLabel;
    }

    public Label getAmountSoldLabel() {
        return amountSoldLabel;
    }

    public void setAmountSoldLabel(Label amountSoldLabel) {
        this.amountSoldLabel = amountSoldLabel;
    }
}
