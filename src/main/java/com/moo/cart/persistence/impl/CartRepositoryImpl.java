package com.moo.cart.persistence.impl;

import com.moo.cart.models.Cart;
import com.moo.cart.models.Item;
import com.moo.cart.persistence.CartRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository("cartRepository")
public class CartRepositoryImpl implements CartRepository {

    private final Map<String, Cart> cartMap = new ConcurrentHashMap<>();

    @Override
    public Optional<Cart> getItem(String id) {
        return Optional.ofNullable(cartMap.get(id));
    }

    @Override
    public Optional<Cart> addItem(String id, Item item) {
        Cart cart = cartMap.get(id);
        cart.getItems()
        return Optional.ofNullable(cartMap.putIfAbsent(id, item) == null ? user : null);
    }


    @Override
    public Optional<User> replaceUser(String key, User user) {
        return Optional.ofNullable(userList.replace(key, user) != null ? user : null);
    }

    @Override
    public boolean removeUser(String key) {
        return userList.remove(key) != null ? true : false;
    }

    @Override
    public List<User> getAllUser() {
        return new ArrayList<>(userList.values());
    }
}