package suiteC;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.TestBase;
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
		Thread.sleep(4000);
		BrowserWaits.waitUntilText("Sign in with Project Neon");
	}
	
	/**
	 * Method for enter Application Url and enter Credentials
	 */
	public static void enterTRCredentials(String userName, String password) {
		ob.findElement(By.cssSelector(TestBase.OR.getProperty("tr_home_signInwith_projectNeon_css"))).click();
		waitUntilTextPresent(TestBase.OR.getProperty("tr_signIn_header_css"),"Thomson Reuters ID");
		//waitUntilTextPresent(TestBase.OR.getProperty("tr_signIn_login_css"),"Sign in");
		ob.findElement(By.cssSelector(TestBase.OR.getProperty("tr_signIn_username_css"))).clear();
		ob.findElement(By.cssSelector(TestBase.OR.getProperty("tr_signIn_username_css"))).sendKeys(userName);
		ob.findElement(By.cssSelector(TestBase.OR.getProperty("tr_signIn_password_css"))).sendKeys(password);
	}
	
	public static void clickLogin() throws InterruptedException {
		ob.findElement(By.cssSelector(TestBase.OR.getProperty("tr_signIn_login_css"))).click();
		Thread.sleep(6000);
	}
	
	public static void searchArticle(String article) throws InterruptedException {
		//waitUntilElementClickable("Watchlist");
		System.out.println("article name-->"+article);
		ob.findElement(By.cssSelector(TestBase.OR.getProperty("tr_search_box_css"))).sendKeys(article);
		Thread.sleep(4000);
		List<WebElement> searchElements=ob.findElement(By.cssSelector("ul[class='dropdown-menu ng-isolate-scope']")).findElements(By.tagName("li"));
		System.out.println("list of articles-->"+searchElements.size());
		for(WebElement searchElement:searchElements){
			String articleName=searchElement.findElement(By.tagName("a")).getText();
			System.out.println("article name-->"+articleName);
			if(searchElement.findElement(By.tagName("a")).getText().equalsIgnoreCase(articleName)){
				WebElement element = searchElement.findElement(By.tagName("a"));
				JavascriptExecutor executor = (JavascriptExecutor)ob;
				executor.executeScript("arguments[0].click();", element);
				//searchElement.findElement(By.tagName("a")).click();
				Thread.sleep(4000);
				break;
			}//if
		}//for
		Thread.sleep(4000);
	}
	
	public static void chooseArticle(String linkName) throws InterruptedException {
		ob.findElement(By.linkText(linkName)).click();
		waitUntilTextPresent(TestBase.OR.getProperty("tr_authoring_header_css"), linkName);
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
	
	
	public static void logOutApp() {
		ob.findElement(By.cssSelector(OR.getProperty("tr_profile_dropdown_css"))).click();
		AuthoringProfileCommentsTest.waitUntilText("Sign out");
		ob.findElement(By.linkText("Sign out")).click();
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_LOGIN_BUTTON_CSS);
		
	}
	


}
