package Jaxb;

import Order.*;
import Jaxb.jaxbClasses.SuperDuperMarketDescriptor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlToObject {

/*    public static final String FILE_NAME = "ex1-big.xml";*/

    public static SuperDuperMarketDescriptor fromXmlFileToObject(File file) {

        try {
/*            File file = new File(FILE_NAME);*/
            JAXBContext jaxbContext = JAXBContext.newInstance(SuperDuperMarketDescriptor.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return  (SuperDuperMarketDescriptor) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static OrderWrapper fromXmlFileToOrder(File file) throws JAXBException {
        try {

        JAXBContext jaxbContext = JAXBContext.newInstance(OrderWrapper.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        OrderWrapper order = (OrderWrapper) jaxbUnmarshaller.unmarshal(file);
        return order;
    } catch (JAXBException e) {
        e.printStackTrace();
        return null;
    }
    }
}
