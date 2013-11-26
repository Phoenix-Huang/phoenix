package com.morningstar.grocerystore;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import com.morningstar.grocerystore.cashiers.CashierRegular;
import com.morningstar.grocerystore.cashiers.CashierTraining;
import com.morningstar.grocerystore.customers.Customer;
import com.morningstar.grocerystore.registers.Register;

//Try to use AutoCloseable here, but JDK6 doesn't support it
public class DataFileReader implements Closeable {

	private BufferedReader reader = null;

	public DataFileReader(Reader input) {
		this.reader = new BufferedReader(input);
	}

	public class CustomerCompare implements Comparator<Customer> {

		public int compare(Customer a, Customer b) {

			Integer priorityRankA = a.getPriorityRank();
			Integer priorityRankB = b.getPriorityRank();

			BigDecimal itemCountA = a.getItemCount();
			BigDecimal itemCountB = b.getItemCount();

			int itemCompare = itemCountA.compareTo(itemCountB);

			if (0 == itemCompare) {

				return priorityRankA.compareTo(priorityRankB);
			}
			return itemCompare;
		}
	}

	private Customer pollNextCustomer() throws Exception {

		if(this.customerCache!=null){
			
			Customer customer = this.customerCache;
			this.customerCache = null;
			return customer;
			
		}
		
		
		String line = this.reader.readLine();
		if (line != null) {

			String[] items = line.split(" +");

			if (3 == items.length) {
				Class<?> customer = Class
						.forName("com.morningstar.grocerystore.customers.Customer"
								+ items[0].toUpperCase().trim());

				Customer c = (Customer) customer.newInstance();
				c.setArriveTime(Integer.parseInt(items[1].trim()));
				c.setItemCount(new BigDecimal(items[2].trim()));

				return c;
			} else {
				throw new Exception("The data is not validated\t" + line);
			}
		}
		return null;
	}

	private Customer customerCache = null;

	public Customer peekNextCustomer() throws Exception {

		if (this.customerCache == null) {
			Customer c = this.pollNextCustomer();
			this.customerCache = c;
		}
		return customerCache;
	}

	public List<Register> getRegisters() throws IOException {

		if (this.reader.ready()) {
			List<Register> regsters = new ArrayList<Register>();
			String line = this.reader.readLine();
			int count = Integer.parseInt(line.trim());

			Register r = null;
			for (int i = 0; i < count; i++) {
				if (i < count - 1) {
					r = new Register(i + 1, new CashierRegular());
				} else {
					r = new Register(i + 1, new CashierTraining());
				}
				regsters.add(r);
			}

			return regsters;
		}
		return null;
	}

	public Queue<Customer> getCustomers(int time) {
		Queue<Customer> customers = new PriorityQueue<Customer>(20,
				new CustomerCompare());
		try {

			while (true) {
				Customer customer = this.peekNextCustomer();
				if (customer != null && customer.getArriveTime() == time) {
					customers.offer(this.pollNextCustomer());
				} else {
					break;
				}
			}

		} catch (IOException ioex) {
			System.err.println(ioex.getMessage());
			ioex.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return customers;
	}

	@Override
	public void close() throws IOException {

		if (reader != null) {
			reader.close();
		}
	}
}
