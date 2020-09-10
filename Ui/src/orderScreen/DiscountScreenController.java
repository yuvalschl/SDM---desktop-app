package orderScreen;

import Store.Discount;
import Store.Store;
import appController.AppController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import listCells.discountCell.discountCell;
import listCells.storeCell.StoreListViewCell;

import java.util.ArrayList;
import java.util.Map;

public class DiscountScreenController {

    @FXML private ListView<Discount> discountListView;

    private ObservableMap<Integer, Discount> discountObservableMap;
    private AppController appController;

    public void setData(ArrayList<Discount> discounts){
        this.appController = appController;
        discountListView.getItems().addAll(discounts);
        discountListView.setCellFactory(e -> new discountCell());

    }
}
