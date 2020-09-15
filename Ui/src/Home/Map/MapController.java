package Home.Map;

import Costumer.Customer;
import Store.Store;
import StoreManager.StoreManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Comparator;

public class MapController {

    @FXML private GridPane mapGrid;


    public void setSize(StoreManager storeManager) throws IOException {
        int mapWidth = calcMapWidth(storeManager);
        int mapHeight = calcMapHeight(storeManager);
        Customer customer = null;
        Store store = null;
        for(int i = 0 ; i < mapWidth + 1 ; i++){
            for(int j = 0; j < mapHeight + 1; j++){
                customer = customerInLocation(storeManager,i,j);
                store = storeInLocation(storeManager,i,j);
                if(customer != null){
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Home/Map/MapCell/Customer/customer.fxml"));
                    Pane pane = fxmlLoader.load();
                    String customerToolTip = String.format("%1$s \n %2$s \n Location: (%3$s,%4$s)", customer.getName(), customer.getId(), customer.getX(), customer.getY());
                    Tooltip.install(pane, new Tooltip(customerToolTip));
                    mapGrid.add(pane,i,j);
                }
                else if(store != null){
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Home/Map/MapCell/Store/store.fxml"));
                    Pane pane = fxmlLoader.load();
                    String customerToolTip = String.format("%1$s \n %2$s \n Location: (%3$s,%4$s) \n PPK: %5$s \n Total orders %6$s",
                            store.getName(), store.getSerialNumber(), store.getX(), store.getY(), store.getPPK(), store.getAllOrders().size());
                    Tooltip.install(pane, new Tooltip(customerToolTip));
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

    private Store storeInLocation(StoreManager storeManager, int x, int y){
        Store storeInLocation = null;
        for(Store store : storeManager.getAllStores().values()){
            if(store.getX() == x && store.getY() == y){
                storeInLocation = store;
                break;
            }
        }

        return storeInLocation;
    }

    private Customer customerInLocation(StoreManager storeManager, int x, int y){
        Customer customerInLocation = null;
        for(Customer customer : storeManager.getAllCustomers().values()){
            if(customer.getX() == x && customer.getY() == y){
                customerInLocation = customer;
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
