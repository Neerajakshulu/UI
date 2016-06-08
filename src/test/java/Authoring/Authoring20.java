package Authoring;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
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

public class Authoring20 extends TestBase {

	
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
		test = extent.startTest(var, "Verify that user is not able to unflag the comment without selecting a Reason")
				.assignCategory("Authoring");

	}

	@Test
	public void testUnflagActionWithoutReason() throws Exception {
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

			openBrowser();
			maximizeWindow();
			clearCookies();

			// Navigate to TR login page and login with valid TR credentials
			ob.navigate().to(host);
			login();
			String PROFILE_NAME = LOGIN.getProperty("PROFILE1");
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_search_box_css")), 20);
			ob.findElement(By.cssSelector(OR.getProperty("tr_search_box_css"))).sendKeys("Synthetic Biology");
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();

			pf.getpostRVPageInstance(ob).searchForArticleWithComments();
			pf.getpostRVPageInstance(ob).loadComments();

			waitForAllElementsToBePresent(ob, By.xpath(OR.getProperty("tr_authoring_comments_xpath")), 40);
			List<WebElement> commentsList = ob.findElements(By.xpath(OR.getProperty("tr_authoring_comments_xpath")));
			String commentText;
			int commentsCount = 0;
			WebElement flagWe;
			for (int i = 0; i < commentsList.size(); i++) {
				commentText = commentsList.get(i).getText();

				if (!commentText.contains(PROFILE_NAME) && !commentText.contains("Comment deleted")) {
					flagWe = commentsList.get(i).findElement(
							By.xpath(OR.getProperty("tr_authoring_comments_flag_dynamic_xpath")));
					if (flagWe.getAttribute("class").contains("flag-inactive")) {
						jsClick(ob, flagWe);
						commentsCount = i;
						break;
					}
				}
			}

			waitForElementTobeVisible(ob,
					By.cssSelector(OR.getProperty("tr_authoring_comments_flag_reason_modal_css")), 40);
			jsClick(ob, ob.findElement(By.cssSelector(OR.getProperty("tr_authoring_comments_flag_reason_chkbox_css"))));
			jsClick(ob, ob.findElement(By.cssSelector(OR.getProperty("tr_authoring_comments_flag_button_modal_css"))));
			Thread.sleep(5000);// Wait for modal to disappear

			try {
				boolean IsFlagged = commentsList.get(commentsCount)
						.findElement(By.xpath(OR.getProperty("tr_authoring_comments_flag_dynamic_xpath")))
						.getAttribute("class").contains("flag-active");
				Assert.assertTrue(IsFlagged);
				test.log(LogStatus.PASS, "User comment is flagged");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "User comment is not flagged");
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "Flag_validation_for_comments_failed")));// screenshot

			}
			commentsList = ob.findElements(By.xpath(OR.getProperty("tr_authoring_comments_xpath")));

			jsClick(ob,
					commentsList.get(commentsCount).findElement(
							By.xpath(OR.getProperty("tr_authoring_comments_flag_dynamic_xpath"))));

			waitForElementTobeVisible(ob,
					By.cssSelector(OR.getProperty("tr_authoring_comments_flag_reason_modal_css")), 40);

			try {
				WebElement flagButton = ob.findElement(By.cssSelector(OR
						.getProperty("tr_authoring_comments_flag_button_modal_css")));
				Assert.assertFalse(flagButton.isEnabled());
				test.log(LogStatus.PASS,
						"Unflag action without selecting the reason is working for comments as expected");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Unflag action without selecting the reason verification failed");
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "Cancel_Flag_validation_for_comments_failed")));// screenshot

			}
			jsClick(ob, ob.findElement(By.cssSelector(OR.getProperty("tr_authoring_comments_flag_reason_chkbox_css"))));

			jsClick(ob, ob.findElement(By.cssSelector(OR.getProperty("tr_authoring_comments_flag_button_modal_css"))));

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
		pf.getLoginTRInstance(ob).logOutApp();
		closeBrowser();
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
