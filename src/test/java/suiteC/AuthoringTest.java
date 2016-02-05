package suiteC;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.BrowserWaits;
import util.ErrorUtil;
import util.TestUtil;

public class AuthoringTest extends TestBase {
	
	String runmodes[]=null;
	static int count=-1;
	
	static boolean fail=false;
	static boolean skip=false;
	static int status=1;
	
	static int time=15;
	
	
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		String var=xlRead2(returnExcelPath('C'),this.getClass().getSimpleName(),1);
		test = extent.startTest(var, "Verify that user Is able to comment on any article and validate the comment count increment").assignCategory("Suite C");
		runmodes=TestUtil.getDataSetRunmodes(suiteCxls, this.getClass().getSimpleName());
		System.out.println("Run modes-->"+runmodes.length);
	}
	
			
	@Test
	public void testLoginTRAccount() throws Exception  {
		
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
	
	@Test(dependsOnMethods="testLoginTRAccount",dataProvider="getTestData")
	public void performAuthoringCommentOperations(String username,String password,
			String article,String completeArticle, String addComments) throws Exception  {
		try {
			waitForTRHomePage();
			enterTRCredentials(username, password);
			clickLogin();
			searchArticle(article);
			chooseArticle(completeArticle);
			
			Authoring.enterArticleComment(addComments);
			Authoring.clickAddCommentButton();
			Authoring.validateCommentAdd();
			Authoring.validateViewComment(addComments);
			Authoring.updateComment("comment updated");
			validateUpdatedComment("comment updated");
			closeBrowser();
		} catch (Exception e) {
			test.log(LogStatus.FAIL,"Error: Login not happended");
			//print full stack trace
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO,errors.toString());
			ErrorUtil.addVerificationFailure(e);
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_login_not_done")));//screenshot
			closeBrowser();
		}
	}
	
	
	
	@AfterMethod
	public void reportDataSetResult() {
		if(skip)
			TestUtil.reportDataSetResult(suiteCxls, this.getClass().getSimpleName(), count+2, "SKIP");
		
		else if(fail) {
			
			status=2;
			TestUtil.reportDataSetResult(suiteCxls, this.getClass().getSimpleName(), count+2, "FAIL");
		}
		else
			TestUtil.reportDataSetResult(suiteCxls, this.getClass().getSimpleName(), count+2, "PASS");
		
		
		skip=false;
		fail=false;

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
		
		//closeBrowser();
	}
	

	@DataProvider
	public Object[][] getTestData() {
		return TestUtil.getData(suiteCxls, this.getClass().getSimpleName()) ;
	}
	
	
	/**
	 * Method for wait TR Home Screen
	 * @throws InterruptedException 
	 */
	public static void waitForTRHomePage() throws InterruptedException {
		Thread.sleep(4000);
		//ob.waitUntilTextPresent(TestBase.OR.getProperty("tr_home_signInwith_projectNeon_css"),"Sign in with Project Neon");
	}
	
	/**
	 * Method for enter Application Url and enter Credentials
	 */
	public static void enterTRCredentials(String userName, String password) {
		ob.findElement(By.cssSelector(OR.getProperty("tr_home_signInwith_projectNeon_css"))).click();
		new TestBase().waitForElementTobeVisible(ob,By.cssSelector(TestBase.OR.getProperty("tr_signIn_username_css")), 60);
		ob.findElement(By.cssSelector(TestBase.OR.getProperty("tr_signIn_username_css"))).clear();
		ob.findElement(By.cssSelector(TestBase.OR.getProperty("tr_signIn_username_css"))).sendKeys(userName);
		ob.findElement(By.cssSelector(TestBase.OR.getProperty("tr_signIn_password_css"))).sendKeys(password);
	}
	
	public static void clickLogin() throws InterruptedException {
		ob.findElement(By.cssSelector(TestBase.OR.getProperty("tr_signIn_login_css"))).click();
		Thread.sleep(6000);
		//waitUntilTextPresent(TestBase.OR.getProperty("tr_home_css"), "Home");
		//waitUntilElementClickable("Home");
	}
	
	public static void searchArticle(String article) throws InterruptedException {
		ob.findElement(By.cssSelector(OR.getProperty("tr_search_box_css"))).sendKeys(article);
		Thread.sleep(4000);
		
		ob.findElement(By.cssSelector("i[class='webui-icon webui-icon-search']")).click();
		Thread.sleep(4000);
	}
	
	public static void chooseArticle(String linkName) throws InterruptedException {
		BrowserWaits.waitForElementTobeVisible(ob, By.linkText(linkName), 90);
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
	
	public  static void validateUpdatedComment(String updatedComments) throws Exception  {
		scrollingToElementofAPage();
		String commentText=ob.findElements(By.cssSelector("div[class='col-xs-12 watching-article-comments']")).get(0).getText();
		System.out.println("Commentary Text-->"+commentText);
		if(!(commentText.contains(updatedComments) && commentText.contains("EDITED")))  {
			//TestBase.test.log(LogStatus.INFO, "Snapshot below: " + TestBase.test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"Entered comment not added")));
			status=2;
			throw new Exception("Updated "+updatedComments+" not present");
		}
	}
	
}
