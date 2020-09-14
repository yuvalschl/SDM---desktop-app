package orderScreen;


import Order.Order;
import javafx.fxml.FXML;

import java.awt.event.ActionEvent;

public class OrderSummeryController {
    private Order order;
    private OrderScreenController orderScreenController;
    public void setData(Order order, OrderScreenController orderScreenController){
        this.order = order;
        this.orderScreenController = orderScreenController;
    }

    public void approveOrderAction(javafx.event.ActionEvent actionEvent) {

    }

    public void cancelOrderAction(javafx.event.ActionEvent actionEvent) {

    }
}
