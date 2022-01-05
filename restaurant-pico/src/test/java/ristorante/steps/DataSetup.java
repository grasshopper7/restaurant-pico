package ristorante.steps;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.opera.OperaDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.Status;
import io.github.bonigarcia.wdm.WebDriverManager;
import ristorante.entity.Order;
import ristorante.entity.Tables;
import ristorante.pages.PageFrameSwitcher;
import ristorante.pages.ScenarioData;

public class DataSetup {

	public static WebDriver driver;

	private ScenarioData data;

	private PageFrameSwitcher frameSwitcher;

	public static WebDriver altSimilarDriver;

	public static WebDriver altDifferentDriver;
	
	static {
		Runtime.getRuntime().addShutdownHook(new Thread ( () ->  {
			removeDrivers();
		}));
	}

	public DataSetup(ScenarioData data, PageFrameSwitcher frameSwitcher) {
		this.data = data;
		this.frameSwitcher = frameSwitcher;
	}

	@Before(order = 1)
	public void setup(Scenario scenario) {

		//Use Threadlocal to store drivers for each thread for parallel
		if (driver == null) {
			WebDriverManager.chromedriver().setup();
			WebDriver currentDriver = new ChromeDriver();
			currentDriver.manage().window().maximize();
			driver = currentDriver;
		} 

		String tableno = scenario.getSourceTagNames().stream().filter(t -> t.startsWith("@Table")).findFirst()
				.orElse("");

		Order order = new Order(0);
		order.setOrderLines(new ArrayList<>());
		data.setInitialOrder(order);

		if (!tableno.isEmpty()) {
			data.setTableNo(tableno.substring(1));
			order.setTable(new Tables(Integer.parseInt(tableno.substring(6))));
		}
	}
	
	@Before(order = 2, value="@Similar")
	public void setupSimilarBrowser() {

		if (altSimilarDriver == null) {
			WebDriverManager.chromedriver().setup();
			WebDriver currentDriver = new ChromeDriver();
			currentDriver.manage().window().maximize();
			currentDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			altSimilarDriver = currentDriver;
		}
	}
	
	@Before(order = 3, value="@Different")
	public void setupDifferentBrowser(Scenario scenario) {

		if (altDifferentDriver == null) {
			WebDriverManager.operadriver().setup();
			WebDriver currentDriver = new OperaDriver();
			currentDriver.manage().window().maximize();
			currentDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			altDifferentDriver = currentDriver;
		}
	}

	@After
	public void teardown(Scenario scenario) {
		
		if (scenario.getStatus() == Status.PASSED && closeOtherTabs(frameSwitcher.getKitchenWindowHandle())
				&& closeOtherTabs(frameSwitcher.getServerWindowHandle())) {
			driver.switchTo().window(frameSwitcher.getHomeWindowHandle());
		} else {
			removeDrivers();
		}
	}

	private boolean closeOtherTabs(String handle) {
		if(handle == null || handle.isEmpty())
			return true;
		try {
			driver.switchTo().window(handle).close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private static void removeDrivers() {

		if (driver != null)
			driver.quit();
		//Force to null
		driver=null;
		
		if (altSimilarDriver != null)
			altSimilarDriver.quit();
		//Force to null
		altSimilarDriver=null;
		
		if (altDifferentDriver != null)
			altDifferentDriver.quit();
		//Force to null
		altDifferentDriver=null;
	}
}
