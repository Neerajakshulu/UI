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

public class ENW006 extends TestBase {

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
	public void testcaseENW006() throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {
			String statuCode = deleteUserAccounts(CONFIG.getProperty("fbusername1"));
			Assert.assertTrue(statuCode.equalsIgnoreCase("200") || statuCode.equalsIgnoreCase("400"));

//			String statuCode1 = deleteUserAccounts(CONFIG.getProperty("Steamonlyuser"));
//			Assert.assertTrue(statuCode1.equalsIgnoreCase("200") || statuCode.equalsIgnoreCase("400"));

		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Delete accounts api call failed");// extent
			ErrorUtil.addVerificationFailure(t);
		}
		try {
			openBrowser();
			maximizeWindow();
			clearCookies();

			ob.get(host);

		//	String expectedSuccessMessage = "Sent To EndNote";

			pf.getLoginTRInstance(ob).loginWithFBCredentials(CONFIG.getProperty("fbusername1"),
					CONFIG.getProperty("fbpwrd1"));

			pf.getAuthoringInstance(ob).searchArticle(CONFIG.getProperty("article"));

			pf.getSearchResultsPageInstance(ob).clickSendToEndnoteSearchPage();

			ob.findElement(By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_LINKIINGMODAl_CLOSE_BUTTON_CSS.toString()))
					.click();
			BrowserWaits.waitTime(5);
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_BOX_CSS);
			pf.getSearchResultsPageInstance(ob).clickSendToEndnoteSearchPage();
			// new Actions(ob).moveByOffset(200, 200).click().build().perform();
			// pf.getSearchResultsPageInstance(ob).clickSendToEndnoteSearchPage();

			pf.getSearchResultsPageInstance(ob).linkDiffSteamAcctWhileSendToEndnoteSearchPage(test);

//			try {
//				logger.info("Actual label text after send to EndNote-->"+pf.getSearchResultsPageInstance(ob).ValidateSendToEndnoteSearchPage());
//				Assert.assertEquals(expectedSuccessMessage,
//						pf.getSearchResultsPageInstance(ob).ValidateSendToEndnoteSearchPage());
//				test.log(LogStatus.PASS,
//						" Record sent successfully from Search Results Page after linking with steam account with different emailid");
//
//			}
//
//			catch (Throwable t) {
//
//				test.log(LogStatus.FAIL,
//						" Record is not sent to Endnote from Search Results Page after  linking with steam account with different emailid");// extent
//				// reports
//				status = 2;// excel
//				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass()
//						.getSimpleName()
//						+ "_more_search_results_do_not_get_displayed_when_user_scrolls_down_in_ALL_search_results_page")));// screenshot
//				ErrorUtil.addVerificationFailure(t);
//			}

			test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
			pf.getLoginTRInstance(ob).logOutApp();
			closeBrowser();

		} catch (Throwable t) {
			logger.info("field");
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

	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

	
	}
}
