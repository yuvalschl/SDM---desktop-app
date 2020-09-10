package Item;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class UnitItem extends Item {
    private int amountSold;

    //constructor for store specific item
    public UnitItem(Item item, float price) {
        super(item.getId(), item.getName(), price);
        amountSold = 0;
    }

    //constructor for general items list
    public UnitItem(int serialNumber, String name) {
        super(serialNumber, name);
        amountSold = 0;
    }

    public UnitItem(int serialNumber, String name, int amountSold, float price) {
        super(serialNumber, name, price);
        this.amountSold = amountSold;
    }


    public float getAmountSold() {
        return amountSold;
    }

    public void setAmountSold(float amountSold) {
        this.amountSold = (int) amountSold;
    }

    public String toString(Boolean isInShowStores){
        String details = super.toString(true)+ "\tItem sell by: unit"+"\n";
        if(isInShowStores)
            details += "\tPrice per unit: " + getPrice()+"\n";
        return details;
    }
}

