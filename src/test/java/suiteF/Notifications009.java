package suiteF;

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

public class Notifications009 extends TestBase {

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
				"Verify that user receives a notification when someone he is following user comments on a post")
				.assignCategory("Notifications");

	}

	@Test
	public void testCaseF9() throws Exception {
		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "F Suite");
		boolean testRunmode = TestUtil.isTestCaseRunnable(suiteFxls, this.getClass().getSimpleName());
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

			ob.navigate().to(host);
			// Logging in with User1
			pf.getLoginTRInstance(ob).enterTRCredentials(user1, CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			// searching for posts
			pf.getLoginTRInstance(ob).searchArticle("POST for");

			JavascriptExecutor jse = (JavascriptExecutor) ob;
			jse.executeScript("scroll(0,-500)");
			Thread.sleep(2000);
			test.log(LogStatus.INFO, "Commenting on a post");
			ob.findElement(By.xpath(OR.getProperty("search_results_posts_tab_link"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_results_post_link")), 30);
			String postLinkClicked = ob.findElement(By.xpath(OR.getProperty("search_results_post_link"))).getText();
			ob.findElement(By.xpath(OR.getProperty("search_results_post_link"))).click();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("document_comment_textbox")), 30);
			ob.findElement(By.xpath(OR.getProperty("document_comment_textbox"))).sendKeys("TestCase_F9:Nice Post");
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("document_addComment_button")), 30);
			jsClick(ob, ob.findElement(By.xpath(OR.getProperty("document_addComment_button"))));
			Thread.sleep(2000);
			pf.getLoginTRInstance(ob).logOutApp();

			// Login with User2 who is following user1 and check if he's notified
			pf.getLoginTRInstance(ob).enterTRCredentials(user2, CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			test.log(LogStatus.INFO, "Checking if Notification is received");
			Thread.sleep(8000);
			String text = ob.findElement(By.xpath(OR.getProperty("notificationCommentEvent"))).getText();
			System.out.println(text);

			try {
				Assert.assertTrue(/* text.contains("TODAY") && */text.contains(fn1 + " " + ln1)
						&& text.contains("commented on") && text.contains(postLinkClicked));
				test.log(LogStatus.PASS, "User receiving notification with correct content");
			} catch (Throwable t) {

				test.log(LogStatus.FAIL, "User receiving notification with incorrect content");// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// reports
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

	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		/*
		 * if (status == 1) TestUtil.reportDataSetResult(suiteFxls, "Test Cases", TestUtil.getRowNum(suiteFxls,
		 * this.getClass().getSimpleName()), "PASS"); else if (status == 2) TestUtil.reportDataSetResult(suiteFxls,
		 * "Test Cases", TestUtil.getRowNum(suiteFxls, this.getClass().getSimpleName()), "FAIL"); else
		 * TestUtil.reportDataSetResult(suiteFxls, "Test Cases", TestUtil.getRowNum(suiteFxls,
		 * this.getClass().getSimpleName()), "SKIP");
		 */
	}
}
