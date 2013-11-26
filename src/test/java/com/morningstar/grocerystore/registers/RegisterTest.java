package com.morningstar.grocerystore.registers;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.morningstar.grocerystore.DataFileReader;
import com.morningstar.grocerystore.customers.Customer;

public class RegisterTest {

	private Register register = null;
	private DataFileReader data = null;

	@Before
	public void setUp() throws Exception {
		URL path = getClass().getResource("/test.txt");
		data = new DataFileReader(new InputStreamReader(new FileInputStream(
				path.getPath())));

		List<Register> registers = data.getRegisters();
		List<Customer> list = new ArrayList<Customer>();

		for (int i = 0; i < 20; i++) {
			Queue<Customer> c = this.data.getCustomers(i);
			list.addAll(c);
		}

		this.register = registers.get(0);
		this.register.getCustomerQueue().addAll(list);

	}

	@After
	public void tearDown() throws Exception {

		if (data != null) {
			data.close();
		}

	}

	@Test
	public void testTimePass() {

		this.register.timePass();
		this.register.timePass();
		this.register.timePass();
		this.register.timePass();
		BigDecimal itemCount = this.register.getCustomerQueue().peek()
				.getItemCount();
		assertTrue(itemCount.compareTo(BigDecimal.valueOf(1)) == 0);
		this.register.timePass();
		itemCount = this.register.getCustomerQueue().peek()
				.getItemCount();
		assertTrue(itemCount.compareTo(BigDecimal.valueOf(1)) == 0);
		this.register.timePass();
		itemCount = this.register.getCustomerQueue().peek()
				.getItemCount();
		assertTrue(itemCount.compareTo(BigDecimal.valueOf(5)) == 0);
	}

}
