package com.moo.cart.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moo.cart.ApplicationMain;
import com.moo.cart.api.exception.BadRequestException;
import com.moo.cart.api.exception.NotFoundException;
import com.moo.cart.models.Cart;
import com.moo.cart.models.Item;
import com.moo.cart.models.Product;
import com.moo.cart.service.CartService;
import com.moo.cart.service.Validator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = ApplicationMain.class)
public class CartControllerUTest {

    @MockBean
    private CartService cartService;

    @MockBean
    private Validator validator;

    @Autowired
    private MockMvc mvc;


    String apiEndPoint = "/cart";

    @Test
    public void testIfGivenCartIdWhenCartHasItemsThenResponseJsonArrayOfCartItems() throws Exception{

        Cart cart = new Cart("1");
        Product p0 = new Product("ABCD", "Product-ABCD", 1.50d);
        Product p1 = new Product("UXYZ", "Product-UXYZ", 5.50d);
        Item item0 = new Item(p0, 10);
        Item item1 = new Item(p1, 20);
        cart.addItem(item0);
        cart.addItem(item1);

        doReturn(cart.getItems()).when(cartService).getAllItem(cart.getId());

        mvc.perform(get(apiEndPoint + "/" + cart.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.*.quantity", containsInAnyOrder(10, 20)))
                .andExpect(jsonPath("$.*.product.code", containsInAnyOrder("ABCD", "UXYZ")))
                .andReturn();
        verify(cartService, times(1)).getAllItem(cart.getId());
    }

    @Test
    public void testIfGivenCartIdWhenCartHasNoItemsThenResponseEmptyJsonArray() throws Exception{

        Cart cart = new Cart("1");
        doReturn(cart.getItems()).when(cartService).getAllItem(cart.getId());

        mvc.perform(get(apiEndPoint + "/" + cart.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(0)))
                .andReturn();
        verify(cartService, times(1)).getAllItem(cart.getId());
    }

    @Test
    public void testIfGivenCartIdWhenCartNotExistThenResponseJsonError() throws Exception{

        Cart cart = new Cart("101");
        doThrow(new NotFoundException("CART NOT FOUND", ErrorEnum.API_ERROR_CART_NOT_FOUND))
                .when(cartService).getAllItem(cart.getId());

        mvc.perform(get(apiEndPoint + "/" + cart.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(ErrorEnum.API_ERROR_CART_NOT_FOUND.getCode())))
                .andReturn();
    }

    @Test
    public void testIfGivenCartIdAndIfCartExistThenIfAddItemResponseJsonItem() throws Exception {

        Cart cart = new Cart("1");
        Product p0 = new Product("ABCD", "Product-ABCD", 1.50d);
        Item item0 = new Item(p0, 10);
        cart.addItem(item0);

        ItemDTO itemDTO = new ItemDTO("ABCD", 10);

        when(validator.validateItemDto(any(ItemDTO.class))).thenReturn(item0);
        when(cartService.addItem(cart.getId(), item0)).thenReturn(cart.getItems());

        mvc.perform(put(apiEndPoint + "/" + cart.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(itemDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.*.quantity", containsInAnyOrder(10)))
                .andExpect(jsonPath("$.*.product.code", containsInAnyOrder("ABCD")))
                .andReturn();

        verify(validator, times(1)).validateItemDto(any(ItemDTO.class));
        verify(cartService, times(1)).addItem(cart.getId(), item0);

    }


    @Test
    public void testIfGivenCartIdAndIfCartExistWhenRequestBodyNotValidThenResponseJsonError() throws Exception {

        Cart cart = new Cart("1");
        Product p0 = new Product("ABCD", "Product-ABCD", 1.50d);
        Item item0 = new Item(p0, 10);

        ItemDTO itemDTO = new ItemDTO(null, 10);


        doThrow(new BadRequestException("DATA INVALID", ErrorEnum.API_ERROR_INVALID_REQUEST_BODY))
                .when(validator).validateItemDto(any(ItemDTO.class));
        when(cartService.addItem(cart.getId(), item0)).thenReturn(any());

        mvc.perform(put(apiEndPoint + "/" + cart.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(itemDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(ErrorEnum.API_ERROR_INVALID_REQUEST_BODY.getCode())))
                .andReturn();

        verify(validator, times(1)).validateItemDto(any(ItemDTO.class));
        verify(cartService, times(0)).addItem(cart.getId(), item0);
    }

    @Test
    public void testIfCartExistWhenRequestBodyWithInValidProductCodeThenResponseJsonError() throws Exception {

        Cart cart = new Cart("1");
        Product p0 = new Product("ABCD", "Product-ABCD", 1.50d);
        Item item0 = new Item(p0, 10);

        ItemDTO itemDTO = new ItemDTO("INVALID_CODE", 10);


        doThrow(new BadRequestException("INVALID PRODUCT CODE", ErrorEnum.API_ERROR_PRODUCT_CODE_NOT_CORRECT))
                .when(validator).validateItemDto(any(ItemDTO.class));

        when(cartService.addItem(cart.getId(), item0)).thenReturn(any());

        mvc.perform(put(apiEndPoint + "/" + cart.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(itemDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(ErrorEnum.API_ERROR_PRODUCT_CODE_NOT_CORRECT.getCode())))
                .andReturn();

        verify(validator, times(1)).validateItemDto(any(ItemDTO.class));
        verify(cartService, times(0)).addItem(cart.getId(), item0);
    }

    @Test
    public void testIfCartNotExistWhenAddItemRequestThenResponseJsonError() throws Exception {

        ItemDTO itemDTO = new ItemDTO("ABCD", 10);
        Cart cart = new Cart("1");
        Product p0 = new Product("ABCD", "Product-ABCD", 1.50d);
        Item item0 = new Item(p0, 10);

        when(validator.validateItemDto(any(ItemDTO.class))).thenReturn(item0);

        doThrow(new NotFoundException("CART NOT FOUND", ErrorEnum.API_ERROR_CART_NOT_FOUND))
                .when(cartService).addItem(cart.getId(), item0);

        mvc.perform(put(apiEndPoint + "/" + cart.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(itemDTO)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(ErrorEnum.API_ERROR_CART_NOT_FOUND.getCode())))
                .andReturn();

        verify(validator, times(1)).validateItemDto(any(ItemDTO.class));
        verify(cartService, times(1)).addItem(cart.getId(), item0);
    }

    @Test
    public void testIfCartNotExistWhenClearCartRequestThenResponseJsonError() throws Exception {

        Cart cart = new Cart("101");
        Product p0 = new Product("ABCD", "Product-ABCD", 1.50d);
        Item item0 = new Item(p0, 10);

        doThrow(new NotFoundException("CART NOT FOUND", ErrorEnum.API_ERROR_CART_NOT_FOUND))
                .when(cartService).clearItem(cart.getId());

        mvc.perform(delete(apiEndPoint + "/" + cart.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(ErrorEnum.API_ERROR_CART_NOT_FOUND.getCode())))
                .andReturn();

        verify(cartService, times(1)).clearItem(cart.getId());
    }


    @Test
    public void testIfCartHasItemsWhenClearCartRequestThenResponseOK() throws Exception {

        Cart cart = new Cart("1");
        Product p0 = new Product("ABCD", "Product-ABCD", 1.50d);
        Item item0 = new Item(p0, 10);

        doNothing().when(cartService).clearItem(cart.getId());

        mvc.perform(delete(apiEndPoint + "/" + cart.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        verify(cartService, times(1)).clearItem(cart.getId());
    }


    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}