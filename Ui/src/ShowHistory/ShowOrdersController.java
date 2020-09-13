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
import listCells.discountCell.discountCell;
import listCells.orderCell.OrderHistoryListViewCell;
import listCells.orderCell.OrderListViewCell;
import org.omg.CORBA.INTERNAL;

import java.util.ArrayList;
import java.util.Set;

public class ShowOrdersController {

    @FXML private ListView<Order> OrderView;
    @FXML private TableView<Item> itemsTable;
    @FXML private TableColumn<Item, Integer> itemIdCol;
    @FXML private TableColumn<Item, String> itemNameCol;
    @FXML private TableColumn<Item, String> itemSellByCol;
    @FXML private TableColumn<Item, Float> itemAmountSoldCol;
    @FXML private TableColumn<Item, Float> itemPriceCol;
    @FXML private TableColumn<Item, Float> itemTotalPriceCol;
    @FXML private TableColumn<Item, String> itemPartOfDiscountCol;
    @FXML private TableView<Store> storesTable;
    @FXML private TableColumn<Store,Integer> storeIdCol;
    @FXML private TableColumn<Store, String> storeNameCol;
    @FXML private TableColumn<Store, Float> storeDistanceToClientcol;
    @FXML private TableColumn<Store, Float> storePkkCol;
    @FXML private TableColumn<Store, Float> storeShippingCostCol;

    // private ObservableSet<Integer, Store> storesObservableMap;
    private AppController appController;

    public void setData(AppController appController){
        this.appController = appController ;
        OrderView.getItems().addAll(appController.getStoreManager().getAllOrders());
        OrderView.setCellFactory(e -> new OrderHistoryListViewCell());
    }



    public ObservableList<ItemCell> getItemsCells(ArrayList<ItemAmountAndStore> itemAmountAndStores){

        ObservableList<ItemCell> itemCells = FXCollections.observableArrayList();

        for (ItemAmountAndStore item: itemAmountAndStores){
            itemCells.add(new ItemCell(item));
        }
        return  itemCells;
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }
}
