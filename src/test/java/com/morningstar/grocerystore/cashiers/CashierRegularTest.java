package com.morningstar.grocerystore.cashiers;

import java.math.BigDecimal;


import org.junit.Test;
import static org.junit.Assert.*;

import com.morningstar.grocerystore.customers.*;

public class CashierRegularTest {

	@Test
	public void testCheckOut() {
		Customer customer = new CustomerA();
		customer.setItemCount(BigDecimal.valueOf(10));
		CashierRegular cashier = new CashierRegular();
		cashier.checkOut(customer);
		assertTrue(customer.getItemCount().compareTo(BigDecimal.valueOf(9)) == 0);

		cashier.checkOut(customer);
		assertTrue(customer.getItemCount().compareTo(BigDecimal.valueOf(8)) == 0);
	}

	
}
