package ristorante.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ristorante.steps.DataSetup;

public class AltBrowserPageObject {
		
	public AltBrowserPageObject() {	}
	
	public void navigateToHomePage(BrowserType browserType) {		
		
		WebDriver altDriver = driverFromBrowserType(browserType);
		altDriver.get("http://localhost:8080");
	}
	
	public void navigateToSelectedTable(BrowserType browserType, String tableNo) {		
		
		WebDriver altDriver = driverFromBrowserType(browserType);
		altDriver.switchTo().defaultContent();
		altDriver.switchTo().frame("tables");

		WebElement table = altDriver.findElement(By.xpath("//span[@class='tablenum'][.='" + tableNo + "']"));
		table.click();
		
		altDriver.switchTo().defaultContent();
	}
	
	public String retrieveTableStatusTextClass(BrowserType browserType, String tableNo) {
		
		WebDriver altDriver = driverFromBrowserType(browserType);
		altDriver.switchTo().defaultContent();
		altDriver.switchTo().frame("tables");

		WebElement status = altDriver.findElement(By.xpath("//span[@class='tablenum'][.='" + tableNo + "']/following-sibling::span"));
		String tableTextClass = status.getAttribute("class");
		
		altDriver.switchTo().defaultContent();		
		return tableTextClass;		
	}
	
	public String retrieveTableStatus(BrowserType browserType, String tableNo) {
		
		WebDriver altDriver = driverFromBrowserType(browserType);
		altDriver.switchTo().defaultContent();
		altDriver.switchTo().frame("tables");

		WebElement status = altDriver.findElement(By.xpath("//span[@class='tablenum'][.='" + tableNo + "']/following-sibling::span"));
		String tableStatus = status.getText();
		
		altDriver.switchTo().defaultContent();		
		return tableStatus;
	}
	
	public String retrieveModificationOrderMessage(BrowserType browserType) {
		
		WebDriver altDriver = driverFromBrowserType(browserType);
		altDriver.switchTo().defaultContent();
		altDriver.switchTo().frame("menuDetails");
		
		WebElement message = altDriver.findElement(By.id("message"));
		String msgText = message.getText();
		
		altDriver.switchTo().defaultContent();
		return msgText;
	}
	
	public boolean[] retrieveButtonEnableStatus(BrowserType browserType) {
		
		WebDriver altDriver = driverFromBrowserType(browserType);
		altDriver.switchTo().defaultContent();
		altDriver.switchTo().frame("menuDetails");

		WebElement modifyBtn = altDriver.findElement(By.id("modifyOrder"));
		WebElement cancelBtn = altDriver.findElement(By.id("cancelOrder"));
		
		boolean[] enabled = {modifyBtn.isEnabled(), cancelBtn.isEnabled()};
		altDriver.switchTo().defaultContent();
		
		return enabled;
	}
	

	public enum BrowserType {
		SIMILAR, DIFFERENT
	}

	private WebDriver driverFromBrowserType(BrowserType type) {
		if (type == BrowserType.SIMILAR)
			return DataSetup.altSimilarDriver;
		else if (type == BrowserType.DIFFERENT)
			return DataSetup.altDifferentDriver;
		else
			throw new RuntimeException("Driver messed up!!");
	}
}
