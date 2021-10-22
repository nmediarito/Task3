package com.example.productservice.services;

import com.example.productservice.entities.Product;
import com.example.productservice.entities.ProductDetail;
import com.example.productservice.repositories.ProductDetailRepository;
import com.example.productservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;

    public ProductService(ProductRepository productRepository, ProductDetailRepository productDetailRepository){
        this.productRepository = productRepository;
        this.productDetailRepository = productDetailRepository;
    }

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    public Product findProduct(Long id){
        return productRepository.findById(id).orElse(null);
    }

    public void addProduct(Product product){
        productRepository.save(product);
    }

    public Product changeProduct(Product newProduct, Long id){
        return productRepository.findById(id)
                .map(product -> {
                    product.setStockQuantity(newProduct.getStockQuantity());
                    product.setName(newProduct.getName());
                    product.setPrice(newProduct.getPrice());
                    return productRepository.save(product);
                }).orElseGet(() -> {
                    newProduct.setId(id);
                    return productRepository.save(newProduct);
                });
    }

    public ProductDetail changeProductDetail(ProductDetail newProductDetail, Long id){
        return productDetailRepository.findById(id)
                .map(productDetail -> {
                    productDetail.setDescription(newProductDetail.getDescription());
                    productDetail.setComment(newProductDetail.getComment());
                    return productDetailRepository.save(productDetail);
                }).orElseGet(() -> {
                    newProductDetail.setId(id);
                    return productDetailRepository.save(newProductDetail);
                });
    }


    public void deleteProduct(Long productId){
        boolean exists = productRepository.existsById(productId);
        if(!exists){
            throw new IllegalStateException(productId + " does not exist");
        } else {
            productRepository.deleteById(productId);
            System.out.println(productId + " has been deleted");
        }
    }


    public void updateStock(@PathVariable String productName, @PathVariable int quantity){
        System.out.println("PRODUCT ORDERED: " + productName +
                "\nQUANTITY ORDERED: " + quantity);

        //find the product entity by its name
        Optional<Product> productByName = productRepository.findByName(productName);

        if(quantity <= productByName.get().getStockQuantity()){
            //update the stock with the quantity the user requested or bought
            int newQuantity = productByName.get().getStockQuantity() - quantity;

            System.out.println("ORIGINAL TOTAL STOCK: " + productByName.get().getStockQuantity());

            //set the product with new quantity
            productRepository.findByName(productName)
                    .map(product -> {
                        product.setStockQuantity(newQuantity);
                        return productRepository.save(product);
                    }).orElse(null);

            System.out.println("UPDATED TOTAL STOCK: " + productByName.get().getStockQuantity());
        } else {
            System.out.println("THERE IS NOT ENOUGH STOCK AS THERE\n" +
                    "CURRENT TOTAL STOCK: " + productByName.get().getStockQuantity());
        }

    }

    public int checkInventory(@PathVariable String productName){
        //checks for product by its name
        Optional<Product> productByName = productRepository.findByName(productName);
        int totalQuantity = productByName.get().getStockQuantity();
        int totalPrice = 0;

        //if product exists by name
        if(productByName.isPresent()){
            //check if quantity has enough
            System.out.println("PRODUCT: " + productName);
            //if there is enough quantity
            if(totalQuantity != 0){
                //return unit price
                totalPrice = productByName.get().getPrice();
                System.out.println("CURRENT STOCK QUANTITY: " + productByName.get().getStockQuantity());
                return totalPrice;
            } else {
                return totalPrice;
            }
        } else {
            System.out.println("PRODUCT DOES NOT EXIST");
            return totalPrice;
        }
    }

    public Product findProduct(@PathVariable String productName){
        //checks for product by its name
        Product productByName = productRepository.findByName(productName).orElse(null);
        return productByName;
    }
}
