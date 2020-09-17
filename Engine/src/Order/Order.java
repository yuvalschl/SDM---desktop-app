package Order;

import Costumer.Customer;
import Store.*;
import ItemPair.*;

import javax.xml.bind.annotation.*;
import java.util.*;

@XmlType(propOrder={"dateOfOrder", "amountOfItems", "storeIdAndName","totalPriceOfItems", "shippingCost", "totalCost", "itemAmountAndStores", "shippingCostByStore"})

@XmlRootElement(name="Order")

public class Order {

    private Date dateOfOrder;
    private int amountOfItems;
    private float totalPriceOfItems;
    private float shippingCost;
    private HashMap<Integer, Float> shippingCostByStore;
    private float totalCost;
    private HashMap<Integer, Store> stores = new HashMap<>();
    private int orderId;
    private static int staticId = 0;
    private Customer customer;
    private HashMap<Integer, ItemAmountAndStore> itemAmountAndStores;
    private HashMap<Integer,String> storeIdAndName = new HashMap<>();

    //Constructor for an order loaded from file
    public Order(int ID, Date date, int amountOfItems, float totalPriceOfItems, float shippingCost, float totalCost, HashMap<Integer, ItemAmountAndStore> itemAmountAndStores)
    {
        this.orderId = ID;
        this.dateOfOrder = date;
        this.amountOfItems = amountOfItems;
        this.totalPriceOfItems = totalPriceOfItems;
        this.shippingCost = shippingCost;
        this.totalCost = totalCost;
        this.itemAmountAndStores = itemAmountAndStores;
    }


    public Order(Date dateOfOrder, int amountOfItems, float totalPriceOfItems, float shippingCost, float totalCost,
                 HashMap<Integer, Store> stores, HashMap<Integer, ItemAmountAndStore> items, HashMap<Integer, Float> shippingCostByStore, Customer customer) {
        this.dateOfOrder = dateOfOrder;
        this.amountOfItems = amountOfItems;
        this.totalPriceOfItems = totalPriceOfItems;
        this.shippingCost = shippingCost;
        this.totalCost = totalCost;
        this.stores = stores;
        this.orderId = ++staticId;
        this.itemAmountAndStores = items;
        this.shippingCostByStore = shippingCostByStore;
        this.customer = customer;
        updateStoreIdAndName(itemAmountAndStores);
    }
    public Order(){}

    private void updateStoreIdAndName(HashMap<Integer, ItemAmountAndStore> itemAmountAndStores) {
        for (ItemAmountAndStore store: itemAmountAndStores.values()){
            int key = store.getStore().getSerialNumber();
            String name = store.getStore().getName();
            storeIdAndName.put(key,name);
        }
    }

    public int getStaticID(){ return staticId;}
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public HashMap<Integer, Float> getShippingCostByStore() {
        return shippingCostByStore;
    }

    @XmlElement(name = "shippingCostByStore")
    public void setShippingCostByStore(HashMap<Integer, Float> shippingCostByStore) {
        this.shippingCostByStore = shippingCostByStore;
    }

    @XmlAttribute
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


    public void setItemAmountAndStores(HashMap<Integer, ItemAmountAndStore> itemAmountAndStores) {
        this.itemAmountAndStores = itemAmountAndStores;
    }

    public int getOrderId() {
        return orderId;
    }

    public HashMap<Integer, Store> getStores() {
        return stores;
    }

    @XmlTransient
    public void setStores(HashMap<Integer, Store> stores) {
        this.stores = stores;
    }

    public static int getStaticId() {
        return staticId;
    }

    public static void setStaticId(int staticId) {
        Order.staticId = staticId;
    }


    public HashMap<Integer, ItemAmountAndStore> getItemAmountAndStores() {
        return itemAmountAndStores;
    }

    public Date getDateOfOrder() {
        return dateOfOrder;
    }

    @XmlElement
    public void setDateOfOrder(Date dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public int getAmountOfItems() {
        return amountOfItems;
    }

    @XmlElement
    public void setAmountOfItems(int amountOfItems) {
        this.amountOfItems = amountOfItems;
    }

    public float getTotalPriceOfItems() {
        return totalPriceOfItems;
    }
    @XmlElement
    public void setTotalPriceOfItems(float totalPriceOfItems) {
        this.totalPriceOfItems = totalPriceOfItems;
    }

    public float getShippingCost() {
        return shippingCost;
    }

    @XmlElement
    public void setShippingCost(float shippingCost) {
        this.shippingCost = shippingCost;
    }

    public float getTotalCost() {
        return totalCost;
    }

    @XmlElement
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

    private String allStoresNameString(Order order) {
        StringBuilder storesNames = new StringBuilder();
        for (Store store : order.getStores().values()) {
            storesNames.append(store.getName()).append(", ");
        }
        storesNames.deleteCharAt(storesNames.length()-2);
        return storesNames.toString();
    }
    private String allStoresIdString(Order order) {
        StringBuilder storesId = new StringBuilder();
        for (Store store : order.getStores().values()) {
            storesId.append(store.getSerialNumber()).append(", ");
        }
        storesId.deleteCharAt(storesId.length()-2);
        return storesId.toString();
    }

    @XmlElement(name = "stores")
    public void setStoreIdAndName(HashMap<Integer, String> storeIdAndName) {
        this.storeIdAndName = storeIdAndName;
    }

    public HashMap<Integer, String> getStoreIdAndName() {
        return storeIdAndName;
    }
}
