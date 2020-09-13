package orderScreen;

import Item.Item;
import Order.Order;
import Store.Discount;
import Store.Offer;
import appController.AppController;
import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import listCells.discountCell.discountCell;
import listCells.discountCell.offerCell;
import Store.DiscountOperator;
import java.util.ArrayList;
import Store.MyThenYouGet;


public class DiscountScreenController {

    @FXML private ListView<Discount> discountListView;
    @FXML private Label becuaseYouBoughtLabel;
    @FXML private ListView<Offer> offerListView;
    @FXML private Label youGetLabel;
    @FXML private Label forAdditionalLabel;
    private ObservableMap<Integer, Discount> discountObservableMap;
    private AppController appController;
    private Order order;
    private boolean oneOf;
    private SplitPane discountScreen;
    private ArrayList<Discount> discounts;
    public void setData(ArrayList<Discount> discounts, AppController appController, Order order, SplitPane discountScreen){
        this.appController = appController;
        this.discounts = discounts;
        discountListView.getItems().addAll(discounts);
        discountListView.setCellFactory(e -> new discountCell());
        this.order = order;
        this.discountScreen = discountScreen;
        discountScreen.setVisible(true);
        /*discountListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Discount>() {
            @Override
            public void changed(ObservableValue<? extends Discount> observable, Discount oldValue, Discount newValue) {


            }
        });*/

    }

    @FXML//displays the information of the clicked discount in the discount list view
    public void displaySelectedDiscount(javafx.scene.input.MouseEvent mouseEvent) {
        Discount discount = discountListView.getSelectionModel().getSelectedItem();
        int itemID = discount.getIfYouBuy().getItemId();
        String becauseYouBoughtItemName = discount.getIfYouBuy().getQuantity()+" "+appController.getStoreManager().getAllItems().get(itemID).getName()+"s";
        becuaseYouBoughtLabel.textProperty().set(becauseYouBoughtItemName);
        if (discount.getThenYouGet().getDiscountOperator() == DiscountOperator.oneOf){
            youGetLabel.textProperty().set("You get one of the items below:");
            oneOf = true;
        }
        else {
            youGetLabel.textProperty().set("You get all of the items below:");
            appController.getStoreManager().addDiscountItemsToOrderAllOrNothing(order, discount);
            oneOf = false;
        }

        //set the offers list view
        offerListView.getItems().clear();
        offerListView.getItems().addAll(discount.getThenYouGet().getAllOffers());
        offerListView.setCellFactory(e -> new offerCell(appController.getStoreManager()));
    }

    //method to display quantity when clicking on an offer
    public void displayQuantity(MouseEvent mouseEvent) {
       Offer offer = offerListView.getSelectionModel().getSelectedItem();
        forAdditionalLabel.textProperty().set(Float.toString(offer.getForAdditional())+" Shekels");
    }

    public void addToOrderButtonAction(ActionEvent actionEvent) {//TODO: add an update for the discount entiteled list after choosing an offer
        Offer offer = offerListView.getSelectionModel().getSelectedItem();
        Discount discount = discountListView.getSelectionModel().getSelectedItem();
        if(oneOf){
            appController.getStoreManager().addDiscountItemToOrder(offer.getItemId(), order, discount);
        }
        else {
            appController.getStoreManager().addDiscountItemsToOrderAllOrNothing(order, discount);
        }
        for (Discount currDiscount: discounts){
        /*    if(discount.getIfYouBuy().getItemId() == offer.getItemId())*/
        }
        //discountScreen.setVisible(false);
    }

    public void notInterestedBtnAction(ActionEvent actionEvent) {
        offerListView.getItems().clear();
        discountListView.getItems().clear();
        discountScreen.setVisible(false);
    }

}
