package ristorante.pages;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class SwitchPageFrameAspect {

	@Before(/* "target(ristorante.aspects.SwitchScreen) && " */
			"execution(public * ristorante.pages.TableListPageObject.*(..)) || "
			+ "execution(public * ristorante.pages.OrderPageObject.*(..)) || "
			+ "execution(public * ristorante.pages.KitchenPageObject.*(..)) || "
			+ "execution(public * ristorante.pages.ServerPageObject.*(..)) || "
			+ "execution(public * ristorante.pages.SearchOrdersPageObject.*(..))")
	public void managePageFrameAdvice(JoinPoint jp) {
		//System.out.println(jp.getSignature().getName());
		BasePageObject<?> pageObject = (BasePageObject<?>) jp.getTarget();
		PageFrameSwitcher frameSwitcher = pageObject.getSwitcher();
		frameSwitcher.switchScreen(pageObject);
	}
}
