package com.morningstar.grocerystore.customers;

import java.util.List;

import com.morningstar.grocerystore.registers.Register;

public class CustomerB extends Customer {

	public CustomerB() {
		this.priorityRank = 10;
	}

	@Override
	public void joinQueue(List<Register> registerList) {

		if (registerList != null && registerList.size() > 0) {

			Register register = registerList.get(0);

			for (Register re : registerList) {
				{
					Customer lastCustomer = re.getCustomerQueue().peekLast();
					if (lastCustomer == null
							|| register.getCustomerQueue().size() == 0) {
						register = re;
						break;
					}

					if (lastCustomer.getItemCount().compareTo(
							register.getCustomerQueue().peekLast().getItemCount()) < 0) {
						register = re;
					}
				}
			}
			register.getCustomerQueue().offer(this);
		}
	}
}
