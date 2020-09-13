package ShowHistory;

import Item.Item;
import Store.Store;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DisplaySingleOrderController {
    @FXML
    private TableView<Item> itemsTable;
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
}
