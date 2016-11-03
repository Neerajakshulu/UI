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

public class ENWIAM080 extends TestBase {

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
	public void testcase080() throws Exception {
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
			String statuCode = deleteUserAccounts(LOGIN.getProperty("UserFBENWIAM80"));

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
			int watchlistCount = 10;
			openBrowser();
			maximizeWindow();
			clearCookies();
			ob.navigate().to(host);
			// Activating the facebook account

			pf.getLoginTRInstance(ob).loginWithFBCredentials(LOGIN.getProperty("UserFBENWIAM80"),
					LOGIN.getProperty("PWDUserFBENWIAM80"));
			test.log(LogStatus.PASS, "user has logged in with social account");

			String firstAccountProfileName = pf.getLinkingModalsInstance(ob).getProfileName();
			test.log(LogStatus.INFO, "Social account profile name: " + firstAccountProfileName);
			pf.getHFPageInstance(ob).clickProfileImage();
			pf.getHFPageInstance(ob).clickOnAccountLink();
			String accountType = "Facebook";

			validateAccounts(1, accountType);

			pf.getLoginTRInstance(ob).logOutApp();
			BrowserWaits.waitTime(5);

			try {
				ob.navigate().to(host + CONFIG.getProperty("appendENWAppUrl"));
				pf.getLoginTRInstance(ob).enterTRCredentials(LOGIN.getProperty("UserFBENWIAM80"),
						LOGIN.getProperty("PWDUserFBENWIAM80"));
				pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.LOGIN_PAGE_SIGN_IN_BUTTON_CSS);
				waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.ENW_NOTNOW_XPATH.toString()), 30);
				ob.findElement(By.xpath(OnePObjectMap.ENW_NOTNOW_XPATH.toString())).click();
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.ENW_HOME_CONTINUE_XPATH);
				pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.ENW_HOME_CONTINUE_XPATH);

				waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.ENW_PROFILE_HEADER_XPATH.toString()), 30);
				ob.findElement(By.xpath(OnePObjectMap.ENW_PROFILE_HEADER_XPATH.toString())).click();
				pf.getLoginTRInstance(ob).closeOnBoardingModal();
				String firstAccountProfileNameSteam1 = pf.getLinkingModalsInstance(ob).getProfileName();
				test.log(LogStatus.INFO, "Steam account profile name: " + firstAccountProfileNameSteam1);
				pf.getHFPageInstance(ob).clickProfileImage();
				pf.getHFPageInstance(ob).clickOnAccountLink();
				String accountType1 = "Neon";

				validateAccounts(1, accountType1);
				waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.NEON_PROFILE_HEADER_XPATH.toString()), 30);
				ob.findElement(By.xpath(OnePObjectMap.NEON_PROFILE_HEADER_XPATH.toString())).click();
				for (int j = 1; j <= 10; j++) {
					logger.info("Creating " + j + " Watchlist");
					pf.getLinkingModalsInstance(ob).toMakeAccountNeonActive();
				}

				test.log(LogStatus.INFO, "Steam account is made Neon active ");
				pf.getLoginTRInstance(ob).logOutApp();

				ob.navigate().to(host + CONFIG.getProperty("appendENWAppUrl"));
				pf.getBrowserWaitsInstance(ob)
						.waitUntilElementIsClickable(OnePObjectMap.LOGIN_PAGE_FB_SIGN_IN_BUTTON_CSS);
				pf.getBrowserActionInstance(ob).click(OnePObjectMap.LOGIN_PAGE_FB_SIGN_IN_BUTTON_CSS);

				pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.DID_YOU_KNOW_MODAL_STEAMPW_CSS,
						LOGIN.getProperty("PWDUserFBENWIAM80"));
				pf.getBrowserActionInstance(ob).click(OnePObjectMap.DID_YOU_KNOW_LETS_GO_BUTTON_XPATH);
				test.log(LogStatus.PASS, "user is able to link");

				ob.findElement(By.xpath(OnePObjectMap.ENW_PROFILE_USER_ICON_XPATH.toString())).click();

				waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.ENDNOTE_ACCOUNT_LINK_XPATH.toString()), 30);
				ob.findElement(By.xpath(OnePObjectMap.ENDNOTE_ACCOUNT_LINK_XPATH.toString())).click();
				pf.getBrowserWaitsInstance(ob)
						.waitUntilElementIsClickable(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_BOX_CSS);
				String accountType2 = "Neon";
				validateLinkedAccounts(2, accountType2);

				String firstAccountProfileName2 = pf.getLinkingModalsInstance(ob).getProfileName();
				test.log(LogStatus.INFO, " profile name: " + firstAccountProfileName2);

				try {

					Assert.assertEquals(firstAccountProfileNameSteam1, firstAccountProfileName2);
					test.log(LogStatus.PASS, "automated Merge is happened");

				}

				catch (Throwable t) {

					test.log(LogStatus.FAIL, "automated Merge is not happened");// extent
					// reports
					status = 2;// excel
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this
							.getClass().getSimpleName()
							+ "_more_search_results_do_not_get_displayed_when_user_scrolls_down_in_ALL_search_results_page")));// screenshot
					ErrorUtil.addVerificationFailure(t);

				}
				pf.getHFPageInstance(ob).clickProfileImage();
				pf.getHFPageInstance(ob).clickProfileImage();
				// pf.getProfilePageInstance(ob).clickProfileLink();
				waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.Neon_GROUP_BUTTON_XPATH.toString()), 30);
				ob.findElement(By.xpath(OnePObjectMap.Neon_GROUP_BUTTON_XPATH.toString())).click();

				BrowserWaits.waitTime(3);

				pf.getBrowserActionInstance(ob)
						.scrollToElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_TAB_WATCHLIST_CSS);

				int WinningAccount_WatclistCount = pf.getLinkingModalsInstance(ob).getWatchlistCount();
				System.out.println(WinningAccount_WatclistCount);
				if (WinningAccount_WatclistCount == watchlistCount) {
					test.log(LogStatus.PASS,
							"User is able to see the same watchlist count in profile page in Social account as in Neon");
				}

				else {

					test.log(LogStatus.FAIL, "User is not able to see the same watchlist count in Social account");
				}

			} catch (Throwable t) {
				closeBrowser();
				t.printStackTrace();
				test.log(LogStatus.FAIL, "User is not able to click the link button");
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test
						.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "_Not_able_to_link")));

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

		closeBrowser();
	}

	private void validateLinkedAccounts(int accountCount, String linkName) throws Exception {
		try {

			Assert.assertTrue(
					pf.getAccountPageInstance(ob).verifyLinkedAccount("Facebook", LOGIN.getProperty("UserFBENWIAM80")));
			Assert.assertTrue(
					pf.getAccountPageInstance(ob).verifyLinkedAccount(linkName, LOGIN.getProperty("UserFBENWIAM80")));
			Assert.assertTrue(pf.getAccountPageInstance(ob).validateAccountsCount(accountCount));
			test.log(LogStatus.PASS,
					"Linked accounts are available in accounts page : Facebook and " + linkName + " accounts");

		} catch (Throwable t) {
			test.log(LogStatus.FAIL,
					"Linked accounts are not available in accounts page : Facebook and " + linkName + " accounts");
			ErrorUtil.addVerificationFailure(t);// testng
			test.log(LogStatus.INFO, "Snapshot below: "
					+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "Linking_failed")));// screenshot
		}
	}

	private void validateAccounts(int accountCount, String linkName) throws Exception {
		try {

			Assert.assertTrue(
					pf.getAccountPageInstance(ob).verifyLinkedAccount(linkName, LOGIN.getProperty("UserFBENWIAM80")));
			Assert.assertTrue(pf.getAccountPageInstance(ob).validateAccountsCount(accountCount));
			test.log(LogStatus.PASS, "Single Social account is available and is not linked to Steam account");

		} catch (Throwable t) {
			test.log(LogStatus.FAIL,
					"Linked accounts are available in accounts page : Neon and " + linkName + " accounts");
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
