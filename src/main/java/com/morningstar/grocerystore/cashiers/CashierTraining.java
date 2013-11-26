package com.morningstar.grocerystore.cashiers;

import java.math.BigDecimal;

import com.morningstar.grocerystore.customers.Customer;

public class CashierTraining implements Cashier {
	
	//The cashier can only check out 0.5 item because he/she is still in training
	private static final BigDecimal SPEED = BigDecimal.valueOf(0.5D);
	@Override
	public void checkOut(Customer customer) {

		customer.reduceItemCount(CashierTraining.SPEED);
		
	}

	

}
