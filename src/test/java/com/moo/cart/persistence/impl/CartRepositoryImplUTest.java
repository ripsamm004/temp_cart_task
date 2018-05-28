package com.moo.cart.persistence.impl;

import com.moo.cart.models.Cart;
import com.moo.cart.models.Item;
import com.moo.cart.models.Product;
import com.moo.cart.persistence.CartRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class CartRepositoryImplUTest {

    private CartRepository cartRepository;

    @Before
    public void setup(){
        cartRepository = new CartRepositoryImpl();
    }

    @Test
    public void testGetCartIfCartExist() {
        Optional<Cart> cartExpected = cartRepository.getCart("1");
        assertTrue(cartExpected.isPresent());
    }

    @Test
    public void testGetNoCartIfCartNotExist() {
        Optional<Cart> cartExpected = cartRepository.getCart("101");
        assertFalse(cartExpected.isPresent());
    }

    @Test
    public void testIfCartExistWhenAddItemThenAddedSuccessful() {
        String cardId = "1";
        Product p0 = new Product("ABCD", "Product-ABCD", 1.50d);
        Item item0 = new Item (p0, 10);
        Optional<Cart> cartExpected = cartRepository.addItem(cardId, item0);
        assertTrue(cartExpected.isPresent());
        assertEquals(cartExpected.get().getId(), cardId);
    }

    @Test
    public void testIfAddItemWhenAddSameProductThenAddQuantity() {
        String cardId = "1";
        Product p0 = new Product("ABCD", "Product-ABCD", 1.50d);
        Item item0 = new Item (p0, 10);
        cartRepository.addItem(cardId, item0);
        Optional<Cart> cartExpected = cartRepository.addItem(cardId, item0);
        assertTrue(cartExpected.isPresent());
        assertEquals(cartExpected.get().getId(), cardId);
        assertEquals(cartExpected.get().getItems().get(0).getProduct(), p0);
        assertEquals(cartExpected.get().getItems().get(0).getQuantity(), 20);
    }

    @Test
    public void testIfAddItemWhenAddSameProductWithNegativeQuantityThenRemoveQuantity() {
        String cardId = "1";
        Product p0 = new Product("ABCD", "Product-ABCD", 1.50d);
        Item item0 = new Item (p0, 10);
        Item item1 = new Item (p0, -5);
        cartRepository.addItem(cardId, item0);
        Optional<Cart> cartExpected = cartRepository.addItem(cardId, item1);
        assertTrue(cartExpected.isPresent());
        assertEquals(cartExpected.get().getId(), cardId);
        assertEquals(cartExpected.get().getItems().get(0).getProduct(), p0);
        assertEquals(cartExpected.get().getItems().get(0).getQuantity(), 5);
    }

    @Test
    public void testIfCartNotExistWhenAddItemThenAddFiled() {
        String cardId = "101";
        Product p0 = new Product("ABCD", "Product-ABCD", 1.50d);
        Item item0 = new Item (p0, 10);
        Optional<Cart> cartExpected = cartRepository.addItem(cardId, item0);
        assertFalse(cartExpected.isPresent());
    }

    @Test
    public void testIfCartExistWhenClearItemThenClearAllItem() {
        String cardId = "1";
        Product p0 = new Product("ABCD", "Product-ABCD", 1.50d);
        Item item0 = new Item (p0, 10);
        Optional<Cart> cartExpected = cartRepository.addItem(cardId, item0);
        assertTrue(cartExpected.isPresent());
        assertEquals(cartExpected.get().getItems().size(), 1);

        assertTrue(cartRepository.clearItem(cardId));
        assertEquals(cartExpected.get().getItems().size(), 0);
    }

    @Test
    public void testIfCartNotExistWhenClearItemThenReturnFalse() {
        String cardId = "101";
        assertFalse(cartRepository.clearItem(cardId));
    }
}
