package Order;

import Item.UnitItem;
import ItemPair.ItemAmountAndStore;
import Store.Store;

import java.util.ArrayList;
import java.util.Date;

public class StoreOrder {
    private Date dateOfOrder;
    private float amountOfItems;
    private float totalPriceOfItems;
    private float shippingCost;
    private float totalCost;
    private float distance;
    private Store store;
    private int storeID;
    private String name;
    private int orderId;
    private ArrayList<ItemAmountAndStore> items;
    private float pkk;

    public Date getDateOfOrder() {
        return dateOfOrder;
    }

    public StoreOrder(Date dateOfOrder, float shippingCost, float distance, Store store, int orderId, float pkk) {
        this.dateOfOrder = dateOfOrder;
        this.shippingCost = shippingCost;
        this.distance = distance;
        this.store = store;
        this.orderId = orderId;
        this.totalPriceOfItems = 0;
        this.totalCost = shippingCost;
        this.items = new ArrayList<>();
        this.pkk = pkk;
        this.name = store.getName();
        this.storeID = store.getSerialNumber();
    }

    public void addItemToOrder(ItemAmountAndStore item){
        this.items.add(item);
        float priceToAddToTotalCost;
        float priceToAddToTotalItemCost;
        if (item.getIsPartOfDiscount()){
            priceToAddToTotalCost = item.getOfferPrice();
            priceToAddToTotalItemCost = item.getOfferPrice();
        }
        else{
            priceToAddToTotalCost = item.getStore().getInventory().get(item.getItemId()).getPrice() * item.getAmount();
            priceToAddToTotalItemCost = item.getStore().getInventory().get(item.getItemId()).getPrice() * item.getAmount();
        }
        totalCost += priceToAddToTotalCost;
        totalPriceOfItems += priceToAddToTotalItemCost;
        if(item.getItem() instanceof UnitItem){
            amountOfItems += item.getAmount();
        }
        else {
            amountOfItems += 1;
        }
    }


    public float getPkk() {
        return pkk;
    }

    public void setPkk(float pkk) {
        this.pkk = pkk;
    }

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }    public void setDateOfOrder(Date dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public float getAmountOfItems() {
        return amountOfItems;
    }

    public void setAmountOfItems(float amountOfItems) {
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
