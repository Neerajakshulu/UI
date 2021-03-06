package suiteC;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import pages.HeaderFooterLinksPage;
import pages.PostRecordViewPage;
import pages.ProfilePage;
import util.ErrorUtil;
import util.TestUtil;

public class CreateAndEditPost extends TestBase{

	
	
	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		String var=xlRead2(returnExcelPath('C'),this.getClass().getSimpleName(),1);
		test = extent.startTest(var, "Verify that user is able to create and edit post and verify that time stamp is displayed ")
				.assignCategory("Suite C");

	}

	@Test
	public void testCreateAndPublishPost() throws Exception {
		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "C Suite");
		boolean testRunmode = TestUtil.isTestCaseRunnable(suiteCxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		try {
			String postString="PostCreationTest"+RandomStringUtils.randomNumeric(10);
			String postStringEdited="PostEditTest"+RandomStringUtils.randomNumeric(10);
			openBrowser();
			maximizeWindow();
			clearCookies();

			// Navigate to TR login page and login with valid TR credentials
			ob.navigate().to(host);
			//ob.get(CONFIG.getProperty("testSiteName"));
			loginAs("USERNAME1","PASSWORD1");
			test.log(LogStatus.INFO, "Logged in to NEON");
			HeaderFooterLinksPage.clickOnProfileLink();
			test.log(LogStatus.INFO, "Navigated to Profile Page");
			ProfilePage.clickOnPublishPostButton();
			ProfilePage.enterPostTitle(postString);
			test.log(LogStatus.INFO, "Entered Post Title");
			ProfilePage.enterPostContent(postString);
			test.log(LogStatus.INFO, "Entered Post Content");
			ProfilePage.clickOnPostPublishButton();
			test.log(LogStatus.INFO, "Published the post");
			ProfilePage.clickOnFirstPost();
			
			
			try {
				Assert.assertTrue(PostRecordViewPage.verifyPostCreationDate());
				test.log(LogStatus.PASS, "Post creation timestamp is displayed in post record view page");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Post creation timestamp is not displayed in post record view page");
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "Post_count_validation_failed")));// screenshot

			}
			PostRecordViewPage.clickOnEditButton();
			ProfilePage.enterPostTitle(postStringEdited);
			test.log(LogStatus.INFO, "Entered Post Title");
			ProfilePage.enterPostContent(postStringEdited);
			test.log(LogStatus.INFO, "Entered Post Content");
			ProfilePage.clickOnPostPublishButton();

			try {
				Assert.assertTrue(PostRecordViewPage.verifyPostEditedDate());
				test.log(LogStatus.PASS, "Post edited timestamp is displayed in post record view page");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Post edited timestamp is not displayed in post record view page");
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "Post_count_validation_failed")));// screenshot

			}
			
			try {
				Assert.assertEquals(postStringEdited, PostRecordViewPage.getPostTitle());
				test.log(LogStatus.PASS, "Actual post title matches expected post title");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Actual post title does not match expected post title");
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "Post_creation_validation_failed")));// screenshot

			}
			
			try {
				Assert.assertEquals(postStringEdited, PostRecordViewPage.getPostContent());
				test.log(LogStatus.PASS, "Actual post content matches expected post content");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Actual post content does not match expected post content");
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "Post_creation_validation_failed")));// screenshot

			}
		
			logout();
			closeBrowser();
		} catch (Throwable t) {
			t.printStackTrace();
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent
																		// reports
			// next 3 lines to print whole testng error in report
			status = 2;// excel
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng

			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		if (status == 1)
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases",
					TestUtil.getRowNum(suiteCxls, this.getClass().getSimpleName()), "PASS");
		else if (status == 2)
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases",
					TestUtil.getRowNum(suiteCxls, this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases",
					TestUtil.getRowNum(suiteCxls, this.getClass().getSimpleName()), "SKIP");

	}

	
}
