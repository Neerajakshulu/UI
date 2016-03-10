package suiteC;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.TestBase;
import util.BrowserAction;
import util.BrowserWaits;
import util.OnePObjectMap;

/**
 * class for Project Neon Login with TR Credentials
 */
public class LoginTR extends TestBase{

	
	static int time=15;
	
	/**
	 * Method for wait TR Home Screen
	 * @throws InterruptedException 
	 */
	public static void waitForTRHomePage() throws InterruptedException {
		waitForElementTobeClickable(ob, By.cssSelector(OR.getProperty("tr_home_signInwith_projectNeon_css")), 90);
		waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_home_signInwith_projectNeon_css")), 90);
		BrowserWaits.waitUntilText("Sign in with Project Neon");
	}
	
	/**
	 * Method for enter Application Url and enter Credentials
	 */
	public static void enterTRCredentials(String userName, String password) {
		ob.findElement(By.cssSelector(OR.getProperty("tr_home_signInwith_projectNeon_css"))).click();
		waitUntilTextPresent(OR.getProperty("tr_signIn_header_css"),"Thomson Reuters ID");
		//waitUntilTextPresent(OR.getProperty("tr_signIn_login_css"),"Sign in");
		ob.findElement(By.cssSelector(OR.getProperty("tr_signIn_username_css"))).clear();
		ob.findElement(By.cssSelector(OR.getProperty("tr_signIn_username_css"))).sendKeys(userName);
		ob.findElement(By.cssSelector(OR.getProperty("tr_signIn_password_css"))).sendKeys(password);
	}
	
	public static void clickLogin() throws InterruptedException {
		ob.findElement(By.cssSelector(OR.getProperty("tr_signIn_login_css"))).click();
		waitForElementTobeVisible(ob, By.cssSelector("i[class='webui-icon webui-icon-search']"), 90);
		waitForElementTobeClickable(ob, By.cssSelector(OR.getProperty("tr_search_box_css")), 90);
		
	}
	
	public static void searchArticle(String article) throws InterruptedException {
		ob.findElement(By.cssSelector(OR.getProperty("tr_search_box_css"))).sendKeys(article);
		Thread.sleep(4000);
		ob.findElement(By.cssSelector("i[class='webui-icon webui-icon-search']")).click();
		waitForAjax(ob);
		waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchResults_links")), 90);
	}
	
	public static void chooseArticle(String linkName) throws InterruptedException {
		BrowserWaits.waitForAllElementsToBePresent(ob, By.xpath(OR.getProperty("searchResults_links")), 90);
		jsClick(ob,ob.findElement(By.xpath(OR.getProperty("searchResults_links"))));
	}
	
	
	
	public static void waitUntilTextPresent(String locator,String text){
		try {
			WebDriverWait wait = new WebDriverWait(ob, time);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(locator),text));
		} catch (TimeoutException e) {
			throw new TimeoutException("Failed to find element Locator , after waiting for " + time
					+ "ms");
		}
	}
	
	public static void waitUntilElementClickable(String linkName) {
		WebDriverWait wait = new WebDriverWait(ob, 10);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(linkName)));
	}
	
	
	public static void logOutApp() throws Exception {
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_IMAGE_CSS);
		BrowserWaits.waitUntilText("Sign out");
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_SIGNOUT_LINK);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_LOGIN_BUTTON_CSS);
		
	}
	


}
