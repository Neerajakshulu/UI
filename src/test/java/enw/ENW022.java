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

public class ENW022 extends TestBase {
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
	public void testcaseENW022() throws Exception {
		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;
		String expected_URL = "http://ip-science.thomsonreuters.com/support/";
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
			pf.getOnboardingModalsPageInstance(ob).ENWSTeamLogin(LOGIN.getProperty("NONMARKETUSEREMAIL"),
					(LOGIN.getProperty("NONMARKETUSERPASSWORD")));
			BrowserWaits.waitTime(6);
			jsClick(ob, ob.findElement(By.xpath(OnePObjectMap.ENW_PROFILE_USER_ICON_XPATH.toString())));
			BrowserWaits.waitTime(3);
			ob.findElement(By.xpath(OnePObjectMap.ENW_FEEDBACK_XPATH.toString())).click();
			String newWindow = switchToNewWindow(ob);
			if (newWindow != null) {
				if (ob.getCurrentUrl().contains(expected_URL)) {
					logger.info("Expected page is displayed and  Navigating to the proper URL");
				}
			} else {
				test.log(LogStatus.FAIL, "Expected page is not displayed and  URL is wrong.");
				Assert.assertEquals(true, false);

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
