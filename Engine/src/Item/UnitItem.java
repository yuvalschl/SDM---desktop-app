package Item;

import java.util.Objects;

public class UnitItem {
    private float amountSold;

    public UnitItem() {
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
