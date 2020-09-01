package showItems;

import DtoObjects.DtoItem;
import appController.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.Map;

public class ShowItemsController {
    private AppController appController;
    @FXML
    private ListView<String> itemListView;
    public ShowItemsController(AppController appController) {
        this.appController = appController;
    }
    public void initialize(){
        for (Map.Entry<Integer, DtoItem> item: appController.getStoreManager().getAllDtoItems().entrySet())
            itemListView.getItems().add(item.getValue().getName());
    }
}
