import com.sun.xml.internal.ws.util.StringUtils;

import java.util.Scanner;

public class ConsoleUi {

    private Menu menu = new Menu();
    public enum Echoice{
        readFile,
        ShowStores,
        ShowItems,
        PlaceOrder,
        ShowHistory
    }

    public int validateChoice(int largestChoiceNumber){
        System.out.println("Please enter choose number of a commands above(must be between 1 and " + largestChoiceNumber+"):\n");
        Scanner input = new Scanner(System.in);
        int numOfChoice = 1;
        boolean isChoiceValid = false;
            do {
                if (input.hasNextInt() && numOfChoice > 0 && numOfChoice <= largestChoiceNumber){
                    isChoiceValid = true;
                    numOfChoice = input.nextInt();
                }
                else {
                    System.out.println("Wrong input please try again");
                    input.next();
                }
            }while (isChoiceValid == false);
            return numOfChoice;


    }

}
