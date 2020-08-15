package ItemPair;

import DtoObjects.DtoItem;
import Store.Store;

public class ItemAmountAndStore {
    private double amount;
    private final DtoItem item;
    private Store store;

    public ItemAmountAndStore(DtoItem item, Double amount, Store store) {
        this.item = item;
        this.amount = amount;
        this.store = store;
    }

    public ItemAmountAndStore(DtoItem item, Store store) {
        this.item = item;
        this.store = store;
    }

    public ItemAmountAndStore(double amount, DtoItem item) {
        this.amount = amount;
        this.item = item;
    }

    public double getAmount() {
        return amount;
    }

    public DtoItem getItem() {
        return item;
    }

    public Store getStore() {
        return store;
    }

    public boolean containsItem(DtoItem item) {
        return (item == this.item);
    }

    public DtoItem item() {
        return item;
    }

    public double amount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    ;
}
