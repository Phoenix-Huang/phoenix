package com.morningstar.grocerystore.customers;

public class CustomerFactory {

	public static Customer getCustomer(String type)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		Class<?> customerClass = Class
				.forName("com.morningstar.grocerystore.customers.Customer"
						+ type);
		
		Customer customer = (Customer)customerClass.newInstance();
		return customer;
	}
}
