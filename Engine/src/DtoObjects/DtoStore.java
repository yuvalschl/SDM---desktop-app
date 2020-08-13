package DtoObjects;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DtoStore {
    private String name;
    private int serialNumber;
    private Map<Integer, DtoItem> inventory;
    private Set<DtoOrder> allOrders;
    private Point location;
    private float PPK;
    private float totalPayment;

    public DtoStore(String name, int serialNumber, Map<Integer, DtoItem> inventory, Set<DtoOrder> allOrders, Point location, float PPK, float totalPayment) {
        this.name = name;
        this.serialNumber = serialNumber;
        this.inventory = inventory;
        this.allOrders = allOrders;
        this.location = location;
        this.PPK = PPK;
        this.totalPayment = totalPayment;
    }

    public String getName() {
        return name;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public Map<Integer, DtoItem> getInventory() {
        return inventory;
    }

    public Set<DtoOrder> getAllOrders() {
        return allOrders;
    }

    public Point getLocation() {
        return location;
    }

    public float getPPK() {
        return PPK;
    }

    public float getTotalPayment() {
        return totalPayment;
    }
}
