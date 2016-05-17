package notifications;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import pages.PageFactory;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;

public class Notifications0005 extends NotificationsTestBase {

	static int status = 1;
	String watchListName = null;
	String watchListDescription = null;
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
						"Verify that user is receiving notification when someone he is following created a public watch list. (single event notification)")
				.assignCategory("Notifications");
	}

	@Test
	public void testcaseF5() throws Exception {
		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "Notifications");
		boolean testRunmode = TestUtil.isTestCaseRunnable(notificationxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;
		if (!master_condition) {
			status = 3;// excel
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {
			if (user1 != null && user2 != null && user2 != null) {
				openBrowser();
				maximizeWindow();
				clearCookies();
				ob.navigate().to(host);
				// Logging in with User1
				pf.getLoginTRInstance(ob).enterTRCredentials(user3, CONFIG.getProperty("defaultPassword"));
				pf.getLoginTRInstance(ob).clickLogin();
				waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchBox_textBox")), 30);
				watchListName = "Creating WatchList for notification testing" + new Random().nextInt(1000);
				watchListDescription = "Creating Public WatchList for UI notification testing" + RandomStringUtils.randomNumeric(15);
				test.log(LogStatus.INFO, this.getClass().getSimpleName() + " Creating public watchlist");
				createWatchList("public", watchListName, watchListDescription);
				pf.getLoginTRInstance(ob).logOutApp();
				pf.getLoginTRInstance(ob).enterTRCredentials(user2, CONFIG.getProperty("defaultPassword"));
				pf.getLoginTRInstance(ob).clickLogin();
				waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchBox_textBox")), 30);
				BrowserWaits.waitTime(6);
				String text = ob.findElement(By.xpath(OR.getProperty("newPublicWatchListNotification"))).getText();
				logger.info("Notification Text: " + text);
				try {
					Assert.assertTrue(/* text.contains("TODAY") && */text.contains(fn3 + " " + ln3)
							&& text.contains("created a new public watchlist") && text.contains(watchListName)
							&& text.contains(watchListDescription));
					test.log(LogStatus.PASS, "User receiving notification with correct content");
					pf.getLoginTRInstance(ob).logOutApp();
					closeBrowser();
				} catch (Throwable t) {
					test.log(LogStatus.FAIL, "User receiving notification with incorrect content");// extent
					StringWriter errors = new StringWriter();
					t.printStackTrace(new PrintWriter(errors));
					test.log(LogStatus.INFO, errors.toString()); // reports
					test.log(LogStatus.INFO, "Error--->" + t);
					ErrorUtil.addVerificationFailure(t);
					status = 2;// excel
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
							this.getClass().getSimpleName() + "_user_receiving_notification_with_incorrect_content")));// screenshot
					closeBrowser();
				}
			} else {
				throw new Exception("User creation problem hence throwing exception");
			}
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "User receiving notification with incorrect content: \n" +t);// extent
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// reports
			test.log(LogStatus.INFO, "Error--->" + t);
			ErrorUtil.addVerificationFailure(t);
			status = 2;// excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
					this.getClass().getSimpleName() + "_user_receiving_notification_with_incorrect_content")));// screenshot
			closeBrowser();
		}
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		/*
		 * if (status == 1) TestUtil.reportDataSetResult(notificationxls, "Test Cases",
		 * TestUtil.getRowNum(notificationxls, this.getClass().getSimpleName()), "PASS"); else if (status == 2)
		 * TestUtil.reportDataSetResult(notificationxls, "Test Cases", TestUtil.getRowNum(notificationxls,
		 * this.getClass().getSimpleName()), "FAIL"); else TestUtil.reportDataSetResult(notificationxls, "Test Cases",
		 * TestUtil.getRowNum(notificationxls, this.getClass().getSimpleName()), "SKIP");
		 */
	}
}
