package header;

import appController.AppController;
import appController.Main;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.Optional;

public class HeaderController {


    private AppController appController;

    @FXML private Button loadXmlButton;
    @FXML private Button mainMenuButton;
    @FXML private Text loadActionText;
    @FXML private ProgressBar fileProgressBar;
    @FXML private Label progressPercentText;

    public Text getLoadActionText() {
        return loadActionText;
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    void loadXmlAction() {
        appController.loadXmlAction();
        loadingFileProgress();
        //appController.getXmlLoaded().setValue(false);
    }

    @FXML
    void goToMainMenuAction(){
        Main.getPrimaryStage().setScene(Main.getMainMenu());
    }

    private void loadingFileProgress(){
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int i=1 ; i<=100; i++) {
                    Thread.sleep(10);
                    updateProgress(i, 100);
                }
                return null;
            }
        };
        fileProgressBar.progressProperty().unbind();
        fileProgressBar.progressProperty().bind(task.progressProperty());
        progressPercentText.textProperty().bind(Bindings.concat(
                Bindings.format(
                        "%.0f",
                        Bindings.multiply(
                                task.progressProperty(),
                                100)),
                " %"));
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}
