package Store;

public enum DiscountOperator {
    irrelevant,
    oneOf,
    allOrNothing;

    @Override
    public String toString() {
        String ret = "";
        switch (this){
            case  irrelevant:
                ret = "Irrelevant";
                break;
            case oneOf:
                ret = "One of";
                break;
            case allOrNothing:
                ret = "All or nothing";
                break;
        }

        return ret;
    }
}


