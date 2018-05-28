package com.moo.cart.service;

import com.moo.cart.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * This is stub , if any code send to the api will return product details
 */

@Service
public class ProductServiceImpl implements ProductService {

    private static final Map<String, Product> productMap = new HashMap<>();

    @Autowired
    public void ProductServiceImpl(){
        productMap.put("ABCD", new Product("ABCD", "Product-ABCD", 1.50d));
        productMap.put("UXYZ", new Product("UXYZ", "Product-UXYZ", 5.50d));
        productMap.put("LTDN", new Product("LTDN", "Product-LTDN", 3.00d));
        productMap.put("IHRO", new Product("IHRO", "Product-IHRO", 6.00d));
    }

    @Override
    public Optional<Product> getProduct(String code){
        return Optional.ofNullable(productMap.get(code));
    }
}
