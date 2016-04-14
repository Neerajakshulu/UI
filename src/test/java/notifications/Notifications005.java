package notifications;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
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

public class Notifications005 extends TestBase {

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
		String var = xlRead2(returnExcelPath('F'), this.getClass().getSimpleName(), 1);
		test = extent.startTest(var,
				"Verify that user receives a notification when someone he is following  publishes a post")
				.assignCategory("Notifications");

	}

	@Test
	public void testcaseF5() throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "Notifications");
		boolean testRunmode = TestUtil.isTestCaseRunnable(notificationxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP, "Skipping test case " + this.getClass().getSimpleName()
					+ " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		try {
			String postString = "PostCreationTest" + RandomStringUtils.randomNumeric(10);
			openBrowser();
			maximizeWindow();
			clearCookies();

			ob.navigate().to(host);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 20);

			pf.getLoginTRInstance(ob).enterTRCredentials(user1, CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("home_page_publish_post_link")), 3000);
			ob.findElement(By.xpath(OR.getProperty("home_page_publish_post_link"))).click();
			pf.getProfilePageInstance(ob).enterPostTitle(postString);
			test.log(LogStatus.INFO, "Entered Post Title");
			pf.getProfilePageInstance(ob).enterPostContent(postString);
			test.log(LogStatus.INFO, "Entered Post Content");
			pf.getProfilePageInstance(ob).clickOnPostPublishButton();
			test.log(LogStatus.INFO, "Published the post");
			Thread.sleep(2000);
			pf.getLoginTRInstance(ob).logOutApp();

			// Login using user2 and check for the notification
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 20);

			pf.getLoginTRInstance(ob).enterTRCredentials(user2, CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			Thread.sleep(5000);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("header_label")), 50);
			String text = ob.findElement(By.xpath(OR.getProperty("notificationForNewPost"))).getText();
			System.out.println(text);
			String expected_text = fn1 + " " + ln1;
			try {
				Assert.assertTrue(/* text.contains("TODAY") && */text.contains(expected_text)
						&& text.contains("published a post") && text.contains(postString));
				test.log(LogStatus.PASS, "User receiving notification with correct content");
			} catch (Throwable t) {

				test.log(LogStatus.FAIL, "User receiving notification with incorrect content");// extent
				// reports
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "_user_receiving_notification_with_incorrect_content")));// screenshot
				closeBrowser();
			}

			closeBrowser();

		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent
			// reports
			// next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng
			status = 2;// excel
			test.log(
					LogStatus.INFO,
					"Snapshot below: "
							+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
									+ "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}

	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		/*
		 * if (status == 1) TestUtil.reportDataSetResult(notificationxls, "Test Cases", TestUtil.getRowNum(notificationxls,
		 * this.getClass().getSimpleName()), "PASS"); else if (status == 2) TestUtil.reportDataSetResult(notificationxls,
		 * "Test Cases", TestUtil.getRowNum(notificationxls, this.getClass().getSimpleName()), "FAIL"); else
		 * TestUtil.reportDataSetResult(notificationxls, "Test Cases", TestUtil.getRowNum(notificationxls,
		 * this.getClass().getSimpleName()), "SKIP");
		 */
	}

}
