import Item.*;
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
                    showAllStores();
                    break;
                }
                case ShowItems: {
                    showItems();
                    break;
                }
                case PlaceOrder: {
                    placeOrder();
                    break;
                }
                case ShowHistory:{
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

    private void showAllStores() {
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
            showItem(currentInventory.get(itemId));
            System.out.println();
        }
    }

    private void showItem(Item item){
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
    }

    private void showItems() {
    }

    private void readFile(){
        this.storeEngine = new JaxbClassToSdmClass().jaxbClassToStoreManager();
    }
}

