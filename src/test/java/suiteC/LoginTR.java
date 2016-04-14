package suiteC;

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
		browserWait.waitUntilText("Sign in with Project Neon");
		// PageFactory.getBrowserWaitsInstance(ob).waitUntilText("Sign in with Project Neon");

	}

	/**
	 * Method for enter Application Url and enter Credentials
	 */
	public void enterTRCredentials(String userName,
			String password) {
		ob.findElement(By.cssSelector(OR.getProperty("tr_home_signInwith_projectNeon_css"))).click();
		waitUntilTextPresent(OR.getProperty("tr_signIn_header_css"), "Thomson Reuters ID");
		// waitUntilTextPresent(OR.getProperty("tr_signIn_login_css"),"Sign in");
		ob.findElement(By.cssSelector(OR.getProperty("tr_signIn_username_css"))).clear();
		ob.findElement(By.cssSelector(OR.getProperty("tr_signIn_username_css"))).sendKeys(userName);
		ob.findElement(By.cssSelector(OR.getProperty("tr_signIn_password_css"))).sendKeys(password);
	}

	public void clickLogin() throws InterruptedException {
		ob.findElement(By.cssSelector(OR.getProperty("tr_signIn_login_css"))).click();
		waitForElementTobeVisible(ob, By.cssSelector("i[class='webui-icon webui-icon-search']"), 90);
		waitForElementTobeClickable(ob, By.cssSelector(OR.getProperty("tr_search_box_css")), 90);

	}

	public void searchArticle(String article) throws InterruptedException {
		ob.findElement(By.cssSelector(OR.getProperty("tr_search_box_css"))).sendKeys(article);
		ob.findElement(By.cssSelector("i[class='webui-icon webui-icon-search']")).click();
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
		browserAction.click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_IMAGE_CSS);
		browserWait.waitUntilText("Sign out");
		browserAction.click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_SIGNOUT_LINK);
		browserWait.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_LOGIN_BUTTON_CSS);

	}

}
