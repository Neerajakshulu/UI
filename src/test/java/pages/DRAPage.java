package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.BrowserWaits;
import util.ErrorUtil;
import util.OnePObjectMap;

public class DRAPage extends TestBase {

	public DRAPage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}

	public void logoutDRA() throws Exception {

		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_PROFILE_CSS);
		jsClick(ob, ob.findElement(By.cssSelector(OnePObjectMap.DRA_PROFILE_CSS.toString())));
		waitForAjax(ob);
		waitForAllElementsToBePresent(ob, By.cssSelector(OnePObjectMap.DRA_SIGNOUT_BUTTON_CSS.toString()), 60);
		jsClick(ob, ob.findElement(By.cssSelector(OnePObjectMap.DRA_SIGNOUT_BUTTON_CSS.toString())));
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

	
}