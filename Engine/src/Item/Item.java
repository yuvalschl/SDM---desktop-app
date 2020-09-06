package Item;

import Exceptions.InvalidValueException;

import java.util.Objects;

public abstract class Item {
    boolean isSold;
    private int id;
    private String name;
    private float price;


    public Item(int id, String name, float price) {
        this.id = id;
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

    public String toString(Boolean isInShowStores){
        return "*   Item ID: " + id +"\n"+
                "\tItem name: " + name+"\n";
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



    public abstract float getAmountSold();

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPrice());
    }

    public abstract void setAmountSold(float amountSold);
}
