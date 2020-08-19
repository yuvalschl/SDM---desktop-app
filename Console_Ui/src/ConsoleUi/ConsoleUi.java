package ConsoleUi;

import DtoObjects.*;
import Exceptions.*;
import Item.Item;
import ItemPair.ItemAmountAndStore;
import Order.*;
import Store.*;
import StoreManager.StoreManager;
import jaxb.JaxbClassToStoreManager;
import jaxb.JaxbClasses.SuperDuperMarketDescriptor;
import jaxb.XmlToObject;

import java.awt.*;
import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.InvalidPathException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ConsoleUi {

    private final Menu menu = new Menu();
    private StoreManager storeEngine;
    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public enum menuChoice {
        readFile,
        ShowStores,
        ShowItems,
        PlaceOrder,
        ShowHistory,
        UpdateStore,
        Exit
    }

    public enum storeUpdateChoice {
        deleteItem,
        addItem,
        updateItem
    }

    private int getAndValidateChoice(int smallestChoice, int largestChoice) {
        Scanner input = new Scanner(System.in);
        int choice = -1;
        boolean isValid = false;
        System.out.println("Please enter a number between " + smallestChoice + " and " + largestChoice + ":\n");
        do {
            String choiceString = input.next();
            if (!choiceString.isEmpty()) {
                try {
                    choice = Integer.parseInt(choiceString);
                    if (choice >= smallestChoice && choice <= largestChoice) {
                        isValid = true;
                    } else {
                        System.out.println("Please enter a number between " + smallestChoice + " and " + largestChoice);
                    }
                } catch (Exception e) {
                    System.out.println("Please enter a number!");
                }
            } else {
                System.out.println("Answer cannot be empty!");
            }
        } while (!isValid);
        return choice;
    }

    public void runUI() {
        ConsoleUi.menuChoice[] eChoices = menuChoice.values();
        boolean isFileLoaded = false;
        while (true) {
            System.out.println(menu.getMenuOption());
            ConsoleUi.menuChoice choice = eChoices[getAndValidateChoice(1, 6) - 1];
            if (isFileLoaded || choice == menuChoice.readFile) {
                switch (choice) {
                    case readFile: {
                        try {
                            readFile();
                            isFileLoaded = true;
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    }
                    case ShowStores: {
                        showAllStoresInTheSystem();
                        break;
                    }
                    case ShowItems: {
                        showAllItemsInSystem();
                        break;
                    }
                    case PlaceOrder: {
                        try {
                            placeOrder();
                        } catch (ParseException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    }
                    case ShowHistory: {
                        ShowHistory();
                        break;
                    }
                    case UpdateStore:{
                        updateStore();
                        break;
                    }
                    case Exit: {
                        System.exit(0);
                    }
                }
            } else {
                System.out.println("No files are loaded to the system yet");
            }
        }
    }

    private void ShowHistory() {
        if (storeEngine.getAllOrders().isEmpty()) {
            System.out.println("No order were made yet");
        }
        storeEngine.getAllOrders().forEach(this::printSingleOrder);
    }

    private void printSingleOrder(Order order) {
        System.out.println(
                "*   Order ID: " + order.getOrderId() + "\n" +
                        "\tDate: " + order.getDateOfOrder() + "\n" +
                        "\tStores names: " + allStoresNameString(order) + "\n" +
                        "\tStores ID: " + allStoresIdString(order) + "\n" +
                        "\tNumber of items in order: " + decimalFormat.format(order.getAmountOfItems()) + "\n" +
                        "\tTotal item cost: " + decimalFormat.format(order.getTotalPriceOfItems()) + "\n" +
                        "\tShipping price: " + decimalFormat.format(order.getShippingCost()) + "\n" +
                        "\tTotal order price: " + decimalFormat.format(order.getTotalCost()));

    }

    private String allStoresNameString(Order order) {
        StringBuilder storesNames = new StringBuilder();
        for (Store store : order.getStores().values()) {
            storesNames.append(store.getName()).append(", ");
        }
        storesNames.deleteCharAt(storesNames.length()-2);
        return storesNames.toString();
    }


    private String allStoresIdString(Order order) {
        StringBuilder storesId = new StringBuilder();
        for (Store store : order.getStores().values()) {
            storesId.append(store.getSerialNumber()).append(", ");
        }
        storesId.deleteCharAt(storesId.length()-2);
        return storesId.toString();
    }

    private void showAllStoresInTheSystem() {
        System.out.println("Showing all the stores in the system");
        System.out.println("====================================");
        for (Integer storeId : storeEngine.getAllDtoStores().keySet()) {
            showStore(storeEngine.getAllDtoStores().get(storeId), false);
            System.out.println("===================================");
        }
    }

    private void showStore(DtoStore store, boolean showOnlyNameAndID) {
        System.out.println("Store ID:" + store.getSerialNumber());
        System.out.println("Store name:" + store.getName());
        if(!showOnlyNameAndID){
            showStoreInventory(store);
            if (store.getAllOrders().size() != 0)
                showStoreOrdersHistory(store);
            else
                System.out.println("\tThere were no orders from this store");
            System.out.println("\tStore PPK:" + store.getPPK());
            System.out.println("\tThe total cost for delivery so far is: " + decimalFormat.format(store.getTotalDeliveriesCost()));
        }

    }

    private void showStoreOrdersHistory(DtoStore store) {
        System.out.println("The orders are:\n");
        for (DtoStoreOrder order : store.getAllOrders()) {
            System.out.println("*  Order ID: " + order.getOrderId());
            System.out.println("\tThe order date is: " + order.getDateOfOrder());
            System.out.println("\tThe amount of items are: " + order.getAmountOfItems());
            System.out.println("\tThe items total cost is: " + decimalFormat.format(order.getTotalPriceOfItems()));
            System.out.println("\tThe delivery cost is: " + decimalFormat.format(order.getShippingCost()));
            System.out.println("\tThe order total cost is: " + decimalFormat.format(order.getTotalCost()));
        }
    }

    private void showStoreInventory(DtoStore store) {
        Map<Integer, DtoItem> currentInventory = store.getInventory();
        System.out.println(store.getName() + " Items are:");
        for (Integer itemId : currentInventory.keySet()) {
            showItemInStore(currentInventory.get(itemId));
            System.out.println();
        }
    }

    private void showItemInStore(DtoItem item) {
        System.out.println("*   Item ID: " + item.getSerialNumber());
        System.out.println("\tItem name: " + item.getName());
        if (item instanceof DtoUnitItem) {
            System.out.println("\tItem sell by: unit");
            System.out.println("\tPrice per unit: " + decimalFormat.format(item.getPrice()));
        } else {
            System.out.println("\tItem sell by: weight");
            System.out.println("\tPrice per kilo: " + decimalFormat.format(item.getPrice()));
        }

        System.out.println("\tTotal amount sold: " + (int) item.getAmountSold()); //TODO Check why casting
    }

    private void showItemInSystem(DtoItem item) {
        System.out.println("*   Item ID: " + item.getSerialNumber());
        System.out.println("\tItem name: " + item.getName());
        if (item instanceof DtoUnitItem) {
            System.out.println("\tItem sell by: unit");
            System.out.println("\tAverage price per unit: " + decimalFormat.format(storeEngine.getAveragePrice(item)));
        } else {
            System.out.println("\tItem sell by: weight");
            System.out.println("\tAverage price per kilo: " + decimalFormat.format(storeEngine.getAveragePrice(item)));
        }

        System.out.println("\tTotal amount sold in the system: " + (int) item.getAmountSold());//TODO check why casting
        System.out.println("\tNumber of stores selling the item " + storeEngine.NumberOfStoresSellingItem(item));
    }

    private void placeOrder() throws ParseException {//TODO: handale the case no item was selceted yet but q was enterd
/*        showAllStoresInOrderMenu();
        System.out.println("Please choose a store by its ID from the list above:");*/
        boolean itemExist = false;
        //int storeID = getIDFromUser("store");
        Date orderDate = getDateOfOrder();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM-hh:mm");
        //Date orderDate = dateFormat.parse("20/05-12:20"); //TODO delete this
        //TODO: set year to 2020
        //Point customerLocation = getCustomerLocation();TODO:bring this back
        Point customerLocation = new Point(1, 1);

        showAllItemsInSystem();
        System.out.println("Please choose items by its ID from the list above or enter q to end order:");
        int itemID = getIDFromUser(storeEngine.getAllDtoItems().keySet(), true);
        ArrayList<ItemAmountAndStore> orderItems = new ArrayList<>();
        while (itemID != -1) {
            ItemAmountAndStore itemToAdd = storeEngine.getCheapestItem(itemID);
            double amount = getItemAmount(itemToAdd.getItem());
            for (ItemAmountAndStore orderItem : orderItems) {//TODO: improve this using contains
                if (itemToAdd.getItem().equals(orderItem.getItem())) {
                    amount += orderItem.getAmount();
                    itemExist = true;
                    orderItem.setAmount(amount);
                }
            }
            if (!itemExist){
                itemToAdd.setAmount(amount);
                orderItems.add(itemToAdd);
            }
            itemExist = false;
            System.out.println("Please chose another item or press q to exit");
            itemID = getIDFromUser(storeEngine.getAllDtoItems().keySet(), true);
        }
        if(orderItems.size() == 0){
            System.out.println("No order was made.");
            return;
        }
        Order order = storeEngine.createOrder(customerLocation, orderDate, orderItems);
        showItemsInOrder(order);
        if (getOrderApproval()) {
            storeEngine.placeOrder(order);
            System.out.println("Order was added successfully.");
        } else {
            System.out.println("Order canceled.");
        }
    }



    private Boolean getOrderApproval() {
        Scanner input = new Scanner(System.in);
        do {
            System.out.println("enter 1 to approve the order or 2 to cancel the order");
            String choice = input.next();
            if (choice.charAt(0) == '1') {
                return true;
            } else if (choice.charAt(0) == '2') {
                return false;
            } else {
                System.out.println("Wrong input please try again");
            }
        } while (true);
    }


    //TODO re-write the method
    private double getItemAmount(DtoItem item) {
        Scanner input = new Scanner(System.in);
        double amount;
        String stringAmount;
        if (item instanceof DtoUnitItem) {
            System.out.println("Please enter how many units of " + item.getName() + " would you like");
            while (true) {
                try {
                    stringAmount = input.next();
                    amount = Integer.parseInt(stringAmount);
                    if (amount <= 0) {
                        System.out.println("Please enter a positive number");
                    } else {
                        return amount;
                    }
                } catch (Exception e) {
                    System.out.println("Please enter a whole number!");
                }
            }
        }
        else {
            System.out.println("Please enter how many KG's of " + item.getName() + " would you like");
        }
        while (true) {
            try {
                stringAmount = input.next();
                amount = Double.parseDouble(stringAmount);
                if (amount <= 0) {
                    System.out.println("Please enter a positive number");
                } else {
                    return amount;
                }
            } catch (Exception e) {
                System.out.println("Please enter a number!");
            }
        }
    }

    private Point getCustomerLocation() {
        System.out.println("Please enter your X coordinate");
        int x = readCoordinate();
        System.out.println("Please enter your Y coordinate");
        int y = readCoordinate();
        return new Point(x, y);
    }

    private int readCoordinate() {
        return getAndValidateChoice(1, 50);
    }

    private Date getDateOfOrder() {
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

    private int getIDFromUser(Set<Integer> idSet, boolean exitWithQ) {
        do {
            Scanner scanner = new Scanner(System.in);
            int userSelection;
            try {
                String userSelectionString = scanner.next();
                if (userSelectionString.toLowerCase().charAt(0) == 'q' && exitWithQ && userSelectionString.length() == 1)
                    return -1;
                userSelection = Integer.parseInt(userSelectionString);
                if(userSelection > 0 && idSet.contains(userSelection)==true)
                    return userSelection;
                else
                    System.out.println("ID not available");
            } catch (NumberFormatException e) {
            System.out.println("Please enter a number");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        }while (true);
    }

    private void showAllStoresInOrderMenu() {
        Map<Integer, DtoStore> allStores = storeEngine.getAllDtoStores();
        System.out.println("====================================");
        for (Integer storeId : allStores.keySet()) {
            showStoreInPurchaseMenu(allStores.get(storeId));
            System.out.println("====================================");
        }
    }

    private void showStoreInPurchaseMenu(DtoStore store) {
        System.out.println("Store ID:" + store.getSerialNumber());
        System.out.println("Store name:" + store.getName());
        System.out.println("Store PPK:" + store.getPPK());
    }

    private void showAllItemsInSystem() {
        Map<Integer, DtoItem> allItems = storeEngine.getAllDtoItems();
        System.out.println("Showing all the items in the system");
        System.out.println("====================================");
        for (Integer itemId : allItems.keySet()) {
            showItemInSystem(allItems.get(itemId));
            System.out.println("====================================");
        }
    }


    private void printItemDetails(DtoItem item, boolean showAveragePrice, int storeID) {
        Store store = storeEngine.getAllStores().get(storeID);
        int itemID = item.getSerialNumber();
        System.out.println("*   Item ID: " + itemID);
        System.out.println("\tItem name: " + item.getName());
        if (item instanceof DtoUnitItem) {
            System.out.println("\tItem sell by: unit");
            if (showAveragePrice)
                System.out.println("\tAverage price per unit: " + storeEngine.getAveragePrice(item));
            else {
                if (store.getInventory().containsKey(itemID)) {
                    System.out.println("\tprice per unit: " + store.getInventory().get(itemID).getPrice());
                } else
                    System.out.println("\tThe store " + store.getName() + " does not sell this item");
            }
        } else {
            System.out.println("\tItem sell by: weight");
            {
                if (showAveragePrice)
                    System.out.println("\tAverage price per kilo: " + storeEngine.getAveragePrice(item));
                else {
                    if (store.getInventory().containsKey(itemID)) {
                        System.out.println("\tprice per kilo: " + store.getInventory().get(itemID).getPrice());
                    } else
                        System.out.println("\tThe store " + store.getName() + " does not sell this item");
                }
            }

        }
    }

    private void showItemsInOrder(Order order) {
        ArrayList<ItemAmountAndStore> items = order.getItems();
        DtoItem item;
        System.out.println("The order details:");
        for (ItemAmountAndStore itemInPair : items) {
            item = itemInPair.item();
            printItemDetails(item, false, itemInPair.getStore().getSerialNumber());
            if (item instanceof DtoUnitItem) {
                System.out.println("\tThe requested amount is: " + (int) itemInPair.amount() + " units.");
                System.out.println("\tTotal price of requested item is: " + decimalFormat.format((int) itemInPair.amount() * item.getPrice()));
            } else {
                System.out.println("\tThe requested amount is: " + decimalFormat.format(itemInPair.amount()) + " KG.");
                System.out.println("\tTotal price of requested item is: " + decimalFormat.format(itemInPair.amount() * item.getPrice()));
            }
        }
        System.out.println("\tThe delivery cost is: " + decimalFormat.format(order.getShippingCost()));
        System.out.println("\tThe total cost of order is: " + decimalFormat.format(order.getTotalCost()));
        System.out.println("===================================================");

    }

    private void readFile() throws DuplicateValueException, ItemNotSoldException, InvalidValueException {

        //TODO: get user input and delete hard coded stuff
        // bring this back
        // File file = getFileFromUser();
        File file = new File("ex1-big.xml"); //TODO: delete this
        SuperDuperMarketDescriptor superDuperMarketDescriptor = XmlToObject.fromXmlFileToObject(file);
        JaxbClassToStoreManager jaxbClassToStoreManager = new JaxbClassToStoreManager();
        if (superDuperMarketDescriptor == null) {
            throw new NullPointerException("The file you entered is null");
        }
        if (storeEngine == null || !file.toString().equals(storeEngine.getCurrentFilePath())) {
            this.storeEngine = jaxbClassToStoreManager.convertJaxbClassToStoreManager(superDuperMarketDescriptor);
            storeEngine.setCurrentFilePath(file.toString());
            System.out.println("File loaded successfully");
        } else {
            System.out.println("This file is already loaded");
        }
    }

    private File getFileFromUser() throws InvalidFileTypeException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter full path to the file");
        String path = scanner.nextLine();
        if (path == null) {
            throw new NullPointerException("the path you entered is null");
        }
        File file = new File(path);
        if (!file.exists()) {
            throw new InvalidPathException(path, "The path you entered is invalid");
        }
        String fileType = file.toString().substring(file.toString().lastIndexOf(".") + 1);
        if (!fileType.equals("xml")) {
            throw new InvalidFileTypeException(fileType + " is an invalid file type");
        }
        return file;
    }

    private void updateStore(){
        ConsoleUi.storeUpdateChoice[] eChoices = storeUpdateChoice.values();
        storeEngine.getAllDtoStores().forEach((Integer, DtoStore)->showStore(DtoStore, true));
        System.out.println("========================================");
        System.out.println("Please chose a store from the list above");
        int storeId = getIDFromUser(storeEngine.getAllDtoStores().keySet(), false);
        DtoStore storeToUpdate = storeEngine.getAllDtoStores().get(storeId);
        System.out.println("Please chose an action\n" +
                "1. delete item from store inventory\n" +
                "2. add an item to store inventory\n" +
                "3. update an item price");
        ConsoleUi.storeUpdateChoice choice = eChoices[getAndValidateChoice(1, 3) - 1];
        switch (choice){
            case deleteItem:{
                deleteItem(storeToUpdate);
                break;
            }
            case addItem:{
                addItem(storeToUpdate);
                break;
            }
            case updateItem:{
                updateItem(storeToUpdate);
                break;
            }
        }
    }

    private void updateItem(DtoStore store) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter item id");
        while (true){
            int itemId = getIDFromUser(store.getInventory().keySet(), false);
            if(store.getInventory().containsKey(itemId)){
                DtoItem itemToUpdate = store.getInventory().get(itemId);
                try {
                    System.out.println("Please enter the new price");
                    float newPrice = scanner.nextFloat();
                    if(newPrice <= 0){
                        System.out.println("Invalid price");
                    }
                    else {
                        storeEngine.updateItemPrice(itemToUpdate, newPrice, store);
                        System.out.println(itemToUpdate.getName() + "price updated");
                        break;
                    }
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
            else {
                System.out.println(store.getName() + "dose not sell this item, please try again");
            }
        }
    }

    private void addItem(DtoStore storeToUpdate) {
        Scanner scanner = new Scanner(System.in);
        showAllItemsInSystem();
        System.out.println("Please choose an item from the list above to add to the store - "+storeToUpdate.getName());
        int itemID = getIDFromUser(storeToUpdate.getInventory().keySet(), false);
        if(storeToUpdate.getInventory().containsKey(itemID))
            System.out.println("The store "+storeToUpdate.getName()+" already has the item "+ storeToUpdate.getInventory().get(itemID));
        else{
            System.out.println("Please enter the new price");
            float price = scanner.nextFloat();
            if(price <= 0){
                System.out.println("Invalid price");
            }
            else {
                DtoItem dtoItemToAdd = storeEngine.getAllDtoItems().get(itemID);
                try {
                    storeEngine.addItemToStore(storeToUpdate, dtoItemToAdd, price);
                } catch (InvalidValueException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("Added "+dtoItemToAdd.getName()+" successfully");
            }
        }
    }

    private void deleteItem(DtoStore storeToUpdate) {
        storeToUpdate.getInventory().forEach((Integer,DtoItem)->printItemDetails(DtoItem, false, storeToUpdate.getSerialNumber()));
        System.out.println("Please choose an item to delete from the list above, by entering its ID:");
        int itemID = getIDFromUser(storeToUpdate.getInventory().keySet(), false);
        if(storeToUpdate.getInventory().containsKey(itemID)){
                if(storeEngine.NumberOfStoresSellingItem(storeEngine.getAllDtoItems().get(itemID)) > 1){
                    storeEngine.deleteItemFromStore(storeToUpdate, itemID);
                    System.out.println(storeEngine.getAllDtoItems().get(itemID).getName() +" was deleted");
                }
            else {
                    System.out.println(storeToUpdate.getName() + " is the only store selling "
                            + storeToUpdate.getInventory().get(itemID).getName() + ", cannot delete.");
                }
        }
        else
            System.out.println("The store "+storeToUpdate.getName()+" does not have the item "+ storeEngine.getAllDtoItems().get(itemID).getName());
    }
}

