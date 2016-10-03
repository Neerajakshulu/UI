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

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;
import util.TestUtil;

public class ENWIAM51 extends TestBase {

	static int count = -1;

	static boolean fail = false;
	static boolean skip = false;
	static int status = 1;
	static String followBefore = null;
	static String followAfter = null;

	/**
	 * Method for displaying JIRA ID's for test case in specified path of Extent Reports
	 * 
	 * @throws Exception , When Something unexpected
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
	 * @throws Exception , When TR Login is not done
	 */
	@Test
	public void testcaseh11() throws Exception {
		boolean testRunmode = TestUtil.isTestCaseRunnable(enwiamxls, this.getClass().getSimpleName());
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
			ob.get(host + CONFIG.getProperty("appendENWAppUrl"));

			// Verify EndNote landing page displays EndNote branding and
			// marketing copy

			try {
				WebElement b_element = ob.findElement(By.xpath(OnePObjectMap.NEON_ENW_COMPANY_XPATH.toString()));
				WebElement m_element = ob.findElement(By.xpath(OnePObjectMap.ENW_MARKETING_COPY_XPATH.toString()));

				String branding_name = b_element.getText();
				String marketing_Copy = m_element.getText();

				if (b_element.isDisplayed() && m_element.isDisplayed()) {
					Assert.assertEquals(branding_name, "Thomson Reuters");
					Assert.assertEquals(marketing_Copy, "EndNote");
					test.log(LogStatus.PASS, "EndNote Landing page displays EndNote branding and marketing copy");
				}

			} catch (Throwable t) {
				t.printStackTrace();
				test.log(LogStatus.FAIL, "EndNote Landing page doesn't displays EndNote branding and marketing copy");
				ErrorUtil.addVerificationFailure(t);

			}

			// Verify EndNote landing page displays Integration with EndNote
			WebElement integrationmsg = ob
					.findElement(By.xpath(OnePObjectMap.NEON_ENW_INTEGRATION_TEXT_XPATH.toString()));
			String actual_text = integrationmsg.getText();
			String expected_text = "You can use your Web of Science™, EndNote™, or ResearcherID credentials to sign in.";

			try {
				if (integrationmsg.isDisplayed()) {
					Assert.assertEquals(actual_text, expected_text);
					test.log(LogStatus.PASS, "EndNote Landing page displays integration with Neon");
				}
			} catch (Throwable t) {
				t.printStackTrace();
				test.log(LogStatus.FAIL, "EndNote Landing page doesn't display integration with Neon");
				ErrorUtil.addVerificationFailure(t);
			}

			// Verifying that on ENW landing page, link to login with Shibboleth
			// is displayed.
			try {
				boolean shibbLink = checkElementIsDisplayed(ob,
						By.cssSelector(OnePObjectMap.ENW_SHIBB_LINK_CSS.toString()));
				Assert.assertEquals(shibbLink, true);
				test.log(LogStatus.PASS, "EndNote Landing page displays link to login with Shibboleth");
			} catch (Throwable t) {
				t.printStackTrace();
				test.log(LogStatus.FAIL, "EndNote Landing page doesn't display link to login with Shibboleth");
				ErrorUtil.addVerificationFailure(t);
			}

			// verifying that on ENW landing page displays, informational text
			// that explains the trial link for Endnote desktop.

			try {
				boolean endnote_desktop_text = checkElementIsDisplayed(ob,
						By.cssSelector(OnePObjectMap.ENW_DESKTOP_TEXT_CSS.toString()));
				Assert.assertEquals(endnote_desktop_text, true);
				test.log(LogStatus.PASS,
						"EndNote Landing page displays text that explains the trial link for Endnote desktop");

			} catch (Throwable t) {
				t.printStackTrace();
				test.log(LogStatus.FAIL,
						"EndNote Landing page doesn't display text that explains the trial link for Endnote desktop");
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
