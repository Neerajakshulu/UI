package Authoring;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pages.PageFactory;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class Authoring67 extends TestBase {

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
		test = extent
				.startTest(var, "Verfiy that user is able to delete the draft post from the list in their profile")
				.assignCategory("Authoring");

	}

	@Test
	public void deleteDraftPostFromProfile() throws Exception {
		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "Authoring");
		boolean testRunmode = TestUtil.isTestCaseRunnable(authoringxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP, "Skipping test case " + this.getClass().getSimpleName()
					+ " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		try {
			String postString = "DraftPostCreationTest" + RandomStringUtils.randomNumeric(10);
			openBrowser();
			maximizeWindow();
			clearCookies();

			// Navigate to TR login page and login with valid TR credentials
			ob.navigate().to(host);
			//ob.get(CONFIG.getProperty("testSiteName"));
			loginAs("USERNAME14", "PASSWORD14");
			test.log(LogStatus.INFO, "Logged in to NEON");
			pf.getHFPageInstance(ob).clickOnProfileLink();
			test.log(LogStatus.INFO, "Navigated to Profile Page");
			pf.getProfilePageInstance(ob).clickOnPublishPostButton();
			pf.getProfilePageInstance(ob).enterPostTitle(postString);
			test.log(LogStatus.INFO, "Entered Post Title");
			pf.getProfilePageInstance(ob).enterPostContent(postString);
			test.log(LogStatus.INFO, "Entered Post Content");
			pf.getProfilePageInstance(ob).clickOnPostCancelButton();
			pf.getProfilePageInstance(ob).clickOnPostCancelKeepDraftButton();
			test.log(LogStatus.INFO, "Saved the draft post");
			pf.getHFPageInstance(ob).clickOnProfileLink();
			waitForPageLoad(ob);
			waitForAjax(ob);
			
			
			int postCountBefore = pf.getProfilePageInstance(ob).getDraftPostsCount();
			test.log(LogStatus.INFO, "Draft Post count:" + postCountBefore);
			pf.getProfilePageInstance(ob).clickOnDraftPostsTab();
			pf.getProfilePageInstance(ob).deleteDraftPostFromPostModal(postString);
			BrowserWaits.waitTime(5);
			pf.getHFPageInstance(ob).clickOnProfileLink();
			waitForPageLoad(ob);
			int postCountAfter = 0;
			if (postCountBefore == 1) {

				if (!pf.getProfilePageInstance(ob).isDraftPostTabDispalyed()) {
					postCountAfter = 0;
				}

				else {

					postCountAfter = pf.getProfilePageInstance(ob).getDraftPostsCount();

				}
			} else {
				postCountAfter = pf.getProfilePageInstance(ob).getDraftPostsCount();
			}
			test.log(LogStatus.INFO, "Draft Post count:" + postCountAfter);

			try {
				Assert.assertEquals(postCountBefore - 1, postCountAfter);
				test.log(LogStatus.PASS, "Post count is decremented after the post deletion");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Post count is not decremented after the post deletion");
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "Post_count_validation_failed")));// screenshot

			}
			if (postCountBefore != 1) {
				try {
					pf.getProfilePageInstance(ob).clickOnDraftPostsTab();
					Assert.assertTrue(!pf.getProfilePageInstance(ob).getAllDraftPostTitle().contains(postString));
					test.log(LogStatus.PASS, "Deleted post is not displayed under posts tab in profile");
				} catch (Throwable t) {
					test.log(LogStatus.FAIL, "Deleted post is displayed under posts tab in profile");
					test.log(LogStatus.INFO, "Error--->" + t);
					ErrorUtil.addVerificationFailure(t);
					status = 2;
					test.log(
							LogStatus.INFO,
							"Snapshot below: "
									+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
											+ "Post_creation_validation_failed")));// screenshot

				}
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
		 * if (status == 1) TestUtil.reportDataSetResult(authoringxls, "Test Cases", TestUtil.getRowNum(authoringxls,
		 * this.getClass().getSimpleName()), "PASS"); else if (status == 2) TestUtil.reportDataSetResult(authoringxls,
		 * "Test Cases", TestUtil.getRowNum(authoringxls, this.getClass().getSimpleName()), "FAIL"); else
		 * TestUtil.reportDataSetResult(authoringxls, "Test Cases", TestUtil.getRowNum(authoringxls,
		 * this.getClass().getSimpleName()), "SKIP");
		 */
	}

}
