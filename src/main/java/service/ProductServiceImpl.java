package service;

import com.moo.cart.models.Product;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * This is stub , if any code send to the api will return product details
 */

@Service
public class ProductServiceImpl implements ProductService {

    private Map<String, Product> productMap = new HashMap<>();

    public void ProductServiceImpl(){
        productMap.put("ABCD", new Product("ABCD", "Product-ABCD", 1.00d));
        productMap.put("UXYZ", new Product("UXYZ", "Product-UXYZ", 5.00d));
        productMap.put("LTDN", new Product("ABCD", "Product-LTDN", 3.00d));
        productMap.put("IHRO", new Product("IHRO", "Product-IHRO", 6.00d));
    }

    @Override
    public Product getProduct(String code){
        return productMap.get(code);
    }
}
