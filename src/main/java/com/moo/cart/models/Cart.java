package com.moo.cart.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@RequiredArgsConstructor // create Constructor with parameter id
@Getter
public class Cart {
    @NonNull
    private String id;
    private final List<Item> items = new ArrayList<Item>();

    public void addItem(Item item) {
        Item itemByProductCode = this.findItemByProductCode(item.getProduct().getCode());

        if (itemByProductCode == null) {
            itemByProductCode = new Item(item.getProduct(), 0);
            this.items.add(itemByProductCode);
        }

        int newQuantity = itemByProductCode.getQuantity() + item.getQuantity();

        if (newQuantity <= 0) {
            this.items.remove(itemByProductCode);
        } else {
            itemByProductCode.updateQuantity(newQuantity);
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
