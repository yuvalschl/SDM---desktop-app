package ShowHistory;

import DtoObjects.DtoUnitItem;
import Item.Item;
import Item.UnitItem;

import ItemPair.ItemAmountAndStore;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

public class ItemCell {
    private  String itemNameCol;
    private String itemSellByCol;
    private Float itemAmountSoldCol;
    private Float itemPriceCol;
    private Float itemTotalPriceCol;
    private String itemPartOfDiscountCol;

    public ItemCell(ItemAmountAndStore item){
        itemNameCol = item.getItemName() ;
        if(item.getItem() instanceof DtoUnitItem) {
            itemSellByCol = "Unit";
        }
        else
            itemSellByCol = "KG";
        itemAmountSoldCol = item.getAmount();
        itemPriceCol = item.getItem().getPrice();
        itemTotalPriceCol = itemPriceCol * itemAmountSoldCol;
        if (item.getIsPartOfDiscount())
            itemPartOfDiscountCol = "Yes";
        else
            itemPartOfDiscountCol = "no";
    }
    public String getItemNameCol() {
        return itemNameCol;
    }

    public void setItemNameCol(String itemNameCol) {
        this.itemNameCol = itemNameCol;
    }

    public String getItemSellByCol() {
        return itemSellByCol;
    }

    public void setItemSellByCol(String itemSellByCol) {
        this.itemSellByCol = itemSellByCol;
    }

    public Float getItemAmountSoldCol() {
        return itemAmountSoldCol;
    }

    public void setItemAmountSoldCol(Float itemAmountSoldCol) {
        this.itemAmountSoldCol = itemAmountSoldCol;
    }

    public Float getItemPriceCol() {
        return itemPriceCol;
    }

    public void setItemPriceCol(Float itemPriceCol) {
        this.itemPriceCol = itemPriceCol;
    }

    public Float getItemTotalPriceCol() {
        return itemTotalPriceCol;
    }

    public void setItemTotalPriceCol(Float itemTotalPriceCol) {
        this.itemTotalPriceCol = itemTotalPriceCol;
    }

    public String getItemPartOfDiscountCol() {
        return itemPartOfDiscountCol;
    }

    public void setItemPartOfDiscountCol(String itemPartOfDiscountCol) {
        this.itemPartOfDiscountCol = itemPartOfDiscountCol;
    }


}
