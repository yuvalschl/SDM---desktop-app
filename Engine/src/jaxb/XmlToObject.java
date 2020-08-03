package jaxb;

import jaxb.JaxbClasses.SuperDuperMarketDescriptor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlToObject {

    public static final String FILE_NAME = "ex1-small.xml";

    public static void main(String[] args) {
        fromXmlFileToObject();
    }

    private static SuperDuperMarketDescriptor fromXmlFileToObject() {
        System.out.println("\nFrom File to Object");

        try {
            File file = new File(FILE_NAME);
            JAXBContext jaxbContext = JAXBContext.newInstance(SuperDuperMarketDescriptor.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return  (SuperDuperMarketDescriptor) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}
