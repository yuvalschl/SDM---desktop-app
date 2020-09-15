package orderScreen;


import Order.Order;
import ShowHistory.DisplaySingleOrderController;
import appController.AppController;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.TimeUnit;
//TODO: add a label that reflects the user choice for a dew seconds and desapear
public class OrderSummeryController {
    private Order order;
    private AppController appController;
    private SplitPane orderSummeryScreen;
    private SplitPane orderScreen;
    private OrderScreenController orderScreenController;
    @FXML private DisplaySingleOrderController singleOrderComponentController;
    @FXML private Pane singleOrderComponent;
    @FXML private Label allItemsPriceLabel;
    @FXML private  Label totalShippingCostLabel;
    @FXML private  Label totalOrderCostLabel;

    public void setData( AppController appController, Order order, Point costumerLocation, SplitPane summeryScreen, SplitPane orderScreen, OrderScreenController orderScreenController){
        this.order = order;
        this.appController = appController;
        this.orderSummeryScreen = summeryScreen;
        this.orderScreen = orderScreen;
        this.orderScreenController = orderScreenController;
        singleOrderComponentController.setData(appController, order.getItemAmountAndStores(), order, costumerLocation);
        orderSummeryScreen.setVisible(true);
        orderScreenController.setInterestedInDiscount(true);
        allItemsPriceLabel.textProperty().set(Float.toString(order.getTotalPriceOfItems()));
        totalShippingCostLabel.textProperty().set(Float.toString(order.getShippingCost()));
        totalOrderCostLabel.textProperty().set(Float.toString(order.getTotalCost()));
    }

    public void approveOrderAction(javafx.event.ActionEvent actionEvent) throws InterruptedException {
      //  approvedOrCanceledLabel.textProperty().set("Order was made successfully");
        orderSummeryScreen.setVisible(false);
        orderScreenController.clearAction();
        orderScreen.setVisible(true);
        appController.getStoreManager().placeOrder(order);
        appController.getShowOrdersController().setData(appController);
    }

    public void cancelOrderAction(javafx.event.ActionEvent actionEvent) {
        orderSummeryScreen.setVisible(false);
        orderScreenController.clearAction();
        orderScreen.setVisible(true);
    }
}
