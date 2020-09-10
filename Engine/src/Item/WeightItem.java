package Item;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;

public class WeightItem extends Item {
    private float amountSold;

    //constructor for store specific item
    public WeightItem(Item item, float price) {
        super(item.getId(), item.getName(), price);
        amountSold = 0;
    }

    //constructor for general items list
    public WeightItem(int serialNumber, String name) {
        super(serialNumber, name);
        amountSold = 0;
    }

    public WeightItem(int serialNumber, String name, float amountSold, float price) {
        super(serialNumber, name, price);
        this.amountSold = amountSold;
    }

    public float getAmountSold() {
        return amountSold;
    }

    @Override
    public void setAmountSold(float amountSold) {
        this.amountSold = amountSold;
    }

    public String toString(Boolean isInShowStores){
       String details = super.toString(true)+ "\tItem sell by: weight"+"\n";
       if(isInShowStores)
           details += "\tPrice per kilo: " + getPrice()+"\n";
        return  details;
    }
}
