package com.morningstar.grocerystore.registers;
import java.math.BigDecimal;
import java.util.LinkedList;

import com.morningstar.grocerystore.cashiers.*;
import com.morningstar.grocerystore.customers.*;

public class Register {
	
	private Cashier cashier;
	private final int registerNumber;
    private LinkedList<Customer> customerQueue = new LinkedList<Customer>();

    public Register(int registerNumber,Cashier cashier) {
		this.registerNumber = registerNumber;
		this.cashier = cashier;
	} 
    
    
    //Every minute elapsed, the cashier will check out item
    public void timePass() 
    {
        if (this.customerQueue.size() > 0)
        {
            Customer customer = this.customerQueue.peek();
            this.cashier.checkOut(customer);
            if (customer.getItemCount().compareTo(BigDecimal.valueOf(0))==0) 
            {
                this.customerQueue.poll();
            }
        }
    }

	public int getRegisterNumber() {
		return registerNumber;
	}	

	public LinkedList<Customer> getCustomerQueue() {
		return customerQueue;
	}
}
