package Authoring;

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
import pages.PageFactory;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;

public class Authoring87 extends TestBase {

	private static final String URL = "/cmty/#/profile/f8606cb6-8765-4ad4-878b-baf1175b9a52";
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
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("Authoring");

	}

	@Test
	public void testCreateAndPublishPost() throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {
			String postString = "PostCreationTest" + RandomStringUtils.randomNumeric(10);
			openBrowser();
			maximizeWindow();
			clearCookies();

			// Navigate to TR login page and login with valid TR credentials
			ob.navigate().to(host);
			// ob.get(CONFIG.getProperty("testSiteName"));
			loginAs("USERNAME16", "PASSWORD16");
			test.log(LogStatus.INFO, "Logged in to NEON");
			pf.getHFPageInstance(ob).clickOnProfileLink();
			test.log(LogStatus.INFO, "Navigated to Profile Page");
			int postCountBefore = pf.getProfilePageInstance(ob).getPostsCount();
			test.log(LogStatus.INFO, "Post count:" + postCountBefore);
			pf.getProfilePageInstance(ob).clickOnPublishPostButton();
			pf.getProfilePageInstance(ob).enterPostTitle(postString);
			test.log(LogStatus.INFO, "Entered Post Title");
			pf.getProfilePageInstance(ob).enterPostContent(postString);
			test.log(LogStatus.INFO, "Entered Post Content");
			pf.getProfilePageInstance(ob).addExternalLinkToPostContent(host + URL);
			pf.getProfilePageInstance(ob).clickOnPostPublishButton();
			test.log(LogStatus.INFO, "Published the post");
			//Added by KR
			//BrowserWaits.waitTime(2);
			ob.navigate().back();
			ob.navigate().back();
			
			pf.getProfilePageInstance(ob).clickFirstPostTitle();
			try {
				Assert.assertTrue(pf.getpostRVPageInstance(ob).validatePostContentForExternalLink(host + URL));
				test.log(LogStatus.PASS, "Post is published with internal link");
				pf.getpostRVPageInstance(ob).clickExternalLinkInPostContent(host + URL);
				String hosts = "https://apps.dev-stable.clarivate.com";
				Assert.assertTrue(pf.getpostRVPageInstance(ob).validateURL(hosts + URL));
				test.log(LogStatus.PASS, "internal links added to post are working fine");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "internal links added to post are not working fine");
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "Post_count_validation_failed")));// screenshot

			}
			ob.navigate().back();
			pf.getLoginTRInstance(ob).logOutApp();
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

		/*
		 * if (status == 1) TestUtil.reportDataSetResult(authoringxls, "Test Cases", TestUtil.getRowNum(authoringxls,
		 * this.getClass().getSimpleName()), "PASS"); else if (status == 2) TestUtil.reportDataSetResult(authoringxls,
		 * "Test Cases", TestUtil.getRowNum(authoringxls, this.getClass().getSimpleName()), "FAIL"); else
		 * TestUtil.reportDataSetResult(authoringxls, "Test Cases" , TestUtil.getRowNum(authoringxls,
		 * this.getClass().getSimpleName()), "SKIP");
		 */

	}

}
