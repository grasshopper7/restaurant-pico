package ristorante.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ristorante.entity.Order;
import ristorante.entity.Order.OrderStatus;
import ristorante.pages.KitchenPageObject;
import ristorante.pages.PageFrameSwitcher;
import ristorante.pages.ScenarioData;

public class KitchenStepDefs {

	private KitchenPageObject kitchenPO;

	private ScenarioData data;

	private PageFrameSwitcher frameSwitcher;
	

	public KitchenStepDefs(ScenarioData data, PageFrameSwitcher frameSwitcher, KitchenPageObject kitchenPO) {

		this.data = data;
		this.frameSwitcher = frameSwitcher;
		this.kitchenPO = kitchenPO;
	}
	
	@When("User promotes order to {status} status in kitchen")
	public void userPromotesOrderToNewStatus(OrderStatus status) {
	    
		//frameSwitcher.switchScreen(kitchenPO);
		data.setOrderNo(kitchenPO.getOrderNum(data.getTableNo()));
		if(status == OrderStatus.PREPARING)
			kitchenPO.promoteOrderToPreparing(data.getTableNo());
		if(status == OrderStatus.READY)
			kitchenPO.promoteOrderToReady(data.getTableNo());
		data.getInitialOrder().setStatus(status);
	}
	
	@Then("Order should be available in {status} status in kitchen")
	public void orderShouldBeAvailableInStatusInKitchenPage(OrderStatus status) {

		//frameSwitcher.switchScreen(kitchenPO);
		Order expectedOrder = data.getInitialOrder();
		//System.out.println("kitchen expectedOrder - "+expectedOrder);
		Order actualOrder = kitchenPO.getOrderDetails(data.getOrderNo(), status.toString().toLowerCase());	
		//System.out.println("kitchen actualOrder - "+actualOrder);
		assertThat(actualOrder).isEqualToIgnoringGivenFields(expectedOrder, "id", "orderLines").extracting("orderLines")
			.asList().usingElementComparatorIgnoringFields("id").containsOnlyElementsOf(expectedOrder.getOrderLines());

	}
	
	@Then("Order should not be available in kitchen")
	public void orderShouldNotBeAvailableInInKitchenPage() {
	    
		//frameSwitcher.switchScreen(kitchenPO);
		//System.out.println("kitchen not order - " + kitchenPO.orderForTableExists(data.getTableNo().substring(5)));
		assertThat(kitchenPO.orderForTableExists(data.getTableNo().substring(5))).isEqualTo(false);	
	}
}
