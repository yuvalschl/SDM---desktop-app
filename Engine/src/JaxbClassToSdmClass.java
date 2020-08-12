import Exceptions.InvalidValueException;
import Item.Item;
import Item.WeightItem;
import Item.UnitItem;
import jaxb.JaxbClasses.*;
import jaxb.XmlToObject;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JaxbClassToSdmClass {

    public StoreManager jaxbClassToStoreManager(){
        SuperDuperMarketDescriptor xmlStore = XmlToObject.fromXmlFileToObject();
        Map<Integer, Item> allItems = createAllItemsMap(xmlStore.getSDMItems().getSDMItem());
        Map<Integer, Store> allStores = createAllStoresMap(xmlStore.getSDMStores().getSDMStore(), allItems);

        return new StoreManager(allStores, allItems);
    }

    private Map<Integer, Item> createAllItemsMap(List<SDMItem> sdmItems) throws InvalidValueException {
        Map<Integer, Item> allItems = new HashMap<Integer, Item>();
        for(SDMItem item : sdmItems){
            if(allItems.containsKey(item.getId())){
                throw new
            }
            switch (item.getPurchaseCategory().toLowerCase()){
                case "quantity":
                    allItems.put(item.getId(), new UnitItem(item.getId() ,item.getName()));
                    break;
                case "weight":
                    allItems.put(item.getId(), new WeightItem(item.getId() ,item.getName()));
                    break;
                default:
                    throw new InvalidValueException(item.getName(), "Item", "sell type");
            }
        }
        return allItems;
    }

    private Map<Integer, Store> createAllStoresMap(List<SDMStore> sdmStores, Map<Integer, Item> allItems) {
        Map<Integer, Store> allStores = new HashMap<Integer, Store>();
        for(SDMStore store : sdmStores){
            Map<Integer, Item> currentStoreInventory = createCurrentStoreInventory(allItems, store);
            Point currentStoreLocation = new Point(store.getLocation().getX(), store.getLocation().getY());
            Store currentStore = new Store(store.getName(), store.getId(), currentStoreInventory, null, currentStoreLocation, store.getDeliveryPpk());
            allStores.put(store.getId(), currentStore);
        }
        return allStores;
    }
    private Map<Integer, Item> createCurrentStoreInventory(Map<Integer, Item> allItems, SDMStore currentStore){
        List<SDMSell> sdmSellList = currentStore.getSDMPrices().getSDMSell();
        Map<Integer, Item> currentInventory = new HashMap<Integer, Item>();
        for(SDMSell item : sdmSellList){
            Item itemToAdd = allItems.get(item.getItemId());
            if(itemToAdd instanceof UnitItem){
                itemToAdd = new UnitItem(itemToAdd, item.getPrice());
            }
            else {
                itemToAdd = new WeightItem(itemToAdd, item.getPrice());
            }
            currentInventory.put(itemToAdd.getSerialNumber(), itemToAdd);
        }

        return currentInventory;
    }
}
