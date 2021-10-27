package com.example.biservice.model;

public class KafkaEvent {

    private Long customerID;
    private String productName;
    private int quantity;
    private int price;

    public KafkaEvent(){

    }

    public KafkaEvent(Long customerID, String productName, int quantity, int price) {
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

    public Long getCustomerID() {
        return customerID;
    }

    public int getPrice() {
        return price;
    }

    public void setCustomerID(Long customerID){
        this.customerID = customerID;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public void setPrice(int price){
        this.price = price;
    }

    public void setProductName(String productName){
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "KafkaEvent{" +
                "customerID=" + customerID +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }

}
