import Item.Item;
import jaxb.JaxbClasses.SDMStore;
import jaxb.JaxbClasses.SuperDuperMarketDescriptor;
import jaxb.XmlToObject;

import java.util.List;
import java.util.Map;

public class StoreManager {
    private Map<Integer, Store> allStores;
    private Map<Integer, Item> allItems;

    public StoreManager(Map<Integer, Store> allStores, Map<Integer, Item> allItems) {
        this.allStores = allStores;
        this.allItems = allItems;
    }

    public Map<Integer, Store> getAllStores() {
        return allStores;
    }

    public void setAllStores(Map<Integer, Store> allStores) {
        this.allStores = allStores;
    }

    public Map<Integer, Item> getAllItems() {
        return allItems;
    }

    public void setAllItems(Map<Integer, Item> allItems) {
        this.allItems = allItems;
    }
}
