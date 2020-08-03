import jaxb.JaxbClasses.SuperDuperMarketDescriptor;
import jaxb.XmlToObject;

import java.util.Map;

public class StoreManager {
    private Map<Integer, Store> allStores;

    public StoreManager(){
        SuperDuperMarketDescriptor xmlObject = XmlToObject.fromXmlFileToObject();
    }
}
