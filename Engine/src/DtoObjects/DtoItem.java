package DtoObjects;

public abstract class DtoItem {
    private int serialNumber;
    private String name;
    private float price;
    private float amountSold;

    public DtoItem(int serialNumber, String name, float price, float amountSold) {
        this.serialNumber = serialNumber;
        this.name = name;
        this.price = price;
        this.amountSold = amountSold;
    }

    public DtoItem(DtoItem item) {
        this.serialNumber = item.getSerialNumber();
        this.name = item.getName();
        this.price = item.getPrice();
        this.amountSold = item.getPrice();
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public float getAmountSold() {
        return amountSold;
    }
}
