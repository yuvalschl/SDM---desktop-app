package Store;

public class Discount {
    private String name;
    private MyIfYouBuy myIfYouBuy;
    private MyThenYouGet myThenYouGet;

    public Discount(String name, MyIfYouBuy myIfYouBuy, MyThenYouGet myThenYouGet) {
        this.myIfYouBuy = myIfYouBuy;
        this.myThenYouGet = myThenYouGet;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyIfYouBuy getIfYouBuy() {
        return myIfYouBuy;
    }

    public void setIfYouBuy(MyIfYouBuy myIfYouBuy) {
        this.myIfYouBuy = myIfYouBuy;
    }

    public MyThenYouGet getThenYouGet() {
        return myThenYouGet;
    }

    public void setThenYouGet(MyThenYouGet myThenYouGet) {
        this.myThenYouGet = myThenYouGet;
    }
}
