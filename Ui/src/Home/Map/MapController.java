package Home.Map;

import Costumer.Customer;
import Store.Store;
import StoreManager.StoreManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

import java.io.IOException;
import java.util.Comparator;
import java.util.Optional;

public class MapController {

    @FXML private GridPane mapGrid;
    @FXML private ColumnConstraints rowScale;
    @FXML private RowConstraints colScale;

    public void setSize(StoreManager storeManager) throws IOException {
        int mapWidth = calcMapWidth(storeManager);
        int mapHeight = calcMapHeight(storeManager);

        for(int i = 0 ; i < mapWidth + 2 ; i++){
            for(int j = 0; j < mapHeight + 2; j++){
                if(customerInLocation(storeManager,i,j)){
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Home/Map/MapCell/customer.fxml"));
                    Pane pane = fxmlLoader.load();
                    mapGrid.add(pane,i,j);
                }
                else if(storeInLocation(storeManager,i,j)){
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Home/Map/MapCell/store.fxml"));
                    Pane pane = fxmlLoader.load();
                    mapGrid.add(pane,i,j);
                }
                else {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Home/Map/MapCell/empty.fxml"));
                    Pane pane = fxmlLoader.load();
                    mapGrid.add(pane,i,j);
                }
            }
        }
        mapGrid.visibleProperty().setValue(true);
    }

    private boolean storeInLocation(StoreManager storeManager, int x, int y){
        boolean storeInLocation = false;
        for(Store store : storeManager.getAllStores().values()){
            if(store.getX() == x && store.getY() == y){
                storeInLocation = true;
                break;
            }
        }

        return storeInLocation;
    }

    private boolean customerInLocation(StoreManager storeManager, int x, int y){
        boolean customerInLocation = false;
        for(Customer customer : storeManager.getAllCustomers().values()){
            if(customer.getX() == x && customer.getY() == y){
                customerInLocation = true;
                break;
            }
        }

        return customerInLocation;
    }

    private int calcMapWidth(StoreManager storeManager){
        return Math.max(storeManager.getAllStores().values().stream().max(Comparator.comparing(Store::getX)).get().getX(),
                storeManager.getAllCustomers().values().stream().max(Comparator.comparing(Customer::getX)).get().getX());


    }

    private int calcMapHeight(StoreManager storeManager){
        return Math.max(storeManager.getAllStores().values().stream().max(Comparator.comparing(Store::getY)).get().getY(),
                storeManager.getAllCustomers().values().stream().max(Comparator.comparing(Customer::getY)).get().getY());
    }
}
