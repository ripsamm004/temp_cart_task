package com.moo.cart.persistence;

import com.moo.cart.models.Cart;
import com.moo.cart.models.Item;
import java.util.Optional;

public interface CartRepository {
    Optional<Cart> getItem(String id);
    Optional<Cart> addItem(String id, Item item);
    boolean clearCart(String key);
}