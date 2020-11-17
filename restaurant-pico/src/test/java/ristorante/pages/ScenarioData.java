package ristorante.pages;

import ristorante.entity.Order;

public class ScenarioData {
	
	private String tableNo;
	
	private String orderNo;
	
	private Order initialOrder;
		

	public String getTableNo() {
		return tableNo;
	}

	public void setTableNo(String tableNo) {
		this.tableNo = tableNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Order getInitialOrder() {
		return initialOrder;
	}

	public void setInitialOrder(Order initialOrder) {
		this.initialOrder = initialOrder;
	}	
}
