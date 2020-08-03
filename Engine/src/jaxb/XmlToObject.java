package jaxb;

import JaxbClasses.SDMItem;
import JaxbClasses.SuperDuperMarketDescriptor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlToObject {

    public static final String FILE_NAME = "ex1-small.xml";

    public static void main(String[] args) {
        fromXmlFileToObject();
    }

    private static void fromXmlFileToObject() {
        System.out.println("\nFrom File to Object");

        try {

            File file = new File(FILE_NAME);
            JAXBContext jaxbContext = JAXBContext.newInstance(SuperDuperMarketDescriptor.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            SuperDuperMarketDescriptor item = (SuperDuperMarketDescriptor) jaxbUnmarshaller.unmarshal(file);
            System.out.println(SuperDuperMarketDescriptor.class);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
}
