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

    public int getAndValidateChoice(int largestChoiceNumber){
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


}
