package ShowHistory;

import Order.*;
import Store.Store;
import appController.AppController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import listCells.orderCell.OrderHistoryListViewCell;

import java.util.ArrayList;

public class ShowOrdersController {

    @FXML private ListView<Order> orderView;
    @FXML private Pane displaySingleOrderComponent;
    @FXML private DisplaySingleOrderController displaySingleOrderComponentController;

    // private ObservableSet<Integer, Store> storesObservableMap;
    private AppController appController;

    public void setData(AppController appController){
        this.appController = appController ;
        orderView.getItems().clear();
        orderView.getItems().addAll(appController.getStoreManager().getAllOrders());
        orderView.setCellFactory(e -> new OrderHistoryListViewCell());

        orderView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Order>() {
            @Override
            public void changed(ObservableValue<? extends Order> observable, Order oldValue, Order newValue) {
                displaySingleOrderComponentController.getStoresTable().getItems().clear();
                ArrayList<StoreOrder> storesToAdd = new ArrayList<>();
                for(Store store : newValue.getStores().values()){
                    if(store.getAllOrders().containsKey(newValue.getOrderId())){
                        storesToAdd.add(store.getAllOrders().get(newValue.getOrderId()));
                    }
                }
                displaySingleOrderComponentController.getStoresTable().getItems().addAll(storesToAdd);
                }
            });

/*        displaySingleOrderComponentController.getStoresTable().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<StoreOrder>() {
            @Override
            public void changed(ObservableValue<? extends StoreOrder> observable, StoreOrder oldValue, StoreOrder newValue) {

            }
        });*/
    }

    public ListView<Order> getOrderView() {
        return orderView;
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }
}
