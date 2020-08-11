import Item.Item;
import Item.WeightItem;
import Item.UnitItem;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.awt.*;
import java.util.*;

public class StoreManager {
    private Map<Integer, Store> allStores;
    private Map<Integer, Item> allItems;
    private Set<Order> allOrders;
    public StoreManager(Map<Integer, Store> allStores, Map<Integer, Item> allItems) {
        this.allStores = allStores;
        this.allItems = allItems;
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
    public Order createOrder(Point customerLocation, int storeID, Date date, ArrayList<ItemPair> items)
    {

        allOrders = new HashSet<>();
        int totalPriceOfItems = 0;
        float distance = distanceCalculator(customerLocation, allStores.get(storeID).getLocation());
        float shippingCost = distance * allStores.get(storeID).getPPK();
        for (ItemPair pair: items) {
            if (pair.item() instanceof UnitItem)
                totalPriceOfItems += (int)pair.amount() * pair.item().getPrice();
            else
                totalPriceOfItems += pair.amount() * pair.item().getPrice();

        }
        Order newOrder = new Order(date,items.size(),totalPriceOfItems, shippingCost, totalPriceOfItems + shippingCost, items, distance, allStores.get(storeID) );
        return newOrder;
    }

    public void placeOrder(Order order) {
        allOrders.add(order);
    }

    private float distanceCalculator(Point point1, Point point2){
        return (float) Math.sqrt(Math.pow(point1.x-point2.x, 2)+Math.pow(point1.y-point2.y, 2));
    }

    public String getAllStoresDetails(){//TODO: add סעיף e an below
        //allItems.forEach((Integer, Item)-> System.out.println(Item.getItemDetails()));
        String storeDetails = "";
        for (Map.Entry<Integer, Store> set : allStores.entrySet()) {
             storeDetails += set.getValue().toString();
        }
        return storeDetails;
    }

    public String getAllItemsDetails(){//gets the string to print in choice 3 (show all items in store)
        String itemDetails = "";
        for (Map.Entry<Integer, Item> set : allItems.entrySet()) {
            Item item = set.getValue();
            itemDetails += set.getValue().toString(false);
            itemDetails += "\tNumber of stores that sell "+ item.getName()+" are: " +howManyStoresSellItem(item)+"\n";
            itemDetails += "\tAverage price for "+ item.getName()+" is: "+ getAveragePrice(item)+"\n";
         //   itemDetails += "\tNumber of times "+item.getName()+" was sold is: "+howManyTimesItemSold(item)+"\n";
        }
        return itemDetails;
    }

    private int howManyStoresSellItem(Item item) {
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
                for (ItemPair pair:order.getItems())
                    if(pair.item().equals(item)) {//TODO: finish to check if the pair in set has the item
                        timesSold++;

                    }
            }
        return  timesSold;
    }
}
