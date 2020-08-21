package ConsoleUi;

import ConsoleUi.ConsoleUi;

import javax.xml.bind.JAXBException;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws JAXBException {
        ConsoleUi ui = new ConsoleUi();
        ui.runUI();
    }
}