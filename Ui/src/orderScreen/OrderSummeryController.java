package orderScreen;


import Costumer.Customer;
import Order.Order;
import ShowHistory.DisplaySingleOrderController;
import appController.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.text.DecimalFormat;

//TODO: add a label that reflects the user choice for a dew seconds and desapear
public class OrderSummeryController {
    private Order order;
    private AppController appController;
    private VBox orderSummeryScreen;
    private SplitPane orderScreen;
    private OrderScreenController orderScreenController;
    private DecimalFormat decimalFormat;
    @FXML private DisplaySingleOrderController singleOrderComponentController;
    @FXML private Pane singleOrderComponent;
    @FXML private Label allItemsPriceLabel;
    @FXML private  Label totalShippingCostLabel;
    @FXML private  Label totalOrderCostLabel;

    public void setData(AppController appController, Order order, Point costumerLocation, VBox summeryScreen, SplitPane orderScreen, OrderScreenController orderScreenController){
        this.order = order;
        this.appController = appController;
        this.orderSummeryScreen = summeryScreen;
        this.orderScreen = orderScreen;
        this.orderScreenController = orderScreenController;
        singleOrderComponentController.setData(appController, order.getItemAmountAndStores(), order, costumerLocation);
        orderSummeryScreen.setVisible(true);
        decimalFormat = appController.getStoreManager().getDecimalFormat();
        orderScreenController.setInterestedInDiscount(true);
        allItemsPriceLabel.textProperty().set(Float.toString(Float.parseFloat(decimalFormat.format(order.getTotalPriceOfItems()))));
        totalShippingCostLabel.textProperty().set(Float.toString(Float.parseFloat(decimalFormat.format(order.getShippingCost()))));
        totalOrderCostLabel.textProperty().set(Float.toString(Float.parseFloat(decimalFormat.format(order.getTotalCost()))));
    }

    public void approveOrderAction(javafx.event.ActionEvent actionEvent) throws InterruptedException {
        orderSummeryScreen.setVisible(false);
        orderScreenController.clearAction();
        orderScreen.setVisible(true);
        appController.getStoreManager().placeOrder(order);
        appController.getShowOrdersController().setData(appController);
        //update the customer details with this order
        updateCustomerDetails();
        appController.getShowCostumerScreenController().updateCustomerToShow();
        appController.getShowItemsController().updateItemsToShow();
        singleOrderComponentController.getStoresTable().getItems().clear();
    }

    private void updateCustomerDetails(){
        Customer customer = orderScreenController.getCustomer();
        customer.setNumberOfOrdersMade(customer.getNumberOfOrdersMade()+1);
        customer.setTotalShippingCost(customer.getTotalShippingCost()+ order.getShippingCost());
        customer.setTotalOrdersWithoutShippingPrice(customer.getTotalOrdersWithoutShippingPrice()+order.getTotalPriceOfItems());
        float shippingCostAverage = customer.getTotalShippingCost()/customer.getNumberOfOrdersMade();
        float ordersWithoutShippingCostAverage = customer.getTotalOrdersWithoutShippingPrice()/customer.getNumberOfOrdersMade();
        customer.setAverageOrdersShippingPrice(shippingCostAverage);
        customer.setAverageOrdersWithoutShippingPrice(ordersWithoutShippingCostAverage);
        //TODO update show items, items amount
    }
    public void cancelOrderAction(javafx.event.ActionEvent actionEvent) {
        orderSummeryScreen.setVisible(false);
        orderScreenController.clearAction();
        orderScreen.setVisible(true);
    }
}
