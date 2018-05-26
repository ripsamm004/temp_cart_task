package com.moo.cart.persistence;


import com.moo.cart.models.Cart;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<Cart>  getCartItem(String key);
    Optional<Cart>  addCartItem(String key, User user);
    boolean clearCart(String key);
}