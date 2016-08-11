package Authoring;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import util.BrowserAction;
import util.BrowserWaits;
import util.OnePObjectMap;
import base.TestBase;

/**
 * class for Project Neon Login with TR Credentials
 */
public class LoginTR extends TestBase {

	static int time = 15;
	BrowserWaits browserWait;
	BrowserAction browserAction;

	final WebDriver ob;

	public LoginTR(WebDriver ob) {
		this.ob = ob;
		browserWait = new BrowserWaits(ob);
		browserAction = new BrowserAction(ob);

	}

	/**
	 * Method for wait TR Home Screen
	 * 
	 * @throws InterruptedException
	 */
	public void waitForTRHomePage() throws InterruptedException {
		waitForElementTobeClickable(ob, By.cssSelector(OR.getProperty("tr_home_signInwith_projectNeon_css")), 90);
		waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_home_signInwith_projectNeon_css")), 90);
		browserWait.waitUntilText("Thomson Reuters","Project Neon");
		// PageFactory.getBrowserWaitsInstance(ob).waitUntilText("Sign in with Project Neon");

	}

	/**
	 * Method for enter Application Url and enter Credentials
	 * @throws InterruptedException 
	 */
	public void enterTRCredentials(String userName,
			String password) throws InterruptedException {
		//ob.findElement(By.cssSelector(OR.getProperty("tr_home_signInwith_projectNeon_css"))).click();
		//waitUntilTextPresent(OR.getProperty("tr_signIn_header_css"), "Thomson Reuters ID");
		// waitUntilTextPresent(OR.getProperty("tr_signIn_login_css"),"Sign in");
		ob.findElement(By.cssSelector(OnePObjectMap.LOGIN_PAGE_EMAIL_TEXT_BOX_CSS.toString())).clear();
		ob.findElement(By.cssSelector(OnePObjectMap.LOGIN_PAGE_EMAIL_TEXT_BOX_CSS.toString())).sendKeys(userName);
		ob.findElement(By.cssSelector(OnePObjectMap.LOGIN_PAGE_PASSWORD_TEXT_BOX_CSS.toString())).sendKeys(password);
	}

	public void clickLogin() throws Exception {
		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.LOGIN_PAGE_SIGN_IN_BUTTON_CSS);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_BOX_CSS);
		
		List<WebElement> onboardingStatus=pf.getBrowserActionInstance(ob).getElements(OnePObjectMap.HOME_PROJECT_NEON_ONBOARDING_MODAL_CSS);
		logger.info("onboarding status-->"+onboardingStatus.size());
		
		if(onboardingStatus.size() == 1) {
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.HOME_PROJECT_NEON_ONBOARDING_WELCOME_MODAL_CSS);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.HOME_PROJECT_NEON_ONBOARDING_WELCOME_MODAL_CSS);
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.HOME_PROJECT_NEON_ONBOARDING_PROFILE_MODAL_CSS);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.HOME_PROJECT_NEON_ONBOARDING_PROFILE_MODAL_CSS);
			BrowserWaits.waitTime(4);
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_BOX_CSS);
		}
	}

	public void searchArticle(String article) throws InterruptedException {
		ob.findElement(By.cssSelector(OR.getProperty("tr_search_box_css"))).sendKeys(article);
		ob.findElement(By.cssSelector("div[class='ne-main-nav'] button[title='Search'] i[class='fa fa-search']")).click();
		waitForAjax(ob);
		waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchResults_links")), 90);
	}

	public void chooseArticle(String linkName) throws InterruptedException {
		BrowserWaits.waitForAllElementsToBePresent(ob, By.xpath(OR.getProperty("searchResults_links")), 180);
		jsClick(ob, ob.findElement(By.xpath(OR.getProperty("searchResults_links"))));
	}

	public void waitUntilTextPresent(String locator,
			String text) {
		try {
			WebDriverWait wait = new WebDriverWait(ob, time);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(locator), text));
		} catch (TimeoutException e) {
			throw new TimeoutException("Failed to find element Locator , after waiting for " + time + "ms");
		}
	}

	public void waitUntilElementClickable(String linkName) {
		WebDriverWait wait = new WebDriverWait(ob, 10);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(linkName)));
	}

	public void logOutApp() throws Exception {
		BrowserWaits.waitTime(20);
		jsClick(ob, ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_IMAGE_CSS.toString())));
		browserWait.waitUntilText("Sign out");
		jsClick(ob, ob.findElement(By.linkText(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_SIGNOUT_LINK.toString())));
		browserWait.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_LOGIN_BUTTON_CSS);

	}
	
	public void loginWithLinkedInCredentials(String username, String pwd) {

		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.LOGIN_PAGE_LI_SIGN_IN_BUTTON_CSS.toString()), 30);
		ob.findElement(By.cssSelector(OnePObjectMap.LOGIN_PAGE_LI_SIGN_IN_BUTTON_CSS.toString())).click();

		waitForElementTobeVisible(ob, By.name(OnePObjectMap.LOGIN_PAGE_LI_EMAIL_TEXT_BOX_ID.toString()), 30);

		// Verify that existing LI user credentials are working fine
		ob.findElement(By.name(OnePObjectMap.LOGIN_PAGE_LI_EMAIL_TEXT_BOX_ID.toString())).sendKeys(username);
		ob.findElement(By.name(OnePObjectMap.LOGIN_PAGE_LI_PASSWORD_TEXT_BOX_ID.toString())).sendKeys(pwd);
		// BrowserWaits.waitTime(2);
		ob.findElement(By.name(OnePObjectMap.LOGIN_PAGE_LI_ALLOW_ACCESS_BUTTON_ID.toString())).click();
	}
	public void loginWithFBCredentials(String username, String pwd) {

		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.LOGIN_PAGE_FB_SIGN_IN_BUTTON_CSS.toString()), 30);
		ob.findElement(By.cssSelector(OnePObjectMap.LOGIN_PAGE_FB_SIGN_IN_BUTTON_CSS.toString())).click();

		waitForElementTobeVisible(ob, By.name(OnePObjectMap.LOGIN_PAGE_FB_EMAIL_TEXT_BOX_ID.toString()), 30);

		// Verify that existing LI user credentials are working fine
		ob.findElement(By.name(OnePObjectMap.LOGIN_PAGE_FB_EMAIL_TEXT_BOX_ID.toString())).sendKeys(username);
		ob.findElement(By.name(OnePObjectMap.LOGIN_PAGE_FB_PASSWORD_TEXT_BOX_ID.toString())).sendKeys(pwd);
		// BrowserWaits.waitTime(2);
		ob.findElement(By.name(OnePObjectMap.LOGIN_PAGE_FB_LOGIN_BUTTON_ID.toString())).click();
	}
}
