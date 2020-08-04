package Item;

import java.util.Objects;

public class UnitItem extends Item {
    private float amountSold;

    //constructor for store specific item
    public UnitItem(int serialNumber, String name, float price) {
        super(serialNumber, name, price);
        this.amountSold = 0;
    }

    //constructor for general items list
    public UnitItem(int serialNumber, String name) {
        super(serialNumber, name);
        this.amountSold = 0;
    }

    public float getAmountSold() {
        return amountSold;
    }

    public void setAmountSold(float amountSold) {
        this.amountSold = amountSold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnitItem unitItem = (UnitItem) o;
        return Float.compare(unitItem.getAmountSold(), getAmountSold()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAmountSold());
    }
}
