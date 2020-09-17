package Store;

import java.util.Set;

public class MyThenYouGet {
    private DiscountOperator discountOperator;
    private Set<Offer> allOffers;
    private boolean decreasedFromItemAmount = false;//this operator indicates if a set of items in the you get has decreased its amount from the itemDiscount amount
    public MyThenYouGet(DiscountOperator discountOperator, Set<Offer> allOffers) {
        this.discountOperator = discountOperator;
        this.allOffers = allOffers;
    }
    public Offer getOfferByID(int ID){
        for (Offer offer: allOffers){
            if (offer.getItemId() == ID)
                return offer;
        }
        return null;
    }


    public boolean isDecreasedFromItemAmount() { return decreasedFromItemAmount; }

    public void setDecreasedFromItemAmount(boolean decreasedFromItemAmount) { this.decreasedFromItemAmount = decreasedFromItemAmount; }

    public DiscountOperator getDiscountOperator() {
        return discountOperator;
    }

    public void setDiscountOperator(DiscountOperator discountOperator) {
        this.discountOperator = discountOperator;
    }

    public Set<Offer> getAllOffers() {
        return allOffers;
    }

    public void setAllOffers(Set<Offer> allOffers) {
        this.allOffers = allOffers;
    }

    public static DiscountOperator getOperatorFromSdmOffer(String offerString){
        DiscountOperator operator;
        switch (offerString){
            case "IRRELEVANT":
                operator = DiscountOperator.irrelevant;
                break;
            case "ONE-OF":
                operator = DiscountOperator.oneOf;
                break;
            case "ALL-OR-NOTHING":
                operator = DiscountOperator.allOrNothing;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + offerString);
        }

        return operator;
    }
}
