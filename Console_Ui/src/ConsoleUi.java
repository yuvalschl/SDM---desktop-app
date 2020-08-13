import DtoObjects.DtoItem;
import DtoObjects.DtoStore;
import DtoObjects.DtoUnitItem;
import Exceptions.*;

import Item.Item;
import Item.UnitItem;
import jaxb.JaxbClasses.SuperDuperMarketDescriptor;
import jaxb.XmlToObject;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class ConsoleUi {

    private Menu menu = new Menu();
    private StoreManager storeEngine;

    public enum Echoic {
        readFile,
        ShowStores,
        ShowItems,
        PlaceOrder,
        ShowHistory,
        Exit
    }

    private int getAndValidateChoice(int largestChoiceNumber){
        System.out.println("Please enter choose number of a commands above(must be between 1 and " + largestChoiceNumber+"):\n");
        Scanner input = new Scanner(System.in);
        int choice = -1;
        boolean isValid = false;
        do {
            String choiceString = input.next();
            if (!choiceString.isEmpty()) {
                try {
                    choice = Integer.parseInt(choiceString);
                    if(choice > 0 && choice <= largestChoiceNumber) {
                        isValid = true;
                    }
                    else {
                        System.out.println("Please enter a number between 1 and " + largestChoiceNumber);
                    }
                } catch (Exception e) {
                    System.out.println("Please enter a number!");
                }
            }
            else   {
                System.out.println("Answer cannot be empty!");
            }
        }while(isValid == false);
        return choice;
    }

    public void runUI(){
        System.out.println(menu.getMenuOption());
        Echoic[] eChoices =  Echoic.values();
        Echoic choice = eChoices[getAndValidateChoice(6)-1];
        while (true){
            switch (choice){
                case readFile: {
                    readFile();
                    break;
                }
                case ShowStores: {
                    //TODO check if a file is loaded to the system
                    showAllStoresInTheSystem();
                    break;
                }
                case ShowItems: {
                    //TODO check if a file is loaded to the system
                    showAllItemsInSystem();
                    break;
                }
                case PlaceOrder: {
                    //TODO check if a file is loaded to the system
                    placeOrder();
                    break;
                }
                case ShowHistory:{
                    //TODO check if a file is loaded to the system
                    ShowHistory();
                    break;
                }
                case Exit:{
                    System.exit(0);
                }
            }
            choice = eChoices[getAndValidateChoice(6)-1];
        }

    }

    private void ShowHistory() {
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
        showStoreOrdersHistory(store);
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

    private void showStoreOrdersHistory(Store store){
        //TODO fill this method
    }
    private void placeOrder() {
        showAllStoresInOrderMenu();
        int storeId = getStoreToBuyFrom();
        Date orderDate = getDateOfOrder();
        Point customerLocation = getCustomerLocation();

    }

    private Point getCustomerLocation() {
        System.out.println("Please enter your x coordinate");
        int x = readCoordinate();
        System.out.println("Please enter your x coordinate");
        int y = readCoordinate();
        return new Point(x,y);
    }

    private int readCoordinate(){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                int coordinate = scanner.nextInt();
                if (coordinate >= 0 && coordinate <= 50) {
                    return coordinate;
                }
                else {
                    System.out.println("coordinate is not in range of 0 - 50");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter an int");
                scanner.next();
            }
        }
    }

    private Date getDateOfOrder() {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm-hh:mm");
        Date dateOfOrder;

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

    private int getStoreToBuyFrom() {
        Scanner scanner = new Scanner(System.in);
        int userSelection;
        do{
            try{
                String userSelectionString = scanner.next();
                userSelection = Integer.parseInt(userSelectionString);
                if(storeEngine.getAllStores().containsKey(userSelection)){
                    return userSelection;
                }
                else {
                    System.out.println("The store you selected is not available");
                }
            }catch (NumberFormatException e){
                System.out.println("Please enter a number");
            }
        }while (true);
    }

    private void showAllStoresInOrderMenu() {
        Map<Integer, DtoStore> allStores = storeEngine.getAllDtoStores();
        for (Integer storeId : allStores.keySet()) {
            showStoreInPurchaseMenu(allStores.get(storeId));
        }
    }

    private void showStoreInPurchaseMenu(DtoStore store){
        System.out.println("Store ID:" + store.getSerialNumber());
        System.out.println("Store name:" + store.getName());
        System.out.println("Store PPK:" + store.getPPK());
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

    private void readFile(){

        SuperDuperMarketDescriptor superDuperMarketDescriptor = XmlToObject.fromXmlFileToObject();
        JaxbClassToStoreManager jaxbClassToStoreManager = new JaxbClassToStoreManager();
        try {
            this.storeEngine = jaxbClassToStoreManager.convertJaxbClassToStoreManager();
        } catch (DuplicateValueException | InvalidValueException | ItemNotSoldException e) {
            System.out.println(e.getMessage());
        }

    }
}

