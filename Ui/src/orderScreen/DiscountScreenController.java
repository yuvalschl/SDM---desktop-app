package orderScreen;

import ItemPair.ItemAmountAndStore;
import Order.Order;
import Store.Discount;
import Store.Offer;
import appController.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import listCells.discountCell.discountCell;
import listCells.discountCell.offerCell;
import Store.DiscountOperator;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DiscountScreenController {

    @FXML private ListView<Discount> discountListView;
    @FXML private Label becuaseYouBoughtLabel;
    @FXML private ListView<Offer> offerListView;
    @FXML private Label youGetLabel;
    @FXML private Label forAdditionalLabel;
    private AppController appController;
    private Order order;
    private boolean oneOf;
    private SplitPane discountScreen;
    private ArrayList<Discount> discounts;
    private OrderSummeryController orderSummeryScreenController;
    private Point costumerLocation;
    private VBox orderSummeryScreen;
    private SplitPane orderScreenSplitPane;
    private OrderScreenController orderScreenController;

    public void setData(ArrayList<Discount> discounts, AppController appController, Order order, SplitPane discountScreen, Point location, VBox orderSummeryScreen, OrderSummeryController orderSummeryScreenController, SplitPane orderScreenSplitPane, OrderScreenController orderScreenController) {
        this.appController = appController;
        this.discounts = discounts;
        this.orderSummeryScreenController = orderSummeryScreenController;
        this.orderSummeryScreen = orderSummeryScreen;
        this.costumerLocation = location;
        this.orderScreenSplitPane = orderScreenSplitPane;
        this.orderScreenController = orderScreenController;
        displayEntitledDiscounts();
        this.order = order;
        this.discountScreen = discountScreen;
        discountScreen.setVisible(true);
    }

    private void displayEntitledDiscounts(){
        discountListView.getItems().clear();
        discountListView.getItems().addAll(discounts);
        discountListView.setCellFactory(e -> new discountCell());
    }
    @FXML//displays the information of the clicked discount in the discount list view
    public void displaySelectedDiscount(javafx.scene.input.MouseEvent mouseEvent) {
        Discount discount = discountListView.getSelectionModel().getSelectedItem();
        if( discount != null) {
            int itemID = discount.getIfYouBuy().getItemId();
            String becauseYouBoughtItemName = discount.getIfYouBuy().getQuantity() + " " + appController.getStoreManager().getAllItems().get(itemID).getName() + "s";
            becuaseYouBoughtLabel.textProperty().set(becauseYouBoughtItemName);
            if (discount.getThenYouGet().getDiscountOperator() == DiscountOperator.oneOf) {
                youGetLabel.textProperty().set("You get one of the items below:");
                oneOf = true;
            } else {
                youGetLabel.textProperty().set("You get all of the items below:");
                oneOf = false;
            }

            //set the offers list view
            offerListView.getItems().clear();
            offerListView.getItems().addAll(discount.getThenYouGet().getAllOffers());
            offerListView.setCellFactory(e -> new offerCell(appController.getStoreManager()));
        }
    }

    //method to display quantity when clicking on an offer
    public void displayQuantity(MouseEvent mouseEvent) {
        Offer offer = offerListView.getSelectionModel().getSelectedItem();
        if (offer != null)
            forAdditionalLabel.textProperty().set(Float.toString(offer.getForAdditional()) + " Shekels");
    }

    public void addToCartButtonAction(ActionEvent actionEvent) {
        Discount discount = discountListView.getSelectionModel().getSelectedItem();
        if(discount != null) {
            Map<Integer, ItemAmountAndStore> itemsToAdd = new HashMap<>();
            if (oneOf) {
                Offer offer = offerListView.getSelectionModel().getSelectedItem();
                if(offer != null)
                    itemsToAdd.put(offer.getItemId(), appController.getStoreManager().addDiscountItemToOrder(offer.getItemId(), order, discount, true));
            } else {
                itemsToAdd = appController.getStoreManager().addDiscountItemsToOrderAllOrNothing(order, discount);
            }
            ArrayList<ItemAmountAndStore> itemsToPutInTheList = new ArrayList<>();
            for(ItemAmountAndStore offerItem : itemsToAdd.values()){
                boolean itemInList = false;
                for(ItemAmountAndStore item : orderScreenController.getOrderSummaryTable().getItems()){
                    //if the offer item is already in the order summery
                    if(offerItem.getItemId() == item.getItemId() && item.getIsPartOfDiscount()){
                        item.setAmount(offerItem.getAmount());
                        itemInList = true;
                    }
                }
                if(!itemInList){
                    itemsToPutInTheList.add(offerItem);
                }
            }
            orderScreenController.getOrderSummaryTable().getItems().addAll(itemsToPutInTheList);
            orderScreenController.getOrderSummaryTable().refresh();
            discounts = appController.getStoreManager().getEntitledDiscounts(order);
            offerListView.getItems().clear();
            displayEntitledDiscounts();
        }
    }

    public void notInterestedBtnAction(ActionEvent actionEvent) {
        appController.getOrderScreenComponentController().getOrderSummaryTable().getItems().removeIf(item-> item.getIsPartOfDiscount());
        offerListView.getItems().clear();
        discountListView.getItems().clear();
        discountScreen.setVisible(false);
        orderScreenController.setInterestedInDiscount(false);
    }

    public void placeOrderBtnAction(ActionEvent actionEvent) {
        discountScreen.setVisible(false);
        orderScreenSplitPane.setVisible(false);
        orderSummeryScreenController.setData(appController, order, costumerLocation, orderSummeryScreen, orderScreenSplitPane, orderScreenController);
    }
}