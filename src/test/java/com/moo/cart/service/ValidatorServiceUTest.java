package com.moo.cart.service;

import com.moo.cart.api.ItemDTO;
import com.moo.cart.api.exception.BadRequestException;
import com.moo.cart.models.Item;
import com.moo.cart.models.Product;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class ValidatorServiceUTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    protected ProductService productService;

    @InjectMocks
    private Validator validator;


    @Test
    public void testIfCartItemProductCodeIsValidThenExpectTrue() {
        Product p0 = new Product("ABCD", "Product-ABCD", 1.50d);
        Optional<Product> mockProduct0 = Optional.ofNullable(p0);
        when(productService.getProduct(anyString())).thenReturn(mockProduct0);
        assertThat(validator.validateProductCode("ABCD"), is(true));
        verify(productService, times(1)).getProduct("ABCD");
    }

    @Test
    public void testIfCartItemProductCodeIsNotValidThenExpectFalse() {
        Optional<Product> mockProduct0 = Optional.ofNullable(null);
        when(productService.getProduct(anyString())).thenReturn(mockProduct0);
        assertThat(validator.validateProductCode("ABCD"), is(false));
        verify(productService, times(1)).getProduct("ABCD");
    }

    @Test
    public void testIfGivenItemDTOHaveEmptyValuesThenThrowExceptions() {
        ItemDTO itemDTO = new ItemDTO(null, 0);
        Optional<Product> mockProduct0 = Optional.ofNullable(null);
        when(productService.getProduct(anyString())).thenReturn(mockProduct0);
        thrown.expect(BadRequestException.class);
        thrown.expectMessage(equalTo("DATA INVALID"));
        validator.validateItemDto(itemDTO);
    }

    @Test
    public void testIfGivenItemDTOWithWrongCodeThenThrowExceptions() {
        ItemDTO itemDTO = new ItemDTO("WRONG_CODE", 10);
        Optional<Product> mockProduct0 = Optional.ofNullable(null);
        when(productService.getProduct(anyString())).thenReturn(mockProduct0);
        thrown.expect(BadRequestException.class);
        thrown.expectMessage(equalTo("INVALID PRODUCT CODE"));
        validator.validateItemDto(itemDTO);
    }

    @Test
    public void testIfGivenCorrectItemDTOThenExpectItem() {
        String code = "ABCD";
        ItemDTO itemDTO = new ItemDTO(code, 10);
        Product p0 = new Product(code, "Product-ABCD", 1.50d);
        Optional<Product> mockProduct0 = Optional.ofNullable(p0);
        when(productService.getProduct(code)).thenReturn(mockProduct0);
        Item item = validator.validateItemDto(itemDTO);
        assertEquals(item.getProduct(), p0);
        assertEquals(item.getQuantity(), 10);
    }

    @Test
    public void testIfItemDTOIsValidThenReturnItemWithProduct() throws Exception {

        String code = "ABCD";
        ItemDTO itemDTO = new ItemDTO(code, 10);
        Product p0 = new Product(code, "Product-ABCD", 1.50d);
        Optional<Product> mockProduct0 = Optional.ofNullable(p0);
        when(productService.getProduct(code)).thenReturn(mockProduct0);
        Method method = Validator.class.getDeclaredMethod("dtoToItem", ItemDTO.class);
        method.setAccessible(true);
        Item item = (Item) method.invoke(validator, itemDTO);
        assertEquals(item.getProduct(), p0);
        assertEquals(item.getQuantity(), 10);
    }

    /**
     * Reflection method invocation throws InvocationTargetException exception if the method call throws exceptions
     * Where method.invoke(validator, itemDTO) return the Optional<Product> as null so we expect InvocationTargetException
     * @throws Exception
     */

    @Test(expected = InvocationTargetException.class)
    public void testIfItemDTOIsInValidThenThrowExceptions() throws Exception  {
        String code = "ABCD";
        ItemDTO itemDTO = new ItemDTO(code, 10);
        Optional<Product> mockProduct0 = Optional.ofNullable(null);
        when(productService.getProduct(code)).thenReturn(mockProduct0);
        Method method = Validator.class.getDeclaredMethod("dtoToItem", ItemDTO.class);
        method.setAccessible(true);
        method.invoke(validator, itemDTO);
    }
}
