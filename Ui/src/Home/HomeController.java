package Home;


import Jaxb.JaxbClassToStoreManager;
import appController.AppController;
import appController.Main;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;

public class HomeController {


    private AppController appController;
    private File file;
    private SimpleStringProperty messageFileProperty = new SimpleStringProperty();
    private SimpleDoubleProperty progressFileProperty = new SimpleDoubleProperty();
    private SimpleBooleanProperty valueFileProperty = new SimpleBooleanProperty();

    @FXML
    private Button chooseFileButton;
    @FXML private Button loadXmlButton;
    @FXML private Text loadActionText;
    @FXML private ProgressBar fileProgressBar;
    @FXML private Label progressPercentText;

    public Text getLoadActionText() {
        return loadActionText;
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void loadXmlAction() throws InterruptedException {
        JaxbClassToStoreManager jaxbClassToStoreManager = new JaxbClassToStoreManager(file, appController.getXmlLoaded());
        Thread thread = new Thread(jaxbClassToStoreManager);
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
        thread.setDaemon(true);
        thread.start();
    }

    public String getMessageFileProperty() {
        return messageFileProperty.get();
    }

    public SimpleStringProperty messageFilePropertyProperty() {
        return messageFileProperty;
    }

    public void setMessageFileProperty(String messageFileProperty) {
        this.messageFileProperty.set(messageFileProperty);
    }

    public double getProgressFileProperty() {
        return progressFileProperty.get();
    }

    public SimpleDoubleProperty progressFilePropertyProperty() {
        return progressFileProperty;
    }

    public void setProgressFileProperty(double progressFileProperty) {
        this.progressFileProperty.set(progressFileProperty);
    }

    public boolean isValueFileProperty() {
        return valueFileProperty.get();
    }

    public SimpleBooleanProperty valueFilePropertyProperty() {
        return valueFileProperty;
    }

    public void setValueFileProperty(boolean valueFileProperty) {
        this.valueFileProperty.set(valueFileProperty);
    }

    private void loadingFileProgress(){

/*        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    loadingFileProgress();
                    appController.setStoreManager(jaxbClassToStoreManager.convertJaxbClassToStoreManager(Objects.requireNonNull(XmlToObject.fromXmlFileToObject(file))));
                } catch (DuplicateValueException | InvalidValueException | ItemNotSoldException e) {
                    loadActionText.setText(e.getMessage());
                }
                for (int i=0 ; i<101; i++) {
                    Thread.sleep(10);
                    updateProgress(i, 100);
                }
                return null;
            }
        };*/

/*        fileProgressBar.progressProperty().unbind();
        fileProgressBar.progressProperty().bind(task.progressProperty());

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();*/
    }

    public void updateData(){
        appController.getShowStoresComponentController().setData(appController);
        appController.getOrderScreenComponentController().setData(appController);
        appController.getShowItemsController().setData(appController);
        appController.getAddStoreComponentController().setData(appController);
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
        loadActionText.textProperty().unbind();
        progressPercentText.textProperty().unbind();
        fileProgressBar.progressProperty().unbind();
        progressPercentText.setText(" ");
        fileProgressBar.setProgress(0);
        loadActionText.setText("File: "+file.getAbsolutePath());//TODO: move this into initlaize with bind
    }
    @FXML
    void goToMainMenuAction(){
        Main.getPrimaryStage().setScene(Main.getMainMenu());
    }


}
