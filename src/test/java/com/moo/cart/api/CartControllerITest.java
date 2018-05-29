package com.moo.cart.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moo.cart.ApplicationMain;
import com.moo.cart.models.Cart;
import com.moo.cart.service.CartService;
import com.moo.cart.service.Validator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(
        webEnvironment = WebEnvironment.RANDOM_PORT,
        classes = ApplicationMain.class)
public class CartControllerITest {

    @LocalServerPort
    private int port;

    String apiEndPoint = createURLWithPort("/cart");

    @Autowired
    private CartService cartService;

    @Autowired
    private Validator validator;

    @Autowired
    private MockMvc mvc;


    @Test
    public void testIfGivenCartIdWhenCartHasItemsThenResponseJsonArrayOfCartItems() throws Exception {

        String cartId = "1";
        mvc.perform(get(apiEndPoint + "/" + cartId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(0)))
                .andReturn();

        ItemDTO itemDTO = new ItemDTO("ABCD", 10);

        mvc.perform(put(apiEndPoint + "/" + cartId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(itemDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.*.quantity", containsInAnyOrder(10)))
                .andExpect(jsonPath("$.*.product.code", containsInAnyOrder("ABCD")))
                .andReturn();

        mvc.perform(get(apiEndPoint + "/" + cartId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andReturn();
    }


    @Test
    public void testIfGivenCartIdIsNotValidWhenGetCartItemThenResponseJsonError() throws Exception {

        String invalidCartId = "101";
        mvc.perform(get(apiEndPoint + "/" + invalidCartId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(ErrorEnum.API_ERROR_CART_NOT_FOUND.getCode())))
                .andReturn();
    }


    @Test
    public void testIfGivenCartIdWhenCartIsValidAndAddItemWithValidProductThenResponseJsonArrayOfCartItems() throws Exception {
        String cartId = "2";
        ItemDTO itemDTO = new ItemDTO("ABCD", 10);
        mvc.perform(put(apiEndPoint + "/" + cartId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(itemDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.*.quantity", containsInAnyOrder(10)))
                .andExpect(jsonPath("$.*.product.code", containsInAnyOrder("ABCD")))
                .andReturn();
    }

    @Test
    public void testIfGivenCartIdWhenCartIsValidAndAddItemWithInValidProductThenResponseJsonError() throws Exception {
        String cartId = "3";
        ItemDTO itemDTO = new ItemDTO("INVALID_PRODUCT_CODE", 10);
        mvc.perform(put(apiEndPoint + "/" + cartId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(itemDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(ErrorEnum.API_ERROR_PRODUCT_CODE_NOT_CORRECT.getCode())))
                .andReturn();
    }

    @Test
    public void testIfGivenCartIdWhenRequestWithInvalidPayloadOnAddItemThenResponseJsonError() throws Exception {
        String cartId = "1";
        ItemDTO itemDTO = new ItemDTO(null, 10);
        mvc.perform(put(apiEndPoint + "/" + cartId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(itemDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(ErrorEnum.API_ERROR_INVALID_REQUEST_BODY.getCode())))
                .andReturn();
    }

    @Test
    public void testIfGivenCartIdWhenRequestWithInvalidJsonPayloadOnAddItemThenResponseJsonError() throws Exception {
        String cartId = "1";
        String invalidJsonPayLoad = "";
        mvc.perform(put(apiEndPoint + "/" + cartId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJsonPayLoad))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(ErrorEnum.API_ERROR_REQUEST_BODY_FORMAT_INVALID.getCode())))
                .andReturn();
    }

    @Test
    public void testIfCartNotExistWhenClearCartRequestThenResponseJsonError() throws Exception {
        Cart cart = new Cart("101");
        mvc.perform(delete(apiEndPoint + "/" + cart.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is(ErrorEnum.API_ERROR_CART_NOT_FOUND.getCode())))
                .andReturn();
    }


    @Test
    public void testIfCartHasItemsWhenClearCartRequestThenResponseOK() throws Exception {

        String cartId = "3";
        ItemDTO itemDTO = new ItemDTO("ABCD", 10);
        mvc.perform(put(apiEndPoint + "/" + cartId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(itemDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.*.quantity", containsInAnyOrder(10)))
                .andExpect(jsonPath("$.*.product.code", containsInAnyOrder("ABCD")))
                .andReturn();

        mvc.perform(delete(apiEndPoint + "/" + cartId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

    }


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}