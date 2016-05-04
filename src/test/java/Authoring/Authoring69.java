package Authoring;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import pages.PageFactory;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;

public class Authoring69 extends TestBase {

	private static final String URL = "https://www.yahoo.com";
	static int status = 1;
	PageFactory pf = new PageFactory();

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		String var = xlRead2(returnExcelPath('C'), this.getClass().getSimpleName(), 1);
		test = extent.startTest(var,
				"Verify that the user is able to add external links to the comment and publish it.").assignCategory(
				"Authoring");

	}

	@Test
	public void testCreateAndPublishPost() throws Exception {
		boolean suiteRunmode = testUtil.isSuiteRunnable(suiteXls, "Authoring");
		boolean testRunmode = testUtil.isTestCaseRunnable(authoringxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP, "Skipping test case " + this.getClass().getSimpleName()
					+ " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		try {
			openBrowser();
			maximizeWindow();
			clearCookies();

			// Navigate to TR login page and login with valid TR credentials
			ob.navigate().to(host);
			// ob.get(CONFIG.getProperty("testSiteName"));
			loginAs("LOGINUSERNAME1", "LOGINPASSWORD1");
			String profileName=LOGIN.getProperty("PROFILE1");
			test.log(LogStatus.INFO, "Logged in to NEON");
			pf.getHFPageInstance(ob).searchForText("test");
			pf.getSearchResultsPageInstance(ob).clickOnPostTab();
			pf.getSearchResultsPageInstance(ob).viewOtherUsersPost(profileName);
			pf.getpostRVPageInstance(ob).addExternalLinkComments(URL);
			pf.getAuthoringInstance(ob).clickAddCommentButton();
			test.log(LogStatus.INFO, "Added external link to the comment");
			try {
				Assert.assertTrue(pf.getpostRVPageInstance(ob).validateCommentForExternalLink(URL));
				test.log(LogStatus.PASS, "Comment is published with external link");
				pf.getpostRVPageInstance(ob).clickExternalLinkInComments(URL);
				Assert.assertTrue(pf.getpostRVPageInstance(ob).validateURL(URL));
				test.log(LogStatus.PASS, "External links added to comment are working fine");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "External links added to comment are not working fine");
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "Post_count_validation_failed")));// screenshot

			}
			ob.navigate().back();
			waitForPageLoad(ob);
			BrowserWaits.waitTime(10);
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

			test.log(
					LogStatus.INFO,
					"Snapshot below: "
							+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
									+ "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		/*
		 * if (status == 1) testUtil.reportDataSetResult(authoringxls, "Test Cases", testUtil.getRowNum(authoringxls,
		 * this.getClass().getSimpleName()), "PASS"); else if (status == 2) testUtil.reportDataSetResult(authoringxls,
		 * "Test Cases", testUtil.getRowNum(authoringxls, this.getClass().getSimpleName()), "FAIL"); else
		 * testUtil.reportDataSetResult(authoringxls, "Test Cases", testUtil.getRowNum(authoringxls,
		 * this.getClass().getSimpleName()), "SKIP");
		 */

	}

}
