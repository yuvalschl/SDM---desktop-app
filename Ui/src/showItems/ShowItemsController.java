package showItems;

import appController.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.util.Map;

public class ShowItemsController {
    private AppController appController;

    @FXML
    private ListView<String> itemListView;
    @FXML
    private VBox headerVBComponent;


    public ShowItemsController(AppController appController) {
        this.appController = appController;
    }

    public AppController getAppController() {
        return appController;
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }


/*    public void initialize(){
        headerVBComponent = appController.getHeaderComponent();
        for (Map.Entry<Integer, DtoItem> item: appController.getStoreManager().getAllDtoItems().entrySet())
            itemListView.getItems().add(item.getValue().getName());
    }*/
}
