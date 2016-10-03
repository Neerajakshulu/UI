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

public class ENW009 extends TestBase {

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
	public void testcaseENW009() throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
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
			String header_Expected = "Thomson Reuters";
			loginAs("NONMARKETUSEREMAIL", "NONMARKETUSERPASSWORD");

			if (ob.findElement(By.xpath(OnePObjectMap.ENW_CONTINUE_DIOLOG_BOX.toString())).isEnabled()) {
				// pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.ENW_CONTINUE_BUTTON);
				ob.findElement(By.xpath(OR.getProperty("ENW_CONTINUE_BUTTON"))).click();
			}
			String actual_result = ob.findElement(By.xpath(OnePObjectMap.ENW_Header_XPATH.toString())).getText();
			logger.info("Header Text displayed as:" + actual_result);

			if (ob.findElement(By.className("inactiveLink")) == null) {
				logger.info("Header text is enabled and it's HyperLinked");
				logger.info("Header Text displayed as:" + actual_result);
				actual_result = "test case is failed";
			}
			logger.info("Actual result displayed as :" + actual_result
					+ " text without the hot link and not allow user to Navigate to Neon");
			try {
				Assert.assertEquals(header_Expected, actual_result);
				test.log(LogStatus.PASS, " Header Logo text is displayed properly for Non-Market users");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, " Header Logo text is not displayed properly for Non-Market users");// extent
				ErrorUtil.addVerificationFailure(t);// testng
													// reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "Header Text is displayed wrongly and its Hyperlinked")));// screenshot
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
