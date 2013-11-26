package com.morningstar.grocerystore.customers;

import java.util.List;

import com.morningstar.grocerystore.registers.Register;

public class CustomerA extends Customer {
	public CustomerA() {
		this.priorityRank = 20;
	}

	@Override
	public void joinQueue(List<Register> registerList) {

		if (registerList != null && registerList.size() > 0) {
			Register register = registerList.get(0);

			for (Register re : registerList) {
				if (re.getCustomerQueue().size() < register.getCustomerQueue().size()) {
					register = re;
				}
			}

			register.getCustomerQueue().offer(this);
		}
	}
}
