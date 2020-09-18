package Item;

import Exceptions.InvalidValueException;
import javafx.beans.property.FloatProperty;

import java.util.Objects;

public abstract class Item {
    boolean isSold;
    private int id;
    private String name;
    private float price;
    private float amountSold;
    private  static int maxID = 0;

    public Item(int id, String name, float price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amountSold = 0;
        isSold = false;
        if (id > maxID)
            maxID = id;
    }

    public Item(int id, String name, float price, float amountSold) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amountSold = amountSold;
        isSold = false;
        if (id > maxID)
            maxID = id;
    }

    public int getMaxID() { return maxID; }

    public void setMaxID(int maxID) { this.maxID = maxID; }

    public boolean getIsSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }

    public Item(int id, String name) {
        this.id = id;
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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



    public void setPrice(float price) throws InvalidValueException {
        if(price > 0){
            this.price = price;
        }
        else {
            throw new InvalidValueException("Price cant be negative");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return getId() == item.getId() &&
                Float.compare(item.getPrice(), getPrice()) == 0 &&
                getName().equals(item.getName());
    }

    public float getAmountSold(){return this.amountSold;}



    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPrice());
    }

    public void setAmountSold(float amountSold){
        this.amountSold = amountSold;
    };

}
