package com.morningstar.grocerystore.customers;

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
import com.morningstar.grocerystore.registers.Register;

public class CustomerBTest {

	private Customer customer = null;
	private List<Register> registers = null;

	private DataFileReader data = null;

	@Before
	public void setUp() throws Exception {

		URL path = getClass().getResource("/testJoinQueueB.txt");
		data = new DataFileReader(new InputStreamReader(new FileInputStream(
				path.getPath())));

		this.registers = data.getRegisters();
		List<Customer> list = new ArrayList<Customer>();

		for (int i = 0; i < 20; i++) {
			Queue<Customer> c = this.data.getCustomers(i);
			list.addAll(c);
		}
		this.registers.get(0).getCustomerQueue().add(list.get(0));
		this.registers.get(1).getCustomerQueue().add(list.get(1));
		this.registers.get(1).getCustomerQueue().add(list.get(2));
		this.customer = list.get(3);
	}

	@After
	public void tearDown() throws Exception {
		if (data != null) {
			data.close();
		}
		this.customer = null;
		this.registers = null;
	}
	
	
	@Test
	public void testJoinQueue() throws Exception {
		this.customer.joinQueue(this.registers);
		assertSame(this.customer, this.registers.get(1).getCustomerQueue().peekLast());		
	}
	
	
	@Test
	public void testSetGetArriveTime() throws Exception {
		
		Customer customerB = new CustomerB();
		customerB.setArriveTime(2);
		assertEquals(customerB.getArriveTime(),2);
	}
	
	@Test
	public void testSetItemCount() throws Exception {
		
		Customer customerB = new CustomerB();
		customerB.setItemCount(BigDecimal.valueOf(5));		
		assertTrue(customerB.getItemCount()
				.compareTo(BigDecimal.valueOf(5)) == 0);
	}

	

	@Test
	public void testGetItemCount() throws Exception {
		
		Customer customerB = new CustomerB();
		customerB.setItemCount(BigDecimal.valueOf(4.5));		
		assertTrue(customerB.getItemCount()
				.compareTo(BigDecimal.valueOf(4.5)) == 0);
	}

	@Test
	public void testReduceItemCount() throws Exception {
		Customer customerB = new CustomerB();
		customerB.setItemCount(BigDecimal.valueOf(4.5));		
		customerB.reduceItemCount(BigDecimal.valueOf(1.5));
		assertTrue(customerB.getItemCount()
				.compareTo(BigDecimal.valueOf(3)) == 0);
	}	


	@Test
	public void testGetPriorityRank() throws Exception {
		assertEquals(10, this.customer.getPriorityRank());
	}

}
