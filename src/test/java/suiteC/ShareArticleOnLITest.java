package suiteC;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import pages.PageFactory;
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
	PageFactory pf=new PageFactory();
	
	
	@BeforeTest
	public void beforeTest() throws Exception {
		String var=xlRead2(returnExcelPath('C'),this.getClass().getSimpleName(),1);
		test = extent.startTest(var,"Verify that user is able to add an article on LinkedIn").assignCategory("Suite C");
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
			pf.getLoginTRInstance(ob).enterTRCredentials(username, password);
			pf.getLoginTRInstance(ob).clickLogin();
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
			waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_CSS.toString()), 80);
			jsClick(ob, ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_CSS.toString())));
			//pf.getBrowserActionInstance(ob).click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_CSS);
			String PARENT_WINDOW=ob.getWindowHandle();
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_ON_LI_LINK);
			waitForElementTobeVisible(ob, By.cssSelector("div[class='modal-dialog']"), 40);
			ob.findElement(By.cssSelector("div[class='modal-footer ng-scope'] button[data-ng-click='shareModal.close()']")).click();
			waitForNumberOfWindowsToEqual(ob, 2);
			Set<String> child_window_handles= ob.getWindowHandles();
			System.out.println("window hanles-->"+child_window_handles.size());
			 for(String child_window_handle:child_window_handles) {
				 if(!child_window_handle.equals(PARENT_WINDOW)) {
					 ob.switchTo().window(child_window_handle);
					 maximizeWindow();
					 System.out.println("child window--->"+ob.getTitle());
					 pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_USERNAME_CSS);
					 pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_USERNAME_CSS, liusername);
					 pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_PASSWORD_CSS, lipassword);
					 pf.getBrowserActionInstance(ob).click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_LOGIN_CSS);
					 ob.switchTo().window(PARENT_WINDOW);
					waitForElementTobeVisible(ob, By.cssSelector("div[class='modal-footer ng-scope'] button[data-ng-click='shareModal.cancel()']"), 40);
					 ob.findElement(By.cssSelector("div[class='modal-footer ng-scope'] button[data-ng-click='shareModal.cancel()']")).click();
				 }
			 }
			 Thread.sleep(5000);
			logout();
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
	public  void waitForTRHomePage() throws InterruptedException {
		Thread.sleep(4000);
		pf.getBrowserWaitsInstance(ob).waitUntilText("Sign in with Project Neon");
	}
	
	
	public void searchArticle(String article) throws InterruptedException {
		ob.findElement(By.cssSelector(OR.getProperty("tr_search_box_css"))).sendKeys(article);
		jsClick(ob,ob.findElement(By.cssSelector("i[class='webui-icon webui-icon-search']")));
		waitForAjax(ob);
	}
	
	public void chooseArticle(String linkName) throws InterruptedException {
		BrowserWaits.waitForAllElementsToBePresent(ob, By.xpath(OR.getProperty("searchResults_links")), 90);
		jsClick(ob,ob.findElement(By.xpath(OR.getProperty("searchResults_links"))));
	}
	
	public  void waitUntilTextPresent(String locator,String text){
		try {
			WebDriverWait wait = new WebDriverWait(ob, time);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(locator),text));
		} catch (TimeoutException e) {
			throw new TimeoutException("Failed to find element Locator , after waiting for " + time
					+ "ms");
		}
	}
	
}
