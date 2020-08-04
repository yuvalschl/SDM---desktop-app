import Item.Item;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Store {
    private String name;
    private int serialNumber;
    private Map<String, Item> inventory;
    private List<Order> allOrders;
    private Point location;

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

    public Map<String, Item> getInventory() {
        return inventory;
    }

    public void setInventory(Map<String, Item> inventory) {
        this.inventory = inventory;
    }

    public List<Order> getAllOrders() {
        return allOrders;
    }

    public void setAllOrders(List<Order> allOrders) {
        this.allOrders = allOrders;
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
