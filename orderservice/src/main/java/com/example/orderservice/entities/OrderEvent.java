package com.example.orderservice.entities;

public class OrderEvent {

    private final String message;
    private final String productName;
    private final int quantity;

    public OrderEvent(String message, String productName, int quantity) {
        this.message = message;
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getMessage() {
        return message;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }


}
