package DtoObjects;

import Item.*;
import Order.StoreOrder;
import Store.Store;

public class DtoConvertor {

    public static Item dtoItemToItem(DtoItem item){
        Item itemToReturn;

        if(item instanceof DtoUnitItem){
            itemToReturn = new UnitItem(item.getSerialNumber(),item.getName(),(int)item.getAmountSold(), item.getPrice());
        }
        else {
            itemToReturn = new WeightItem(item.getSerialNumber(),item.getName(),item.getAmountSold() ,item.getPrice());
        }
        return itemToReturn;
    }

    public static DtoItem itemToDtoItem(Item item){
        DtoItem itemToReturn;

        if(item instanceof UnitItem){
            itemToReturn = new DtoUnitItem(item.getId(),item.getName(),item.getPrice(),item.getAmountSold().getValue());
        }
        else {
            itemToReturn = new DtoWeightItem(item.getId(),item.getName(),item.getPrice(),item.getAmountSold().getValue());
        }
        return itemToReturn;
    }

    public static DtoStore storeToDtoStore(Store store){
        return new DtoStore(store.getName(),store.getSerialNumber(),store.getDtoInventory(), store.getDtoStoreOrders(),
                store.getLocation(),store.getPPK(),store.getTotalPayment(),store.getTotalDeliveriesCost());
    }

    public static DtoStoreOrder storeOrderToDtoStoreOrder(StoreOrder order){
        return new DtoStoreOrder(order.getDateOfOrder(),order.getItems().size(),order.getTotalPriceOfItems(),order.getShippingCost(),order.getTotalCost(),
                order.getDistance(),order.getStore(), order.getItems(), order.getOrderId());
    }

}
