package Home;


import Home.Map.MapController;
import Jaxb.JaxbClassToStoreManager;
import StoreManager.StoreManager;
import appController.AppController;
import appController.Main;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class HomeController {


    private AppController appController;
    private File file;
    private BooleanProperty fileChosen = new SimpleBooleanProperty(false);

    @FXML private Button chooseFileButton;
    @FXML private Button loadXmlButton;
    @FXML private Text loadActionText;
    @FXML private ProgressBar fileProgressBar;
    @FXML private Label progressPercentText;
    @FXML private GridPane mapGrid;
    @FXML private ScrollPane mapScrollPane;
    @FXML private AnchorPane mapAnchorPane;
    @FXML private MapController mapGridController;

    public ProgressBar getFileProgressBar() {
        return fileProgressBar;
    }

    public Label getProgressPercentText() {
        return progressPercentText;
    }

    public ScrollPane getMapScrollPane() {
        return mapScrollPane;
    }

    public Text getLoadActionText() {
        return loadActionText;
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    public void initialize(){
        mapScrollPane.prefHeightProperty().bind(mapGrid.heightProperty());
        mapScrollPane.prefWidthProperty().bind(mapGrid.widthProperty());
        mapScrollPane.setVisible(false);
        mapGrid.visibleProperty().setValue(false);
        loadXmlButton.disableProperty().bind(fileChosen.not());
    }

    public void loadXmlAction() throws InterruptedException {
        StoreManager storeManager = new StoreManager();
        Task<StoreManager> jaxbClassToStoreManager = new JaxbClassToStoreManager(storeManager,file, appController.getXmlLoaded());
        jaxbClassToStoreManager.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                appController.setStoreManager(jaxbClassToStoreManager.getValue());
                updateData();
            }
        });

        loadActionText.textProperty().unbind();
        fileProgressBar.progressProperty().unbind();
        progressPercentText.textProperty().unbind();
        loadActionText.textProperty().bind(jaxbClassToStoreManager.messageProperty());
        fileProgressBar.progressProperty().bind(jaxbClassToStoreManager.progressProperty());
        progressPercentText.textProperty().bind(Bindings.concat(
                Bindings.format(
                        "%.0f",
                        Bindings.multiply(
                                jaxbClassToStoreManager.progressProperty(),
                                100)),
                " %"));

        Thread thread = new Thread(jaxbClassToStoreManager);
        thread.start();
    }

    /**
     * clear the old data when loading a new file
     */
    public void clearData(){
        appController.setStoreManager(new StoreManager());
        appController.getShowStoresComponentController().setData(appController);
        appController.getOrderScreenComponentController().setData(appController);
        appController.getShowItemsController().setData(appController);
        appController.getAddStoreComponentController().setData(appController);
        appController.getShowOrdersController().setData(appController);
    }

    /**
     * update the data when loading a new file
     */
    public void updateData(){
        appController.getShowStoresComponentController().setData(appController);
        appController.getOrderScreenComponentController().setData(appController);
        appController.getShowItemsController().setData(appController);
        appController.getAddStoreComponentController().setData(appController);
        appController.getShowOrdersController().setData(appController);
        appController.getShowCostumerScreenController().setData(appController);
        try {
            mapGridController.setSize(appController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void chooseFileAction(){
        FileChooser fileChooser = new FileChooser();
        this.file = fileChooser.showOpenDialog(Main.getPrimaryStage());
        if (file != null){
            loadActionText.textProperty().unbind();
            progressPercentText.textProperty().unbind();
            fileProgressBar.progressProperty().unbind();
            progressPercentText.setText(" ");
            fileProgressBar.setProgress(0);
            loadActionText.setText("File: "+file.getAbsolutePath());//TODO: move this into initlaize with bind
            this.fileChosen.setValue(true);
        }
    }
}
