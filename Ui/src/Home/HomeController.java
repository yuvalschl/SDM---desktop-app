package Home;

import DtoObjects.DtoItem;
import DtoObjects.DtoUnitItem;
import Exceptions.DuplicateValueException;
import Exceptions.InvalidValueException;
import Exceptions.ItemNotSoldException;
import ItemPair.ItemAmountAndStore;
import Jaxb.JaxbClassToStoreManager;
import Jaxb.XmlToObject;
import Order.Order;
import Store.Discount;
import Store.Store;
import StoreManager.StoreManager;
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
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;

public class HomeController {


    private AppController appController;

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

    public void loadXmlAction() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(Main.getPrimaryStage());
        if (file != null){
            JaxbClassToStoreManager jaxbClassToStoreManager = new JaxbClassToStoreManager();
            try {
                appController.setStoreManager(jaxbClassToStoreManager.convertJaxbClassToStoreManager(XmlToObject.fromXmlFileToObject(file)));
            } catch (DuplicateValueException | InvalidValueException | ItemNotSoldException e) {
                e.printStackTrace();
            }
            appController.getXmlLoaded().setValue(false);
            loadActionText.setText("File: "+file.getAbsolutePath());//TODO: move this into initlaize with bind
        }
        else
            loadActionText.setText("Error! no file loaded");
        loadingFileProgress();
        appController.getShowStoresComponentController().setData(appController);
        appController.getOrderScreenComponentController().setData(appController);
        //deleteeeeeeeeeee from here
        ArrayList<ItemAmountAndStore> itemAmountAndStores = new ArrayList<ItemAmountAndStore>();

       // itemAmountAndStores item1 = appController.getStoreManager().getCheapestItem(1);
        Store store = appController.getStoreManager().getAllStores().get(1);
        ItemAmountAndStore itemAmountAndStore1 = new ItemAmountAndStore(new DtoUnitItem(1,"Toilet Paper",10,0),store);
        itemAmountAndStore1.setAmount(1);
        ArrayList<ItemAmountAndStore> itemList = new ArrayList<ItemAmountAndStore>();
        itemList.add(itemAmountAndStore1);
        Order order = appController.getStoreManager().createOrder(new Point(2,3), getDateOfOrder(),itemList);//TODO: delete this
        ArrayList<Discount> discount = appController.getStoreManager().getEntitledDiscounts(1,order.getItemAmountAndStores());
        //up til hereeeeeeeee
    }

    private Date getDateOfOrder() {//TODO: delete this
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

    }
    @FXML
    void goToMainMenuAction(){
        Main.getPrimaryStage().setScene(Main.getMainMenu());
    }

    private void loadingFileProgress(){
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int i=0 ; i<101; i++) {
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
