package suiteF;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.ErrorUtil;
import util.TestUtil;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class TestCase_F3 extends TestBase {
	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		String var = xlRead(returnExcelPath(this.getClass().getSimpleName().charAt(9)),
				Integer.parseInt(this.getClass().getSimpleName().substring(10) + ""), 1);
		test = extent
				.startTest(var,
						"Verify that user receives a notificatication when his follower comments on an article contained in his watchlist")
				.assignCategory("Suite F");

	}

	@Test
	public void testcaseF3() throws Exception {

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

			// 1)Login with user1,add an article to watchlist and logout

			openBrowser();
			maximizeWindow();
			clearCookies();

			ob.navigate().to(host);
			Thread.sleep(8000);

			ob.findElement(By.xpath(OR.getProperty("TR_login_button"))).click();
			Thread.sleep(4000);
			ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).sendKeys(user1);
			ob.findElement(By.id(OR.getProperty("TR_password_textBox")))
					.sendKeys(CONFIG.getProperty("defaultPassword"));
			ob.findElement(By.id(OR.getProperty("login_button"))).click();
			Thread.sleep(15000);

			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("tiger");
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			Thread.sleep(4000);

			String document_title = ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).getText();
			ob.findElement(By.xpath(OR.getProperty("search_watchlist_image"))).click();
			Thread.sleep(1000);

			logout();
			Thread.sleep(5000);

			// 2)Login with user2,comment on article contained in user1's
			// watchlist and logout
			ob.findElement(By.xpath(OR.getProperty("TR_login_button"))).click();
			Thread.sleep(4000);
			ob.findElement(By.id("userid")).clear();
			ob.findElement(By.id("userid")).sendKeys(user2);
			ob.findElement(By.id("password")).sendKeys(CONFIG.getProperty("defaultPassword"));
			ob.findElement(By.id(OR.getProperty("login_button"))).click();
			Thread.sleep(15000);

			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("tiger");
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			Thread.sleep(4000);

			ob.findElement(By.xpath(OR.getProperty("searchResults_links"))).click();
			Thread.sleep(4000);
			ob.findElement(By.xpath(OR.getProperty("document_comment_textbox"))).sendKeys("green tea");
			Thread.sleep(5000);
			jsClick(ob, ob.findElement(By.xpath(OR.getProperty("document_addComment_button"))));
			Thread.sleep(2000);
			logout();
			Thread.sleep(5000);

			// 3)Login with user1 again and verify that he receives a correct
			// notification
			ob.findElement(By.xpath(OR.getProperty("TR_login_button"))).click();
			Thread.sleep(4000);
			ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).clear();
			ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).sendKeys(user1);
			ob.findElement(By.id(OR.getProperty("TR_password_textBox")))
					.sendKeys(CONFIG.getProperty("defaultPassword"));
			ob.findElement(By.id(OR.getProperty("login_button"))).click();
			Thread.sleep(25000);

			String text = ob.findElement(By.xpath(OR.getProperty("notification"))).getText();
			System.out.println(text);

			String expected_text = fn2 + " " + ln2;

			try {
				Assert.assertTrue(text.contains(expected_text) && text.contains("TODAY")
						&& text.contains(document_title) && text.contains("green tea"));
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
