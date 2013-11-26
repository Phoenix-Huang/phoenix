package com.morningstar.grocerystore.cashiers;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import com.morningstar.grocerystore.customers.Customer;
import com.morningstar.grocerystore.customers.CustomerA;

public class CashierTrainingTest {

	@Test
	public void testCheckOut() {

		Customer customer = new CustomerA();
		customer.setItemCount(BigDecimal.valueOf(10));
		CashierTraining cashier = new CashierTraining();
		cashier.checkOut(customer);
		assertTrue(customer.getItemCount().compareTo(BigDecimal.valueOf(9.5)) == 0);

		cashier.checkOut(customer);
		assertTrue(customer.getItemCount().compareTo(BigDecimal.valueOf(9)) == 0);

	}

}
