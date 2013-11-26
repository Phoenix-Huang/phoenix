package com.morningstar.grocerystore;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.List;
import java.util.Queue;

import com.morningstar.grocerystore.customers.Customer;
import com.morningstar.grocerystore.registers.Register;

public class Dispatcher {

	private int time = 0;

	private List<Register> registerList = null;

	private void init(DataFileReader reader) throws IOException {
		this.registerList = reader.getRegisters();
	}

	private boolean isRegisterCleared() {

		for (Register re : this.registerList) {
			if (re.getCustomerQueue().size() > 0) {
				return false;
			}
		}
		return true;
	}

	// push Customer to queue of each registers
	private void buildUpQueues(DataFileReader reader) {
		Queue<Customer> customerList = reader.getCustomers(this.time);

		for (Customer c : customerList) {
			if(c.getItemCount().compareTo(BigDecimal.valueOf(0))>0){
			c.joinQueue(this.registerList);
			}
		}
	}

	// every minutes, each register will try to check out something
	private void checkoutQueues() {
		for (Register r : this.registerList) {
			r.timePass();
		}
	}

	public int run(Reader input) {
		DataFileReader reader = null;
		try {
			reader = new DataFileReader(input);
			this.init(reader);

			while (reader.peekNextCustomer() != null || !isRegisterCleared()) {
				this.buildUpQueues(reader);
				this.checkoutQueues();
				this.time++;				
			}

		} catch (Throwable ex) {
			ex.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}

		return this.time;

	}

}
