package showStores.storeInfo.orderInfo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OrderInfoController {

    @FXML
    private Label dateLabel;

    @FXML
    private Label amountOfItemsLabel;

    @FXML
    private Label itemCostLabel;

    @FXML
    private Label shippingCostLabel;

    @FXML
    private Label totalCostLabel;

    public Label getDateLabel() {
        return dateLabel;
    }

    public void setDateLabel(Label dateLabel) {
        this.dateLabel = dateLabel;
    }

    public Label getAmountOfItemsLabel() {
        return amountOfItemsLabel;
    }

    public void setAmountOfItemsLabel(Label amountOfItemsLabel) {
        this.amountOfItemsLabel = amountOfItemsLabel;
    }

    public Label getItemCostLabel() {
        return itemCostLabel;
    }

    public void setItemCostLabel(Label itemCostLabel) {
        this.itemCostLabel = itemCostLabel;
    }

    public Label getShippingCostLabel() {
        return shippingCostLabel;
    }

    public void setShippingCostLabel(Label shippingCostLabel) {
        this.shippingCostLabel = shippingCostLabel;
    }

    public Label getTotalCostLabel() {
        return totalCostLabel;
    }

    public void setTotalCostLabel(Label totalCostLabel) {
        this.totalCostLabel = totalCostLabel;
    }
}
