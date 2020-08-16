package DtoObjects;

import ItemPair.ItemAmountAndStore;
import Store.Store;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DtoStoreOrder {
    private final Date dateOfOrder;
    private final int amountOfItems;
    private final float totalPriceOfItems;
    private final float shippingCost;
    private final float totalCost;
    private final float distance;
    private final Store store;
    private int orderId;
    private ArrayList<ItemAmountAndStore> items;

    public DtoStoreOrder(Date dateOfOrder, int amountOfItems, float totalPriceOfItems, float shippingCost, float totalCost, float distance, Store store, ArrayList<ItemAmountAndStore> items, int orderId) {
        this.dateOfOrder = dateOfOrder;
        this.amountOfItems = amountOfItems;
        this.totalPriceOfItems = totalPriceOfItems;
        this.shippingCost = shippingCost;
        this.totalCost = totalCost;
        this.distance = distance;
        this.store = store;
        this.orderId = orderId;
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

    public Store getStore() {
        return store;
    }

    public int getOrderId() {
        return orderId;
    }
}
