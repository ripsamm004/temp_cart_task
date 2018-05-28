package com.moo.cart.service;


import com.moo.cart.models.Product;

import java.util.Optional;

/**
 * Service to identify the product
 *
 * @author gamesys
 */
public interface ProductService {
    Optional<Product> getProduct(String code);
}


