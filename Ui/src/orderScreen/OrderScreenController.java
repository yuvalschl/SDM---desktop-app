package orderScreen;

import appController.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import Costumer.Customer;

import java.util.Date;

public class OrderScreenController {

    private AppController appController;

    @FXML
    private ComboBox<String> CustomerCB;

    @FXML
    private DatePicker orderDatePicker;

    @FXML
    private CheckBox dynamicOrderCB;

    @FXML
    private ComboBox<?> storeCB;

    public OrderScreenController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    public void initialize(){
        for(Customer costumer : appController.getStoreManager().getAllCustomers().values()){
            CustomerCB.getItems().add(costumer.getName());
        }
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }
}
