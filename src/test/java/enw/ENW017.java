package enw;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;
import util.TestUtil;

public class ENW017 extends TestBase {
	static int status = 1;

	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("ENW");
	}

	@Test
	public void testcaseENW017() throws Exception {
		boolean testRunmode = TestUtil.isTestCaseRunnable(enwxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;
		if (!master_condition) {

			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {

			openBrowser();
			maximizeWindow();
			clearCookies();

			ob.get(host + CONFIG.getProperty("appendENWAppUrl"));
			loginAs("MARKETUSEREMAIL", "MARKETUSERPASSWORD");
			if (ob.findElement(By.xpath(OnePObjectMap.ENW_CONTINUE_DIOLOG_BOX_XPATH.toString())).isDisplayed()) {

				ob.findElement(By.xpath(OR.getProperty("ENW_CONTINUE_BUTTON"))).click();
			}
			ob.findElement(By.xpath(OnePObjectMap.ENW_PROFILE_USER_ICON_XPATH.toString())).click();
			ob.findElement(By.xpath(OnePObjectMap.ENW_FEEDBACK_XPATH.toString())).click();
			ob.findElement(By.partialLinkText("Send feedback")).click();
			Thread.sleep(2000);
			ob.findElement(By.xpath(OnePObjectMap.COMMON_FEEDBACK_COMMENTS_XPATH.toString()))
					.sendKeys("Feedback sending");
			Thread.sleep(2000);
			jsClick(ob, ob.findElement(By.xpath("//button[contains(text(),'Submit')]")));
			Thread.sleep(2000);
			jsClick(ob, ob.findElement(By.xpath("//button[contains(text(),'Submit')]")));
			Thread.sleep(3000);
			if (!ob.findElement(By.xpath("//h4[contains(text(),'Your feedback has been sent')]")).isDisplayed()) {
				test.log(LogStatus.FAIL, "Feedback not sent");
				logger.info("Sorry, but we couldn't deliver your submission. Please fix these issues and try again:");
				Assert.assertEquals(true, false);
			} else {
				test.log(LogStatus.PASS, "Feedback has been sent successfully.");
				ob.findElement(By.xpath(OnePObjectMap.COMMON_FEEDBACK_CLOSE_XPATH.toString())).click();
				Thread.sleep(2000);
			}
			try {
				test.log(LogStatus.PASS, "Expected page is displayed and  Navigating to the proper URL.");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Expected page is not displayed and  URL is wrong.");// extent
				ErrorUtil.addVerificationFailure(t); // reports
				status = 2;// excel
				test.log(LogStatus.INFO,
						"Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
								+ "Feedback New window is not displayed and content is not matching")));// screenshot
			}
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent
																		// reports
			// next 3 lines to print whole testng error in report
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
		closeBrowser();
	}
}
