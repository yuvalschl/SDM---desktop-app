import DtoObjects.*;
import Exceptions.*;
import ItemPair.ItemPair;
import Order.*;
import Item.Item;
import Item.UnitItem;
import Store.Store;
import jaxb.JaxbClasses.SuperDuperMarketDescriptor;
import jaxb.XmlToObject;

import java.awt.*;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ConsoleUi {

    private final Menu menu = new Menu();
    private StoreManager storeEngine;

    public enum Echoic {
        readFile,
        ShowStores,
        ShowItems,
        PlaceOrder,
        ShowHistory,
        Exit
    }

    private int getAndValidateChoice(int smallestChoice ,int largestChoice){
        Scanner input = new Scanner(System.in);
        int choice = -1;
        boolean isValid = false;
        do {
            System.out.println("Please enter a number between "+smallestChoice+" and " + largestChoice+":\n");
            String choiceString = input.next();
            if (!choiceString.isEmpty()) {
                try {
                    choice = Integer.parseInt(choiceString);
                    if(choice >= smallestChoice && choice <= largestChoice) {
                        isValid = true;
                    }
                    else {
                        System.out.println("Please enter a number between "+smallestChoice+" and " + largestChoice);
                    }
                } catch (Exception e) {
                    System.out.println("Please enter a number!");
                }
            }
            else   {
                System.out.println("Answer cannot be empty!");
            }
        }while(!isValid);
        return choice;
    }

    public void runUI() {
        System.out.println(menu.getMenuOption());
        Echoic[] eChoices = Echoic.values();
        boolean isFileLoaded = false;
        Echoic choice = eChoices[getAndValidateChoice(1,6) - 1];
        while (true) {
            if (isFileLoaded || choice == Echoic.readFile) {
                switch (choice) {
                    case readFile: {
                        readFile();
                        isFileLoaded = true;
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
                    case Exit: {
                        System.exit(0);
                    }
                }
            }
            else {
                System.out.println("File is not loaded to the system");
            }
            choice = eChoices[getAndValidateChoice(1,6) - 1];
        }
    }

    private void ShowHistory() {
        storeEngine.getAllOrders().stream().forEach(System.out::println);
    }

    private void showAllStoresInTheSystem() {
        System.out.println("Showing all the stores in the system");
        System.out.println("====================================");
        for(Integer storeId : storeEngine.getAllStores().keySet()){
            showStore(storeEngine.getAllStores().get(storeId));
            System.out.println("===================================");
        }
    }

    private void showStore(Store store){
        System.out.println("Store ID:" + store.getSerialNumber());
        System.out.println("Store name:" + store.getName());
        showStoreInventory(store);
        /*showStoreOrdersHistory(store);*/
        System.out.println("Store PPK:" + store.getPPK());
        System.out.println("Total payment to the store:" + store.getTotalPayment());
    }

    private void showStoreInventory(Store store){
        Map<Integer, Item> currentInventory = store.getInventory();
        System.out.println(store.getName() + " Items are:");
        for(Integer itemId : currentInventory.keySet()){
            showItemInStore(currentInventory.get(itemId));
            System.out.println();
        }
    }

    private void showItemInStore(Item item){
        System.out.println("*   Item ID: " + item.getSerialNumber());
        System.out.println("\tItem name: " + item.getName());
        if(item instanceof UnitItem){
            System.out.println("\tItem sell by: unit");
            System.out.println("\tPrice per unit: " + item.getPrice());
        }
        else {
            System.out.println("\tItem sell by: weight");
            System.out.println("\tPrice per kilo: " + item.getPrice());
        }

        System.out.println("\tTotal amount sold: " + item.getAmountSold());
    }

    private void showItemInSystem(DtoItem item){
        System.out.println("*   Item ID: " + item.getSerialNumber());
        System.out.println("\tItem name: " + item.getName());
        if(item instanceof DtoUnitItem){
            System.out.println("\tItem sell by: unit");
            System.out.println("\tAverage price per unit: " + storeEngine.getAveragePrice(item));
        }
        else {
            System.out.println("\tItem sell by: weight");
            System.out.println("\tAverage price per kilo: " + storeEngine.getAveragePrice(item));
        }

        System.out.println("\tTotal amount sold in the system: " + item.getAmountSold());
        System.out.println("\tNumber of stores selling the item " + storeEngine.NumberOfStoresSellingItem(item));
    }

    //-----------------------------------


    private void placeOrder() throws ParseException {
        Order order = null;
        showAllStoresInOrderMenu();
        System.out.println("Please choose a store by its ID from the list above:");
        int storeID = getIDFromUser("store");
        Date orderDate = getDateOfOrder();
        Point customerLocation = new Point(1,3);//getCustomerLocation();//TODO delete left of ; and umnark right of it
        showAllItemsInSystem();
        System.out.println("Please choose items by its ID from the list above or enter q to end order:");
        int itemID = getIDFromUser("item");
        if (itemID != -1 ) {
            order = createOrder(customerLocation,storeID,itemID, orderDate);
            if (order!= null){
                showItemsInOrder(order,storeID);
                if(getOrderApproval()) {
                    storeEngine.placeOrder(order);
                    System.out.println("Order was added successfully.");
                }
                else
                    System.out.println("Order canceled.");
            }
        }
    }

    private Boolean getOrderApproval(){
        Scanner input = new Scanner(System.in);
        do {
            System.out.println("enter 1 to approve the order or 2 to cancel the order");
            String choice = input.next();
            if (choice.charAt(0) == '1'){
                return true;
            }
            else if(choice.charAt(0) == '2'){
                return false;
            }
            else {
                System.out.println("Wrong input please try again");
            }
        }while (true);



    }
    private Order createOrder(Point customerLocation, int storeID, int itemID, Date date) {
       ArrayList<ItemPair> items =  getItemsFromUser(storeID, itemID);
       Order order = null;
            if(items.size() != 0)
               order = storeEngine.createOrder(customerLocation, storeID ,date, items);
       return order;
    }

    private ArrayList<ItemPair> getItemsFromUser(int storeID, int itemID) {
        ArrayList<ItemPair> items = new ArrayList<ItemPair>();
        double amount;
        while (true){
            DtoStore store= storeEngine.getAllDtoStores().get(storeID);
            if(store.getInventory().containsKey(itemID)){
                amount = getItemAmount(store.getInventory().get(itemID));
                ItemPair pair = new ItemPair(store.getInventory().get(itemID), amount);
                items.add(pair);
            }
            else {
                System.out.println("The store "+ storeEngine.getAllStores().get(storeID).getName()+
                    " does not have the item "+ storeEngine.getAllItems().get(itemID).getName()+".\n");
            }
            System.out.println("Please enter another item ID or q to end order");
            itemID = getIDFromUser("item");
            if (itemID == -1)
                return items;
        }
    }

    private double getItemAmount(DtoItem item){
        Scanner input = new Scanner(System.in);
        double amount;
        String stringAmount;
        if (item instanceof DtoUnitItem){
            System.out.println("Please enter how many units of "+ item.getName()+ " would you like");
           while (true){
               try {
                   stringAmount = input.next();
                   amount = Integer.parseInt(stringAmount);
                   return amount;
               }catch (Exception e){
                   System.out.println("Please enter a hole number!");
               }
           }
        }
        else{
            System.out.println("Please enter how many KG's of "+ item.getName()+ " would you like");
        }
        while (true)
        {
            try {
                stringAmount = input.next();
                amount = Double.parseDouble(stringAmount);
                return amount;
            }catch (Exception e){
                System.out.println("Please enter a number!");
            }
        }
    }
    private Point getCustomerLocation() {
        System.out.println("Please enter your X coordinate");
        int x = readCoordinate();
        System.out.println("Please enter your Y coordinate");
        int y = readCoordinate();
        return new Point(x,y);
    }

    private int readCoordinate(){
       return getAndValidateChoice(1,50);
    }

    private Date getDateOfOrder() {
        Scanner scanner = new Scanner(System.in);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM-hh:mm");
        Date dateOfOrder;
        dateFormat.setLenient(false);
        System.out.println("Please enter the date of the order in dd/mm-hh:mm format");
        while (true){
            String dateString = scanner.next();
            try{
                dateOfOrder = dateFormat.parse(dateString);
                return dateOfOrder;
            } catch (ParseException e) {
                System.out.println("Invalid date format, try again");
            }
        }

    }

    private int getIDFromUser(String StoreOrItem) {
        Scanner scanner = new Scanner(System.in);
        int userSelection;
        do {
            try{
                String userSelectionString = scanner.next();
                if (userSelectionString.charAt(0) == 'q' && StoreOrItem =="item")
                    return -1;
                userSelection = Integer.parseInt(userSelectionString);
                Method method = null;
                if (StoreOrItem == "store") {
                    method = StoreManager.class.getMethod("getAllStores", null);
                }
                else {
                    method = StoreManager.class.getMethod("getAllItems", null);

                }
                if(((Map<Integer, Store>) method.invoke(storeEngine)).containsKey(userSelection)){
                    return userSelection;
                }
                else{
                    if (StoreOrItem == "store")
                        System.out.println("The store ID you entered is not available please choose an ID from the list above");
                    else
                        System.out.println("The Item ID you entered is not available please choose an ID from the list above");
                }

            }
            catch (NumberFormatException e){
                System.out.println("Please enter a number");
            }
            catch (Exception e){}
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

    private void showStoreInPurchaseMenu(DtoStore store){
        System.out.println("Store.Store ID:" + store.getSerialNumber());
        System.out.println("Store.Store name:" + store.getName());
        System.out.println("Store.Store PPK:" + store.getPPK());
    }

    private void showAllItemsInSystem() {
        Map<Integer, DtoItem> allItems = storeEngine.getAllDtoItems();
        System.out.println("Showing all the items in the system");
        System.out.println("====================================");
        for (Integer itemId : allItems.keySet()){
            showItemInSystem(allItems.get(itemId));
            System.out.println("====================================");
        }
    }


    private void printItemDetails(DtoItem item, boolean showAveragePrice)
    {
        System.out.println("*   Item ID: " + item.getSerialNumber());
        System.out.println("\tItem name: " + item.getName());
        if(item instanceof DtoUnitItem){
            System.out.println("\tItem sell by: unit");
            if (showAveragePrice)
            System.out.println("\tAverage price per unit: " + storeEngine.getAveragePrice(item));
            else
            System.out.println("\tprice per unit: " + item.getPrice());
        }
        else {
            System.out.println("\tItem sell by: weight");
            if (showAveragePrice)
                System.out.println("\tAverage price per kilo: " + storeEngine.getAveragePrice(item));
            else
                System.out.println("\tprice per kilo: " + item.getPrice());
        }
    }

    private void showItemsInOrder(Order order, int storeID){
        ArrayList<ItemPair> items = order.getItems();
        DtoItem item;
        System.out.println("The order details:");
        for (ItemPair itemInPair: items) {
            item = itemInPair.item();
            printItemDetails(item, false);
            if(item instanceof DtoUnitItem){
                System.out.println("\tThe requested amount is: "+ (int)itemInPair.amount()+" units.");
                System.out.println("\tTotal price of requested item is: "+ (int)itemInPair.amount()* item.getPrice());
            }
            else{
                System.out.println("\tThe requested amount is: "+ itemInPair.amount()+" KG.");
                System.out.println("\tTotal price of requested item is: "+ itemInPair.amount()* item.getPrice());
            }
        }
        System.out.println("\tThe price per kilometer is: "+ storeEngine.getAllStores().get(storeID).getPPK());
        System.out.println("\tThe distance from "+storeEngine.getAllStores().get(storeID).getName()+" is :"+order.getDistance()+" KM");
        System.out.println("\tThe total cost of order is: "+order.getTotalCost());
        System.out.println("===================================================");

    }
    private void readFile(){

        SuperDuperMarketDescriptor superDuperMarketDescriptor = XmlToObject.fromXmlFileToObject();
        JaxbClassToStoreManager jaxbClassToStoreManager = new JaxbClassToStoreManager();
        try {
            this.storeEngine = jaxbClassToStoreManager.convertJaxbClassToStoreManager();
        } catch (DuplicateValueException | InvalidValueException | ItemNotSoldException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("File loaded successfully");
    }
}

