package com.example.productservice.services;

import com.example.productservice.entities.ProductDetail;
import com.example.productservice.repositories.ProductDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDetailService {

    private final ProductDetailRepository productDetailRepository;

    public ProductDetailService(ProductDetailRepository productDetailRepository){
        this.productDetailRepository = productDetailRepository;
    }

    public List<ProductDetail> getProductDetails(){
        return productDetailRepository.findAll();
    }

    public void addProductDetail(ProductDetail productDetail){
        productDetailRepository.save(productDetail);
    }

    public ProductDetail changeProductDetail(ProductDetail newProductDetail, Long id){
        return productDetailRepository.findById(id)
                .map(customer -> {
                    customer.setComment(newProductDetail.getComment());
                    customer.setDescription(newProductDetail.getDescription());
                    return productDetailRepository.save(customer);
                }).orElseGet(() -> {
                    newProductDetail.setId(id);
                    return productDetailRepository.save(newProductDetail);
                });
    }

    public void deleteProductDetail(Long productDetailId){
        boolean exists = productDetailRepository.existsById(productDetailId);
        if(!exists){
            throw new IllegalStateException(productDetailId + " does not exist");
        } else {
            productDetailRepository.deleteById(productDetailId);
            System.out.println(productDetailId + " has been deleted");
        }
    }

    public ProductDetail findProductDetail(Long id){
        return productDetailRepository.findById(id).orElse(null);
    }

}
