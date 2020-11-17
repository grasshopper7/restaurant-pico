package ristorante.steps;

import io.cucumber.java.en.Given;
import ristorante.pages.HomePageObject;

public class HomeStepDefs {

	private HomePageObject homePO;
		
	public HomeStepDefs(HomePageObject homePO) {
		
		this.homePO = homePO;
	}	
	
	@Given("User navigates to home page")
	public void userNavigatesToHomePage() {		
		
		homePO.get();
	}
}
