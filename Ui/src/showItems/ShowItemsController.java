package showItems;

import Item.Item;
import appController.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ShowItemsController {
    private AppController appController;
    
    @FXML
    private ListView<Item> itemsList;

    public void setData(AppController appController){
        this.appController = appController;

        itemsList.getItems().addAll(appController.getStoreManager().getAllItems().values());
        itemsList.setCellFactory(e -> new ItemListCellController(appController));
    }
}
