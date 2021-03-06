package suiteD;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import pages.HeaderFooterLinksPage;
import pages.ProfilePage;
import suiteC.LoginTR;
import util.ErrorUtil;
import util.TestUtil;

public class TopicTypeaheadValidationTest extends TestBase {
	
	String runmodes[]=null;
	static int count=-1;
	
	static boolean fail=false;
	static boolean skip=false;
	static int status=1;
	
	@BeforeTest
	public void beforeTest() throws Exception {
		String var=xlRead2(returnExcelPath('D'),this.getClass().getSimpleName(),1);
		test = extent.startTest(var,
				"Verify that topic typeahead options should display while enter min 2 characters").assignCategory("Suite D");
		runmodes=TestUtil.getDataSetRunmodes(suiteDxls, this.getClass().getSimpleName());
	}
	
	/**
	 * Method for wait TR Login Screen		
	 * @throws Exception, When TR Login screen not displayed
	 */
	@Test
	@Parameters({"username","password"})
	public void testLoginTRAccount(String username,String password) throws Exception  {
		
		boolean suiteRunmode=TestUtil.isSuiteRunnable(suiteXls, "D Suite");
		boolean testRunmode=TestUtil.isTestCaseRunnable(suiteDxls,this.getClass().getSimpleName());
		boolean master_condition=suiteRunmode && testRunmode;
		
		if(!master_condition) {
			status=3;
			test.log(LogStatus.SKIP, "Skipping test case "+this.getClass().getSimpleName()+" as the run mode is set to NO");
			throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports
		}
		
		test.log(LogStatus.INFO,this.getClass().getSimpleName()+" execution starts ");
		
				try {
					openBrowser();
					clearCookies();
					maximizeWindow();
					
					ob.navigate().to(System.getProperty("host"));
					LoginTR.waitForTRHomePage();
					LoginTR.enterTRCredentials(username, password);
					LoginTR.clickLogin();
				} catch (Throwable t) {
					test.log(LogStatus.FAIL,"Something Unexpected");
					//print full stack trace
					StringWriter errors = new StringWriter();
					t.printStackTrace(new PrintWriter(errors));
					test.log(LogStatus.INFO,errors.toString());
					ErrorUtil.addVerificationFailure(t);
					status=2;//excel
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_TR_Login_Not_happended")));
					closeBrowser();
				}
	}
	
	
	@Test(dependsOnMethods="testLoginTRAccount")
	@Parameters({"oneChar","twoChar"})
	public void topicTypeaheadValidation(String oneChar,String twoChar) throws Exception  {
			try {
				test.log(LogStatus.INFO,"go to user profile page");
				HeaderFooterLinksPage.clickProfileImage();
				ProfilePage.clickProfileLink();
				test.log(LogStatus.INFO,"enter two characters and verify topic typeahead options should display");
				ProfilePage.topicTypeaheadOptionsMinChars(oneChar, twoChar);
				test.log(LogStatus.INFO,this.getClass().getSimpleName()+" Test execution ends ");
				LoginTR.logOutApp();
				closeBrowser();
			} catch (Throwable t) {
				test.log(LogStatus.FAIL,"Something Unexpected");
				//print full stack trace
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO,errors.toString());
				ErrorUtil.addVerificationFailure(t);
				status=2;//excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_add_topic_not_done")));
				closeBrowser();
			}
	}
	
	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);
		if(status==1)
			TestUtil.reportDataSetResult(suiteDxls, "Test Cases", TestUtil.getRowNum(suiteDxls,this.getClass().getSimpleName()), "PASS");
		else if(status==2)
			TestUtil.reportDataSetResult(suiteDxls, "Test Cases", TestUtil.getRowNum(suiteDxls,this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteDxls, "Test Cases", TestUtil.getRowNum(suiteDxls,this.getClass().getSimpleName()), "SKIP");
	}
}
