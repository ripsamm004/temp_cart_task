package com.moo.cart.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@RequiredArgsConstructor // create Constructor for id not for items
@Getter
public class Cart {
    private String id;
    private final List<Item> items = new ArrayList<Item>();

    public void addItem(Product product, int quantity) {
        Item item = this.findItemByProductCode(product.getCode());

        if (item == null) {
            item = new Item(product, 0);
            this.items.add(item);
        }

        int newQuantity = item.getQuantity() + quantity;

        if (newQuantity <= 0) {
            this.items.remove(item);
        } else {
            item.updateQuantity(newQuantity);
        }
    }

    private Item findItemByProductCode(String code) {
        for (Item item : this.items) {
            if (item.getProduct().getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
