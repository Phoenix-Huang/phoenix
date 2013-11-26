package com.morningstar.grocerystore.customers;


import java.math.BigDecimal;
import java.util.List;

import com.morningstar.grocerystore.registers.*;


public abstract class Customer {
	
	public abstract void joinQueue(List<Register> registerList);
	protected int priorityRank;
	private int arriveTime;
	private BigDecimal itemCount;

	public BigDecimal getItemCount() {
		return itemCount;
	}

	public void reduceItemCount(BigDecimal itemCount) {

		this.itemCount =this.itemCount.subtract(itemCount) ;
	}
	
	public void setItemCount(BigDecimal itemCount) {
		this.itemCount = itemCount;
	}

	public int getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(int arriveTime) {
		this.arriveTime = arriveTime;
	}

	public int getPriorityRank() {
		return priorityRank;
	}

	
}
