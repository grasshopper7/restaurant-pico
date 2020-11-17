package ristorante.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ristorante.entity.Order;
import ristorante.entity.Order.OrderStatus;
import ristorante.entity.Tables;
import ristorante.pages.PageFrameSwitcher;
import ristorante.pages.ScenarioData;
import ristorante.pages.ServerPageObject;

public class ServerStepDefs {

	private ServerPageObject serverPO;

	private ScenarioData data;

	private PageFrameSwitcher frameSwitcher;

	public ServerStepDefs(ScenarioData data, PageFrameSwitcher frameSwitcher, ServerPageObject serverPO) {

		this.data = data;
		this.frameSwitcher = frameSwitcher;
		this.serverPO = serverPO;
	}

	@When("User promotes order to {status} status in server")
	public void userPromotesOrderToNewStatus(OrderStatus status) {

		//frameSwitcher.switchScreen(serverPO);
		data.setOrderNo(serverPO.getOrderNum(data.getTableNo()));

		if (status == OrderStatus.SERVED)
			serverPO.promoteOrderToServed(data.getTableNo());
		if (status == OrderStatus.BILLED) {
			serverPO.promoteOrderToBilled(data.getTableNo());
			data.getInitialOrder().setTable(new Tables(0));
		}
		data.getInitialOrder().setStatus(status);
	}

	@Then("Order should be available in {status} status in server")
	public void orderShouldBeAvailableInStatusInServerPage(OrderStatus status) {

		//frameSwitcher.switchScreen(serverPO);
		Order expectedOrder = data.getInitialOrder();
		Order actualOrder = serverPO.getOrderDetails(data.getOrderNo(), status.toString().toLowerCase());
		//System.out.println("server expectedOrder - "+expectedOrder);
		//System.out.println("server actualOrder - "+actualOrder);
		assertThat(actualOrder).isEqualToIgnoringGivenFields(expectedOrder, "id", "orderLines").extracting("orderLines")
				.asList().usingElementComparatorIgnoringFields("id")
				.containsOnlyElementsOf(expectedOrder.getOrderLines());

	}

	@Then("Order should not be available in server")
	public void orderShouldNotBeAvailableInInServerPage() {

		//frameSwitcher.switchScreen(serverPO);
		// System.out.println("kitchen not order - " +
		// kitchenPO.orderForTableExists(data.getTableNo().substring(5)));
		assertThat(serverPO.orderForTableExists(data.getTableNo().substring(5))).isEqualTo(false);
	}
}
