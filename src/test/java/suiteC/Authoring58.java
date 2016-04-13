package suiteC;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pages.PageFactory;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class Authoring58 extends TestBase {

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
		test = extent.startTest(var, "Verify that user is able to flag/unflag the posts of others.").assignCategory(
				"Authoring");

	}

	@Test
	public void testPostComments() throws Exception {
		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "C Suite");
		boolean testRunmode = TestUtil.isTestCaseRunnable(suiteCxls, this.getClass().getSimpleName());
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
			loginAs("USERNAME1", "PASSWORD1");
			test.log(LogStatus.INFO, "Logged in to NEON");
			pf.getHFPageInstance(ob).searchForText("test");
			pf.getSearchResultsPageInstance(ob).clickOnPostTab();
			pf.getSearchResultsPageInstance(ob).viewOtherUsersPost("Kavya Revanna");
			try {
				pf.getpostRVPageInstance(ob).validateFlagAndUnflagActionOnPost(test);
				pf.getpostRVPageInstance(ob).validateFlagAndUnflagActionOnPost(test);
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Flag/Unflag validation failed for posts");
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "Post_count_validation_failed")));// screenshot

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
		 * if (status == 1) TestUtil.reportDataSetResult(suiteCxls, "Test Cases", TestUtil.getRowNum(suiteCxls,
		 * this.getClass().getSimpleName()), "PASS"); else if (status == 2) TestUtil.reportDataSetResult(suiteCxls,
		 * "Test Cases", TestUtil.getRowNum(suiteCxls, this.getClass().getSimpleName()), "FAIL"); else
		 * TestUtil.reportDataSetResult(suiteCxls, "Test Cases", TestUtil.getRowNum(suiteCxls,
		 * this.getClass().getSimpleName()), "SKIP");
		 */
	}

}
