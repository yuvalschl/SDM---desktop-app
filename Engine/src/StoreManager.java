import DtoObjects.*;
import Item.*;
import Order.Order;

import java.util.*;

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

    public int NumberOfStoresSellingItem(Item item){
        int numberOfStoresSellingTheItem = 0;
        for(Integer storeId : allStores.keySet()){
            if(allStores.get(storeId).getInventory().containsKey(item.getSerialNumber())){
                numberOfStoresSellingTheItem++;
            }
        }

        return numberOfStoresSellingTheItem;
    }

    public float getAveragePrice(Item item){
        int priceAccumulator = 0;
        for(Integer storeId : allStores.keySet()){
            Map<Integer, Item> currentStoreInventory = allStores.get(storeId).getInventory();
            if(currentStoreInventory.containsKey(item.getSerialNumber())){
                priceAccumulator += currentStoreInventory.get(item.getSerialNumber()).getPrice();
            }
        }

        return priceAccumulator / (float)NumberOfStoresSellingItem(item);
    }

    public HashMap<Integer ,DtoStore> getAllDtoStores(){
        HashMap<Integer ,DtoStore> allDtoStores = new HashMap<>();
        Map<Integer, DtoItem> currentDtoInventory = new HashMap<>();
        Set<DtoOrder> currentDtoOrders = new HashSet<>();
        for(Integer key : allStores.keySet()){
            Store currentStore = allStores.get(key);
            currentDtoInventory = makeDtoInventory(currentStore);
            currentDtoOrders = makeDtoOrders(currentStore);
            allDtoStores.put(key, new DtoStore(currentStore.getName(), currentStore.getSerialNumber(), currentDtoInventory,
                    currentDtoOrders, currentStore.getLocation(), currentStore.getPPK(), currentStore.getTotalPayment()));
        }
        return allDtoStores;
    }

    private Map<Integer, DtoItem> makeDtoInventory(Store store){
        Map<Integer, DtoItem> currentDtoInventory = new HashMap<>();
        for(Integer key : store.getInventory().keySet()){
            Item currentItem = store.getInventory().get(key);
            if(currentItem instanceof UnitItem){
                currentDtoInventory.put(key, new DtoUnitItem(key, currentItem.getName(), currentItem.getPrice(), currentItem.getAmountSold()));
            }
            else {
                currentDtoInventory.put(key, new DtoWeightItem(key, currentItem.getName(), currentItem.getPrice(), currentItem.getAmountSold()));
            }
        }

        return currentDtoInventory;
    }

    private Set<DtoOrder> makeDtoOrders(Store store){
        Set<DtoOrder> currentOrdersDtoSet = new HashSet<>();
        for(Order order : store.getAllOrders()){
            currentOrdersDtoSet.add(new DtoOrder(order.getDateOfOrder(), order.getAmountOfItems(), order.getTotalPriceOfItems(), order.getShippingCost(), order.getTotalCost()));
        }

        return currentOrdersDtoSet;
    }
}
