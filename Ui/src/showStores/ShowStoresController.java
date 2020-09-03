package showStores;

import DtoObjects.DtoStore;
import Store.Store;
import appController.AppController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import showStores.storeInfo.StoreInfoController;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class ShowStoresController implements Initializable {

    private AppController appController;
    private ObservableMap<Integer, Store> storesObservableMap;

    @FXML
    private SplitPane storeInfo;
    @FXML
    private StoreInfoController storeInfoComponentController;
    @FXML
    private ListView<Store> storesLV;

    public AppController getAppController() {
        return appController;
    }

    public ShowStoresController(AppController appController) {
        this.appController = appController;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        storeInfoComponentController.setShowStoresController(this);

        //set listener to the map of stores so the list view updates dynamically
        storesObservableMap = FXCollections.observableMap(appController.getStoreManager().getAllStores());
        storesObservableMap.addListener(new MapChangeListener<Integer, Store>() {
            @Override
            public void onChanged(Change<? extends Integer, ? extends Store> change) {
                storesLV.getItems().add(change.getValueAdded());
                appController.getStoreManager().getAllStores().put(change.getKey(), change.getValueAdded());
            }
        });

        // this set the on click for the list view
        storesLV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Store>() {
            @Override
            public void changed(ObservableValue<? extends Store> observable, Store oldValue, Store newValue) {
                storeInfoComponentController.getStoreNameLabel().setText(newValue.getName());
                storeInfoComponentController.getStoreIdLabel().setText(String.valueOf(newValue.getSerialNumber()));
                storeInfoComponentController.getPPKLabel().setText(String.valueOf(newValue.getPPK()));
                storeInfoComponentController.getShippingPaymentLabel().setText(String.valueOf(newValue.getTotalDeliveriesCost()));
            }
        });

        for(Map.Entry<Integer, Store> store : appController.getStoreManager().getAllStores().entrySet()){
            storesLV.getItems().add(store.getValue());
        }
        storesLV.setCellFactory(storeListView -> new StoreListViewCell());
    }
}
