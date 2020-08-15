package Order;

import Store.*;
import ItemPair.*;

import java.util.*;


//TODO set correct order id
public class Order {
    private Date dateOfOrder;
    private int amountOfItems;
    private float totalPriceOfItems;
    private float shippingCost;
    private float totalCost;
    private float distance;
    private Store store;
    private int orderId;
    private static int staticId = 0;
    private ArrayList<ItemPair> items;

    public Order(Date dateOfOrder, int amountOfItems, float totalPriceOfItems, float shippingCost, float totalCost, ArrayList<ItemPair> items, float distance, Store store) {
        this.dateOfOrder = dateOfOrder;
        this.amountOfItems = amountOfItems;
        this.totalPriceOfItems = totalPriceOfItems;
        this.shippingCost = shippingCost;
        this.totalCost = totalCost;
        this.items = items;
        this.distance = distance;
        this.orderId = setIdForNewOrder();
        this.store = store;
    }

    public String toString() {
        return "*   Order ID: " + orderId + "\n" +
                "\tDate: " + dateOfOrder + "\n" +
                "\tStore.Store name: " + store.getName() + "\n" +
                "\tStore.Store ID: " + store.getSerialNumber() + "\n" +
                "\tNumber of items in order: " + items.size() + "\n" +
                "\tTotal item cost: " + totalPriceOfItems + "\n" +
                "\tShipping price: " + shippingCost + "\n" +
                "\tTotal order price: " + totalCost;
    }

    private int setIdForNewOrder(){
        staticId++;
        return staticId;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


    public void setItems(ArrayList<ItemPair > items) {
        this.items = items;
    }

    public int getOrderId() {
        return orderId;
    }

    public Store getStore() {
        return store;
    }

    public float getDistance() {
        return distance;
    }

    public ArrayList<ItemPair> getItems() {
        return items;
    }

    public Date getDateOfOrder() {
        return dateOfOrder;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return getAmountOfItems() == order.getAmountOfItems() &&
                Float.compare(order.getTotalPriceOfItems(), getTotalPriceOfItems()) == 0 &&
                Float.compare(order.getShippingCost(), getShippingCost()) == 0 &&
                Float.compare(order.getTotalCost(), getTotalCost()) == 0 &&
                getDateOfOrder().equals(order.getDateOfOrder());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDateOfOrder(), getAmountOfItems(), getTotalPriceOfItems(), getShippingCost(), getTotalCost());
    }
}
