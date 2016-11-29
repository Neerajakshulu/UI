package dra;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

public class DRA0011 extends TestBase {

	static int status = 1;
	static boolean fail = false;

	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("ENWIAM");

	}

	@Test
	public void testcaseDRA0010() throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;
		// static boolean fail = false;

		if (!master_condition) {

			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		try {

			openBrowser();
			clearCookies();
			maximizeWindow();
			ob.navigate().to(host + CONFIG.getProperty("appendDRAAppUrl"));

			pf.getLoginTRInstance(ob).enterTRCredentials(LOGIN.getProperty("DRAUSER0011"),
					LOGIN.getProperty("DRAUSERPWD11"));
			pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.LOGIN_PAGE_SIGN_IN_BUTTON_CSS);

			BrowserWaits.waitTime(5);
			waitForElementTobeVisible(ob, By.name(OR.getProperty("TR_email_textBox")), 30);
			waitForElementTobeVisible(ob, By.name(OR.getProperty("TR_password_textBox")), 30);

			test.log(LogStatus.PASS,
					"user is not allowed to access DRA as the STeAM account is not tied to an active subscription with the 'DRA_TARGET_DRUG' entitlement (SKU).");

			ob.close();

		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent

			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
			ErrorUtil.addVerificationFailure(t);

			closeBrowser();
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}

	public void clickLoginDRA() throws Exception {

		// pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.DRA_SEARCH_BOX_CSS);

	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

	}
}
