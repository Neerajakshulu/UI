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

import com.relevantcodes.extentreports.LogStatus;

import pages.PageFactory;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;

/**
 * The {@code Notifications0003} for testing test case of Verify that user receives a notification when he is followed
 * by someone
 *
 * @author Avinash Potti 
 */
public class Notifications0003 extends NotificationsTestBase {
	static int status = 1;
	PageFactory pf = new PageFactory();
	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not

	/**
	 * class for Notification
	 * 
	 * @author UC196983
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription())
				.assignCategory("Notifications");
	}

	@Test
	public void testcaseF3() throws Exception {
		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;
		if (!master_condition) {
			status = 3;// excel
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + "execution starts--->");
		try {
			if (user1 != null && user2 != null) {
				openBrowser();
				maximizeWindow();
				clearCookies();
				ob.navigate().to(host);
				// Login with user2 for verify that to receives a notification with correct data
				pf.getLoginTRInstance(ob).enterTRCredentials(user2, CONFIG.getProperty("defaultPassword"));
				pf.getLoginTRInstance(ob).clickLogin();
				ob.navigate().refresh();
				waitForElementTobeVisible(ob, By.xpath(OR.getProperty("notification")), 30);
				JavascriptExecutor jse = (JavascriptExecutor) ob;
				for (int i = 1; i <= 3; i++) {
					String text = ob.findElement(By.xpath(OR.getProperty("notification"))).getText();
					if (text.length() > 0) {
						break;
					}
					jse.executeScript("window.scrollTo(0, document.body.scrollHeight)", "");
					BrowserWaits.waitTime(3);
				}
				String text = ob.findElement(By.xpath(OR.getProperty("notification"))).getText();
				logger.info("Notification Text: " + text);
				try {
					Assert.assertTrue(/* text.contains("TODAY") && */text.contains(fn1 + " " + ln1)
							&& text.contains("is now following you"));
					test.log(LogStatus.PASS, "User receiving notification with correct content");
				} catch (Throwable t) {
					test.log(LogStatus.FAIL, "User receiving notification with incorrect content");// extent
					test.log(LogStatus.INFO, "Error--->" + t.getMessage());
					ErrorUtil.addVerificationFailure(t);
					status = 2;// excel
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
							this.getClass().getSimpleName() + "_user_receiving_notification_with_incorrect_content")));// screenshot
				}
				closeBrowser();
			} else {
				throw new Exception("User creation problem hence throwing exception");
			}
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something unexpected happened" + t);// extent
			// next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng
			status = 2;// excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
			closeBrowser();
			throw new Exception();
		}
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);
	}

}
