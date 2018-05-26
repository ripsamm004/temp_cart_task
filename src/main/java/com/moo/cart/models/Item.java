package com.moo.cart.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 *  This class represent the Item in the cart. A item in a cart can have
 *  product (identify by a product code)
 *  quantity (number of product)
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Item {
    private Product product;
    private int quantity;

    public void updateQuantity(int quantity){
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        return !(product != null ? !product.equals(item.product) : item.product != null);

    }

    @Override
    public int hashCode() {
        return product != null ? product.hashCode() : 0;
    }
}