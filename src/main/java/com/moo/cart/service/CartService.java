package com.moo.cart.service;


import com.moo.cart.api.ErrorEnum;
import com.moo.cart.api.exception.NotFoundException;
import com.moo.cart.models.Cart;
import com.moo.cart.models.Item;
import com.moo.cart.persistence.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;


/**
 * This service is offer User data manipulation task.
 */
@Slf4j
@Service
public class CartService
{

    @Inject
    protected CartRepository cartRepository;

    public List<Item> getAllItem(String id) {
        Cart cart = cartRepository.getCart(id).orElseThrow(() -> new NotFoundException("CART NOT FOUND", ErrorEnum.API_ERROR_CART_NOT_FOUND));
        return cart.getItems();
    }

    public List<Item> addItem(String id, Item item) {
        Cart cart = cartRepository.addItem(id, item).orElseThrow(() -> new NotFoundException("CART NOT FOUND", ErrorEnum.API_ERROR_CART_NOT_FOUND));
        return cart.getItems();
    }

    public void clearItem(String id) {
        if (!cartRepository.clearItem(id)) {
            throw new NotFoundException("CART NOT FOUND", ErrorEnum.API_ERROR_CART_NOT_FOUND);
        }
    }

}