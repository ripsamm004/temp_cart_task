package service;


import com.moo.cart.models.Product;

/**
 * Service to identify the product
 *
 * @author gamesys
 */
public interface ProductService {
    Product getProduct(String ssn);
}


