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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
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
    @FXML private MapController mapGridController;

    public Text getLoadActionText() {
        return loadActionText;
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    public void initialize(){
        mapGrid.visibleProperty().setValue(false);
        loadXmlButton.disableProperty().bind(fileChosen.not());
        mapGrid.getColumnConstraints().forEach(columnConstraints -> columnConstraints.setHgrow(Priority.NEVER));
        mapGrid.getRowConstraints().forEach(rowConstraints -> rowConstraints.setVgrow(Priority.NEVER));
        mapGrid.rotateProperty().setValue(270);
/*        BackgroundImage background = new Background(new Image("jaffaMap.PNG"), )
        mapGrid.setBackground("-fx-background-image: BEIGE;");*/
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


    public void updateData(){
        appController.getShowStoresComponentController().setData(appController);
        appController.getOrderScreenComponentController().setData(appController);
        appController.getShowItemsController().setData(appController);
        appController.getAddStoreComponentController().setData(appController);
        appController.getShowOrdersController().setData(appController);
        try {
            mapGridController.setSize(appController.getStoreManager());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



/*    private Date getDateOfOrder() {//TODO: delete this
        Scanner scanner = new Scanner(System.in);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM-hh:mm");
        Date dateOfOrder;
        dateFormat.setLenient(false);
        System.out.println("Please enter the date of the order in dd/mm-hh:mm format");
        while (true) {
            String dateString = scanner.next();
            try {
                dateOfOrder = dateFormat.parse(dateString);
                return dateOfOrder;
            } catch (ParseException e) {
                System.out.println("Invalid date format, try again");
            }
        }

    }*/

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
