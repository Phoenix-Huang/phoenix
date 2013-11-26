package com.morningstar.grocerystore;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.morningstar.grocerystore.customers.Customer;
import com.morningstar.grocerystore.customers.CustomerA;
import com.morningstar.grocerystore.customers.CustomerB;
import com.morningstar.grocerystore.registers.Register;

public class DataFileReaderTest {

	private DataFileReader data = null;

	@Before
	public void setUp() throws Exception {
		
		URL path = getClass().getResource("/test.txt");
		data = new DataFileReader(new InputStreamReader(new FileInputStream(
				path.getPath())));
	}

	@After
	public void tearDown() throws Exception {
		if (data != null) {
			data.close();
		}
	}

	@Test
	public void testGetRegisters() throws IOException {
		List<Register> r = this.data.getRegisters();
		assertEquals(2, r.size());
	}

	@Test
	public void testGetCustomers() throws IOException {

		this.data.getRegisters();
		List<Customer> list = new ArrayList<Customer>();

		for (int i = 0; i < 20; i++) {
			Queue<Customer> c = this.data.getCustomers(i);
			list.addAll(c);
		}
		assertEquals(5, list.size());
		
		assertTrue(list.get(0) instanceof CustomerA);
		assertTrue(list.get(1) instanceof CustomerB);
		assertTrue(list.get(2) instanceof CustomerA);
		assertTrue(list.get(3) instanceof CustomerB);
		assertTrue(list.get(4) instanceof CustomerA);

	}
}
