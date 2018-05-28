package com.moo.cart.service;

import com.moo.cart.api.exception.NotFoundException;
import com.moo.cart.models.Cart;
import com.moo.cart.models.Item;
import com.moo.cart.models.Product;
import com.moo.cart.persistence.CartRepository;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class CartServiceUTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @InjectMocks
    private CartService cartService;

    @Mock
    protected CartRepository cartRepository;

    @Test
    public void testIfCartExistWithItemThenGetItemListWithSizeMoreThanZero() {

        Cart cart = new Cart("1");
        Product p0 = new Product("ABCD", "Product-ABCD", 1.50d);
        Product p1 = new Product("UXYZ", "Product-UXYZ", 5.50d);
        Item item0 = new Item(p0, 10);
        Item item1 = new Item(p1, 20);
        cart.addItem(item0);
        cart.addItem(item1);

        Optional<Cart> cartOptional = Optional.of(cart);
        when(cartRepository.getCart(cart.getId())).thenReturn(cartOptional);
        List<Item> itemListExpected = cartService.getAllItem(cart.getId());
        verify(cartRepository, times(1)).getCart(cart.getId());

        assertThat(itemListExpected.size(), Matchers.is(2));
        assertEquals(itemListExpected.get(0), item0);
        assertEquals(itemListExpected.get(1), item1);
        assertEquals(itemListExpected.get(0).getProduct(), p0);
        assertEquals(itemListExpected.get(1).getProduct(), p1);
    }


    @Test
    public void testIfCartNotExistThenThrowCartNotFoundException() {

        Optional<Cart> cartOptional = Optional.ofNullable(null);
        when(cartRepository.getCart(anyString())).thenReturn(cartOptional);
        thrown.expect(NotFoundException.class);
        thrown.expectMessage(equalTo("CART NOT FOUND"));
        cartService.getAllItem(anyString());
    }


    @Test
    public void testIfCartExistWhenAddingItemThenItemAddedSuccess() {

        Cart cart = new Cart("1");
        Product p0 = new Product("ABCD", "Product-ABCD", 1.50d);
        Item item0 = new Item(p0, 10);
        cart.addItem(item0);

        Optional<Cart> cartOptional = Optional.of(cart);
        when(cartRepository.addItem(cart.getId(), item0)).thenReturn(cartOptional);
        List<Item> itemListExpected = cartService.addItem(cart.getId(), item0);
        verify(cartRepository, times(1)).addItem(cart.getId(), item0);

        assertThat(itemListExpected.size(), Matchers.is(2));
        assertEquals(itemListExpected.get(0), item0);
        assertEquals(itemListExpected.get(0).getProduct(), p0);
    }


    @Test
    public void testIfCartNotExistWhenAddingItemThenThrowCartNotFoundException() {
        String cartId = "1100";
        Product p0 = new Product("ABCD", "Product-ABCD", 1.50d);
        Item item0 = new Item(p0, 10);

        Optional<Cart> cartOptional = Optional.ofNullable(null);
        thrown.expect(NotFoundException.class);
        thrown.expectMessage(equalTo("CART NOT FOUND"));

        when(cartRepository.addItem(cartId, item0)).thenReturn(cartOptional);
        cartService.addItem(cartId, item0);
    }


    @Test
    public void testIfCartExistWhenClearCartThenClearAllItemSuccess() {
        when(cartRepository.clearItem(anyString())).thenReturn(true);
        cartService.clearItem(anyString());
        verify(cartRepository, times(1)).clearItem(anyString());
    }

    @Test
    public void testIfCartNotExistWhenClearCartThenThrowCartNotFoundException() {
        when(cartRepository.clearItem(anyString())).thenReturn(false);
        thrown.expect(NotFoundException.class);
        thrown.expectMessage(equalTo("CART NOT FOUND"));
        cartService.clearItem(anyString());
        verify(cartRepository, times(1)).clearItem(anyString());
    }

}
