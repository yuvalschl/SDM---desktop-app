package Order;

import ItemPair.ItemAmountAndStore;
import Store.Store;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class StoreOrder {
    private Date dateOfOrder;
    private int amountOfItems;
    private float totalPriceOfItems;
    private float shippingCost;
    private float totalCost;
    private float distance;
    private Store store;
    private int orderId;
    private ArrayList<ItemAmountAndStore> items;

    public Date getDateOfOrder() {
        return dateOfOrder;
    }

    public StoreOrder(Date dateOfOrder, float shippingCost, float distance, Store store, int orderId) {
        this.dateOfOrder = dateOfOrder;
        this.shippingCost = shippingCost;
        this.distance = distance;
        this.store = store;
        this.orderId = orderId;
        this.totalPriceOfItems = 0;
        this.totalCost = shippingCost;
        this.items = new ArrayList<>();
    }

    public void addItemToOrder(ItemAmountAndStore item){
        this.items.add(item);
        totalCost += item.getItem().getPrice() * item.getAmount();
        totalPriceOfItems += item.getItem().getPrice() * item.getAmount();
    }

    public void setDateOfOrder(Date dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public int getAmountOfItems() {
        return amountOfItems;
    }

    public void setAmountOfItems(int amountOfItems) {
        this.amountOfItems = amountOfItems;
    }

    public float getTotalPriceOfItems() {
        return totalPriceOfItems;
    }

    public void setTotalPriceOfItems(float totalPriceOfItems) {
        this.totalPriceOfItems = totalPriceOfItems;
    }

    public float getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(float shippingCost) {
        this.shippingCost = shippingCost;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store stores) {
        this.store = stores;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public ArrayList<ItemAmountAndStore> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemAmountAndStore> items) {
        this.items = items;
    }
}
