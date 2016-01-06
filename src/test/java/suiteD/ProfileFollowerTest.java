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
import util.BrowserWaits;
import util.ErrorUtil;
import util.TestUtil;

public class ProfileFollowerTest extends TestBase {
	private static final String PASSWORD = "Welcome123";
	private static final String USER_NAME = "kavya.revanna@thomsonreuters.com";
	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		String var=xlRead2(returnExcelPath('D'),this.getClass().getSimpleName(),1);
		test = extent.startTest(var,"Verify that user is able to test for count of users following me").assignCategory("Suite D");
	}
	
	
	@Test
	@Parameters({ "username", "password" })
	public void testLoginTRAccount(String username, String password) throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "D Suite");
		boolean testRunmode = TestUtil.isTestCaseRunnable(suiteDxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {
			status = 3;
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}

		
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts");

		try {
			openBrowser();
			clearCookies();
			maximizeWindow();

			ob.navigate().to(System.getProperty("host"));
			LoginTR.waitForTRHomePage();
			LoginTR.enterTRCredentials(username, password);
			LoginTR.clickLogin();
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something Unexpected");// extent reports
			// print stack trace
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());
			ErrorUtil.addVerificationFailure(t);// testng
			status = 2;// excel
			test.log(LogStatus.INFO, "Snapshot below: "
					+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "_Login is not done")));
			closeBrowser();
		}
	}

	@Test(dependsOnMethods="testLoginTRAccount")
	public void getFollowers() throws Exception {
		try {
			test.log(LogStatus.INFO,"get users who are following me-My Followers");
			HeaderFooterLinksPage.clickProfileImage();
			ProfilePage.clickProfileLink();
			ProfilePage.getFollowersCount();
			test.log(LogStatus.INFO,"Logout from the application");
			LoginTR.logOutApp();
		} catch (Throwable t) {
			test.log(LogStatus.FAIL,"Something Unexpected");
			//print full stack trace
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO,errors.toString());
			ErrorUtil.addVerificationFailure(t);
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_followers_count")));
			closeBrowser();
		}
		
	}
	
	@Test(dependsOnMethods="getFollowers")
	public void loginwithOtherUser() throws Exception {
		try {
			test.log(LogStatus.INFO,"login with different user");
			LoginTR.waitForTRHomePage();
			LoginTR.enterTRCredentials(USER_NAME, PASSWORD);
			LoginTR.clickLogin();
		} catch (Throwable t) {
			test.log(LogStatus.FAIL,"Something Unexpected");
			//print full stack trace
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO,errors.toString());
			ErrorUtil.addVerificationFailure(t);
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_login_not_done")));
			closeBrowser();
		}
		
	}
	
	@Test(dependsOnMethods="loginwithOtherUser")
	public void followUserAndLogout() throws Exception {
		try {
			test.log(LogStatus.INFO,"Follow/unfollow other user");
			ob.navigate().to("https://dev-stable.1p.thomsonreuters.com/#/profile/f8606cb6-8765-4ad4-878b-baf1175b9a52");
			BrowserWaits.waitTime(10);
			ProfilePage.followOtherProfileFromProfilePage();
			test.log(LogStatus.INFO,"Logout from the application and login with tested user");
			LoginTR.logOutApp();
		} catch (Throwable t) {
			test.log(LogStatus.FAIL,"Something Unexpected");
			//print full stack trace
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO,errors.toString());
			ErrorUtil.addVerificationFailure(t);
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_followers_logout")));
			closeBrowser();
		}
		
	}
	
	@Test(dependsOnMethods="followUserAndLogout")
	@Parameters({ "username", "password" })
	public void validateFollowersCount(String username, String password) throws Exception {
		try {
			test.log(LogStatus.INFO,"login with user");
			LoginTR.waitForTRHomePage();
			LoginTR.enterTRCredentials(username, password);
			LoginTR.clickLogin();
			test.log(LogStatus.INFO,"Go to his own profile page");
			HeaderFooterLinksPage.clickProfileImage();
			ProfilePage.clickProfileLink();
			test.log(LogStatus.INFO,"validate profile followers count, should increase or decrease");
			ProfilePage.validateFollowersCount();
			LoginTR.logOutApp();
			closeBrowser();
			test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends");
		} catch (Throwable t) {
			test.log(LogStatus.FAIL,"Something Unexpected");
			//print full stack trace
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO,errors.toString());
			ErrorUtil.addVerificationFailure(t);
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_followers_validation_count")));
			closeBrowser();
		}
		
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		if (status == 1)
			TestUtil.reportDataSetResult(suiteDxls, "Test Cases",
					TestUtil.getRowNum(suiteDxls, this.getClass().getSimpleName()), "PASS");
		else if (status == 2)
			TestUtil.reportDataSetResult(suiteDxls, "Test Cases",
					TestUtil.getRowNum(suiteDxls, this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteDxls, "Test Cases",
					TestUtil.getRowNum(suiteDxls, this.getClass().getSimpleName()), "SKIP");

	}

	

}
