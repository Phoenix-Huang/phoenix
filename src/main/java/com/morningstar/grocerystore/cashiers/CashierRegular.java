package com.morningstar.grocerystore.cashiers;

import java.math.BigDecimal;

import com.morningstar.grocerystore.customers.Customer;

public class CashierRegular implements Cashier {
	
	private static final BigDecimal SPEED = BigDecimal.valueOf(1);
	@Override
	public void checkOut(Customer customer) {

		customer.reduceItemCount(CashierRegular.SPEED);

	}

}
