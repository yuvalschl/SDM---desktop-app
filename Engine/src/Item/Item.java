package Item;

import java.util.Objects;

public abstract class Item {
    boolean isSold;
    private int serialNumber;
    private String name;
    private float price;


    public Item(int serialNumber, String name, float price) {
        this.serialNumber = serialNumber;
        this.name = name;
        this.price = price;
        isSold = false;
    }

    public boolean getIsSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }

    public Item(int serialNumber, String name) {
        this.serialNumber = serialNumber;
        this.name = name;
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

    public String toString(Boolean isInShowStores){
        return "*   Item ID: " + serialNumber+"\n"+
                "\tItem name: " + name+"\n";
    }


    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return serialNumber == item.serialNumber&&
                amountSold == item.amountSold &&
                Objects.equals(name, item.name);
    }



    @Override
    public int hashCode() {
        return Objects.hash(getSerialNumber(), getName(), getPrice());
    }
}
