package Store;

public class Offer {
    private int itemId;
    private float quantity;
    private float forAdditional;

    public Offer(int itemId, float quantity, float forAdditional) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.forAdditional = forAdditional;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getForAdditional() {
        return forAdditional;
    }

    public void setForAdditional(float forAdditional) {
        this.forAdditional = forAdditional;
    }
}
