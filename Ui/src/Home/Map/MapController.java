package Home.Map;

import Costumer.Customer;
import Store.Store;
import StoreManager.StoreManager;
import appController.AppController;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Comparator;

public class MapController {

    @FXML private GridPane mapGrid;
    private AppController appController;

    public GridPane getMapGrid() {
        return mapGrid;
    }

    public void setSize(AppController appController) throws IOException {
        Task<Boolean> task = new Task<Boolean>() {
            private void onFinish(){
                Platform.runLater(() -> mapGrid.visibleProperty().setValue(true));
                Platform.runLater(() -> appController.getHomeComponentController().getMapScrollPane().setVisible(true));
                Platform.runLater(() -> appController.getHomeComponentController().getMapScrollPane().layout());
            }

            @Override
            protected Boolean call() throws Exception {
                updateMessage("Building map");
                int mapWidth = calcMapWidth(appController.getStoreManager());
                int mapHeight = calcMapHeight(appController.getStoreManager());
                Customer customer = null;
                Store store = null;
                int progress = 85;
                for (int i = 0; i < mapWidth + 1; i++) {
                    if(((i + 1) % (mapWidth / 15) == 0)){
                        updateProgress(progress++, 100);
                    }
                    for (int j = 0; j < mapHeight + 1; j++) {
                        customer = customerInLocation(appController.getStoreManager(), i, j);
                        store = storeInLocation(appController.getStoreManager(), i, j);
                        int finalI = i;
                        int finalJ = j;
                        if (customer != null) {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Home/Map/MapCell/Customer/customer.fxml"));
                            Pane pane = fxmlLoader.load();
                            String customerToolTip = String.format("%1$s \n %2$s \n Location: (%3$s,%4$s)", customer.getName(), customer.getId(), customer.getX(), customer.getY());
                            Tooltip.install(pane, new Tooltip(customerToolTip));
                            Platform.runLater(() -> mapGrid.add(pane, finalI, finalJ));
                        } else if (store != null) {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Home/Map/MapCell/Store/store.fxml"));
                            Pane pane = fxmlLoader.load();
                            String customerToolTip = String.format("%1$s \n %2$s \n Location: (%3$s,%4$s) \n PPK: %5$s \n Total orders %6$s",
                                    store.getName(), store.getSerialNumber(), store.getX(), store.getY(), store.getPPK(), store.getAllOrders().size());
                            Tooltip.install(pane, new Tooltip(customerToolTip));
                            Platform.runLater(() -> mapGrid.add(pane, finalI, finalJ));
                        } else {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Home/Map/MapCell/empty.fxml"));
                            Pane pane = fxmlLoader.load();
                            Platform.runLater(() -> mapGrid.add(pane, finalI, finalJ));
                        }

                    }
                }
                updateMessage("File loaded successfully");
                updateProgress(100,100);
                onFinish();;
                return true;
            }
        };
        Thread mapThread = new Thread(task);
        appController.getHomeComponentController().getFileProgressBar().progressProperty().bind(task.progressProperty());
        appController.getHomeComponentController().getLoadActionText().textProperty().bind(task.messageProperty());
        appController.getHomeComponentController().getProgressPercentText().textProperty().bind(Bindings.concat(
                Bindings.format(
                        "%.0f",
                        Bindings.multiply(
                                task.progressProperty(),
                                100)),
                " %"));
        mapThread.start();
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
