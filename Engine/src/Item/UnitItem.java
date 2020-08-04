package Item;

import java.util.Objects;

public class UnitItem extends Item {

    //constructor for store specific item
    public UnitItem(Item item, float price) {
        super(item.getSerialNumber(), item.getName(), price);
    }

    //constructor for general items list
    public UnitItem(int serialNumber, String name) {
        super(serialNumber, name);
    }
}

