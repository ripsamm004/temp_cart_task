package com.moo.cart;

import com.moo.cart.api.CartController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceApplicationITests {

	@Autowired
	private CartController cartController;

	@Test
	public void contextLoads() {
		assertThat(cartController).isNotNull();
	}
}