package com.example.biservice.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "products_details")
public class ProductDetail {

    @Id
    @Column(name = "id")
    private Long id;
    private String description;
    private String comment;

    @JsonBackReference
    @OneToOne(mappedBy = "productDetail")
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "ProductDetail{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", comment='" + comment + '\'' +
                ", product=" + product +
                '}';
    }
}
