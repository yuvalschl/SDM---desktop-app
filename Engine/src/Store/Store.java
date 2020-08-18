package Store;

import DtoObjects.DtoItem;
import DtoObjects.DtoStoreOrder;
import DtoObjects.DtoUnitItem;
import DtoObjects.DtoWeightItem;
import Item.*;
import Order.*;
import StoreManager.StoreManager;

import java.awt.*;
import java.util.*;

public class Store {
    private String name;
    private int serialNumber;
    private Map<Integer, Item> inventory;
    private Set<StoreOrder> allOrders;
    private Point location;
    private float PPK;
    private float totalDeliveriesCost;
    private float totalPayment;
    private int numberOfItemsSold;

    public int getNumberOfItemsSold() {
        return numberOfItemsSold;
    }

    public void setNumberOfItemsSold(int numberOfItemsSold) {
        this.numberOfItemsSold = numberOfItemsSold;
    }

    public Store(String name, int serialNumber, Map<Integer, Item> inventory, Set<StoreOrder> allOrders, Point location, float PPK) {
        this.name = name;
        this.serialNumber = serialNumber;
        this.inventory = inventory;
        this.allOrders = allOrders;
        this.location = location;
        this.totalPayment = 0;
        this.PPK = PPK;
        this.allOrders = new HashSet<StoreOrder>();
    }

    public float getTotalDeliveriesCost() {
        return totalDeliveriesCost;
    }

    public void setTotalDeliveriesCost(float totalDeliveriesCost) {
        this.totalDeliveriesCost = totalDeliveriesCost;
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

    public Set<StoreOrder> getAllOrders() {
        return allOrders;
    }

    public void setAllOrders(Set<StoreOrder> allOrders) {
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

    public HashMap<Integer, DtoItem> getDtoInventory() {
        HashMap<Integer, DtoItem> dtoInventory = new HashMap<>();
        for (Map.Entry<Integer, Item> item : inventory.entrySet()) {
            Item currentItem = item.getValue();
            dtoInventory.put(item.getKey(), StoreManager.itemToDtoItem(currentItem));
        }
        return dtoInventory;
    }

    public Set<DtoStoreOrder> getDtoStoreOrders(){
        Set<DtoStoreOrder> dtoStoreOrders = new HashSet<>();
        for(StoreOrder order : allOrders){
            dtoStoreOrders.add(StoreOrder.storeOrderToDtoStoreOrder(order));
        }

        return dtoStoreOrders;
    }

    public String toString() {
        String itemDetails = "";
        String storeDetails =
                "* Store.Store ID: " + serialNumber +
                        "\n\tStore.Store name: " + name +
                        "\n\tThe Items in these store are: \n";
        for (Map.Entry<Integer, Item> set : inventory.entrySet()) {
            itemDetails += set.getValue().toString(true);
        }
        return storeDetails + itemDetails;
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

    public float getTotalDeliveryCost() {
        return totalDeliveriesCost;
    }

    public void setTotalDeliveryCost(float deliveryCost) {
        this.totalDeliveriesCost = deliveryCost;
    }

}
