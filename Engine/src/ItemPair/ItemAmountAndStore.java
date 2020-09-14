package ItemPair;

import DtoObjects.DtoItem;
import Store.Store;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class ItemAmountAndStore {
    private float amount;
    private DtoItem item;
    private int itemId;
    private String itemName;
    private int itemStore;
    private Store store;
    private float discountItemAmount;//this represents the amount of the item bought in order to check if a discount is entitled for this amount
    boolean isPartOfDiscount = false;





    public ItemAmountAndStore(DtoItem item, float amount, Store store) {
        this.item = item;
        this.amount = amount;
        this.store = store;
        this.itemId = item.getSerialNumber();
        this.itemName = item.getName();
        this.itemStore = store.getSerialNumber();
        this.discountItemAmount =amount;
    }


    public ItemAmountAndStore(DtoItem item, Store store) {
        this.item = item;
        this.store = store;
        this.itemName = item.getName();
        this.itemId = item.getSerialNumber();
        this.itemStore = store.getSerialNumber();
        this.discountItemAmount =amount;
    }

    public ItemAmountAndStore(){}

    public float getDiscountItemAmount() {
        return discountItemAmount;
    }

    public void setDiscountItemAmount(float discountItemAmount) {
        this.discountItemAmount = discountItemAmount;
    }
    public boolean getIsPartOfDiscount() {
        return isPartOfDiscount;
    }

    public void setPartOfDiscount(boolean partOfDiscount) {
        isPartOfDiscount = partOfDiscount;
    }
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemId() {
        return itemId;
    }
    @XmlElement
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getItemStore() {
        return itemStore;
    }
    @XmlElement
    public void setItemStore(int itemStore) {
        this.itemStore = itemStore;
    }

    public ItemAmountAndStore(float amount, DtoItem item) {
        this.amount = amount;
        this.item = item;
    }
    @XmlTransient
    public void setStore(Store store) {
        this.store = store;
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
    @XmlTransient
    public void setItem(DtoItem item){ this.item =item;}
}
