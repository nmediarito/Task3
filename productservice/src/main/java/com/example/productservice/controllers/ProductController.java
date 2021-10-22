package com.example.productservice.controllers;

import com.example.productservice.entities.Product;
import com.example.productservice.entities.ProductDetail;
import com.example.productservice.repositories.ProductRepository;
import com.example.productservice.services.ProductDetailService;
import com.example.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;
    private final ProductDetailService productDetailService;
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductService productService, ProductDetailService productDetailService, ProductRepository productRepository){
        this.productService = productService;
        this.productDetailService = productDetailService;
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    @GetMapping("/products/{id}")
    public Product findProduct(@PathVariable Long id){
        return productService.findProduct(id);
    }

    @GetMapping("/productByName/{productName}")
    public Product findProductByName(@PathVariable String productName){
        return productService.findProduct(productName);
    }

    @PostMapping("/products")
    public void registerNewProduct(@RequestBody Product product){
        productService.addProduct(product);
    }

    @PutMapping("/products/{id}")
    public Product updateProduct(@RequestBody Product newProduct, @PathVariable Long id){
        return productService.changeProduct(newProduct, id);
    }

    @PutMapping("/products/{productName}/{quantity}")
    public void updateQuantity(@PathVariable String productName, @PathVariable int quantity){
        System.out.println("UPDATED STOCK");
        productService.updateStock(productName, quantity);
    }

    @GetMapping("/productDetails")
    public List<ProductDetail> getProductDetails(){
        return productDetailService.getProductDetails();
    }

    @GetMapping("/productDetails/{id}")
    public ProductDetail findProductDetail(@PathVariable Long id){
        return productDetailService.findProductDetail(id);
    }

    @PostMapping("/productDetails")
    public void registerNewProductDetail(@RequestBody ProductDetail productDetail){
        productDetailService.addProductDetail(productDetail);
    }

    @PutMapping("/productDetails/{id}")
    public ProductDetail updateProductDetails (@RequestBody ProductDetail newProductDetail, @PathVariable Long id){
        return productService.changeProductDetail(newProductDetail, id);
    }

    @GetMapping("/getInventory/{productName}")
    public int checkInventory(@PathVariable String productName){
        return productService.checkInventory(productName);
    }

    @DeleteMapping("/productDetails/{id}")
    public void deleteProductDetail(@PathVariable("id") Long id){
        productDetailService.deleteProductDetail(id);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);
    }

}
