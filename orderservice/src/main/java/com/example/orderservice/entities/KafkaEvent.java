package com.example.orderservice.entities;

public class KafkaEvent {

    private final int customerID;
    private final String productName;
    private final int quantity;
    private final int price;

    public KafkaEvent(int customerID, String productName, int quantity, int price) {
        this.customerID = customerID;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getPrice() {
        return price;
    }


}
