package com.example.biservice.model;

public class OrderQuantity {

    private String productName;
    private long quantity;

    public OrderQuantity(){

    }

    public OrderQuantity(String productName, long quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
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
