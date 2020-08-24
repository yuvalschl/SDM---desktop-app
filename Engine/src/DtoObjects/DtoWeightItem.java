package DtoObjects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DtoWeightItem extends DtoItem {
    public DtoWeightItem(int serialNumber, String name, float price, float amountSold) {
        super(serialNumber, name, price, amountSold);
    }

    public DtoWeightItem() {
        super();
    }
}
