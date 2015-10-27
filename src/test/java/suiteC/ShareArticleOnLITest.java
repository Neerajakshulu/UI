package suiteC;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.BrowserAction;
import util.BrowserWaits;
import util.ErrorUtil;
import util.OnePObjectMap;
import util.TestUtil;

/**
 * Class for Performing Article Sharing on LinkedIn 
 * @author UC202376
 *
 */
public class ShareArticleOnLITest extends TestBase {
	
	String runmodes[]=null;
	static int count=-1;
	
	static boolean fail=false;
	static boolean skip=false;
	static int status=1;
	
	static int time=30;
	
	
	@BeforeTest
	public void beforeTest() {
		test = extent.startTest(this.getClass().getSimpleName(),"Article Sharing on LinkedIn").assignCategory("Suite C");
		runmodes=TestUtil.getDataSetRunmodes(suiteCxls, this.getClass().getSimpleName());
	}
	
			
	@Test
	public void testOpenApplication() throws Exception  {
		boolean suiteRunmode=TestUtil.isSuiteRunnable(suiteXls, "C Suite");
		boolean testRunmode=TestUtil.isTestCaseRunnable(suiteCxls,this.getClass().getSimpleName());
		boolean master_condition=suiteRunmode && testRunmode;
		
		if(!master_condition) {
			status=3;
			test.log(LogStatus.SKIP, "Skipping test case "+this.getClass().getSimpleName()+" as the run mode is set to NO");
			throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports
		}

		// test the runmode of current dataset
		count++;
		if(!runmodes[count].equalsIgnoreCase("Y")) {
			test.log(LogStatus.INFO, "Runmode for test set data set to no "+count);
			skip=true;
			throw new SkipException("Runmode for test set data set to no "+count);
		}
		test.log(LogStatus.INFO,this.getClass().getSimpleName()+" execution starts for data set #"+ count+"--->");
				//selenium code
				openBrowser();
				clearCookies();
				maximizeWindow();
				ob.navigate().to(System.getProperty("host"));
	}
	
	@Test(dependsOnMethods="testOpenApplication")
	@Parameters({"username","password","article","completeArticle"})
	public void chooseArtilce(String username,String password,
			String article,String completeArticle) throws Exception  {
		try {
			waitForTRHomePage();
			LoginTR.enterTRCredentials(username, password);
			LoginTR.clickLogin();
			searchArticle(article);
			chooseArticle(completeArticle);
			
		} catch (Exception e) {
			test.log(LogStatus.FAIL,"UnExpected Error");
			//print full stack trace
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO,errors.toString());
			ErrorUtil.addVerificationFailure(e);
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
					this.getClass().getSimpleName() + "_Unable_to_share_the_Article")));
			closeBrowser();
		}
	}
	
	

	@Test(dependsOnMethods="chooseArtilce")
	@Parameters({"liusername","lipassword"})
	public void shareOnTwitter(String liusername, String lipassword) throws Exception {
		try {
			test.log(LogStatus.INFO,"Sharing Article on LinkedIn");
			BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_CSS);
			Thread.sleep(3000);
			
			String PARENT_WINDOW=ob.getWindowHandle();
			BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_ON_LI_LINK);
			Thread.sleep(6000);
			
			Set<String> child_window_handles= ob.getWindowHandles();
			System.out.println("window hanles-->"+child_window_handles.size());
			 for(String child_window_handle:child_window_handles) {
				 if(!child_window_handle.equals(PARENT_WINDOW)) {
					 ob.switchTo().window(child_window_handle);
					 maximizeWindow();
					 System.out.println("child window--->"+ob.getTitle());
					 BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_USERNAME_CSS);
					 BrowserAction.enterFieldValue(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_USERNAME_CSS, liusername);
					 BrowserAction.enterFieldValue(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_PASSWORD_CSS, lipassword);
					 BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_LOGIN_CSS);
					 Thread.sleep(6000);
					 
					 try {
						Set<String> child_sub_window_handles= ob.getWindowHandles();
						// System.out.println("child sub window--->"+child_sub_window_handles.size());
						 for(String sub_window:child_sub_window_handles) {
							 if(!sub_window.equals(child_window_handle)) {
								 System.out.println("sub window--->"+ob.getTitle());
								 BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_SHARE_CSS);
								 BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_SHARE_CSS);
								 Thread.sleep(3000);
								 BrowserWaits.waitUntilText("Great! You have successfully shared this update.");
							 }
						 }
					} catch (Exception e) {
						test.log(LogStatus.INFO, "Unable to login into LI account from Remote Machines");
					}
					 ob.close();
					 ob.switchTo().window(PARENT_WINDOW);
				 }
			 }
			
			LoginTR.logOutApp();
			closeBrowser();
			
		} catch (Exception e) {
			test.log(LogStatus.FAIL,"UnExpected Error");
			status=2;//excel
			//print full stack trace
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO,errors.toString());
			ErrorUtil.addVerificationFailure(e);
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
					this.getClass().getSimpleName() + "_Unable_to_Tweet_Article_On_Twitter")));
			closeBrowser();
		}
		
	}
	
	
	@AfterTest
	public void reportTestResult() {
		
		extent.endTest(test);
		
		if(status==1)
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases", TestUtil.getRowNum(suiteCxls,this.getClass().getSimpleName()), "PASS");
		else if(status==2)
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases", TestUtil.getRowNum(suiteCxls,this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases", TestUtil.getRowNum(suiteCxls,this.getClass().getSimpleName()), "SKIP");
	}
	
	/**
	 * Method for wait TR Home Screen
	 * @throws InterruptedException 
	 */
	public static void waitForTRHomePage() throws InterruptedException {
		Thread.sleep(4000);
		BrowserWaits.waitUntilText("Sign in with Project Neon");
	}
	
	
	public static void searchArticle(String article) throws InterruptedException {
		System.out.println("article name-->"+article);
		ob.findElement(By.cssSelector(OR.getProperty("tr_search_box_css"))).sendKeys(article);
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
		Thread.sleep(8000);//providing more while script is getting failed in saucelabs
		waitUntilTextPresent(OR.getProperty("tr_authoring_header_css"), linkName);
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
	
}
