package com.moo.cart.api;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * This class is use for data transfer layer it represents the User state.
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ItemDTO {
    private String code;
    private int quantity;
}
