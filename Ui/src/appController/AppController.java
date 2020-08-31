package appController;

import Exceptions.DuplicateValueException;
import Exceptions.InvalidValueException;
import Exceptions.ItemNotSoldException;
import StoreManager.StoreManager;
import header.HeaderController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import Jaxb.JaxbClassToStoreManager;
import Jaxb.XmlToObject;
import optionsMenu.OptionsMenuController;
import orderScreen.OrderScreenController;


import java.io.File;
import java.io.IOException;

public class AppController {
    private StoreManager storeManager;

    @FXML private HBox headerComponent;
    @FXML private HeaderController headerComponentController;
    @FXML private ScrollPane optionsMenuComponent;
    @FXML private OptionsMenuController optionsMenuComponentController;
    @FXML private TextField infoField;
    @FXML private ScrollPane orderScreenComponent;
    @FXML private OrderScreenController orderScreenComponentController;


    @FXML
    public void initialize() throws IOException {
        if(headerComponentController != null && optionsMenuComponentController != null){
            headerComponentController.setAppController(this);
            optionsMenuComponentController.setAppController(this);
        }
    }

    public void loadXmlAction() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(headerComponent.getScene().getWindow());
        if (file != null){
            JaxbClassToStoreManager jaxbClassToStoreManager = new JaxbClassToStoreManager();
            try {
                storeManager = jaxbClassToStoreManager.convertJaxbClassToStoreManager(XmlToObject.fromXmlFileToObject(file));
            } catch (DuplicateValueException e) {
                e.printStackTrace();
            } catch (InvalidValueException e) {
                e.printStackTrace();
            } catch (ItemNotSoldException e) {
                e.printStackTrace();
            }
        }
    }

    public StoreManager getStoreManager() {
        return storeManager;
    }

    public void showStoresAction() {

    }
}
