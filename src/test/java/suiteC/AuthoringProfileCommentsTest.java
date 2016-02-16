package suiteC;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.TestUtil;

public class AuthoringProfileCommentsTest extends TestBase {
	
	String runmodes[]=null;
	static int count=-1;
	static int totalProfileCommentsBeforeAdd=0;
	static int totalProfileCommentsAfterAdd=0;
	static int time=30;
	
	static boolean fail=false;
	static boolean skip=false;
	static int status=1;
	
	    // Checking whether this test case should be skipped or not
		@BeforeTest
		public void beforeTest() throws Exception {
			String var=xlRead2(returnExcelPath('C'),this.getClass().getSimpleName(),1);
			test = extent.startTest(var, "Verify that comments added by the neon user are listed in profile page of the user").assignCategory("Suite C");
			//load the run modes of the tests			
			runmodes=TestUtil.getDataSetRunmodes(suiteCxls, this.getClass().getSimpleName());
		}
	
			
	@Test
	@Parameters({"username","password","article","completeArticle","addComments"})
	public void testLoginTRAccount(String username,String password,
			String article,String completeArticle, String addComments) throws Exception  {
		
			boolean suiteRunmode=TestUtil.isSuiteRunnable(suiteXls, "C Suite");
			boolean testRunmode=TestUtil.isTestCaseRunnable(suiteCxls,this.getClass().getSimpleName());
			boolean master_condition=suiteRunmode && testRunmode;
			
			if(!master_condition) {
				status=3;
				test.log(LogStatus.SKIP, "Skipping test case "+this.getClass().getSimpleName()+" as the run mode is set to NO");
				throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports
			}
			
			
			// test the run mode of current dataset
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
						try {
						AuthoringTest.waitForTRHomePage();
						Thread.sleep(15000);
						performAuthoringCommentOperations(username, password, article, completeArticle, addComments);
						closeBrowser();
					} catch (Throwable t) {
						test.log(LogStatus.FAIL,"Something UnExpected");
						//print full stack trace
						StringWriter errors = new StringWriter();
						t.printStackTrace(new PrintWriter(errors));
						test.log(LogStatus.INFO,errors.toString());
						ErrorUtil.addVerificationFailure(t);
						status=2;
						test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_profile_data_updation_not_done")));//screenshot
						closeBrowser();
					}
	}
	
	public void performAuthoringCommentOperations(String username,String password,
			String article,String completeArticle, String addComments) throws Exception  {
		
		AuthoringTest.enterTRCredentials(username, password);
		AuthoringTest.clickLogin();
		//Get Total No.of comments
		totalProfileCommentsBeforeAdd=getProfleComments();
		System.out.println("comments Before-->"+totalProfileCommentsBeforeAdd);
		System.out.println();
		LoginTR.searchArticle(article);
		LoginTR.chooseArticle(completeArticle);
		//Enter Article Comments
		Authoring.enterArticleComment(addComments);
		Authoring.clickAddCommentButton();
		Authoring.validateCommentAdd();
		Authoring.validateViewComment(addComments);
		totalProfileCommentsAfterAdd = getProfleComments();
		System.out.println("comments After-->"+totalProfileCommentsAfterAdd);
		//Validate Comments count increased or not
		if(!(totalProfileCommentsAfterAdd>totalProfileCommentsBeforeAdd)) {
			status=2;
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_Comments count not updated in My Profile Screen")));
			throw new Exception("Comments count is not updated in My Profile Screen");
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
	
	
	public int getProfleComments() throws InterruptedException  {
		waitForAjax(ob);
		jsClick(ob,ob.findElement(By.cssSelector(TestBase.OR.getProperty("tr_profile_dropdown_css"))));
		waitUntilText("Profile");
		jsClick(ob,ob.findElement(By.linkText(TestBase.OR.getProperty("tr_profile_link"))));
		waitUntilText("Comments"); 
		scrollingToElementofAPage();
		String commentsCount=ob.findElement(By.cssSelector("a[data-event-category='profilecomments'] span[class='ng-binding']:nth-child(2)")).getText();
		int totalComments=Integer.parseInt(commentsCount.replace(",", "").trim());
		return totalComments;
	}
	
	/**
	 * wait for until expected text present
	 * @param text
	 */
	public  static void waitUntilText(final String text) {
		try {
			(new WebDriverWait(ob, time))
					.until(new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver d) {
							try {
								return Boolean.valueOf(d.getPageSource()
										.contains(text));
							} catch (Exception e) {
								return Boolean.valueOf(false);
							}
						}
					});
		} catch (TimeoutException te) {
			throw new TimeoutException("Failed to find text: " + text
					+ ", after waiting for " + time + "ms");
		}
	}
	
	/**
	 * Method for wait until Element is Present
	 * @param locator
	 * @throws Exception, When NoSuchElement Present
	 */
	public  static void IsElementPresent(String locator) throws Exception {
		try{
		 count = ob.findElements(By.cssSelector(locator)).size();
		} catch (NoSuchElementException ne) {
			throw new NoSuchElementException("Failed to find element [Locator = {"
					+ locator+ "}]");
		}
		
		if(!(count > 0)){
			throw new Exception("Unable to find Element...Element is not present");
		}
	}
}
