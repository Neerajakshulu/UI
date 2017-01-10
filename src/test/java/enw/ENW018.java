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
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;

public class ENW018 extends TestBase {

	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("ENW");
	}

	@Test
	public void testcaseENW018() throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;
		String expected_URL = "Thank You";

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
			ob.navigate().to(host);			
			loginAs("MARKETUSEREMAIL", "MARKETUSERPASSWORD");
			pf.getHFPageInstance(ob).clickProfileImage();
			BrowserWaits.waitTime(2);
			jsClick(ob, ob.findElement(By.xpath(OnePObjectMap.NEON_HELP_FEEDBACK_XPATH.toString())));
			jsClick(ob, ob.findElement(By.xpath(OnePObjectMap.NEON_SEND_FEEDBACK_TO_NEONTEAM_XPATH.toString())));
			BrowserWaits.waitTime(3);
			ob.findElement(By.xpath(OnePObjectMap.COMMON_FEEDBACK_COMMENTS_XPATH.toString()))
					.sendKeys("Feedback sending");
			BrowserWaits.waitTime(5);
			jsClick(ob, ob.findElement(By.xpath(OnePObjectMap.COMMON_FEEDBACK_SUBMIT_BTN_XPATH.toString())));
			//BrowserWaits.waitTime(7);
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.FEEDBACK_THANKU_PAGE_XPATH);
			String str = ob.findElement(By.xpath(OnePObjectMap.FEEDBACK_THANKU_PAGE_XPATH.toString())).getText();
			try {
				Assert.assertEquals(expected_URL, str);
				test.log(LogStatus.PASS, " Feedback has  been sent successfully.");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Feedback not sent successfully..");// extent
				ErrorUtil.addVerificationFailure(t); // reports
				status = 2;// excel
				test.log(LogStatus.INFO,
						"Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
								+ "Feedback New window is not displayed and content is not matching")));// screenshot
			}
			closeBrowser();
			test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
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
		
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);
	}
}
