package DtoObjects;

public class DtoUnitItem extends DtoItem {
    public DtoUnitItem(int serialNumber, String name, float price, float amountSold) {
        super(serialNumber, name, price, amountSold);
    }

    public DtoUnitItem(DtoItem item) {
        super(item);
    }
}
