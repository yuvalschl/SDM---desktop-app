import Item.Item;
import jaxb.JaxbClasses.SDMItem;
import jaxb.JaxbClasses.SDMStore;
import jaxb.JaxbClasses.SuperDuperMarketDescriptor;
import jaxb.XmlToObject;

import java.util.List;
import java.util.Map;

public class StoreManager {
    private  static int itemID = 0;
    private Map<Integer, Store> allStores;
    private ClassDatafiller dataFiller = new ClassDatafiller();
    public StoreManager(){
        SuperDuperMarketDescriptor xmlObject = XmlToObject.fromXmlFileToObject();
        SuperDuperMarketDescriptor superDuperMarketDescriptor= XmlToObject.fromXmlFileToObject();
        }
    public void creatStores(){
        List<SDMStore> storeList = dataFiller.getStoreList();

        for (SDMStore store:storeList) {
            Store newStore = new Store();
            newStore.setSerialNumber(store.getId());
            newStore.setInventory(stor);
        }
    }
}
