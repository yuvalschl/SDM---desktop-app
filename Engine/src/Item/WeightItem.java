package Item;

public class WeightItem extends Item {
    private float amountSold;

    //constructor for store specific item
    public WeightItem(Item item, float price) {
        super(item.getSerialNumber(), item.getName(), price);
        amountSold = 0;
    }

    //constructor for general items list
    public WeightItem(int serialNumber, String name) {
        super(serialNumber, name);
        amountSold = 0;
    }

    public float getAmountSold() {
        return amountSold;
    }

    public void setAmountSold(float amountSold) {
        this.amountSold = amountSold;
    }
}
