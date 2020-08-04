import jaxb.JaxbClasses.Location;
import jaxb.JaxbClasses.SDMItem;
import jaxb.JaxbClasses.SDMStore;
import jaxb.JaxbClasses.SuperDuperMarketDescriptor;
import jaxb.XmlToObject;

import java.util.List;

public class ClassDatafiller {
    private SuperDuperMarketDescriptor superDuperMarketDescriptor= XmlToObject.fromXmlFileToObject();
    public List<SDMStore> getStoreList() {
        return (List<SDMStore>) superDuperMarketDescriptor.getSDMStores();
    }
    public List<SDMItem> getItemList() {
        return (List<SDMItem>) superDuperMarketDescriptor.getSDMItems();// TO DO: CHECK IF WE CAN OVRIDE THE CAST HERE
    }

}
