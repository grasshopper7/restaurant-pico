package ristorante.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ristorante.entity.Order;
import ristorante.entity.Order.OrderStatus;
import ristorante.pages.KitchenPageObject;
import ristorante.pages.ScenarioData;

public class KitchenStepDefs {

	private KitchenPageObject kitchenPO;

	private ScenarioData data;

	public KitchenStepDefs(ScenarioData data, KitchenPageObject kitchenPO) {

		this.data = data;
		this.kitchenPO = kitchenPO;
	}

	@When("User promotes order to {orderStatus} status in kitchen")
	public void userPromotesOrderToNewStatus(OrderStatus status) {
		data.setOrderNo(kitchenPO.getOrderNum(data.getTableNo()));

		if (status == OrderStatus.PREPARING)
			kitchenPO.promoteOrderToPreparing(data.getTableNo());
		if (status == OrderStatus.READY)
			kitchenPO.promoteOrderToReady(data.getTableNo());

		data.getInitialOrder().setStatus(status);
	}

	@SuppressWarnings("deprecation")
	@Then("Order should be available in {orderStatus} status in kitchen")
	public void orderShouldBeAvailableInStatusInKitchenPage(OrderStatus status) {
		Order expectedOrder = data.getInitialOrder();

		Order actualOrder = kitchenPO.getOrderDetails(data.getOrderNo(), status.toString().toLowerCase());

		assertThat(actualOrder).isEqualToIgnoringGivenFields(expectedOrder, "id", "orderLines").extracting("orderLines")
				.asList().usingElementComparatorIgnoringFields("id")
				.containsOnlyElementsOf(expectedOrder.getOrderLines());

	}

	@Then("Order should not be available in kitchen")
	public void orderShouldNotBeAvailableInInKitchenPage() {
		assertThat(kitchenPO.orderForTableExists(data.getTableNo().substring(5))).isEqualTo(false);
	}
}
