package DtoObjects;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DtoStore {
    private String name;
    private int serialNumber;
    private Map<Integer, DtoItem> inventory;
    private Set<DtoStoreOrder> allOrders;
    private Point location;
    private float PPK;
    private float totalPayment;
    private float totalDeliveriesCost;

    public DtoStore(String name, int serialNumber, Map<Integer, DtoItem> inventory, Set<DtoStoreOrder> allOrders, Point location, float PPK, float totalPayment, float totalDeliveriesCost) {
        this.name = name;
        this.serialNumber = serialNumber;
        this.inventory = inventory;
        this.allOrders = allOrders;
        this.location = location;
        this.PPK = PPK;
        this.totalPayment = totalPayment;
        this.totalDeliveriesCost = totalDeliveriesCost;
    }

    public float getTotalDeliveriesCost() {
        return totalDeliveriesCost;
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

    public Set<DtoStoreOrder> getAllOrders() {
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
