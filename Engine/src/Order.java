import java.util.Date;
import java.util.Objects;

public class Order {
    private Date dateOfOrder;
    private int amountOfItems;
    private float totalPriceOfItems;
    private float shippingCost;
    private float totalCost;

    public Date getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(Date dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public int getAmountOfItems() {
        return amountOfItems;
    }

    public void setAmountOfItems(int amountOfItems) {
        this.amountOfItems = amountOfItems;
    }

    public float getTotalPriceOfItems() {
        return totalPriceOfItems;
    }

    public void setTotalPriceOfItems(float totalPriceOfItems) {
        this.totalPriceOfItems = totalPriceOfItems;
    }

    public float getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(float shippingCost) {
        this.shippingCost = shippingCost;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return getAmountOfItems() == order.getAmountOfItems() &&
                Float.compare(order.getTotalPriceOfItems(), getTotalPriceOfItems()) == 0 &&
                Float.compare(order.getShippingCost(), getShippingCost()) == 0 &&
                Float.compare(order.getTotalCost(), getTotalCost()) == 0 &&
                getDateOfOrder().equals(order.getDateOfOrder());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDateOfOrder(), getAmountOfItems(), getTotalPriceOfItems(), getShippingCost(), getTotalCost());
    }
}
