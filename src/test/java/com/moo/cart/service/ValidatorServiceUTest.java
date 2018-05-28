package com.moo.cart.service;

import com.moo.cart.models.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
public class ValidatorServiceUTest {

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


    /*@Test
    public void testIfUserNameIsNotValidThenExpectFalse() throws Exception {

        String[] username = {"ripsamm004-", "testspecialchar £004", "test space", "r 333"};

        assertThat(userValidator.validateUsername(username[0]), is(false));
        assertThat(userValidator.validateUsername(username[1]), is(false));
        assertThat(userValidator.validateUsername(username[2]), is(false));
        assertThat(userValidator.validateUsername(username[3]), is(false));
    }


    @Test
    public void testIfUserDOBIsValidThenExpectTrue() throws Exception {

        String[] dob = {"1917-03-02", "1917-12-02", "1917-02-02", "1917-03-02"};

        assertThat(userValidator.validateDob(dob[0]), is(true));
        assertThat(userValidator.validateDob(dob[1]), is(true));
        assertThat(userValidator.validateDob(dob[2]), is(true));
        assertThat(userValidator.validateDob(dob[3]), is(true));

    }


    @Test
    public void testIfUserDOBIsNotValidThenExpectFalse() throws Exception {

        String[] dob = {"1917-03-02 09:02", "1917-20-12", "02-20-1917", "1917-03-02 12:01:04"};

        assertThat(userValidator.validateDob(dob[0]), is(false));
        assertThat(userValidator.validateDob(dob[1]), is(false));
        assertThat(userValidator.validateDob(dob[2]), is(false));
        assertThat(userValidator.validateDob(dob[3]), is(false));
    }

    @Test(expected = BadRequestException.class)
    public void testIfGivenUserDTONameIsNotValidThenThrowExceptions() throws Exception {
        UserDTO userDTO = new UserDTO("usern ame22", "443908Rr", "1986-05-26", "ssn110");
        userValidator.validateUser(userDTO);
    }

    @Test(expected = BadRequestException.class)
    public void testIfGivenUserDTOPasswordIsNotValidThenThrowExceptions() throws Exception {
        UserDTO userDTO = new UserDTO("username22", "44444", "1986-05-26", "ssn110");
        userValidator.validateUser(userDTO);
    }

    @Test(expected = BadRequestException.class)
    public void testIfGivenUserDTODOBIsNotValidThenThrowExceptions() throws Exception {
        UserDTO userDTO = new UserDTO("username22", "44444rR", "1986-30-02", "ssn110");
        userValidator.validateUser(userDTO);
    }

    @Test(expected = BadRequestException.class)
    public void testIfGivenUserDTOHaveEmptyValuesThenThrowExceptions()
            throws Exception {

        UserDTO userDTO = new UserDTO("", "", "", "");
        userValidator.validateUser(userDTO);
    }

    @Test(expected = BadRequestException.class)
    public void testIfGivenUserDTOHaveNullValuesThenThrowExceptions()
            throws Exception {

        UserDTO userDTO = new UserDTO(null, null, null, null);
        userValidator.validateUser(userDTO);
    }*/
}
