package Item;

import java.util.Objects;

public abstract class Item {
    private int serialNumber;
    private String name;
    private float price;
    private int amountSold;

    public Item(int serialNumber, String name, float price) {
        this.serialNumber = serialNumber;
        this.name = name;
        this.price = price;
        this.amountSold = 0;
    }

    public Item(int serialNumber, String name) {
        this.serialNumber = serialNumber;
        this.name = name;
    }

    public int getAmountSold() {
        return amountSold;
    }

    public void setAmountSold(int amountSold) {
        this.amountSold = amountSold;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return getSerialNumber() == item.getSerialNumber() &&
                Float.compare(item.getPrice(), getPrice()) == 0 &&
                getAmountSold() == item.getAmountSold() &&
                getName().equals(item.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSerialNumber(), getName(), getPrice(), getAmountSold());
    }
}
