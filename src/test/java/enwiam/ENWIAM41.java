package enwiam;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;
import util.TestUtil;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class ENWIAM41 extends TestBase {

	static int count = -1;

	static boolean fail = false;
	static boolean skip = false;
	static int status = 1;
	static String followBefore = null;
	static String followAfter = null;

	/**
	 * Method for displaying JIRA ID's for test case in specified path of Extent
	 * Reports
	 * 
	 * @throws Exception
	 *             , When Something unexpected
	 */

	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(),
				rowData.getTestcaseDescription()).assignCategory("ENWIAM");
	}

	/**
	 * Method for login into Neon application using TR ID
	 * 
	 * @throws Exception
	 *             , When TR Login is not done
	 */
	@Test
	public void testcaseh2() throws Exception {
		boolean testRunmode = TestUtil.isTestCaseRunnable(enwiamxls, this
				.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;
		logger.info("checking master condition status-->"
				+ this.getClass().getSimpleName() + "-->" + master_condition);

		if (!master_condition) {
			status = 3;
			test.log(LogStatus.SKIP, "Skipping test case "
					+ this.getClass().getSimpleName()
					+ " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case"
					+ this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}

		try {
			String statuCode = deleteUserAccounts(LOGIN
					.getProperty("sru_fbusername"));
			Assert.assertTrue(statuCode.equalsIgnoreCase("200"));
			String statuCode2 = deleteUserAccounts(LOGIN
					.getProperty("sru_fbpwd"));
			Assert.assertTrue(statuCode2.equalsIgnoreCase("200"));

		} catch (Throwable t) {
			test.log(LogStatus.INFO, "Delete accounts api call failed");// extent
			ErrorUtil.addVerificationFailure(t);
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName()
				+ " execution starts ");

		try {

			openBrowser();
			maximizeWindow();
			clearCookies();

			ob.navigate().to(host);

			pf.getLoginTRInstance(ob).loginWithFBCredentials(
					LOGIN.getProperty("sru_fbusername"),
					LOGIN.getProperty("sru_fbpwd"));
			test.log(LogStatus.PASS, "user has logged in with social account");
			pf.getHFPageInstance(ob).clickOnEndNoteLink();
			try {

				pf.getENWReferencePageInstance(ob).didYouKnow(
						LOGIN.getProperty("sru_steampwd"));
				test.log(LogStatus.PASS, "user is able to link");

				waitForElementTobeVisible(ob,
						By.xpath(OnePObjectMap.ENW_HOME_CONTINUE_XPATH
								.toString()), 30);

				boolean continue_button = checkElementIsDisplayed(
						ob,
						By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_CONTINUE_BUTTON_CSS
								.toString()));
				Assert.assertEquals(continue_button, true);
				test.log(LogStatus.PASS,
						"user is able navigate to EndNote after linking");

				ob.findElement(
						By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_CONTINUE_BUTTON_CSS
								.toString())).click();
			} catch (Throwable t) {
				t.printStackTrace();
				test.log(LogStatus.FAIL,
						"user is not able to link and navigate to EndNote");
				ErrorUtil.addVerificationFailure(t);

			}

			logoutEnw();

		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent
			// reports
			// next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng
			test.log(
					LogStatus.INFO,
					"Snapshot below: "
							+ test.addScreenCapture(captureScreenshot(this
									.getClass().getSimpleName()
									+ "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName()
				+ " execution ends--->");

	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

	}

}
