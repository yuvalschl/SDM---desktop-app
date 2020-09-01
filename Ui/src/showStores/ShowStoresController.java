package showStores;

import DtoObjects.DtoStore;
import appController.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.Map;

public class ShowStoresController {

    private AppController appController;

    @FXML
    private ListView<String> storesLV;

    public ShowStoresController(AppController appController) {
        this.appController = appController;
    }

    public void initialize(){
        for(Map.Entry<Integer, DtoStore> store : appController.getStoreManager().getAllDtoStores().entrySet()){
            storesLV.getItems().add(store.getValue().getName());
        }
    }

}
