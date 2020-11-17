package ristorante.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import ristorante.steps.DataSetup;

public abstract class BasePageObject<T extends BasePageObject<T>> extends LoadableComponent<T> {

	//protected WebDriver driver = DataSetup.drivers.get();
	protected WebDriver driver = DataSetup.driver;

	protected WebDriverWait waitDriver = new WebDriverWait(driver, 10, 250);

	protected PageFrameSwitcher frameSwitcher;
	
	
	public BasePageObject(PageFrameSwitcher driverMgr) {
		this.frameSwitcher = driverMgr;
	}
	
	public PageFrameSwitcher getSwitcher() {
		return frameSwitcher;
	}

	protected abstract void switchScreen(BasePageObject<?> finalPage);
	
	protected boolean checkSameScreen(BasePageObject<?> finalPage) {
		
		if(frameSwitcher.getCurrentPage().getClass().equals(finalPage.getClass()))
			return true;
		return false;
	}
}
