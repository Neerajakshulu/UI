package dra;

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

public class DRA0013 extends TestBase {

	static int status = 1;
	static boolean fail = false;

	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("ENWIAM");

	}

	@Test
	public void testcaseDRA0013() throws Exception {

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
			String email = "iliya89@k3663a40w.com";
			openBrowser();
			clearCookies();
			maximizeWindow();
			ob.navigate().to(host + CONFIG.getProperty("appendDRAAppUrl"));
			test.log(LogStatus.PASS, "User is succeccfully sent to the DRA landing page. ");
			String css = ob.findElement(By.cssSelector(OnePObjectMap.DRA_BGCOLOR_CLASS_CSS.toString()))
					.getCssValue("background-color");
			String cssValue = ob.findElement(By.cssSelector(OnePObjectMap.LOGIN_PAGE_SIGN_IN_BUTTON_CSS.toString()))
					.getCssValue("background-color");
			if (cssValue.contains("rgba(177, 205, 67, 1)")) {
				test.log(LogStatus.INFO, "Sign in button is  displayed in  application specific green colour");
			} else {
				test.log(LogStatus.INFO, "check box is not selected by default");
				status = 2;
			}
			String cssValue1 = ob.findElement(By.cssSelector("html[class='unauth-page-dra']"))
					.getCssValue("background");
			logger.info("Values : " + cssValue);

			if (cssValue1.contains("rgb(99, 116, 31)") && cssValue1.contains("rgb(177, 205, 67)")) {
				test.log(LogStatus.PASS, " DRA Landing page displayed  in  application specific green colour");
			} else {
				test.log(LogStatus.FAIL, "check box is not selected by default");
				status = 2;
			}
			try {
				boolean ForgotPasswordLink = checkElementIsDisplayed(ob,
						By.xpath(OR.getProperty("forgot_password_link").toString()));
				Assert.assertEquals(ForgotPasswordLink, true);
				test.log(LogStatus.PASS, "DRA Landing page displays forgot password link");
			} catch (Throwable t) {
				t.printStackTrace();
				test.log(LogStatus.FAIL, "DRA Landing page doesn't display forgot password link");
				ErrorUtil.addVerificationFailure(t);
			}
			ob.findElement(By.xpath(OR.getProperty("forgot_password_link"))).click();

			// BrowserWaits.waitTime(4);
			waitForElementTobeVisible(ob, By.id(OR.getProperty("email_Address")), 30);
			ob.findElement(By.id(OR.getProperty("email_Address"))).sendKeys(email);
			ob.findElement(By.xpath(OR.getProperty("verification_email_button"))).click();
			test.log(LogStatus.PASS, "'Send verification button' is clicked");
			BrowserWaits.waitTime(3);

			// waitForElementTobeVisible(ob,
			// By.xpath(OR.getProperty("confomr_message")), 30);
			String Msg = "An email with password reset instructions has been sent to " + email;
			try {

				String textConfirm = ob.findElement(By.xpath(OR.getProperty("confomr_message").toString())).getText();
				Assert.assertEquals(textConfirm, Msg);
				test.log(LogStatus.PASS, "An email with password reset instructions has been sent to the user");

			} catch (Throwable t) {
				t.printStackTrace();
				test.log(LogStatus.FAIL, "An email with password reset instructions has not been sent to the user");
				ErrorUtil.addVerificationFailure(t);
			}

			ob.close();

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
			ErrorUtil.addVerificationFailure(t);

			closeBrowser();
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}

	public void logoutDRA() throws Exception {

		BrowserWaits.waitTime(4);
		jsClick(ob, ob.findElement(By.cssSelector(OnePObjectMap.DRA_PROFILE_CSS.toString())));
		waitForAjax(ob);
		waitForAllElementsToBePresent(ob, By.cssSelector(OnePObjectMap.DRA_SIGNOUT_BUTTON_CSS.toString()), 60);
		jsClick(ob, ob.findElement(By.cssSelector(OnePObjectMap.DRA_SIGNOUT_BUTTON_CSS.toString())));
		BrowserWaits.waitTime(3);
	}

	public void clickLoginDRA() throws Exception {
		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.LOGIN_PAGE_SIGN_IN_BUTTON_CSS);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.DRA_SEARCH_BOX_CSS);

	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

	}
}