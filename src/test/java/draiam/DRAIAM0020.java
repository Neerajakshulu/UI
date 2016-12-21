package draiam;

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

public class DRAIAM0020 extends TestBase {

	static int status = 1;
	static boolean fail = false;

	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("DRAIAM");

	}

	@Test
	public void testcaseDRA0020() throws Exception {

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
			ob.navigate().to(host);
			pf.getLoginTRInstance(ob).loginWithFBCredentials(LOGIN.getProperty("DRAFBUSER0017"),
					LOGIN.getProperty("DRAFBUSERPWD17"));
			test.log(LogStatus.PASS, "user has logged in with social account");
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_BOX_CSS);
			pf.getDraPageInstance(ob).clickDRALink();
			test.log(LogStatus.PASS, "STeAM Step Up Auth Modal is displayed");

			waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.NEON_IPA_USERNAME_CSS.toString()), 30);
			pf.getDraPageInstance(ob).clickDRAStepUpAuthLoginSteam(test, LOGIN.getProperty("FBuserENWIAM0009"),
					LOGIN.getProperty("FBpwdENWIAM0009"));
			// pf.getDraPageInstance(ob).ValidateUnverifiedAcount();
			// pf.getDraPageInstance(ob).ValidateUnverifiedBUttons();
			String expectedmessage = "Please activate your account";
			String expectedbuttontext = "Resend activation";
			String actualmessage = pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.ENW_UNVERIFIED_MESSAGE_BUTTON_CSS).getText();
			String actualbuttontext = pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.ENW_RESEND_ACTIVATION_BUTTON_CSS).getText();

			try {
				Assert.assertEquals(actualmessage, expectedmessage);
				Assert.assertEquals(actualbuttontext, expectedbuttontext);
				// Assert.assertEquals(actualOKbuttontext, expectedbuttontext);
				test.log(LogStatus.PASS, "Facebook account is not linked with unverified Steam account ");

			} catch (Throwable t) {

				t.printStackTrace();
				test.log(LogStatus.FAIL,
						"User is not able to see activation message or Resend activation button or OK button");
				ErrorUtil.addVerificationFailure(t);
				closeBrowser();

			}

			try {
				pf.getBrowserActionInstance(ob).click(OnePObjectMap.ENW_RESEND_ACTIVATION_BUTTON_CSS);
				waitForElementTobeVisible(ob,
						By.cssSelector(OnePObjectMap.ENW_RESEND_ACTIVATION_DONE_BUTTON_CSS.toString()), 30);
				test.log(LogStatus.PASS, "OK button is getting displayed when user clicks on resend activation ");
			} catch (Throwable t) {

				t.printStackTrace();
				test.log(LogStatus.FAIL, "User is not able to see  OK button");
				ErrorUtil.addVerificationFailure(t);
				closeBrowser();

			}
			BrowserWaits.waitTime(2);

			closeBrowser();
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent

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

	}
}
