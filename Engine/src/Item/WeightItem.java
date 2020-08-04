package Item;

import java.util.Objects;

public class WeightItem extends Item {

    //constructor for store specific item
    public WeightItem(Item item, float price) {
        super(item.getSerialNumber(), item.getName(), price);
    }

    //constructor for general items list
    public WeightItem(int serialNumber, String name) {
        super(serialNumber, name);
    }
}
