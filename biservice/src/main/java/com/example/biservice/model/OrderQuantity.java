package com.example.biservice.model;

public class OrderQuantity {

    private String productName;
    private int quantity;

    public OrderQuantity(){

    }

    public OrderQuantity(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderQuantity{" +
                "productName='" + productName + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
