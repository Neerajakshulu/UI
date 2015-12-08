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

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.TestUtil;

public class TestCase_F1 extends TestBase {
	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	
	/**
	 * class for Notification
	 * @author UC196983
	 */
	@BeforeTest
	public void beforeTest() {

		test = extent
				.startTest(this.getClass().getSimpleName(),
						"To verify that user receives a notification when he is followed by someone")
				.assignCategory("Suite F");

	}

	@Test
	public void testcaseF1() throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "F Suite");
		boolean testRunmode = TestUtil.isTestCaseRunnable(suiteFxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {
			// 1)Create User1 and logout
			openBrowser();
			maximizeWindow();
			clearCookies();
			fn1 = generateRandomName(8);
			ln1 = generateRandomName(10);
			user1 = createNewUser(fn1, ln1);
			logout();
			Thread.sleep(5000);

			closeBrowser();

			// 2)Create User2 and follow User1
			openBrowser();
			maximizeWindow();
			clearCookies();
			fn2 = generateRandomName(8);
			ln2 = generateRandomName(10);
			user2 = createNewUser(fn2, ln2);

			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(fn1 + " " + ln1);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			Thread.sleep(4000);

			JavascriptExecutor jse = (JavascriptExecutor) ob;
			jse.executeScript("scroll(0,-500)");
			Thread.sleep(2000);

			ob.findElement(By.xpath(OR.getProperty("profilesTabHeading_link"))).click();
			Thread.sleep(2000);
			ob.findElement(By.xpath(OR.getProperty("search_follow_button"))).click();
			Thread.sleep(2000);
			logout();
			Thread.sleep(5000);

			// 3)Verify that User1 receives a notification with correct data
			ob.findElement(By.xpath(OR.getProperty("TR_login_button"))).click();
			Thread.sleep(4000);
			ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).clear();
			ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).sendKeys(user1);
			ob.findElement(By.id(OR.getProperty("TR_password_textBox")))
					.sendKeys(CONFIG.getProperty("defaultPassword"));
			ob.findElement(By.id(OR.getProperty("login_button"))).click();
			Thread.sleep(15000);

			if (!checkElementPresence("notification")) {

				test.log(LogStatus.FAIL, "User not receiving notification");// extent
																			// reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_user_not_receiving_notification")));// screenshot
				closeBrowser();
				return;
			}

			String text = ob.findElement(By.xpath(OR.getProperty("notification"))).getText();
			System.out.println(text);
			String expected_text = fn2 + " " + ln2 + " is now following you";

			try {
				Assert.assertTrue(text.contains(expected_text) && text.contains("TODAY"));
				test.log(LogStatus.PASS, "User receiving notification with correct content");
			} catch (Throwable t) {

				test.log(LogStatus.FAIL, "User receiving notification with incorrect content");// extent
																								// reports
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "_user_receiving_notification_with_incorrect_content")));// screenshot

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
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		if (status == 1)
			TestUtil.reportDataSetResult(suiteFxls, "Test Cases",
					TestUtil.getRowNum(suiteFxls, this.getClass().getSimpleName()), "PASS");
		else if (status == 2)
			TestUtil.reportDataSetResult(suiteFxls, "Test Cases",
					TestUtil.getRowNum(suiteFxls, this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteFxls, "Test Cases",
					TestUtil.getRowNum(suiteFxls, this.getClass().getSimpleName()), "SKIP");

	}

}
