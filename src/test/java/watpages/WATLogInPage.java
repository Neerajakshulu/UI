package watpages;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import pages.PageFactory;
import util.BrowserWaits;
import util.OnePObjectMap;

public class WATLogInPage extends TestBase {

	String WoS_DB_Name = "Web of Science Core Collection";

	public WATLogInPage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}

	public void loginToWAT(String username, String password, ExtentTest test) throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilText("SaR Labs", "Sign in", "Forgot password?");
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.NEON_IPA_USERNAME_CSS);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.NEON_IPA_USERNAME_CSS, username);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.NEON_IPA_PASSWORD_CSS, password);
		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.NEON_IPA_SIGNIN_CSS);
		pf.getBrowserWaitsInstance(ob).waitForAjax(ob);
		pf.getBrowserWaitsInstance(ob).waitUntilText("Search", "Web of Science: Author search");
		test.log(LogStatus.INFO, "Login to WAT Applicaton Successfully");

	}

	public void loginToWOSWAT(ExtentTest test) throws Exception {
		test.log(LogStatus.INFO, "Loading WoS Application");
		ob.navigate().to(host);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.WOS_WAT_LANDING_PAGE_LOGO_XPATH);
		test.log(LogStatus.INFO, "Verifing whether control is in Landing page");
		Assert.assertTrue(pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WOS_WAT_LANDING_PAGE_LOGO_XPATH)
				.isDisplayed());
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WOS_WAT_DB_SELECTION_XPATH);
		List<WebElement> options = pf.getBrowserActionInstance(ob)
				.getElements(OnePObjectMap.WOS_WAT_DB_DROPDOWN_VALUES_XPATH);

		for (WebElement opt : options) {
			if (opt.getText().equalsIgnoreCase(WoS_DB_Name)) {
				opt.click();
				break;
			}
		}
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.WOS_WAT_RESEARCHER_SEARCH_LINK_XPATH);
		test.log(LogStatus.INFO, "Verifing whether Researcher search link is visible");
		Assert.assertTrue(pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WOS_WAT_RESEARCHER_SEARCH_LINK_XPATH)
				.isDisplayed());
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WOS_WAT_RESEARCHER_SEARCH_LINK_XPATH);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.WOS_WAT_NAME_SEARCH_LINK_XPATH);
		test.log(LogStatus.INFO, "Verifing whether Name search link is visible");
		Assert.assertTrue(
				pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WOS_WAT_NAME_SEARCH_LINK_XPATH).isDisplayed());
		test.log(LogStatus.INFO, "Landed in WAT Page Successfully");
	}

	public void logoutWAT() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.WAT_PROFILE_FLYOUT_IMAGE_CSS);
		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.WAT_PROFILE_FLYOUT_IMAGE_CSS);
		waitForAjax(ob);
		pf.getBrowserWaitsInstance(ob)
				.waitUntilElementIsClickable(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_SIGNOUT_LINK);
		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_SIGNOUT_LINK);
		BrowserWaits.waitTime(3);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.NEON_LANDING_PAGE_LOGGIN_BANNER_CSS);
	}
}
