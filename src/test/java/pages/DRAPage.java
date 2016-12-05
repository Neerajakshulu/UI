package pages;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import base.TestBase;
import util.BrowserWaits;
import util.OnePObjectMap;

public class DRAPage extends TestBase {

	public DRAPage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}

	/**
	 * Method for logout DRA application
	 * @throws Exception,When not able to login
	 */
	public void logoutDRA() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.DRA_PROFILE_FLYOUT_IMAGE_CSS);
		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.DRA_PROFILE_FLYOUT_IMAGE_CSS);
		waitForAjax(ob);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_SIGNOUT_LINK);
		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_SIGNOUT_LINK);
		BrowserWaits.waitTime(3);
	}

	public void clickLoginDRA() throws Exception {
		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.LOGIN_PAGE_SIGN_IN_BUTTON_CSS);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.DRA_SEARCH_BOX_CSS);

	}

	public void clickOnSignInWithFBOnDRAModal() throws Exception {
		pf.getBrowserWaitsInstance(ob)
				.waitUntilElementIsClickable(OnePObjectMap.DRA_LINKINGMODAL_FB_SIGN_IN_BUTTON_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.DRA_LINKINGMODAL_FB_SIGN_IN_BUTTON_CSS);
	}
	
	public void clickOutsideTheDRAModal() throws Exception {

		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_LINKINGMODAL_FB_SIGN_IN_BUTTON_CSS);

		Dimension dimesions = pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.DRA_LINKINGMODAL_FB_SIGN_IN_BUTTON_CSS).getSize();
		logger.info("Width : " + dimesions.width);
		logger.info("Height : " + dimesions.height);
		int x = dimesions.width;
		int y = dimesions.height;

		Actions builder = new Actions(ob);
		builder.moveToElement(
				pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.DRA_LINKINGMODAL_FB_SIGN_IN_BUTTON_CSS), x + 150, y)
				.build().perform();
		builder.click().build().perform();
	}
	
	/**
	 * Method for to check DRA landing screen displayed or not
	 * @throws Exception,When DRA landing screen not displayed
	 */
	public void landingScreenDRA() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_LOGO_CSS);
		pf.getBrowserWaitsInstance(ob).waitUntilText("Target Druggability","Drug Research Advisor");
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.LOGIN_PAGE_EMAIL_TEXT_BOX_CSS);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.LOGIN_PAGE_PASSWORD_TEXT_BOX_CSS);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.LOGIN_PAGE_SIGN_IN_BUTTON_CSS);
	}
	
	/**
	 * Method for login DRA Application with valid login credentials
	 * @param userName
	 * @param password
	 * @throws Exception, When login not happend
	 */
	public void loginToDRAApplication(String userName,String password) throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_LOGO_CSS);
		pf.getBrowserWaitsInstance(ob).waitUntilText("Target Druggability","Drug Research Advisor");
		
		pf.getBrowserActionInstance(ob).clickAndClear(OnePObjectMap.LOGIN_PAGE_EMAIL_TEXT_BOX_CSS);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.LOGIN_PAGE_EMAIL_TEXT_BOX_CSS, userName);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.LOGIN_PAGE_PASSWORD_TEXT_BOX_CSS, password);
		
		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.LOGIN_PAGE_SIGN_IN_BUTTON_CSS);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.DRA_SEARCH_BOX_CSS);

	}

	
}