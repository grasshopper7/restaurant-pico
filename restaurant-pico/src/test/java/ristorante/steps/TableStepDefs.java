package ristorante.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ristorante.entity.Order;
import ristorante.pages.OrderPageObject;
import ristorante.pages.PageFrameSwitcher;
import ristorante.pages.ScenarioData;
import ristorante.pages.TableListPageObject;

public class TableStepDefs {
	
	private TableListPageObject tablePO;
	
	private OrderPageObject orderPO;
	
	private ScenarioData data;
	
	private PageFrameSwitcher frameSwitcher;
		
	
	public TableStepDefs(ScenarioData data, PageFrameSwitcher frameSwitcher, TableListPageObject tablePO, OrderPageObject orderPO) {
		
		this.data = data;
		this.frameSwitcher = frameSwitcher;
		this.tablePO = tablePO;
		this.orderPO = orderPO;
	}
	
	@When("User selects (vacant )table")
	public void userSelectsTable() {
		
		//frameSwitcher.switchScreen(tablePO);
		tablePO.selectTableOrder(data.getTableNo());
		
		//Store the initial order details
		//frameSwitcher.switchScreen(orderPO);
		Order initialOrder = orderPO.getOrderDetails();
		data.setInitialOrder(initialOrder);
	}
	
	@When("User selects control order")
	public void userSelectsControlTable() {
		
		//frameSwitcher.switchScreen(tablePO);
		tablePO.selectTableOrder("Table1");
	}
	
	@When("User selects order in Ordered/Preparing/Ready/Served status")
	public void userSelectsOrderInStatus() {		
		userSelectsTable();
	}
	
	@Then("Order status in table list should be {word}")
	public void orderStatusInTableListShouldBeUpdated(String status) {

		//frameSwitcher.switchScreen(tablePO);
		//System.out.println("Table order status - "+tablePO.getTableStatus(data.getTableNo()));
		assertThat(tablePO.getTableStatus(data.getTableNo())).isEqualToIgnoringCase(status.toString().toUpperCase());
	}
	
	@Then("Order status text in table list should be highlighted")
	public void orderStatusInTableListShouldBeHighlighted() {
		
		//frameSwitcher.switchScreen(tablePO);
		//System.out.println("class--"+tablePO.getTableStatusClass(data.getTableNo()));
		assertThat(tablePO.getTableStatusClass(data.getTableNo())).isEqualToIgnoringCase("hightablestatus");		
	}
	
	@Then("Order status text in table list should not be highlighted")
	public void orderStatusInTableListShouldNotBeHighlighted() {
		
		//frameSwitcher.switchScreen(tablePO);
		//System.out.println("class--"+tablePO.getTableStatusClass(data.getTableNo()));
		assertThat(tablePO.getTableStatusClass(data.getTableNo())).isEqualToIgnoringCase("tablestatus");		
	}
	
	@Then("Table status should not be updated")
	public void tableStatusShouldNotBeUpdatedInTableList() {
	    
		//frameSwitcher.switchScreen(tablePO);
		//System.out.println(tablePO.getTableStatus(data.getTableNo()));
	}
	
	@Then("Table should be vacant in table list")
	public void tableShouldBeVacantInTableList() {
	    
		//frameSwitcher.switchScreen(tablePO);
		assertThat(tablePO.getTableStatus(data.getTableNo())).isEqualToIgnoringCase("VACANT");
	}
}
