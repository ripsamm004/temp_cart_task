package com.moo.cart.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Cart {
    private String id;
    private final List<Item> items = new ArrayList<Item>();
}
