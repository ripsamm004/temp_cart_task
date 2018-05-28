package com.moo.cart.persistence.impl;

import com.moo.cart.models.Cart;
import com.moo.cart.models.Item;
import com.moo.cart.persistence.CartRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


@Repository("cartRepository")
public class CartRepositoryImpl implements CartRepository {

    private static final Map<String, Cart> cartMap = new ConcurrentHashMap<>();

    CartRepositoryImpl(){
        //Initialize Data
        Cart car1 = new Cart("1");
        Cart car2 = new Cart("2");
        Cart car3 = new Cart("3");
        cartMap.put("1", car1);
        cartMap.put("2", car2);
        cartMap.put("3", car3);
    }

    @Override
    public Optional<Cart> getCart(String id) {
        return Optional.ofNullable(cartMap.get(id));
    }

    @Override
    public Optional<Cart> addItem(String id, Item item) {
        Cart cart = cartMap.get(id);
        if(cart != null){
            cart.addItem(item);
        }
        return Optional.ofNullable(cart);
    }

    @Override
    public boolean clearItem(String id) {
        Cart cart = cartMap.get(id);
        if(cart != null){
            List<Item> item = cart.getItems();
            item.clear();
            return true;
        }
        return false;
    }

}