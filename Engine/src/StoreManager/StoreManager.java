package StoreManager;
import Costumer.Customer;
import DtoObjects.*;
import Exceptions.InvalidValueException;
import Item.*;
import ItemPair.ItemAmountAndStore;
import Order.*;
import Store.Store;
import Jaxb.XmlToObject;
import Store.Discount;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.awt.*;
import java.io.File;
import java.util.*;

public class StoreManager {
    private Map<Integer, Store> allStores;
    private Map<Integer, Item> allItems;
    private Set<Order> allOrders = new HashSet<Order>();
    private String currentFilePath;
    private Map<Integer, Customer> allCustomers;


    public StoreManager(Map<Integer, Store> allStores, Map<Integer, Item> allItems, Map<Integer, Customer> allCustomers) {
        this.allStores = allStores;
        this.allItems = allItems;
        this.allCustomers = allCustomers;
        this.currentFilePath = " ";
    }

    public void setAllOrders(Set<Order> allOrders) {
        this.allOrders = allOrders;
    }

    public String getCurrentFilePath() {
        return currentFilePath;
    }

    public void setCurrentFilePath(String currentFilePath) {
        this.currentFilePath = currentFilePath;
    }

    public Set<Order> getAllOrders() {
        return allOrders;
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

    public Map<Integer, Customer> getAllCustomers() {
        return allCustomers;
    }

    public void setAllCustomers(Map<Integer, Customer> allCustomers) {
        this.allCustomers = allCustomers;
    }

    public void loadOrder(File file) throws JAXBException {
        OrderWrapper orderWrapper = XmlToObject.fromXmlFileToOrder(file);
        for (Order order: orderWrapper.getOrders()){
            for (ItemAmountAndStore itemAmountAndStore: order.getItemAmountAndStores()) {//This loop recreates the lost data from the history load
                int itemID = itemAmountAndStore.getItemId();
                Store store = allStores.get(itemAmountAndStore.getItemStore());
                itemAmountAndStore.setItem(DtoConvertor.itemToDtoItem(allItems.get(itemID)));
                itemAmountAndStore.setStore(store);
                order.getStores().put(store.getSerialNumber(),store);
            }
            placeOrder(order);
        }

    }


    public void printOrder(Order order){
        System.out.println(order.getDateOfOrder()+"\n"+
                order.getOrderId());
    }

    public void saveHistoryToFile(File file) throws JAXBException {
        OrderWrapper orders = new OrderWrapper(allOrders);
            JAXBContext jaxbContext = JAXBContext.newInstance(OrderWrapper.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(orders, file);
    }

    public int NumberOfStoresSellingItem(DtoItem item){
        int numberOfStoresSellingTheItem = 0;
        for(Integer storeId : allStores.keySet()){
            if(allStores.get(storeId).getInventory().containsKey(item.getSerialNumber())){
                numberOfStoresSellingTheItem++;
            }
        }

        return numberOfStoresSellingTheItem;
    }

    public float getAveragePrice(DtoItem item){
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
        Set<DtoStoreOrder> currentDtoOrders = new HashSet<>();
        for(Integer key : allStores.keySet()){
            Store currentStore = allStores.get(key);
            currentDtoInventory = getDtoInventory(currentStore);
            currentDtoOrders = getDtoOrders(currentStore);
            allDtoStores.put(key, DtoConvertor.storeToDtoStore(currentStore));
        }
        return allDtoStores;
    }

    public HashMap<Integer ,DtoOrder> getAllDtoOrders(){
        HashMap<Integer ,DtoOrder> allDtoOrders = new HashMap<>();
        for (Order order: allOrders){
            DtoOrder dtoOrder = new DtoOrder(order.getDateOfOrder(),order.getAmountOfItems(),order.getTotalPriceOfItems()
            ,order.getShippingCost(), order.getTotalCost(), order.getDistance(), order.getStores(),order.getItemAmountAndStores());
            allDtoOrders.put(order.getOrderId(), dtoOrder);
        }
        return allDtoOrders;
    }

    public void updateItemPrice(DtoItem item, float newPrice, DtoStore store) throws InvalidValueException {
        Store storeToUpdate = allStores.get(store.getSerialNumber());
        storeToUpdate.getInventory().get(item.getSerialNumber()).setPrice(newPrice);
    }

    private Map<Integer, DtoItem> getDtoInventory(Store store){
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

    private Set<DtoStoreOrder> getDtoOrders(Store store){
        Set<DtoStoreOrder> currentOrdersDtoSet = new HashSet<>();
        if(store.getAllOrders() != null){
            for(StoreOrder order : store.getAllOrders()){
                currentOrdersDtoSet.add(new DtoStoreOrder(order.getDateOfOrder(),order.getItems().size(),order.getTotalPriceOfItems(),
                        order.getShippingCost(),order.getTotalCost(),order.getDistance(), store, order.getItems(), order.getOrderId()));
            }
        }

        return currentOrdersDtoSet;
    }

    public Map<Integer, DtoItem> getAllDtoItems(){
        Map<Integer, DtoItem> allDtoItems = new HashMap<Integer, DtoItem>();
        for(Integer key : allItems.keySet()){
            Item currentItem = allItems.get(key);
            if(currentItem instanceof UnitItem){
                allDtoItems.put(key, new DtoUnitItem(currentItem.getId(), currentItem.getName(), currentItem.getPrice(),currentItem.getAmountSold()));
            }
            else {
                allDtoItems.put(key, new DtoWeightItem(currentItem.getId(), currentItem.getName(), currentItem.getPrice(),currentItem.getAmountSold()));
            }
        }
        return allDtoItems;
    }

    public Order createOrder(Point customerLocation, Date date, ArrayList<ItemAmountAndStore> items) {

        int totalPriceOfItems = 0;
        HashMap<Integer, Store> allStoresInOrder = getAllStoresInOrder(items);
        float shippingCost = calcShippingCost(items, customerLocation);
        HashMap<Integer, Float> shippingCostByStore = calcShippingCostByStore(items, customerLocation);
        for (ItemAmountAndStore pair: items) {
            if (pair.item() instanceof DtoUnitItem)
                totalPriceOfItems += (int)pair.amount() * pair.getItem().getPrice();
            else
                totalPriceOfItems += pair.amount() * pair.getItem().getPrice();

        }
        float totalCost = shippingCost + totalPriceOfItems;
        return new Order(date, items.size(), totalPriceOfItems, shippingCost, totalCost, allStoresInOrder, items, shippingCostByStore);
    }


    private HashMap<Integer, Float> calcShippingCostByStore(ArrayList<ItemAmountAndStore> allItems, Point customerLocation){
        HashMap<Integer, Float> shippingCostMap = new HashMap<>();
        for(ItemAmountAndStore item : allItems){
            float distance = distanceCalculator(customerLocation, item.getStore().getLocation());
            shippingCostMap.put(item.getStore().getSerialNumber(), distance * item.getStore().getPPK());
        }

        return  shippingCostMap;
    }

    private float calcShippingCost(ArrayList<ItemAmountAndStore> allItems, Point customerLocation){
        float shippingCost = 0;
        Set<Store> sellingStores = new HashSet<Store>();
        for(ItemAmountAndStore item : allItems){
            if(!sellingStores.contains(item.getStore())){
                float distance = distanceCalculator(customerLocation, item.getStore().getLocation());
                shippingCost += distance * item.getStore().getPPK();
                sellingStores.add(item.getStore());
            }
        }

        return shippingCost;
    }

    private HashMap<Integer, Store> getAllStoresInOrder(ArrayList<ItemAmountAndStore> allItems){
        HashMap<Integer, Store> allStores = new HashMap<Integer, Store>();
        for(ItemAmountAndStore item : allItems){
            allStores.put(item.getStore().getSerialNumber(), item.getStore());
        }

        return allStores;
    }

    public void placeOrder(Order order) {//finilaize the order after final approval, in this method we add the order to the order set and update the amount sold in allitems
        allOrders.add(order);
        for (ItemAmountAndStore item : order.getItemAmountAndStores()) {
            int itemID = item.item().getSerialNumber();
            allItems.get(itemID).setAmountSold(item.getAmount());

            Store currentStore = order.getStores().get(item.getStore().getSerialNumber());
            order.getStores().get(currentStore.getSerialNumber()).getInventory().get(itemID).setAmountSold((int) (currentStore.getInventory().get(itemID).getAmountSold()+ item.getAmount()));//update amount sold
            order.getStores().get(item.getStore().getSerialNumber()).setTotalPayment(calcTotalPayment(currentStore, item));
        }
        for(Map.Entry<Integer, Float> shippingCosts : order.getShippingCostByStore().entrySet()){
            Store currentStore = allStores.get(shippingCosts.getKey());
            currentStore.setTotalDeliveryCost(currentStore.getTotalDeliveryCost() + shippingCosts.getValue());
        }

        for(Map.Entry<Integer, Store> store : order.getStores().entrySet()){
            float shippingCost = order.getShippingCostByStore().get(store.getKey());
            StoreOrder orderToAdd = new StoreOrder(order.getDateOfOrder(), shippingCost, order.getDistance(), store.getValue(), order.getOrderId());
            for(ItemAmountAndStore item : order.getItemAmountAndStores()){
                if(item.getStore().getSerialNumber() == store.getValue().getSerialNumber()){
                    orderToAdd.addItemToOrder(item);
                }
            }

            store.getValue().getAllOrders().add(orderToAdd);
        }
    }


    private float calcTotalPayment(Store store, ItemAmountAndStore item){
        return store.getTotalPayment() + store.getInventory().get(item.getItem().getSerialNumber()).getPrice();
    }

    private float distanceCalculator(Point point1, Point point2){
        return (float) Math.sqrt(Math.pow(point1.x-point2.x, 2)+Math.pow(point1.y-point2.y, 2));
    }

    public String getAllStoresDetails(){
        //allItems.forEach((Integer, Item)-> System.out.println(Item.getItemDetails()));
        String storeDetails = "";
        for (Map.Entry<Integer, Store> set : allStores.entrySet()) {
             storeDetails += set.getValue().toString();
        }
        return storeDetails;
    }


    public ItemAmountAndStore getCheapestItem(int itemId){
        Item cheapestItem = null;
        Store cheapestStore = null;
        ItemAmountAndStore itemToReturn;
        float minPrice = Integer.MAX_VALUE;
        for(Map.Entry<Integer, Store> store : allStores.entrySet()){
            for(Map.Entry<Integer, Item> item : store.getValue().getInventory().entrySet()){
                if(item.getValue().getId() == itemId){
                    if (item.getValue().getPrice() < minPrice){
                        minPrice = item.getValue().getPrice();
                        cheapestItem = item.getValue();
                        cheapestStore = store.getValue();
                    }
                }
            }
        }
        return new ItemAmountAndStore(DtoConvertor.itemToDtoItem(cheapestItem), cheapestStore);
    }

    private int howManyStoresSellItem(DtoItem item) {
        int numberOfStoreThatSell = 0;
        for (Map.Entry<Integer, Store> set : allStores.entrySet()) {
            if (set.getValue().getInventory().containsKey(item.getSerialNumber()))
                numberOfStoreThatSell++;
        }
        return  numberOfStoreThatSell;
    }

    private  int howManyTimesItemSold(Item item){
        int timesSold = 0;
        if(allOrders != null)
            for (Order order: allOrders) {
                for (ItemAmountAndStore pair:order.getItemAmountAndStores())
                    if(pair.item().equals(item)) {
                        timesSold += pair.amount();
                    }
            }
        return  timesSold;
    }

    public void addItemToStore(DtoStore store, DtoItem itemToUpdate, float price) throws InvalidValueException {
        Store storeToUpdate = allStores.get(store.getSerialNumber());
        storeToUpdate.getInventory().put(itemToUpdate.getSerialNumber(), DtoConvertor.dtoItemToItem(itemToUpdate));
        storeToUpdate.getInventory().get(itemToUpdate.getSerialNumber()).setPrice(price);
    }

    /**
     * deletes item from store
     * @return a string that will hold the information about whether the item to delete was a part of a discount, if not null is returned
     */
    public String deleteItemFromStore(DtoStore storeToDeleteFrom, int itemId){
        String stringToReturn = null;
        Store storeToUpdate = allStores.get(storeToDeleteFrom.getSerialNumber());
        Set<Discount> storeDiscounts = allStores.get(storeToDeleteFrom.getSerialNumber()).getAllDiscounts();//get the store discount
        for (Discount discount: storeDiscounts){//loop through discount and check if the item to be deleted is part of a discount
            if (discount.getIfYouBuy().getItemId() == itemId){
                stringToReturn = "Item is part of the discount "+discount.getName()+" the discount will be deleted.";
                storeDiscounts.remove(discount);
                break;
            }
        }
        storeToUpdate.getInventory().remove(itemId);
        return stringToReturn;
    }

    /**
     * looks if any of the items id and quantity  matches any of the stores discounts
     * @return an arrayList with all the relevant discounts
     */
    public ArrayList<Discount> getEntitledDiscounts(int storeID, ArrayList<ItemAmountAndStore> itemAmountAndStores) {
        Store store = getAllStores().get(storeID);
        ArrayList<Discount> discounts = new ArrayList<Discount>();
        for (ItemAmountAndStore itemAndAmount : itemAmountAndStores) {//loop through the items and check if the amount and id matches any of the stores discounts
            for (Discount discount : store.getAllDiscounts()) {
                if (itemAndAmount.getItemId() == discount.getIfYouBuy().getItemId() && itemAndAmount.getAmount() == discount.getIfYouBuy().getQuantity()) {
                    discounts.add(discount);
                }
            }
        }
        return discounts;
    }

    /**
     * takes orders and discounts and add the discount items to the order
     * @param discounts an array with all the discounts to add to order
     * @param order the order to add the items to
     */
    public void AddDiscountItemsToOrderAllOrNothing(ArrayList<Discount> discounts, Order order){
        for (Discount discount: discounts){
            discount.getThenYouGet().getAllOffers();
        }
    }
    public void AddDiscountItemsToOrderOneOf()
}
