package Jaxb;

import Costumer.Customer;
import Exceptions.*;
import Item.Item;
import Item.WeightItem;
import Item.UnitItem;
import Store.*;
import Store.MyIfYouBuy;
import StoreManager.StoreManager;
import Jaxb.jaxbClasses.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class JaxbClassToStoreManager {

    //TODO: do all the new testing
    public StoreManager convertJaxbClassToStoreManager(SuperDuperMarketDescriptor xmlStore) throws DuplicateValueException, InvalidValueException, ItemNotSoldException {
/*        SuperDuperMarketDescriptor xmlStore = XmlToObject.fromXmlFileToObject();*/
        Map<Integer, Item> allItems = createAllItemsMap(xmlStore.getSDMItems().getSDMItem());
        Map<Integer, Store> allStores = createAllStoresMap(xmlStore.getSDMStores().getSDMStore(), allItems);
        Map<Integer, Customer> allCustomers = createAllCustomersMap(xmlStore.getSDMCustomers().getSDMCustomer());
        HashSet<Integer> notSoldItems = checkIfAllTheItemsFromTheFileAreSold(allItems);
        if(!notSoldItems.isEmpty()){
            throw new ItemNotSoldException("Items with id: " + notSoldItems.toString() + " are not sold by any store");
        }
        return new StoreManager(allStores, allItems, allCustomers);
    }

    private Map<Integer, Customer> createAllCustomersMap(List<SDMCustomer> sdmCustomers) throws DuplicateValueException {
        Map<Integer, Customer> allCustomers = new HashMap<>();
        for (SDMCustomer customer : sdmCustomers){
            if(allCustomers.containsKey(customer.getId())){
                throw new DuplicateValueException("Customer with id: " + customer.getId() + " already exists in the system");
            }
            else {
                allCustomers.put(customer.getId(), new Customer(customer.getId(), customer.getName(), new Point(customer.getLocation().getX(), customer.getLocation().getY())));
            }
        }

        return allCustomers;
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
                throw new DuplicateValueException("Store with id: " + store.getId() + " already exists in the system");
            }
            Map<Integer, Item> currentStoreInventory = createCurrentStoreInventory(allItems, store, allItems);
            Set<Discount> currentStoreDiscount = new HashSet<Discount>();
            if(store.getSDMDiscounts() != null){
                currentStoreDiscount = createCurrentStoreDiscount(store.getSDMDiscounts().getSDMDiscount());
            }
            Point currentStoreLocation = new Point(store.getLocation().getX(), store.getLocation().getY());
            if(!isLocationValid(currentStoreLocation)){
                throw new InvalidValueException(store.getId() + "has invalid location");
            }
            Store currentStore = new Store(store.getName(), store.getId(), currentStoreInventory, null, currentStoreLocation, store.getDeliveryPpk(),currentStoreDiscount);
            allStores.put(store.getId(), currentStore);
        }
        return allStores;
    }

    private Set<Discount> createCurrentStoreDiscount(List<SDMDiscount> allDiscounts){
        Set<Discount> currentStoreDiscounts = new HashSet<Discount>();
        for(SDMDiscount sdmDiscount : allDiscounts){
            MyIfYouBuy myIfYouBuy = new MyIfYouBuy(sdmDiscount.getIfYouBuy().getItemId(), (float) sdmDiscount.getIfYouBuy().getQuantity());
            MyThenYouGet thenYouGet = createThenYouGet(sdmDiscount.getThenYouGet());
            currentStoreDiscounts.add(new Discount(sdmDiscount.getName(), myIfYouBuy, thenYouGet));
        }

        return currentStoreDiscounts;
    }

    private MyThenYouGet createThenYouGet(ThenYouGet thenYouGet) {
        Set<Offer> allOffers = new HashSet<Offer>();
        for(SDMOffer sdmOffer : thenYouGet.getSDMOffer()){
            allOffers.add(new Offer(sdmOffer.getItemId(), (float) sdmOffer.getQuantity(), sdmOffer.getForAdditional()));
        }
        return new MyThenYouGet(MyThenYouGet.getOperatorFromSdmOffer(thenYouGet.getOperator()), allOffers);

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
            currentInventory.put(itemToAdd.getId(), itemToAdd);
            allItemsInSystem.get(itemToAdd.getId()).setSold(true);
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
