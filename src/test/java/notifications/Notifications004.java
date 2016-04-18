package notifications;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class Notifications004 extends TestBase {

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
		test = extent.startTest(var, "Verify that user receives a notification when someone likes his comment")
				.assignCategory("Notifications");

	}

	@Test
	public void testcaseF4() throws Exception {

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

			// 1)Login with user2,comment on some article and logout
			openBrowser();
			maximizeWindow();
			clearCookies();

			ob.navigate().to(host);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 20);

			pf.getLoginTRInstance(ob).enterTRCredentials(user2, CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchBox_textBox")), 30);
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("australia");
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();

			// String
			// document_title=ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).getText();
			// System.out.println(document_title);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchResults_links")), 150);
			String document_url = ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).getAttribute("href");
			ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("document_comment_textbox")), 30);
			ob.findElement(By.xpath(OR.getProperty("document_comment_textbox"))).sendKeys("beach");
			Thread.sleep(4000);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("document_addComment_button")), 30);
			jsClick(ob, ob.findElement(By.xpath(OR.getProperty("document_addComment_button"))));
			Thread.sleep(5000);
			pf.getLoginTRInstance(ob).logOutApp();

			// 2)Login with user1,like the comment and logout
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 20);

			pf.getLoginTRInstance(ob).enterTRCredentials(user1, CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("apps")), 30);

			ob.navigate().to(document_url);
			// ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("australia");
			// ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			// Thread.sleep(4000);

			// ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("document_commentLike_button")), 30);
			waitForElementTobeClickable(ob, By.xpath(OR.getProperty("document_commentLike_button")), 30);
			jsClick(ob, ob.findElement(By.xpath(OR.getProperty("document_commentLike_button"))));
			Thread.sleep(1000);
			pf.getLoginTRInstance(ob).logOutApp();

			// 3)Login with user2 again and verify that he receives a correct
			// notification

			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 20);

			pf.getLoginTRInstance(ob).enterTRCredentials(user2, CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			Thread.sleep(10000);

			JavascriptExecutor jse = (JavascriptExecutor) ob;

			for (int i = 1; i <= 3; i++) {

				jse.executeScript("window.scrollTo(0, document.body.scrollHeight)", "");
				Thread.sleep(3000);
			}

			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("notificationForLike")), 30);

			String text = ob.findElement(By.xpath(OR.getProperty("notificationForLike"))).getText();
			System.out.println(text);


			try {
				Assert.assertTrue(/* text.contains("TODAY") && */text.contains("Liked your comment")
						&& text.contains("beach") && text.contains(fn1 + " " + ln1));
				test.log(LogStatus.PASS, "User receiving notification with correct content");
			} catch (Throwable t) {

				test.log(LogStatus.FAIL, "User receiving notification with incorrect content");// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports // reports
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
