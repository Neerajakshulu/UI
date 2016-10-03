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

public class ENWIAM40 extends TestBase {

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
	public void testcaseh1() throws Exception {
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

			ob.navigate().to(host);

			// login using TR credentials
			pf.getLoginTRInstance(ob).enterTRCredentials(CONFIG.getProperty("sru_steamusername"),
					CONFIG.getProperty("sru_steampwd"));
			pf.getLoginTRInstance(ob).clickLogin();

			// Click on the app switcher
			pf.getHFPageInstance(ob).clickOnEndNoteLink();

			// Navigation from Neon to Enw
			waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.ENW_HOME_CONTINUE_XPATH.toString()), 30);
			String actualTitle = ob.getTitle();
			String expectedTitle = "EndNote";

			try {

				Assert.assertEquals(actualTitle, expectedTitle);
				test.log(LogStatus.PASS, "User is able to navigate from Neon to EndNote");
				WebElement click_continue = ob.findElement(By.xpath(OnePObjectMap.ENW_HOME_CONTINUE_XPATH.toString()));
				jsClick(ob, click_continue);
				// waitForElementTobeVisible(ob,
				// By.xpath(OnePObjectMap.ENDNOTE_HEADER_LOGO_XPATH.toString()),
				// 30);
				// Navigation from ENW to Neon
				pf.getEnwReferenceInstance(ob).clickOnProjectNeonLink();
				BrowserWaits.waitTime(4);
				String actual_Neon_Title = ob.getTitle();
				String expected_Neon_Title = "Thomson Reuters - Project Neon";

				try {
					Assert.assertEquals(actual_Neon_Title, expected_Neon_Title);
					test.log(LogStatus.PASS, "User is able to navigate from EndNote to Neon");

				} catch (Throwable t) {
					t.printStackTrace();
					test.log(LogStatus.FAIL, "User is not able to navigate from Endnote to Neon");
					ErrorUtil.addVerificationFailure(t);
				}
			}

			catch (Throwable t) {
				t.printStackTrace();
				test.log(LogStatus.FAIL, "User is not able to navigate from Neon to EndNote");
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);

			}

			logout();
			BrowserWaits.waitTime(4);
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
