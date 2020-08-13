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
