package DtoObjects;

import ItemPair.ItemPair;
import Store.*;

import java.util.ArrayList;
import java.util.Date;

public class DtoOrder {
    private Date dateOfOrder;
    private int amountOfItems;
    private float totalPriceOfItems;
    private float shippingCost;
    private float totalCost;
    private float distance;
    private Store store;
    private static int ID;
    private ArrayList<ItemPair> items;

    public DtoOrder(Date dateOfOrder, int amountOfItems, float totalPriceOfItems, float shippingCost, float totalCost, float distance, Store store, ArrayList<ItemPair> items) {
        this.dateOfOrder = dateOfOrder;
        this.amountOfItems = amountOfItems;
        this.totalPriceOfItems = totalPriceOfItems;
        this.shippingCost = shippingCost;
        this.totalCost = totalCost;
        this.distance = distance;
        this.store = store;
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

    public static int getID() {
        return ID;
    }

    public ArrayList<ItemPair> getItems() {
        return items;
    }
}
