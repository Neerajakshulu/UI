package suiteD;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.RandomStringUtils;
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
import util.TestUtil;

public class ProfileSummaryTest extends TestBase {
	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() {

		test = extent.startTest(this.getClass().getSimpleName(), "Add summary field maximum lenghth validation")
				.assignCategory("Suite D");

	}

	@Test
	public void testprofileSummary() throws Exception {
		String str = RandomStringUtils.randomAlphabetic(1600);
		int maxLength = 1500;
		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "D Suite");
		boolean testRunmode = TestUtil.isTestCaseRunnable(suiteDxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		try {

			openBrowser();
			maximizeWindow();
			clearCookies();

			// Navigate to TR login page and login with valid TR credentials
			ob.navigate().to(host);
			Thread.sleep(8000);
			login();
			Thread.sleep(15000);
			ob.findElement(By.cssSelector(OR.getProperty("tr_profile_dropdown_css"))).click();
			BrowserWaits.waitUntilText("Profile");
			ob.findElement(By.linkText(OR.getProperty("tr_profile_link"))).click();
			try {
				waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_profile_add_summary_css")), 10);
				ob.findElement(By.cssSelector(OR.getProperty("tr_profile_add_summary_css"))).click();

			} catch (Exception e1) {
				ob.findElement(By.cssSelector(OR.getProperty("tr_profile_edit_button_css"))).click();
				Thread.sleep(2000);
				ob.findElement(By.cssSelector(OR.getProperty("tr_profile_summary_textarea_css"))).clear();
				ob.findElement(By.cssSelector(OR.getProperty("tr_profile_update_button_css"))).click();
				Thread.sleep(4000);
				ob.findElement(By.cssSelector(OR.getProperty("tr_profile_add_summary_css"))).click();
			}

			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_profile_summary_textarea_css")), 30);
			ob.findElement(By.cssSelector(OR.getProperty("tr_profile_summary_textarea_css"))).sendKeys(str);
			scrollElementIntoView(ob, ob.findElement(By.cssSelector(OR.getProperty("tr_profile_update_button_css"))));
			jsClick(ob, ob.findElement(By.cssSelector(OR.getProperty("tr_profile_update_button_css"))));
			ob.navigate().refresh();
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr-profile_summary_css")), 30);
			String str3 = ob.findElement(By.cssSelector(OR.getProperty("tr-profile_summary_css"))).getText();
			try {
				Assert.assertEquals(str3.length(), maxLength);
				test.log(LogStatus.PASS, "Maximum length for add summary field validation success");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Maximum length for add summary field validation Failed");
				test.log(LogStatus.INFO, "Error--->" + t);
				status = 2;
				ErrorUtil.addVerificationFailure(t);
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "maximum length validation failed for summary field")));// screenshot

			}

			logout();
			Thread.sleep(5000);

			closeBrowser();

		}

		catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent reports
			// next 3 lines to print whole testng error in report
			status = 2;// excel
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

		if (status == 1)
			TestUtil.reportDataSetResult(suiteDxls, "Test Cases",
					TestUtil.getRowNum(suiteDxls, this.getClass().getSimpleName()), "PASS");
		else if (status == 2)
			TestUtil.reportDataSetResult(suiteDxls, "Test Cases",
					TestUtil.getRowNum(suiteDxls, this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteDxls, "Test Cases",
					TestUtil.getRowNum(suiteDxls, this.getClass().getSimpleName()), "SKIP");

	}

}
