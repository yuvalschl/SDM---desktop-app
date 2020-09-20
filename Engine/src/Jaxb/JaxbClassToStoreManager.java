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
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.concurrent.Task;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class JaxbClassToStoreManager extends Task<StoreManager> {
    private File file;
    private BooleanProperty xmlLoaded;
    private StoreManager storeManager;

    private final int SLEEP_TIME = 5;

    public JaxbClassToStoreManager(StoreManager storeManager, File file, BooleanProperty xmlLoaded){
        this.file = file;
        this.xmlLoaded = xmlLoaded;
        this.storeManager = storeManager;
    }

    @Override
    protected StoreManager call() throws Exception {
        updateMessage("Loading file...");
        for (int i = 0 ; i < 10 ; i++){
            updateProgress(i,100);
            TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
        }

        this.storeManager = convertJaxbClassToStoreManager(Objects.requireNonNull(XmlToObject.fromXmlFileToObject(file)));
        if(storeManager != null){
            xmlLoaded.setValue(false);
        }
        return this.storeManager;
    }

    //TODO: do all the new testing
    public StoreManager convertJaxbClassToStoreManager(SuperDuperMarketDescriptor xmlStore) throws DuplicateValueException, InvalidValueException, ItemNotSoldException, InterruptedException {
        try{
            int i = 10;
            updateMessage("Checking items");
            for (i = i ; i < 20 ; i++){
                updateProgress(i,100);
                TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
            }
            Map<Integer, Item> allItems = createAllItemsMap(xmlStore.getSDMItems().getSDMItem());

            updateMessage("Checking stores");
            for (i = i ; i < 45 ; i++){
                updateProgress(i,100);
                TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
            }

            TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
            Map<Integer, Store> allStores = createAllStoresMap(xmlStore.getSDMStores().getSDMStore(), allItems);

            updateMessage("Checking customers");
            for (i = i ; i < 75 ; i++){
                updateProgress(i,100);
                TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
            }
            TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
            Map<Integer, Customer> allCustomers = createAllCustomersMap(xmlStore.getSDMCustomers().getSDMCustomer());

            updateMessage("Checking that all items are sold");
            for (i = i ; i < 90 ; i++){
                updateProgress(i,100);
                TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
            }
            TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
            HashSet<Integer> notSoldItems = checkIfAllTheItemsFromTheFileAreSold(allItems);

            for(Customer customer : allCustomers.values()){
                for(Store store : allStores.values()){
                    if(customer.getLocation().equals(store.getLocation())){
                        throw new DuplicateValueException(customer.getName() + " and " + store.getName() + " are in the same location");
                    }
                }
            }

            if(!notSoldItems.isEmpty()){
                throw new ItemNotSoldException("Items with id: " + notSoldItems.toString() + " are not sold by any store");
            }
            updateMessage("File loaded successfully");
            updateProgress(100,100);
            return new StoreManager(allStores, allItems, allCustomers);
        }
        catch (Exception e){
            updateMessage(e.getMessage());
            return null;
        }
    }

    private Map<Integer, Customer> createAllCustomersMap(List<SDMCustomer> sdmCustomers) throws DuplicateValueException, InvalidValueException {
        Map<Integer, Customer> allCustomers = new HashMap<>();
        for (SDMCustomer customer : sdmCustomers){
            if(allCustomers.containsKey(customer.getId())){
                throw new DuplicateValueException("Customer with id: " + customer.getId() + " already exists in the system");
            }
            else if(!isLocationValid(new Point(customer.getLocation().getX(), customer.getLocation().getY()))){
                throw new InvalidValueException(customer.getName() + " has invalid location");
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
            }        }
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
                currentStoreDiscount = createCurrentStoreDiscount(store.getSDMDiscounts().getSDMDiscount(), currentStoreInventory, store.getName());
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

    private Set<Discount> createCurrentStoreDiscount(List<SDMDiscount> allDiscounts, Map<Integer, Item> currentStoreInventory, String storeName) throws InvalidValueException {
        Set<Discount> currentStoreDiscounts = new HashSet<Discount>();
        for(SDMDiscount sdmDiscount : allDiscounts){
            MyIfYouBuy myIfYouBuy = new MyIfYouBuy(sdmDiscount.getIfYouBuy().getItemId(), (float) sdmDiscount.getIfYouBuy().getQuantity());
            MyThenYouGet thenYouGet;
            if(currentStoreInventory.containsKey(myIfYouBuy.getItemId())){
                thenYouGet = createThenYouGet(sdmDiscount.getThenYouGet());
                for(Offer offer : thenYouGet.getAllOffers()){
                    if(!currentStoreInventory.containsKey(offer.getItemId())){
                        throw new InvalidValueException(storeName + " dose not sell item with id: " + offer.getItemId());
                    }
                }
            }
            else {
                throw new InvalidValueException(storeName + " dose not sell item with id: " + myIfYouBuy.getItemId());
            }
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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isXmlLoaded() {
        return xmlLoaded.get();
    }

    public BooleanProperty xmlLoadedProperty() {
        return xmlLoaded;
    }

    public void setXmlLoaded(boolean xmlLoaded) {
        this.xmlLoaded.set(xmlLoaded);
    }


}
