package ShowHistory;

import Item.Item;
import ItemPair.ItemAmountAndStore;
import Order.Order;
import Store.Store;
import appController.AppController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import listCells.discountCell.discountCell;
import listCells.orderCell.OrderHistoryListViewCell;
import listCells.orderCell.OrderListViewCell;
import org.omg.CORBA.INTERNAL;

import java.util.ArrayList;
import java.util.Set;

public class ShowOrdersController {

    @FXML private ListView<Order> OrderView;
    @FXML private Pane displaySingleOrderComponent;
    @FXML private DisplaySingleOrderController displaySingleOrderComponentController;

    // private ObservableSet<Integer, Store> storesObservableMap;
    private AppController appController;

    public void setData(AppController appController){
        this.appController = appController ;
        OrderView.getItems().clear();
        OrderView.getItems().addAll(appController.getStoreManager().getAllOrders());
        OrderView.setCellFactory(e -> new OrderHistoryListViewCell());
    }





    public void setAppController(AppController appController) {
        this.appController = appController;
    }
}
