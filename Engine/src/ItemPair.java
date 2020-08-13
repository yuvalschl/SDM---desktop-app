import Item.Item;

public class ItemPair {
        private final double amount;
        private final Item item;

        public ItemPair(Item item, Double amount)
        {
            this.item  = item;
            this.amount = amount;
        }
        public boolean containsItem(Item item){
            return (item == this.item);
        }
        public Item item()   { return item; }
        public double amount() { return amount; }
}
