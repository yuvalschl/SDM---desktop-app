package Costumer;

import java.awt.*;
import java.util.HashMap;

public class Customer {
    private int id;
    private String name;
    private Point location;
    int numberOfOrdersMade;
    HashMap<Integer, Float> shippingCosts = new HashMap<>();
    float averageOrdersWithoutShippingPrice;
    float averageOrdersShippingPrice;


    public Customer(int id, String name, Point location) {
        this.id = id;
        this.name = name;
        this.location = location;
        numberOfOrdersMade = 0;
        averageOrdersShippingPrice = 0;
        averageOrdersWithoutShippingPrice = 0;

    }

    public void addShippingCost(float shippingCost, int orderID){
        /*shippingCosts.put(orderID, shippingCost);
        float totalShippingCosts = 0;
                shippingCosts.forEach((currOrderID, currShippingCost)-> totalShippingCosts += currShippingCost);
        //averageOrdersShippingPrice = */
    }

    public int getNumberOfOrdersMade() { return numberOfOrdersMade; }

    public void setNumberOfOrdersMade(int numberOfOrdersMade) { this.numberOfOrdersMade = numberOfOrdersMade; }

    public float getAverageOrdersWithoutShippingPrice() { return averageOrdersWithoutShippingPrice; }

    public void setAverageOrdersWithoutShippingPrice(float averageOrdersWithoutShippingPrice) {
        this.averageOrdersWithoutShippingPrice = averageOrdersWithoutShippingPrice;
    }

    public float getAverageOrdersShippingPrice() { return averageOrdersShippingPrice; }

    public void setAverageOrdersShippingPrice(float averageOrdersShippingPrice) {
        this.averageOrdersShippingPrice = averageOrdersShippingPrice;
    }
    public int getX(){
        return location.x;
    }

    public int getY(){
        return location.y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
}
