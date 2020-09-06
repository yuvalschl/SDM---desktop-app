package appController;

import Exceptions.DuplicateValueException;
import Exceptions.InvalidValueException;
import Exceptions.ItemNotSoldException;
import StoreManager.StoreManager;
import header.HeaderController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import Jaxb.JaxbClassToStoreManager;
import Jaxb.XmlToObject;
import optionsMenu.OptionsMenuController;



import java.io.File;
import java.io.IOException;

public class AppController {
    private StoreManager storeManager;

    @FXML private VBox headerComponent;
    @FXML private HeaderController headerComponentController;
    @FXML private VBox optionsMenuComponent;
    @FXML private OptionsMenuController optionsMenuComponentController;

    private BooleanProperty xmlLoaded = new SimpleBooleanProperty(true);

    public BooleanProperty getXmlLoaded() {
        return xmlLoaded;
    }

    public void setXmlLoaded(BooleanProperty xmlLoaded) {
        this.xmlLoaded = xmlLoaded;
    }

    public VBox getHeaderComponent() {
        return headerComponent;
    }

    public void setHeaderComponent(VBox headerComponent) {
        this.headerComponent = headerComponent;
    }

    @FXML
    public void initialize() throws IOException {
        if(headerComponentController != null && optionsMenuComponentController != null){
            headerComponentController.setAppController(this);
            optionsMenuComponentController.setAppController(this);
            // bind buttons to enable them if xml is loaded
            optionsMenuComponentController.getShowStoresButton().disableProperty().bind(xmlLoaded);
            optionsMenuComponentController.getPlaceOrderButton().disableProperty().bind(xmlLoaded);
            optionsMenuComponentController.getShowItemsButton().disableProperty().bind(xmlLoaded);
        }



    }


    public HeaderController getHeaderComponentController() {
        return headerComponentController;
    }

    public void setHeaderComponentController(HeaderController headerComponentController) {
        this.headerComponentController = headerComponentController;
    }

    public OptionsMenuController getOptionsMenuComponentController() {
        return optionsMenuComponentController;
    }

    public void setOptionsMenuComponentController(OptionsMenuController optionsMenuComponentController) {
        this.optionsMenuComponentController = optionsMenuComponentController;
    }

    public void loadXmlAction() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(headerComponent.getScene().getWindow());
        if (file != null){
            JaxbClassToStoreManager jaxbClassToStoreManager = new JaxbClassToStoreManager();
            try {
                storeManager = jaxbClassToStoreManager.convertJaxbClassToStoreManager(XmlToObject.fromXmlFileToObject(file));
            } catch (DuplicateValueException | InvalidValueException | ItemNotSoldException e) {
                e.printStackTrace();
            }
            getXmlLoaded().setValue(false);
            headerComponentController.getLoadActionText().setText("File: "+file.getAbsolutePath());//TODO: move this into initlaize with bind
        }
        else
            headerComponentController.getLoadActionText().setText("Error! no file loaded");
    }


    public StoreManager getStoreManager() {
        return storeManager;
    }

}
