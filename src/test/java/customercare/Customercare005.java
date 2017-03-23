
package customercare;

import java.io.PrintWriter;
import java.io.StringWriter;

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

public class Customercare005 extends TestBase {

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
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("customercare");
	}

	/**
	 * Method for login into Neon application using TR ID
	 * 
	 * @throws Exception
	 *             , When TR Login is not done
	 */
	@Test
	public void testcaseIPA1() throws Exception {
		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;
		logger.info("checking master condition status-->" + this.getClass().getSimpleName() + "-->" + master_condition);

		if (!master_condition) {
			status = 3;
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts ");

		try {

			openBrowser();
			maximizeWindow();
			clearCookies();
			ob.navigate().to(host + CONFIG.getProperty("appendPACCAppUrl"));
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.IPA_CC_NAME_CSS);
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.IPA_CC_ORG_NAME_CSS);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.IPA_CC_ORG_NAME_CSS);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.IPA_CC_NAME_CSS);
			String Name = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.IPA_CC_NAME_CSS).getText();
			String Organization = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.IPA_CC_ORG_NAME_CSS)
					.getText();

			try {
				if (Organization.equals("")) {
					Assert.assertTrue(pf.getIpaPage(ob).validateCustomerCareNameErrorMessage());
					test.log(LogStatus.PASS,
							"Error message 'Please enter at least 2 characters for name' is displayed when name field is empty");
				}
			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"Error message 'Please enter at least 2 characters for name' is not displayed when name field is empty");
				closeBrowser();
			}
			try {
				if (Name.equals("")) {
					Assert.assertTrue(pf.getIpaPage(ob).validateCustomerCareOrgNameErrorMessage());
					test.log(LogStatus.PASS,
							"Error message 'Please enter at least 2 characters for Organization' is displayed when Organization name field is empty");
				}
			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"Error message 'Please enter at least 2 characters for Organization name' is not displayed when Organization name field is empty");
				closeBrowser();
			}
			try {
				ob.navigate().refresh();

				Assert.assertTrue(pf.getIpaPage(ob).validateCustomerCareNameErrorMessage());
				test.log(LogStatus.PASS,
						"Error message 'Please enter at least 2 characters for name' is displayed when name field contains 1 character");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"Error message 'Please enter at least 2 characters for name' is not displayed when name field contains 1 character");
				closeBrowser();
			}
			try {
				ob.navigate().refresh();
				Assert.assertTrue(pf.getIpaPage(ob).validateCustomerCareOrgNameErrorMessage());
				test.log(LogStatus.PASS,
						"Error message 'Please enter at least 2 characters for name' is displayed when Organization name field contains 1 character");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL,
						"Error message 'Please enter at least 2 characters for name' is not displayed when Organization name field contains 1 character");
				closeBrowser();
			}
			closeBrowser();
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

	}
}
