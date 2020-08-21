package DtoObjects;

import ItemPair.ItemAmountAndStore;
import Order.Order;
import Store.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class DtoOrder {
    private Date dateOfOrder;
    private int amountOfItems;
    private float totalPriceOfItems;
    private float shippingCost;
    private float totalCost;
    private float distance;
    private HashMap<Integer, Store> stores;
    private int orderId;
    private ArrayList<ItemAmountAndStore> items;

    public DtoOrder(Date dateOfOrder, int amountOfItems, float totalPriceOfItems, float shippingCost, float totalCost, float distance, HashMap<Integer, Store> store, ArrayList<ItemAmountAndStore> items) {
        this.dateOfOrder = dateOfOrder;
        this.amountOfItems = amountOfItems;
        this.totalPriceOfItems = totalPriceOfItems;
        this.shippingCost = shippingCost;
        this.totalCost = totalCost;
        this.distance = distance;
        this.stores = store;
        this.items = items;
    }


    public Date getDateOfOrder() {
        return dateOfOrder;
    }

    public int getAmountOfItems() {
        return amountOfItems;
    }

    public float getTotalPriceOfItems() {
        return totalPriceOfItems;
    }

    public float getShippingCost() {
        return shippingCost;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public float getDistance() {
        return distance;
    }

    public HashMap<Integer, Store>getStores() {
        return stores;
    }

    public int getOrderId() {
        return orderId;
    }

    public ArrayList<ItemAmountAndStore> getItems() {
        return items;
    }

    private String allStoresNameString(DtoOrder order) {
        StringBuilder storesNames = new StringBuilder();
        for (Store store : order.getStores().values()) {
            storesNames.append(store.getName()).append(", ");
        }
        storesNames.deleteCharAt(storesNames.length()-2);
        return storesNames.toString();
    }
    private String allStoresIdString(DtoOrder order) {
        StringBuilder storesId = new StringBuilder();
        for (Store store : order.getStores().values()) {
            storesId.append(store.getSerialNumber()).append(", ");
        }
        storesId.deleteCharAt(storesId.length()-2);
        return storesId.toString();
    }

    public String toString() {
        return  "*   Order ID: " + orderId + "\n" +
                "\tDate: " + dateOfOrder + "\n" +
                "\tStores names: " + allStoresNameString(this) + "\n" +
                "\tStores ID: " + allStoresIdString(this) + "\n" +
                "\tNumber of items in order: " + amountOfItems + "\n" +
                "\tTotal item cost: " + totalPriceOfItems + "\n" +
                "\tShipping price: " + shippingCost + "\n" +
                "\tTotal order price: " + totalCost;
    }
}
