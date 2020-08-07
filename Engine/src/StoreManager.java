import Item.Item;
import Item.UnitItem;
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
            if (pair.item().getClass() == UnitItem.class)
                totalPriceOfItems += (int)pair.amount() * pair.item().getPrice();
            else
                totalPriceOfItems += pair.amount() * pair.item().getPrice();

        }
        Order newOrder = new Order(date,items.size(),totalPriceOfItems, shippingCost, totalPriceOfItems + shippingCost, items,distance);
        return newOrder;
    }

    public void placeOrder(Order order) {
        allOrders.add(order);
    }

    private float distanceCalculator(Point point1, Point point2){
        return (float) Math.sqrt(Math.pow(point1.x-point2.x, 2)+Math.pow(point1.y-point2.y, 2));
    }

}
