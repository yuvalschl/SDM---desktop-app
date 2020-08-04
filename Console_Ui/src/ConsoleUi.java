import java.util.Scanner;

public class ConsoleUi {

    private Menu menu = new Menu();
    private StoreManager storeEngine = new JaxbClassToSdmClass().jaxbClassToStoreManager();

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
        while (isValid == false){
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
        }
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
                }
                case ShowItems: {
                    showItems();
                }
                case PlaceOrder: {
                    placeOrder();
                }
                case ShowStores: {
                    showStores();
                }
                case ShowHistory:{
                    ShowHistory();
                }
                case Exit:{
                    System.exit(0);
                }
            }
        }

    }

    private void ShowHistory() {
    }

    private void showStores() {
    }

    private void placeOrder() {
    }

    private void showItems() {
    }

    private void readFile(){

    }
    
}

