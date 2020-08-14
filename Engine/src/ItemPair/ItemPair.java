package ItemPair;

import DtoObjects.DtoItem;

public class ItemPair {
        private double amount;
        private final DtoItem item;

        public ItemPair(DtoItem item, Double amount)
        {
            this.item  = item;
            this.amount = amount;
        }

        public boolean containsItem(DtoItem item){
            return (item == this.item);
        }

        public DtoItem item()   {
            return item;
        }

        public double amount() {
            return amount;
        }
        public void setAmount(double amount){ this.amount = amount;};
}
