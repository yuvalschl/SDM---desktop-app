package StoreManager;
import Store.Offer;
import Costumer.Customer;
import Exceptions.InvalidValueException;
import Item.*;
import ItemPair.ItemAmountAndStore;
import Order.*;
import Store.Store;
import Jaxb.XmlToObject;
import Store.Discount;
import javafx.concurrent.Task;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.awt.*;
import java.io.File;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class StoreManager {
    private Map<Integer, Store> allStores;
    private Map<Integer, Item> allItems;
    private Set<Order> allOrders = new HashSet<Order>();
    private String currentFilePath;
    private Map<Integer, Customer> allCustomers;//
    private Task<Boolean> currentTask;


    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public StoreManager(Map<Integer, Store> allStores, Map<Integer, Item> allItems, Map<Integer, Customer> allCustomers) {
        this.allStores = allStores;
        this.allItems = allItems;
        this.allCustomers = allCustomers;
        this.currentFilePath = " ";
    }

    public StoreManager(){
        allStores = new HashMap<Integer, Store>();
        allItems = new HashMap<Integer, Item>();
        allCustomers = new HashMap<Integer, Customer>();
    }


    public DecimalFormat getDecimalFormat() { return decimalFormat; }
    public Task<Boolean> getCurrentTask() {
        return currentTask;
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
            for (ItemAmountAndStore itemAmountAndStore: order.getItemAmountAndStores().values()) {//This loop recreates the lost data from the history load
                int itemID = itemAmountAndStore.getItemId();
                Store store = allStores.get(itemAmountAndStore.getItemStore());
                itemAmountAndStore.setItem(allItems.get(itemID));
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

    public int NumberOfStoresSellingItem(Item item){
        int numberOfStoresSellingTheItem = 0;
        for(Integer storeId : allStores.keySet()){
            if(allStores.get(storeId).getInventory().containsKey(item.getId())){
                numberOfStoresSellingTheItem++;
            }
        }

        return numberOfStoresSellingTheItem;
    }

    public float getAveragePrice(Item item){
        int priceAccumulator = 0;
        for(Integer storeId : allStores.keySet()){
            Map<Integer, Item> currentStoreInventory = allStores.get(storeId).getInventory();
            if(currentStoreInventory.containsKey(item.getId())){
                priceAccumulator += currentStoreInventory.get(item.getId()).getPrice();
            }
        }

        return priceAccumulator / (float)NumberOfStoresSellingItem(item);
    }

   /* public HashMap<Integer ,Store> getAllDtoStores(){
        HashMap<Integer ,Store> allDtoStores = new HashMap<>();
        Map<Integer, Item> currentDtoInventory = new HashMap<>();
        Set<StoreOrder> currentDtoOrders = new HashSet<>();
        for(Integer key : allStores.keySet()){
            Store currentStore = allStores.get(key);
            currentDtoInventory = getDtoInventory(currentStore);
            currentDtoOrders = getDtoOrders(currentStore);
            allDtoStores.put(key, currentStore);
        }
        return allDtoStores;
    }
*/
 /*   public HashMap<Integer ,DtoOrder> getAllDtoOrders(){
        HashMap<Integer ,DtoOrder> allDtoOrders = new HashMap<>();
        for (Order order: allOrders){
            DtoOrder dtoOrder = new DtoOrder(order.getDateOfOrder(),order.getAmountOfItems(),order.getTotalPriceOfItems()
            ,order.getShippingCost(), order.getTotalCost(), order.getDistance(), order.getStores(),order.getItemAmountAndStores());
            allDtoOrders.put(order.getOrderId(), dtoOrder);
        }
        return allDtoOrders;
    }*/

    public void updateItemPrice(Item item, float newPrice, Store store) throws InvalidValueException {
        Store storeToUpdate = allStores.get(store.getSerialNumber());
        storeToUpdate.getInventory().get(item.getId()).setPrice(newPrice);
    }



    public Order createOrder(Point customerLocation, Date date, HashMap<Integer, ItemAmountAndStore> items, Customer customer) {
        float totalPriceOfItems = 0;
        HashMap<Integer, Store> allStoresInOrder = getAllStoresInOrder(items);
        float shippingCost = calcShippingCost(items, customerLocation);
        HashMap<Integer, Float> shippingCostByStore = calcShippingCostByStore(items, customerLocation);
        for (ItemAmountAndStore pair: items.values()) {
            if (pair.item() instanceof UnitItem)
                totalPriceOfItems += (int)pair.amount() * pair.getItem().getPrice();
            else
                totalPriceOfItems += pair.amount() * pair.getItem().getPrice();
        }
        float totalCost = shippingCost + totalPriceOfItems;
        return new Order(date, items.size(), totalPriceOfItems, shippingCost, totalCost, allStoresInOrder, items, shippingCostByStore, customer);
    }


    private HashMap<Integer, Float> calcShippingCostByStore(HashMap<Integer, ItemAmountAndStore> allItems, Point customerLocation){
        HashMap<Integer, Float> shippingCostMap = new HashMap<>();
        for(ItemAmountAndStore item : allItems.values()){
            float distance = distanceCalculator(customerLocation, item.getStore().getLocation());
            shippingCostMap.put(item.getStore().getSerialNumber(), distance * item.getStore().getPPK());
        }

        return  shippingCostMap;
    }

    public float calcShippingCost(HashMap<Integer, ItemAmountAndStore> allItems, Point customerLocation){
        float shippingCost = 0;
        Set<Store> sellingStores = new HashSet<Store>();
        for(ItemAmountAndStore item : allItems.values()){
            if(!sellingStores.contains(item.getStore())){
                float distance = distanceCalculator(customerLocation, item.getStore().getLocation());
                shippingCost += distance * item.getStore().getPPK();
                sellingStores.add(item.getStore());
            }
        }

        return shippingCost;
    }

    private HashMap<Integer, Store> getAllStoresInOrder(HashMap<Integer, ItemAmountAndStore> allItems){
        HashMap<Integer, Store> allStores = new HashMap<Integer, Store>();
        for(ItemAmountAndStore item : allItems.values()){
            allStores.put(item.getStore().getSerialNumber(), item.getStore());
        }

        return allStores;
    }


    public void placeOrder(Order order) {//finilaize the order after final approval, in this method we add the order to the order set and update the amount sold in allitems

        allOrders.add(order);
        for (ItemAmountAndStore item : order.getItemAmountAndStores().values()) {
            int itemID = item.item().getId();
            allItems.get(itemID).setAmountSold(item.getAmount()+ allItems.get(itemID).getAmountSold());

            Store currentStore = order.getStores().get(item.getStore().getSerialNumber());
            order.getStores().get(currentStore.getSerialNumber()).getInventory().get(itemID).setAmountSold((currentStore.getInventory().get(itemID).getAmountSold() + item.getAmount()));//update amount sold
            order.getStores().get(item.getStore().getSerialNumber()).setTotalPayment(calcTotalPayment(currentStore, item));
        }
        for(Map.Entry<Integer, Float> shippingCosts : order.getShippingCostByStore().entrySet()){
            Store currentStore = allStores.get(shippingCosts.getKey());
            currentStore.setTotalDeliveryCost(currentStore.getTotalDeliveryCost() + shippingCosts.getValue());
        }

        for(Map.Entry<Integer, Store> store : order.getStores().entrySet()){
            float shippingCost = order.getShippingCostByStore().get(store.getKey());
            float distance = distanceCalculator(order.getCustomer().getLocation(), store.getValue().getLocation()) ;
            StoreOrder ordersToAdd = new StoreOrder(order.getDateOfOrder(), shippingCost, distance, store.getValue(), order.getOrderId(),store.getValue().getPPK());
            for(ItemAmountAndStore item : order.getItemAmountAndStores().values()){
                if(item.getStore().getSerialNumber() == store.getValue().getSerialNumber()){
                    ordersToAdd.addItemToOrder(item);
                }
            }

            store.getValue().getAllOrders().put(ordersToAdd.getOrderId(), ordersToAdd);
        }
    }


    private float calcTotalPayment(Store store, ItemAmountAndStore item){
        return store.getTotalPayment() + store.getInventory().get(item.getItem().getId()).getPrice();
    }

    public float distanceCalculator(Point point1, Point point2){
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

    /**
     * this method find the cheapest item in the system by id
     * @param itemId searched item id
     * @return the cheapest item in the system
     */
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
        return new ItemAmountAndStore(cheapestItem,cheapestStore);
    }

    private int howManyStoresSellItem(Item item) {
        int numberOfStoreThatSell = 0;
        for (Map.Entry<Integer, Store> set : allStores.entrySet()) {
            if (set.getValue().getInventory().containsKey(item.getId()))
                numberOfStoreThatSell++;
        }
        return  numberOfStoreThatSell;
    }

    private  int howManyTimesItemSold(Item item){
        int timesSold = 0;
        if(allOrders != null)
            for (Order order: allOrders) {
                for (ItemAmountAndStore pair:order.getItemAmountAndStores().values())
                    if(pair.item().equals(item)) {
                        timesSold += pair.amount();
                    }
            }
        return  timesSold;
    }

    public void addItemToStore(Store store, Item itemToUpdate, float price) throws InvalidValueException {
        Store storeToUpdate = allStores.get(store.getSerialNumber());
        storeToUpdate.getInventory().put(itemToUpdate.getId(), itemToUpdate);
        storeToUpdate.getInventory().get(itemToUpdate.getId()).setPrice(price);
    }

    /**
     * deletes item from store
     * @return a string that will hold the information about whether the item to delete was a part of a discount, if not null is returned
     */
    public String deleteItemFromStore(Store storeToDeleteFrom, int itemId){
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
    public ArrayList<Discount> getEntitledDiscounts(Order order) {
        ArrayList<Discount> discounts = new ArrayList<Discount>();
        for (Map.Entry<Integer,Store> stores: order.getStores().entrySet()){
            Store store = stores.getValue();
            HashMap<Integer, ItemAmountAndStore> itemAmountAndStores = order.getItemAmountAndStores();
            for (ItemAmountAndStore itemAndAmount : itemAmountAndStores.values()) {//loop through the items and check if the amount and id matches any of the stores discounts
                for (Discount discount : store.getAllDiscounts()) {
                    if ( itemAndAmount.getIsPartOfDiscount()==false &&  itemAndAmount.getItemId() == discount.getIfYouBuy().getItemId() && itemAndAmount.getDiscountItemAmount() >= discount.getIfYouBuy().getQuantity()) {
                        discounts.add(discount);
                    }
                }
            }
        }
        return discounts;
    }

    /**
     * takes order and discounts and adds all the discount items to the order
     * @param discount a discount to add to order
     * @param order the order to add the items to
     */
    public Map<Integer,ItemAmountAndStore> addDiscountItemsToOrderAllOrNothing(Order order, Discount discount){
        Map<Integer, ItemAmountAndStore> itemsMap = new HashMap<Integer, ItemAmountAndStore>();
        for(Offer offer : discount.getThenYouGet().getAllOffers()){
            itemsMap.put(offer.getItemId(), new ItemAmountAndStore(allItems.get(offer.getItemId()), offer.getQuantity(), allStores.get(discount.getStoreId())));
            itemsMap.get(offer.getItemId()).setPartOfDiscount(true);
        }
        discount.getThenYouGet().getAllOffers().forEach(offer -> addDiscountItemToOrder(offer.getItemId(), order, discount, false));

        itemsMap.values().forEach(itemAmountAndStore -> itemAmountAndStore.setAmount(order.getItemAmountAndStores().get(itemAmountAndStore.getItemId()).getAmount()));
        updateItemDiscountAmount(discount.getIfYouBuy().getItemId(), order, discount);
        return itemsMap;

    }
    /**
     * takes an offer ID, an order and discounts and adds a single discount offer to the order
     * @param discount a discount to add to order
     * @param order the order to add the items to
     */
    public ItemAmountAndStore addDiscountItemToOrder(int offerItemIDToAdd, Order order, Discount discount, boolean isOneOf){
        Offer offer = discount.getThenYouGet().getOfferByID(offerItemIDToAdd);
        Store store = allStores.get(discount.getStoreId());
        Item item = allItems.get(offerItemIDToAdd);
        ItemAmountAndStore itemAmountAndStoreToReturn = null;
        //the if below checks if the offered item is already in the order inventory
        if(order.getItemAmountAndStores().values().stream().anyMatch(itemAmountAndStore -> itemAmountAndStore.getItemId() == offerItemIDToAdd && itemAmountAndStore.getIsPartOfDiscount())){
            updateDiscountItem(order, offerItemIDToAdd, offer);
            for (ItemAmountAndStore currItem: order.getItemAmountAndStores().values() ){
                if(currItem.getItemId() == offerItemIDToAdd && currItem.getIsPartOfDiscount()){
                    itemAmountAndStoreToReturn = currItem;
                    break;
                }
            }
        }
        else{
            itemAmountAndStoreToReturn = new ItemAmountAndStore(item, offer.getQuantity(),store);//create the item to be added
            itemAmountAndStoreToReturn.setPartOfDiscount(true);
            itemAmountAndStoreToReturn.setOfferPrice(offer.getForAdditional());
            int key ;
            if (order.getItemAmountAndStores().containsKey(itemAmountAndStoreToReturn.getItemId())) {//this if is to prevent the case that an item with the same id(it will only happen if on offer item has the same id as an item in item amount and store) is to be entered to the order item amount and store map
                key = allItems.get(itemAmountAndStoreToReturn.getItemId()).getMaxID();
                allItems.get(itemAmountAndStoreToReturn.getItemId()).setMaxID(key+1);
            }
            else
                key = itemAmountAndStoreToReturn.getItemId();
            order.getItemAmountAndStores().put(key ,itemAmountAndStoreToReturn);
        }
        if(isOneOf)
            updateItemDiscountAmount(discount.getIfYouBuy().getItemId(), order, discount);
        order.setAmountOfItems(order.getAmountOfItems()+1);//increase amount of items by one
        order.setTotalPriceOfItems(order.getTotalPriceOfItems()+offer.getForAdditional());//add the cost of the offer to the total cost of items
        order.setTotalCost(order.getTotalCost()+ offer.getForAdditional());
        return itemAmountAndStoreToReturn;
    }
    /**
     * updates an item amount. this method is used if a discount item already appears in the order
     * @param offerItemIDToAdd an id of the offered item
     * @param offer an offer to take details from
     * @param order the order which the item is needed to be updated in
     */
    private void updateDiscountItem(Order order, int offerItemIDToAdd, Offer offer) {
        AtomicReference<ItemAmountAndStore> itemToUpdate = new AtomicReference<ItemAmountAndStore>();
        order.getItemAmountAndStores().forEach((ID,currItem)-> {
            if(currItem.getItemId() == offerItemIDToAdd && currItem.getIsPartOfDiscount())
                itemToUpdate.set(currItem);
        });
        itemToUpdate.get().setAmount(itemToUpdate.get().getAmount()+ offer.getQuantity());
    }


    private void updateItemDiscountAmount(int itemID,Order order,Discount discount){
        for (ItemAmountAndStore item: order.getItemAmountAndStores().values()){
            if (item.getItem().getId() == itemID ){
                item.setDiscountItemAmount(item.getDiscountItemAmount() - discount.getIfYouBuy().getQuantity());
                discount.getThenYouGet().setDecreasedFromItemAmount(true);
            }
        }
    }

    /**
     * used to add a new store from user input
     */
    public void addNewStore(Store storeToAdd){
        allStores.put(storeToAdd.getSerialNumber(), storeToAdd);
    }

}
