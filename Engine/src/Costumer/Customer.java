package Costumer;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Customer {
    private int id;
    private String name;
    private Point location;
    private String locationString;
    int numberOfOrdersMade;
    private float totalShippingCost = 0;
    private float totalOrdersWithoutShippingPrice = 0;
    private float averageOrdersWithoutShippingPrice;
    private float averageOrdersShippingPrice;


    public Customer(int id, String name, Point location) {
        this.id = id;
        this.name = name;
        this.location = location;
        numberOfOrdersMade = 0;
        averageOrdersShippingPrice = 0;
        averageOrdersWithoutShippingPrice = 0;
        locationString = "("+location.x+","+location.y+")";
    }



    public String getLocationString() { return locationString; }

    public void setLocationString(String locationString) { this.locationString = locationString; }

    public Float getTotalShippingCost() { return totalShippingCost;}

    public void setTotalShippingCost(Float totalShippingCost) { this.totalShippingCost = totalShippingCost; }

    public Float getTotalOrdersWithoutShippingPrice() { return totalOrdersWithoutShippingPrice; }

    public void setTotalOrdersWithoutShippingPrice(Float totalOrdersWithoutShippingPrice) { this.totalOrdersWithoutShippingPrice = totalOrdersWithoutShippingPrice; }

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
