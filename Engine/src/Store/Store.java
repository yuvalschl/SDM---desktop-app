package Store;

import Item.Item;
import Order.Order;

import java.awt.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Store {
    private String name;
    private int serialNumber;
    private Map<Integer, Item> inventory;
    private Set<Order> allOrders;
    private Point location;
    private float PPK;
    private float totalPayment;
    private int numberOfItemsSold;

    public int getNumberOfItemsSold() {
        return numberOfItemsSold;
    }

    public void setNumberOfItemsSold(int numberOfItemsSold) {
        this.numberOfItemsSold = numberOfItemsSold;
    }

    public Store(String name, int serialNumber, Map<Integer, Item> inventory, Set<Order> allOrders, Point location, float PPK) {
        this.name = name;
        this.serialNumber = serialNumber;
        this.inventory = inventory;
        this.allOrders = allOrders;
        this.location = location;
        this.totalPayment = 0;
        this.PPK = PPK;
        this.allOrders = new HashSet<Order>();
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Map<Integer, Item> getInventory() {
        return inventory;
    }

    public void setInventory(Map<Integer, Item> inventory) {
        this.inventory = inventory;
    }

    public Set<Order> getAllOrders() {
        return allOrders;
    }

    public void setAllOrders(Set<Order> allOrders) {
        this.allOrders = allOrders;
    }

    public float getPPK() {
        return PPK;
    }

    public void setPPK(float PPK) {
        this.PPK = PPK;
    }

    public float getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(float totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String toString(){//TODO add
        String itemDetails = "";
        String storeDetails =
                "* Store.Store ID: "+serialNumber+
                "\n\tStore.Store name: "+name+
                "\n\tThe Items in these store are: \n";
        for (Map.Entry<Integer, Item> set : inventory.entrySet()) {
            itemDetails += set.getValue().toString(true);
        }
        return  storeDetails + itemDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return getSerialNumber() == store.getSerialNumber() &&
                getName().equals(store.getName()) &&
                Objects.equals(getInventory(), store.getInventory()) &&
                Objects.equals(getAllOrders(), store.getAllOrders());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSerialNumber(), getInventory(), getAllOrders());
    }
}
