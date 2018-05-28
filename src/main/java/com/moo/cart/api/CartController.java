package com.moo.cart.api;

import com.moo.cart.models.Item;
import com.moo.cart.models.Product;
import com.moo.cart.service.CartService;
import com.moo.cart.service.ProductService;
import com.moo.cart.service.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;


@Slf4j
@RestController
@RequestMapping("/cart")
public class CartController {

    @Inject
    protected CartService cartService;

    @Inject
    protected Validator validator;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAllItem(@PathVariable String id) {
        log.info("GET : SHOW ALL Item");
        return new ResponseEntity(cartService.getAllItem(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> addItem(@RequestBody @NotNull ItemDTO itemDTO, @PathVariable String id) {
        log.info("PUT : Adding item into cart Item DTO : " + itemDTO);
        Item item = validator.validateItemDto(itemDTO);
        return new ResponseEntity(cartService.addItem(id, item), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void clearItem(@PathVariable String id) {
        log.info("DELETE : Clear CART Item");
        cartService.clearItem(id);
    }

}