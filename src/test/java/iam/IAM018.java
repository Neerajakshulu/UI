package iam;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;

public class IAM018 extends TestBase {

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
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("IAM");
	}

	@Test
	public void testcaseA18() throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
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

			// Navigate to LI login page
			ob.navigate().to(host);
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("LI_login_button")), 30);
			ob.findElement(By.cssSelector(OR.getProperty("LI_login_button"))).click();
			//
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("LI_cancel_link")), 30);
			ob.findElement(By.xpath(OR.getProperty("LI_cancel_link"))).click();

			pf.getBrowserWaitsInstance(ob)
					.waitUntilElementIsDisplayed(OnePObjectMap.NEON_LANDING_PAGE_LOGGIN_BANNER_CSS);
			/*
			 * waitForElementTobeVisible(ob, By.xpath(OR.getProperty("login_banner")), 10); if
			 * (!checkElementPresence("login_banner")) { test.log(LogStatus.FAIL,
			 * "LI CANCEL button not working correctly");// extent reports status = 2;// excel test.log(LogStatus.INFO,
			 * "Snapshot below: " + test.addScreenCapture( captureScreenshot(this.getClass().getSimpleName() +
			 * "_LI_CANCEL_button_not_working")));// screenshot }
			 */

			closeBrowser();

		}

		catch (Throwable t) {

			test.log(LogStatus.FAIL, "Something unexpected happened");// extent reports
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

		/*
		 * if(status==1) TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "PASS"); else if(status==2)
		 * TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "FAIL"); else
		 * TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "SKIP");
		 */
	}

}
