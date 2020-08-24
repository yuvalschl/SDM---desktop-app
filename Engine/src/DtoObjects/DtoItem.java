package DtoObjects;

import Item.Item;

import javax.xml.bind.annotation.*;
import java.util.Objects;

@XmlSeeAlso({DtoUnitItem.class, DtoWeightItem.class})

@XmlRootElement
public abstract class DtoItem {
    @XmlAttribute
    private int serialNumber;
    @XmlAttribute
    private String name;
    @XmlAttribute
    private float price;
    private float amountSold;


    public DtoItem(int serialNumber, String name, float price, float amountSold) {
        this.serialNumber = serialNumber;
        this.name = name;
        this.price = price;
        this.amountSold = amountSold;
    }

    public DtoItem(){}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DtoItem item = (DtoItem) o;
        return getSerialNumber() == item.getSerialNumber() &&
                Float.compare(item.getPrice(), getPrice()) == 0 &&
                getName().equals(item.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSerialNumber(), getName(), getPrice());
    }
}
