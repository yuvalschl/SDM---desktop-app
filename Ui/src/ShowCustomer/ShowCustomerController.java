package ShowCustomer;

import Costumer.Customer;
import appController.AppController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.*;

public class ShowCustomerController {

    @FXML private TableColumn<Customer, Integer> IDCol;
    @FXML private TableColumn<Customer, String> NameCol;
    @FXML private TableColumn<Customer, String> locationCol;
    @FXML private TableColumn<Customer, Integer> numOfOrdersCol;
    @FXML private TableColumn<Customer, Float> averageItemsCost;
    @FXML private TableColumn<Customer, Float> averageShippingCost;
    @FXML private TableView<Customer> customerTable;


    private AppController appController;

    public void setData(AppController appController){
        this.appController = appController;
        IDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        NameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("locationString"));
        numOfOrdersCol.setCellValueFactory(new PropertyValueFactory<>("numberOfOrdersMade"));
        averageItemsCost.setCellValueFactory(new PropertyValueFactory<>("averageOrdersWithoutShippingPrice"));
        averageItemsCost.setCellFactory(tc -> new TableCell<Customer, Float>(){
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                if(empty){
                    setText(null);
                }
                else {
                    setText(String.format(appController.getDecimalFormat().format(item)));
                }
            }
        });
        averageShippingCost.setCellValueFactory(new PropertyValueFactory<>("averageOrdersShippingPrice"));
        averageShippingCost.setCellFactory(tc -> new TableCell<Customer, Float>(){
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                if(empty){
                    setText(null);
                }
                else {
                    setText(String.format(appController.getDecimalFormat().format(item)));
                }
            }
        });

        customerTable.getItems().clear();
       // appController.getStoreManager().getAllCustomers().forEach((id,customer) ->customerTable.getItems().add(customer));
        customerTable.getItems().addAll( appController.getStoreManager().getAllCustomers().values());
    }

    public void updateCustomerToShow(){
        customerTable.getItems().clear();
        customerTable.getItems().addAll( appController.getStoreManager().getAllCustomers().values());
    }

    public AppController getAppController() { return appController; }

    public void setAppController(AppController appController) { this.appController = appController; }
}
