package notifications;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import pages.PageFactory;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;

public class Notifications003 extends NotificationsTestBase {

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
		test = extent
				.startTest(var,
						"Verify that user receives a notificatication when his follower comments on an article contained in his watchlist")
				.assignCategory("Notifications");

	}

	@Test
	public void testcaseF3() throws Exception {

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

			// 1)Login with user1,add an article to watchlist and logout

			openBrowser();
			maximizeWindow();
			clearCookies();

			ob.navigate().to(host);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 20);

			pf.getLoginTRInstance(ob).enterTRCredentials(user1, CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchBox_textBox")), 30);
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("tiger");
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchResults_links")), 120);
			String document_title = ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).getText();
			String document_url = ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).getAttribute("href");
			System.out.println(document_url);
			ob.findElement(By.xpath(OR.getProperty("search_watchlist_image"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("selectWatchListInBucket")), 30);
			ob.findElement(By.xpath(OR.getProperty("selectWatchListInBucket"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("closeWatchListBucketDisplay")), 30);
			ob.findElement(By.xpath(OR.getProperty("closeWatchListBucketDisplay"))).click();
			Thread.sleep(1000);

			pf.getLoginTRInstance(ob).logOutApp();

			// 2)Login with user2,comment on article contained in user1's
			// watchlist and logout
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 20);

			pf.getLoginTRInstance(ob).enterTRCredentials(user2, CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("apps")), 30);

			ob.navigate().to(document_url);

			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("document_comment_textbox")), 30);
			Thread.sleep(70000);
			ob.findElement(By.xpath(OR.getProperty("document_comment_textbox"))).sendKeys("TestCase_F3:green tea");
			Thread.sleep(5000);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("document_addComment_button")), 30);
			jsClick(ob, ob.findElement(By.xpath(OR.getProperty("document_addComment_button"))));
			Thread.sleep(10000);
			pf.getLoginTRInstance(ob).logOutApp();

			// 3)Login with user1 again and verify that he receives a correct
			// notification
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 20);

			pf.getLoginTRInstance(ob).enterTRCredentials(user1, CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("notificationDocumentComment")), 100);
			String text = ob.findElement(By.xpath(OR.getProperty("notificationDocumentComment"))).getText();
			System.out.println(text);

			String expected_text = fn2 + " " + ln2;

			try {
				Assert.assertTrue(text.contains("New comments") && text.contains(expected_text)
						&& /*
							 * text.contains("TODAY") &&
							 */text.contains(document_title) && text.contains("TestCase_F3:green tea"));
				test.log(LogStatus.PASS, "User receiving notification with correct content");
			} catch (Throwable t) {

				test.log(LogStatus.FAIL, "User receiving notification with incorrect content");// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString()); // reports
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "_user_receiving_notification_with_incorrect_content")));// screenshot

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

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
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
