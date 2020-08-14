import Exceptions.*;
import Item.Item;
import Item.WeightItem;
import Item.UnitItem;
import Store.Store;
import jaxb.JaxbClasses.*;
import jaxb.XmlToObject;
import java.awt.*;
import java.util.*;
import java.util.List;

public class JaxbClassToStoreManager {

    public StoreManager convertJaxbClassToStoreManager(SuperDuperMarketDescriptor xmlStore) throws DuplicateValueException, InvalidValueException, ItemNotSoldException {
/*        SuperDuperMarketDescriptor xmlStore = XmlToObject.fromXmlFileToObject();*/
        Map<Integer, Item> allItems = createAllItemsMap(xmlStore.getSDMItems().getSDMItem());
        Map<Integer, Store> allStores = createAllStoresMap(xmlStore.getSDMStores().getSDMStore(), allItems);
        HashSet<Integer> notSoldItems = checkIfAllTheItemsFromTheFileAreSold(allItems);
        if(!notSoldItems.isEmpty()){
            throw new ItemNotSoldException("Items with id: " + notSoldItems.toString() + " are not sold by any store");
        }
        return new StoreManager(allStores, allItems);
    }

    private Map<Integer, Item> createAllItemsMap(List<SDMItem> sdmItems) throws DuplicateValueException {
        Map<Integer, Item> allItems = new HashMap<Integer, Item>();
        for(SDMItem item : sdmItems){
            if(allItems.containsKey(item.getId())){
                throw new DuplicateValueException("Item with id: " + item.getId() + " already exists in the system");
            }
            switch (item.getPurchaseCategory()){
                case "Quantity":
                    allItems.put(item.getId(), new UnitItem(item.getId() ,item.getName()));
                    break;
                case "Weight":
                    allItems.put(item.getId(), new WeightItem(item.getId() ,item.getName()));
                    break;
            }
        }
        return allItems;
    }

    private Map<Integer, Store> createAllStoresMap(List<SDMStore> sdmStores, Map<Integer, Item> allItems) throws DuplicateValueException, InvalidValueException {
        Map<Integer, Store> allStores = new HashMap<Integer, Store>();
        for(SDMStore store : sdmStores){
            if(allStores.containsKey(store.getId())){
                throw new DuplicateValueException("Store.Store with id: " + store.getId() + " already exists in the system");
            }
            Map<Integer, Item> currentStoreInventory = createCurrentStoreInventory(allItems, store, allItems);
            Point currentStoreLocation = new Point(store.getLocation().getX(), store.getLocation().getY());
            if(!isLocationValid(currentStoreLocation)){
                throw new InvalidValueException(store.getId() + "has invalid location");
            }
            Store currentStore = new Store(store.getName(), store.getId(), currentStoreInventory, null, currentStoreLocation, store.getDeliveryPpk());
            allStores.put(store.getId(), currentStore);
        }
        return allStores;
    }
    private Map<Integer, Item> createCurrentStoreInventory(Map<Integer, Item> allItemsFromFile, SDMStore currentStore, Map<Integer, Item> allItemsInSystem) throws InvalidValueException {
        List<SDMSell> sdmSellList = currentStore.getSDMPrices().getSDMSell();
        Map<Integer, Item> currentInventory = new HashMap<Integer, Item>();
        for(SDMSell item : sdmSellList){
            if(!allItemsFromFile.containsKey(item.getItemId())){
                throw new InvalidValueException("Item with id: " + item.getItemId() + " dose not exists in the system and cannot be sold by store with id: " + currentStore.getId());
            }
            if(currentInventory.containsKey(item.getItemId())){
                throw new InvalidValueException("Store.Store with id: " + currentStore.getId() + " already selling item with id:" + item.getItemId());
            }
            Item itemToAdd = allItemsFromFile.get(item.getItemId());
            if(itemToAdd instanceof UnitItem){
                itemToAdd = new UnitItem(itemToAdd, item.getPrice());
            }
            else {
                itemToAdd = new WeightItem(itemToAdd, item.getPrice());
            }
            currentInventory.put(itemToAdd.getSerialNumber(), itemToAdd);
            allItemsInSystem.get(itemToAdd.getSerialNumber()).setSold(true);
        }

        return currentInventory;
    }

    private boolean isLocationValid(Point point){
        int x = point.x;
        int y = point.y;
        return x >= 1 && !(x > 50 | y < 0) && y <= 50;
    }

    private HashSet<Integer> checkIfAllTheItemsFromTheFileAreSold( Map<Integer, Item> allItems){
        HashSet<Integer> notSoldItems = new HashSet<>();
        allItems.keySet().stream().filter(key -> !allItems.get(key).getIsSold()).forEach(notSoldItems::add);
        return notSoldItems;
    }
}
