package ItemPair;

import DtoObjects.DtoItem;
import Store.Store;

public class ItemAmountAndStore {
    private float amount;
    private final DtoItem item;
    private Store store;

    public ItemAmountAndStore(DtoItem item, float amount, Store store) {
        this.item = item;
        this.amount = amount;
        this.store = store;
    }

    public ItemAmountAndStore(DtoItem item, Store store) {
        this.item = item;
        this.store = store;
    }

    public ItemAmountAndStore(float amount, DtoItem item) {
        this.amount = amount;
        this.item = item;
    }

    public float getAmount() {
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

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setItem(DtoItem item){}
}
