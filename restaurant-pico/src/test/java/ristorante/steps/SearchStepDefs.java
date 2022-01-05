package ristorante.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Then;
import ristorante.entity.Order.OrderStatus;
import ristorante.pages.ScenarioData;
import ristorante.pages.SearchOrdersPageObject;

public class SearchStepDefs {

	private SearchOrdersPageObject searchPO;

	private ScenarioData data;

	public SearchStepDefs(ScenarioData data, SearchOrdersPageObject searchPO) {

		this.data = data;
		this.searchPO = searchPO;
	}

	@Then("Order should be available in {orderStatus} status in search")
	public void orderShouldBeAvailableInSearch(OrderStatus state) {
		OrderStatus[] statuses = { state };
		searchPO.searchOrders(statuses, "45");

		boolean present = searchPO.orderPresentInSearch(data.getTableNo().substring(5), data.getOrderNo(), state);
		assertThat(present).isEqualTo(true);
	}

	@Then("User selects {orderStatus} order from search")
	public void userSelectsOrderFromSearch(OrderStatus state) {
		searchPO.selectOrderInSearch(data.getTableNo().substring(5), data.getOrderNo(), state);
	}
}