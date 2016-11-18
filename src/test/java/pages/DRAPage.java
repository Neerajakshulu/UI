package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.TestBase;
import util.BrowserWaits;
import util.OnePObjectMap;

public class DRAPage extends TestBase {

	public DRAPage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}

	public void logoutDRA() throws Exception {

		BrowserWaits.waitTime(4);
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

}
