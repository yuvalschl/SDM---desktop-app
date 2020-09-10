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
        itemsList.getItems().clear();
        itemsList.getItems().addAll(appController.getStoreManager().getAllItems().values());
        itemsList.setCellFactory(e -> new ItemListCellController(appController));
    }

    public ListView<Item> getItemsList() {
        return itemsList;
    }

    public void setItemsList(ListView<Item> itemsList) {
        this.itemsList = itemsList;
    }

    public AppController getAppController() {
        return appController;
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }
}
