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
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
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
 * Class for Performing Authoring Comments Validation with Unsupported HTML tags except <br>,<b>,<i> and <p>
 * @author UC202376
 *
 */
public class UnsupportedTagsCommentsTest extends TestBase {
	
	String runmodes[]=null;
	static int count=-1;
	
	static boolean fail=false;
	static boolean skip=false;
	static int status=1;
	static boolean master_condition;
	
	static int time=30;
	
	
	@BeforeTest
	public void beforeTest() {
		test = extent
				.startTest(this.getClass().getSimpleName(),
						"Authoring Artilce Comment Validation with Unsupported HTML Tags").assignCategory("Suite C");
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
					this.getClass().getSimpleName() + "_Article_Search_not_happening")));
			//closeBrowser();
		}
	}
	
	

	@Test(dependsOnMethods="chooseArtilce",dataProvider="getTestData")
	public void unSupportedTagsCommentsCheck(String htmlTags, String errorMessage) throws Exception {
		try {
			
			test.log(LogStatus.INFO,this.getClass().getSimpleName()+"  UnSupported HTML Tags execution starts for data set #"+ (count+1)+"--->");
			
			Authoring.enterArticleComments(htmlTags);
			Authoring.clickAddCommentButton();
			
			String unSupporteTagErrorMessage=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_AUTHORING_PREVENT_BOT_COMMENT_CSS).getText();
			//System.out.println("Profanity Word Error Message--->"+profanityErrorMessage);
			BrowserWaits.waitUntilText(unSupporteTagErrorMessage);
			
			//Assert.assertEquals(unSupporteTagErrorMessage, errorMessage);
			if(!unSupporteTagErrorMessage.equalsIgnoreCase(errorMessage)){
				throw new Exception("UnSupported_HTML_tags_doesnot_allow_comments_validation");
			}
			
		} catch (Exception e) {
			test.log(LogStatus.FAIL,"UnExpected Error");
			status=2;
			fail=true;
			//print full stack trace
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO,errors.toString());
			ErrorUtil.addVerificationFailure(e);
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
					this.getClass().getSimpleName() + "_UnSupported_HTML_tags_doesnot_allow_comments_validation")));
			//closeBrowser();
		} finally {
			reportDataSetResult();
			++count;
		}
		
	}
	
	public void reportDataSetResult() {
		if(skip) {
			TestUtil.reportDataSetResult(suiteCxls, this.getClass().getSimpleName(), count+2, "SKIP");
		}
		
		else if(fail) {
			status=2;
			TestUtil.reportDataSetResult(suiteCxls, this.getClass().getSimpleName(), count+2, "FAIL");
		}
		else {
			TestUtil.reportDataSetResult(suiteCxls, this.getClass().getSimpleName(), count+2, "PASS");
		}
			
		
		
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
		
		if(master_condition)
		closeBrowser();
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
	
	
	@DataProvider
	public Object[][] getTestData() {
		return TestUtil.getData(suiteCxls, this.getClass().getSimpleName()) ;
	}
	
}
