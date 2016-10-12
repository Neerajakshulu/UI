package enwiam;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.WebElement;
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

public class ENWIAM50 extends TestBase {

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
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("ENWIAM");
	}

	/**
	 * Method for login into Neon application using TR ID
	 * 
	 * @throws Exception
	 *             , When TR Login is not done
	 */
	@Test
	public void testcaseh10() throws Exception {
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

			ob.navigate().to(host);

			// Verify Neon landing page displays Branding and Marketing copy
			try {

				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.NEON_ENW_COMPANY_CSS);
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.NEON_MARKETING_COPY_XPATH);
				WebElement b_element = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.NEON_ENW_COMPANY_CSS);
				WebElement m_element = pf.getBrowserActionInstance(ob)
						.getElement(OnePObjectMap.NEON_MARKETING_COPY_XPATH);

				String branding_name = b_element.getText();
				String marketing_Copy = m_element.getText();

				if (b_element.isDisplayed() && m_element.isDisplayed()) {
					Assert.assertEquals(branding_name, "Thomson Reuters");
					Assert.assertEquals(marketing_Copy, "Project Neon");
					test.log(LogStatus.PASS, "Neon Landing page displays Neon branding and marketing copy");
				}

			} catch (Throwable t) {
				t.printStackTrace();
				test.log(LogStatus.FAIL, "Neon Landing page doesn't displays Neon branding and marketing copy");
				ErrorUtil.addVerificationFailure(t);

			}

			// Verify Neon landing page displays Integration with EndNote
			
			pf.getBrowserActionInstance(ob).scrollToElement(OnePObjectMap.NEON_ENW_INTEGRATION_TEXT_CSS);
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.NEON_ENW_INTEGRATION_TEXT_CSS);

			WebElement integrationmsg = pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.NEON_ENW_INTEGRATION_TEXT_CSS);
			String actual_text = integrationmsg.getText();
			//String expected_text = "You can use your Web of Science�, EndNote�, or ResearcherID credentials to sign in.";

			try {
				if (actual_text.contains("You can use your Web of Science") && actual_text.contains("EndNote") && actual_text.contains("or ResearcherID credentials to sign in"))
					// Assert.assertEquals(actual_text, expected_text);
					test.log(LogStatus.PASS, "Neon Landing page displays integration with Endnote");
				
			} catch (Throwable t) {
				t.printStackTrace();
				test.log(LogStatus.FAIL, "Neon Landing page doesn't display integration with Endnote");
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_Not_able_to_close_modal")));
			}

			// verify Neon Icon is displayed on Neon sign in page

			try {
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.NEON_CONNECT_ICON_CSS);
				WebElement icon_element = pf.getBrowserActionInstance(ob)
						.getElement(OnePObjectMap.NEON_CONNECT_ICON_CSS);
				if (icon_element.isDisplayed())
					test.log(LogStatus.PASS, "Neon Icon is displayed on Neon Landing Page");

			} catch (Throwable t) {
				t.printStackTrace();
				test.log(LogStatus.FAIL, "Neon Icon is not displayed on Neon Landing Page");
				ErrorUtil.addVerificationFailure(t);

			}

			BrowserWaits.waitTime(2);
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