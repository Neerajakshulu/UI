package suiteD;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import pages.PageFactory;
import suiteC.LoginTR;
import util.ErrorUtil;
import util.TestUtil;

public class PublishedAPostTimeStampTest extends TestBase{

	static int status = 1;
	static String  postTitle;
	static List<String> profileDetails;
	PageFactory pf = new PageFactory();

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		String var=xlRead2(returnExcelPath('D'),this.getClass().getSimpleName(),1);
		test = extent.startTest(var, "Verify that Created Post displayed as per System date")
				.assignCategory("Suite D");

	}

	@Test
	@Parameters({"username","password"})
	public void testLoginTRAccount(String username, String password) throws Exception {
		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "D Suite");
		boolean testRunmode = TestUtil.isTestCaseRunnable(suiteDxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts");

		try {
			openBrowser();
			maximizeWindow();
			clearCookies();
			test.log(LogStatus.INFO, "Login Neon app with TR valid credentials");
			// Navigate to TR login page and login with valid TR credentials
			ob.navigate().to(host);
			pf.getLoginTRInstance(ob).waitForTRHomePage();
			pf.getLoginTRInstance(ob).enterTRCredentials(username, password);
			pf.getLoginTRInstance(ob).clickLogin();
			test.log(LogStatus.INFO, "Logged in to NEON");
		} catch (Throwable t) {
			t.printStackTrace();
			test.log(LogStatus.FAIL, "Something unexpected happened");
			status = 2;// excel
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng

			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_loginNotDone")));// screenshot
			closeBrowser();
		}
	}
	
	@Test(dependsOnMethods = "testLoginTRAccount")
	@Parameters({"myPost","postContent"})
	public void testPublishPostTimeStampValidat(String myPost,String postContent) throws Exception {
			try {
				postContent=postContent+RandomStringUtils.randomNumeric(10);
				test.log(LogStatus.INFO," Click Publish A Post and Post your article");
				pf.getHFPageInstance(ob).clickProfileImage();
				pf.getProfilePageInstance(ob).clickProfileLink();
				profileDetails=pf.getProfilePageInstance(ob).getProfileTitleAndMetadata();
				pf.getProfilePageInstance(ob).clickPublishAPost();
				postTitle=myPost+RandomStringUtils.randomAlphanumeric(6);
				pf.getProfilePageInstance(ob).enterPostTitle(postTitle);
				test.log(LogStatus.INFO, "Entered Post Title");
				pf.getProfilePageInstance(ob).enterPostContent(postContent);
				test.log(LogStatus.INFO, "Entered Post Content");
				pf.getProfilePageInstance(ob).clickOnPostPublishButton();
				test.log(LogStatus.INFO, "Published the post and Validate Published Post count");
				pf.getProfilePageInstance(ob).validatePostTitle(postTitle);
				test.log(LogStatus.INFO, "Validate Post creation date and System Date");
				pf.getProfilePageInstance(ob).validatePostTimeStamp();
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends");
				closeBrowser();
			} catch (Exception e) {
				e.printStackTrace();
				test.log(LogStatus.FAIL, "Something unexpected happened");
				status = 2;// excel
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(e);// testng

				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "post_not_increased")));// screenshot
				closeBrowser();
			}
		
	}
	
	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		/*if (status == 1)
			TestUtil.reportDataSetResult(suiteDxls, "Test Cases",
					TestUtil.getRowNum(suiteDxls, this.getClass().getSimpleName()), "PASS");
		else if (status == 2)
			TestUtil.reportDataSetResult(suiteDxls, "Test Cases",
					TestUtil.getRowNum(suiteCxls, this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteDxls, "Test Cases",
					TestUtil.getRowNum(suiteDxls, this.getClass().getSimpleName()), "SKIP");*/

	}

	
}
