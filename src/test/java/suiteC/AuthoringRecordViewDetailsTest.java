package suiteC;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
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

public class AuthoringRecordViewDetailsTest extends TestBase {
	
	String runmodes[]=null;
	static int count=-1;
	
	static boolean fail=false;
	static boolean skip=false;
	static int status=1;
	
	@BeforeTest
	public void beforeTest() {
		test = extent
				.startTest(this.getClass().getSimpleName(),
						"Verify that details link in article record view is redirected to full record view of WOS")
				.assignCategory("Suite C");
		runmodes=TestUtil.getDataSetRunmodes(suiteCxls, this.getClass().getSimpleName());
	}
	
	/**
	 * Method for validating TR Login Screen		
	 * @throws Exception, When TR Login Home screen not displaying
	 */
	@Test
	public void testAuthoringTestAccount() throws Exception  {
		
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
		
				openBrowser();
				clearCookies();
				maximizeWindow();
				
				ob.navigate().to(System.getProperty("host"));
				AuthoringTest.waitForTRHomePage();
	}
	
	@Test(dependsOnMethods="testAuthoringTestAccount")
	@Parameters({"username","password","article","completeArticle"})
	public void backToProjectNeonValiation(String username,String password,
			String article,String completeArticle) throws Exception  {

		try {
			AuthoringTest.enterTRCredentials(username, password);
			AuthoringTest.clickLogin();
			AuthoringTest.searchArticle(article);
			AuthoringTest.chooseArticle(completeArticle);
			recordViewDetailsLinkValidation();
			test.log(LogStatus.INFO,this.getClass().getSimpleName()+" Test execution ends ");
			LoginTR.logOutApp();
			closeBrowser();
		} catch (Throwable t) {
			test.log(LogStatus.FAIL,"Something UnExpected");
			//print full stack trace
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO,errors.toString());
			ErrorUtil.addVerificationFailure(t);
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_Page is not moving back from Back To Project Neon")));//screenshot
			closeBrowser();
		}
	}
	
	/**
	 * Method for Click Details link in Article Record View Page
	 * @throws Exception, When Details link Not Working
	 */
	public void recordViewDetailsLinkValidation() throws Exception {
		waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.HOME_PROJECT_NEON_APP_RECORD_VIEW_DETALIS_XPATH.toString()), 40);
		jsClick(ob, ob.findElement(By.xpath(OnePObjectMap.HOME_PROJECT_NEON_APP_RECORD_VIEW_DETALIS_XPATH.toString())));
		//BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_APP_RECORD_VIEW_DETALIS_XPATH);
		waitForNumberOfWindowsToEqual(ob, 2);
		switchToNewWindow(ob);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_APP_RECORD_VIEW_DETALIS_BACKTOPN_CSS);
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_APP_RECORD_VIEW_DETALIS_BACKTOPN_CSS);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_APP_RECORD_VIEW_DETALIS_XPATH);
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
	
	
}
