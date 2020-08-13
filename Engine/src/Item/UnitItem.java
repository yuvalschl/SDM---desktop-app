package Item;

public class UnitItem extends Item {
    private int amountSold;

    //constructor for store specific item
    public UnitItem(Item item, float price) {
        super(item.getSerialNumber(), item.getName(), price);
        amountSold = 0;
    }

    //constructor for general items list
    public UnitItem(int serialNumber, String name) {
        super(serialNumber, name);
        amountSold = 0;
    }

    public float getAmountSold() {
        return amountSold;
    }

    public void setAmountSold(int amountSold) {
        this.amountSold = amountSold;
    }
}

