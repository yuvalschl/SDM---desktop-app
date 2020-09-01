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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import showStores.storeInfo.StoreInfoController;

import java.util.Map;

public class ShowStoresController {

    private AppController appController;
    private ObservableMap<Integer, Store> storesObservableMap;

    @FXML
    private SplitPane storeInfo;
    @FXML
    private StoreInfoController storeInfoComponentController;
    @FXML
    private ListView<String> storesLV;

    public AppController getAppController() {
        return appController;
    }

    public ShowStoresController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    public void initialize(){
        storeInfoComponentController.setShowStoresController(this);

        //set listener to the map of stores so the list view updates dynamically
        storesObservableMap = FXCollections.observableMap(appController.getStoreManager().getAllStores());
        storesObservableMap.addListener(new MapChangeListener<Integer, Store>() {
            @Override
            public void onChanged(Change<? extends Integer, ? extends Store> change) {
                storesLV.getItems().add(change.getValueAdded().getName());
                appController.getStoreManager().getAllStores().put(change.getKey(), change.getValueAdded());
            }
        });

        //TODO get the store id from the list view somehow
        storesLV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                storeInfoComponentController.getStoreNameLabel().setText(newValue);
            }
        });

        for(Map.Entry<Integer, DtoStore> store : appController.getStoreManager().getAllDtoStores().entrySet()){
            storesLV.getItems().add(store.getValue().getName());
        }
    }
}
