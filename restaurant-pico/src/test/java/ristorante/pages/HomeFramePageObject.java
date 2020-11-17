package ristorante.pages;

public abstract class HomeFramePageObject<T extends HomeFramePageObject<T>> extends BasePageObject<T> {

	protected HomePageObject homePO;
	
	public HomeFramePageObject(PageFrameSwitcher frameSwitcher, HomePageObject homePO) {
		super(frameSwitcher);
		this.homePO = homePO;
	}

	@Override
	protected void switchScreen(BasePageObject<?> finalPage) {
		
		if (checkSameScreen(finalPage))
			return;
		
		driver.switchTo().defaultContent();
		homePO.get();
		
		if (!finalPage.getClass().equals(HomePageObject.class))
			homePO.switchScreen(finalPage);		
	}
	
	protected abstract String getFrameId();
}
