package ristorante.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ristorante.entity.Order;
import ristorante.entity.Order.OrderStatus;
import ristorante.entity.Tables;
import ristorante.pages.ScenarioData;
import ristorante.pages.ServerPageObject;

public class ServerStepDefs {

	private ServerPageObject serverPO;

	private ScenarioData data;

	public ServerStepDefs(ScenarioData data, ServerPageObject serverPO) {

		this.data = data;
		this.serverPO = serverPO;
	}

	@When("User promotes order to {orderStatus} status in server")
	public void userPromotesOrderToNewStatus(OrderStatus status) {
		data.setOrderNo(serverPO.getOrderNum(data.getTableNo()));

		if (status == OrderStatus.SERVED)
			serverPO.promoteOrderToServed(data.getTableNo());
		if (status == OrderStatus.BILLED) {
			serverPO.promoteOrderToBilled(data.getTableNo());
			data.getInitialOrder().setTable(new Tables(0));
		}
		data.getInitialOrder().setStatus(status);
	}

	@SuppressWarnings("deprecation")
	@Then("Order should be available in {orderStatus} status in server")
	public void orderShouldBeAvailableInStatusInServerPage(OrderStatus status) {
		Order expectedOrder = data.getInitialOrder();
		Order actualOrder = serverPO.getOrderDetails(data.getOrderNo(), status.toString().toLowerCase());

		assertThat(actualOrder).isEqualToIgnoringGivenFields(expectedOrder, "id", "orderLines").extracting("orderLines")
				.asList().usingElementComparatorIgnoringFields("id")
				.containsOnlyElementsOf(expectedOrder.getOrderLines());

	}

	@Then("Order should not be available in server")
	public void orderShouldNotBeAvailableInInServerPage() {
		assertThat(serverPO.orderForTableExists(data.getTableNo().substring(5))).isEqualTo(false);
	}
}