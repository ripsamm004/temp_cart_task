package com.moo.cart.persistence;

import com.moo.cart.models.Cart;
import com.moo.cart.models.Item;
import java.util.Optional;

public interface CartRepository {
    Optional<Cart> getCart(String id);
    Optional<Cart> addItem(String id, Item item);
    boolean clearItem(String id);
}