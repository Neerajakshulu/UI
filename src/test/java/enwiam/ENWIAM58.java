package enwiam;

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

public class ENWIAM58 extends TestBase {

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
	public void testcaseh18() throws Exception {
		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;
		logger.info("checking master condition status-->" + this.getClass().getSimpleName() + "-->" + master_condition);

		if (!master_condition) {
			status = 3;
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}

		try {
			String statuCode = deleteUserAccounts(LOGIN.getProperty("sru_fbusername12"));
			
			if (!(statuCode.equalsIgnoreCase("200") || statuCode.equalsIgnoreCase("400"))) {
				// test.log(LogStatus.FAIL, "Delete accounts api call failed");
				throw new Exception("Delete API Call failed");
			}


		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Delete accounts api call failed");// extent
			ErrorUtil.addVerificationFailure(t);
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts ");

		try {

			openBrowser();
			maximizeWindow();
			clearCookies();

			// Making the social account Neon Active

			ob.navigate().to(host);
			pf.getLoginTRInstance(ob).loginWithFBCredentials(LOGIN.getProperty("sru_fbusername12"),
					LOGIN.getProperty("sru_fbpwd12"));
			test.log(LogStatus.PASS, "user has logged in with social account");
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_BOX_CSS);
			pf.getLoginTRInstance(ob).closeOnBoardingModal();
			pf.getHFPageInstance(ob).clickOnAccountLink();
			BrowserWaits.waitTime(2);
			String accountType = "Facebook";

			validateAccounts(1, accountType);
			
			for (int j = 1; j <= 10; j++) {
				logger.info("Creating " + j + " Watchlist");
				pf.getLinkingModalsInstance(ob).toMakeAccountNeonActive();
			}

			test.log(LogStatus.PASS, "Social account is made Neon active ");

			pf.getLoginTRInstance(ob).logOutApp();
			BrowserWaits.waitTime(2);

			// Making the Steam account Neon active

			try {
				ob.navigate().to(host + CONFIG.getProperty("appendENWAppUrl"));
				pf.getLoginTRInstance(ob).enterTRCredentials(LOGIN.getProperty("sru_steam12"),
						LOGIN.getProperty("sru_steampw12"));
				pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.LOGIN_PAGE_SIGN_IN_BUTTON_CSS);
				pf.getBrowserWaitsInstance(ob)
						.waitUntilElementIsDisplayed(OnePObjectMap.ALREADY_HAVE_AN_ACCOUNT_NOTNOW_CSS);
				pf.getLinkingModalsInstance(ob).clickOnNotNowButton();

			} catch (Throwable t) {
				closeBrowser();
				t.printStackTrace();
				test.log(LogStatus.FAIL, "User is not able to skip linking");
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_Not_able_to_skip_link")));
			}
			try {
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.ENW_HOME_AGREE_CSS);
				pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.ENW_HOME_AGREE_CSS);
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.ENW_HOME_CONTINUE_XPATH);
				pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.ENW_HOME_CONTINUE_XPATH);
			} catch (Exception e) {
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.ENW_HOME_CONTINUE_XPATH);
				pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.ENW_HOME_CONTINUE_XPATH);
			}

			pf.getEnwReferenceInstance(ob).clickOnProjectNeonLink();
			pf.getLoginTRInstance(ob).closeOnBoardingModal();
			pf.getHFPageInstance(ob).clickOnAccountLink();
			BrowserWaits.waitTime(2);
			accountType = "Neon";

			validateNeonAccount(1, accountType);
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.HOME_PROJECT_NEON_HEADER_CSS);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.HOME_PROJECT_NEON_HEADER_CSS);

			for (int j = 1; j <= 10; j++) {
				logger.info("Creating " + j + " Watchlist");
				pf.getLinkingModalsInstance(ob).toMakeAccountNeonActive();
			}

			test.log(LogStatus.PASS, "Steam account is made Neon active ");

			pf.getLoginTRInstance(ob).logOutApp();
			BrowserWaits.waitTime(2);

			// Trying to Link the accounts
			try {
				ob.navigate().to(host + CONFIG.getProperty("appendENWAppUrl"));
				pf.getLinkingModalsInstance(ob).clickOnSignInWithFB();
				pf.getENWReferencePageInstance(ob).didYouKnow(LOGIN.getProperty("sru_steampw12"));
				test.log(LogStatus.INFO, "Trying to link two Neon active accounts ");
				pf.getBrowserWaitsInstance(ob)
						.waitUntilElementIsDisplayed(OnePObjectMap.CONTACT_CUSTOMER_SUPPORT_TITLE_CSS);
				String actual_msg = pf.getLinkingModalsInstance(ob).getCustomerSupportMsg();
				if (actual_msg.contains("We're sorry. We are unable to link your accounts."))
					test.log(LogStatus.PASS,
							"Accounts cannot be merged and contact customer support message is displayed");
				pf.getLinkingModalsInstance(ob).clickOnOkButton();
			}

			catch (Throwable t) {

				test.log(LogStatus.FAIL, "contact customer support message is not displayed");// extent
				// reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass()
						.getSimpleName()
						+ "_more_search_results_do_not_get_displayed_when_user_scrolls_down_in_ALL_search_results_page")));// screenshot
				ErrorUtil.addVerificationFailure(t);

			}

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

		}

		BrowserWaits.waitTime(2);
		closeBrowser();
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");

	}

	private void validateNeonAccount(int accountCount, String linkName) throws Exception {
		try {
			Assert.assertTrue(
					pf.getAccountPageInstance(ob).verifyLinkedAccount(linkName, LOGIN.getProperty("sru_steam12")));
			Assert.assertTrue(pf.getAccountPageInstance(ob).validateAccountsCount(accountCount));
			test.log(LogStatus.PASS, "Single Steam account is available and is not linked to Social account");

		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Linked accounts are available in accounts page");
			ErrorUtil.addVerificationFailure(t);// testng
			test.log(LogStatus.INFO, "Snapshot below: "
					+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "_failed")));// screenshot
		}
	}

	private void validateAccounts(int accountCount, String linkName) throws Exception {
		try {
			Assert.assertTrue(
					pf.getAccountPageInstance(ob).verifyLinkedAccount(linkName, LOGIN.getProperty("sru_fbusername12")));
			Assert.assertTrue(pf.getAccountPageInstance(ob).validateAccountsCount(accountCount));
			test.log(LogStatus.PASS, "Single " + linkName + " account is available and is not linked");

		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Linked accounts are available in accounts page");
			ErrorUtil.addVerificationFailure(t);// testng
			test.log(LogStatus.INFO, "Snapshot below: "
					+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "_failed")));// screenshot
		}
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

	}
}
