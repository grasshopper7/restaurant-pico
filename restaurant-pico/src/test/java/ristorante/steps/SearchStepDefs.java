package ristorante.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Then;
import ristorante.entity.Order.OrderStatus;
import ristorante.pages.PageFrameSwitcher;
import ristorante.pages.ScenarioData;
import ristorante.pages.SearchOrdersPageObject;

public class SearchStepDefs {

	private SearchOrdersPageObject searchPO;

	private ScenarioData data;

	private PageFrameSwitcher frameSwitcher;

	public SearchStepDefs(ScenarioData data, PageFrameSwitcher frameSwitcher, SearchOrdersPageObject searchPO) {

		this.data = data;
		this.frameSwitcher = frameSwitcher;
		this.searchPO = searchPO;
	}

	@Then("Order should be available in {status} status in search")
	public void orderShouldBeAvailableInSearch(OrderStatus state) {

		//frameSwitcher.switchScreen(searchPO);
		OrderStatus[] statuses = { state };
		searchPO.searchOrders(statuses, "45");
		
		boolean present = searchPO.orderPresentInSearch(data.getTableNo().substring(5), data.getOrderNo(), state);
		//System.out.println("search order - "+present);
		assertThat(present).isEqualTo(true);
	}
	
	@Then("User selects {status} order from search")
	public void userSelectsOrderFromSearch(OrderStatus state) {
	    
		//frameSwitcher.switchScreen(searchPO);
		searchPO.selectOrderInSearch(data.getTableNo().substring(5), data.getOrderNo(), state);	
	}
}
