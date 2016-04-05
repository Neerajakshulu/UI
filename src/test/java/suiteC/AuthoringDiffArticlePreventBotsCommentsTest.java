package suiteC;

import java.io.PrintWriter;
import java.io.StringWriter;

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
import util.ExtentManager;
import util.TestUtil;

/**
 * Class for Performing Authoring prevent comment flooding by bots with different article
 * @author UC202376
 *
 */
public class AuthoringDiffArticlePreventBotsCommentsTest extends TestBase {
	
	String runmodes[]=null;
	static int count=-1;
	
	static boolean fail=false;
	static boolean skip=false;
	static int status=1;
	
	static int time=30;
	PageFactory pf=new PageFactory();
	
	@BeforeTest
	public void beforeTest() throws Exception{ extent = ExtentManager.getReporter(filePath);
		String var=xlRead2(returnExcelPath('C'),this.getClass().getSimpleName(),1);
		test = extent
				.startTest(var,
						"Verify that prevention of comment flooding by bots with different articles works as expected")
				.assignCategory("Suite C");
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
	@Parameters({"username","password","article","completeArticle","addComments"})
	public void validateDiffArticlePreventBotsComments(String username,String password,
			String article,String completeArticle, String addComments) throws Exception  {
		try {
			waitForTRHomePage();
			pf.getLoginTRInstance(ob).enterTRCredentials(username, password);
			pf.getLoginTRInstance(ob).clickLogin();
			searchArticle(article);
			chooseArticle(completeArticle);
			
			pf.getAuthoringInstance(ob).enterArticleComment(addComments);
			pf.getAuthoringInstance(ob).clickAddCommentButton();
			
			searchArticle("biology");
			waitForAllElementsToBePresent(ob, By.xpath(OR.getProperty("searchResults_links")), 40);
			waitForAjax(ob);
			pf. getSearchResultsPageInstance(ob).clickOnArticleTab();
			ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).click();
			
			//ob.navigate().refresh();
			pf.getAuthoringInstance(ob).enterArticleComment(addComments);
			pf.getAuthoringInstance(ob).clickAddCommentButton();
			pf.getAuthoringInstance(ob).validatePreventBotComment();
			
			pf.getLoginTRInstance(ob).logOutApp();
			closeBrowser();
		} catch (Exception e) {
			test.log(LogStatus.FAIL,"UnExpected Error");
			//print full stack trace
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO,errors.toString());
			ErrorUtil.addVerificationFailure(e);
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
					this.getClass().getSimpleName() + "_Prevent_Bots_functionaliy_not_giving_expected_Result")));
			closeBrowser();
		}
	}
	
	
	@AfterTest
	public void reportTestResult() {
		
		extent.endTest(test);
		
	/*	if(status==1)
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases", TestUtil.getRowNum(suiteCxls,this.getClass().getSimpleName()), "PASS");
		else if(status==2)
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases", TestUtil.getRowNum(suiteCxls,this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases", TestUtil.getRowNum(suiteCxls,this.getClass().getSimpleName()), "SKIP");*/
	}
	
	/**
	 * Method for wait TR Home Screen
	 * @throws InterruptedException 
	 */
	public  void waitForTRHomePage() throws InterruptedException {
		waitForPageLoad(ob);
		pf.getBrowserWaitsInstance(ob).waitUntilText("Sign in with Project Neon");
	}
	
	
	public void searchArticle(String article) throws InterruptedException {
		ob.findElement(By.cssSelector(OR.getProperty("tr_search_box_css"))).clear();
		ob.findElement(By.cssSelector(OR.getProperty("tr_search_box_css"))).sendKeys(article);
		jsClick(ob,ob.findElement(By.cssSelector("i[class='webui-icon webui-icon-search']")));
		waitForAjax(ob);
	}
	
	public void chooseArticle(String linkName) throws InterruptedException {
		BrowserWaits.waitForAllElementsToBePresent(ob, By.xpath(OR.getProperty("searchResults_links")), 180);
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
