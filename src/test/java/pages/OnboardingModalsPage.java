package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import base.TestBase;
import util.BrowserWaits;
import util.OnePObjectMap;

/**
 * Class for perform Onboarding modals - Welcome Modal and Profile Modal
 * validations
 * 
 * @author UC202376
 *
 */
public class OnboardingModalsPage extends TestBase {
	
	public OnboardingModalsPage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}

	/**
	 * Method for click SignIn button
	 * @throws Exception, When login not happend
	 */
	public void clickLogin() throws Exception {
		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.LOGIN_PAGE_SIGN_IN_BUTTON_CSS);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_BOX_CSS);
	}

	/**
	 * Method for Validate Onboarding modals,
	 * @throws Exception, When Onboarding modals are not present for first time users
	 */
	public void validateOnboardingModals() throws Exception {

		List<WebElement> onboardingStatus = pf.getBrowserActionInstance(ob)
				.getElements(OnePObjectMap.HOME_PROJECT_NEON_ONBOARDING_MODAL_CSS);
		logger.info("onboarding status-->" + onboardingStatus.size());

		try {
			pf.getBrowserWaitsInstance(ob)
					.waitUntilElementIsClickable(OnePObjectMap.HOME_PROJECT_NEON_ONBOARDING_WELCOME_MODAL_CSS);
			pf.getBrowserWaitsInstance(ob).waitUntilText("Follow and discuss research", "Connect with researchers",
					"Discover articles, patents, and community contributions");
			pf.getBrowserWaitsInstance(ob).waitUntilText("Recommended people to follow");
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.HOME_PROJECT_NEON_ONBOARDING_WELCOME_MODAL_CSS);
			pf.getBrowserWaitsInstance(ob)
					.waitUntilElementIsClickable(OnePObjectMap.HOME_PROJECT_NEON_ONBOARDING_PROFILE_MODAL_CSS);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.HOME_PROJECT_NEON_ONBOARDING_PROFILE_MODAL_CSS);
			BrowserWaits.waitTime(2);
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_BOX_CSS);
		} catch (Exception e) {
			throw new Exception("Onboarding Modals are not displayed for First time user");
		}

	}
}
