package com.morningstar.grocerystore;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.List;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.morningstar.grocerystore.customers.Customer;
import com.morningstar.grocerystore.registers.Register;

public class Dispatcher {
	
	final static Logger logger = LoggerFactory.getLogger(Dispatcher.class);
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

		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					
					logger.error(e.getMessage(), e);
				}
			}
		}

		return this.time;

	}

}
