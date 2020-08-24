package DtoObjects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DtoUnitItem extends DtoItem {
    public DtoUnitItem(int serialNumber, String name, float price, float amountSold) {
        super(serialNumber, name, price, amountSold);
    }

    public DtoUnitItem() {
        super();
    }

    public DtoUnitItem(DtoItem item) {
        super(item);
    }
}
