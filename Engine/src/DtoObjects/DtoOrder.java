package DtoObjects;

import ItemPair.ItemPair;
import Order.Order;
import Store.*;

import java.awt.*;
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
    private int orderId;
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

    public int getOrderId() {
        return orderId;
    }

    public ArrayList<ItemPair> getItems() {
        return items;
    }
}
