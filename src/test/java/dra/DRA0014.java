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

@SuppressWarnings("unused")
public class DRA0014 extends TestBase {

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
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("DRA");
	}

	/**
	 * Method for login into Neon application using TR ID
	 * 
	 * @throws Exception
	 *             , When TR Login is not done
	 */
	@Test
	public void testcaseh14() throws Exception {
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
			String statuCode = deleteUserAccounts(LOGIN.getProperty("DRASteamuser14"));
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

			// Activating the Steam account
			ob.navigate().to(host + CONFIG.getProperty("appendDRAAppUrl"));
			pf.getLoginTRInstance(ob).enterTRCredentials(LOGIN.getProperty("DRASteamuser14"),
					LOGIN.getProperty("DRAsteampw14"));
			pf.getDraPageInstance(ob).clickLoginDRA();

			String firstAccountProfileName = pf.getDraPageInstance(ob).getProfileNameDRA();

			test.log(LogStatus.INFO, "Steam account profile name: " + firstAccountProfileName);
			BrowserWaits.waitTime(5);
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_PROFILE_FLYOUT_IMAGE_CSS);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.DRA_PROFILE_FLYOUT_IMAGE_CSS);
			pf.getDraPageInstance(ob).clickOnAccountLinkDRA();
			String accountType = "Neon";

			// validateNeonAccount(1, accountType);
			pf.getDraPageInstance(ob).logoutDRA();
			// closeBrowser();
			BrowserWaits.waitTime(5);
			ob.navigate().to(host);
			try {

				pf.getLoginTRInstance(ob).loginWithFBCredentials(LOGIN.getProperty("DRAfbuser14"),
						LOGIN.getProperty("DRAfbpw14"));
				test.log(LogStatus.PASS, "user has logged in with social account");
				pf.getLinkingModalsInstance(ob).clickOnNotNowButton();
				test.log(LogStatus.PASS, "Avoiding the Linking is happened");
				pf.getBrowserWaitsInstance(ob)
						.waitUntilElementIsClickable(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_BOX_CSS);
				pf.getLoginTRInstance(ob).closeOnBoardingModal();
				String secondAccountProfileName = pf.getLinkingModalsInstance(ob).getProfileName();
				test.log(LogStatus.INFO, "Social account profile name: " + secondAccountProfileName);
				pf.getHFPageInstance(ob).clickProfileImage();
				pf.getHFPageInstance(ob).clickOnAccountLink();
				BrowserWaits.waitTime(2);
				accountType = "Facebook";

				validateAccounts(1, accountType);
				int watchlistCount = 1;
				try {

					for (int j = 1; j <= watchlistCount; j++) {
						logger.info("Creating " + j + " Watchlist");
						pf.getLinkingModalsInstance(ob).toMakeAccountNeonActive();
					}
					test.log(LogStatus.PASS, "Social account is made Neon Active");

				} catch (Throwable t) {
					test.log(LogStatus.FAIL, "Unable to create 10 watchlists");// extent
					ErrorUtil.addVerificationFailure(t);

				}
				pf.getLoginTRInstance(ob).logOutApp();
				BrowserWaits.waitTime(5);
				ob.navigate().to(host);
				// Trying to Link the accounts

				try {
					ob.navigate().to(host + CONFIG.getProperty("appendDRAAppUrl"));
					pf.getLoginTRInstance(ob).enterTRCredentials(LOGIN.getProperty("DRASteamuser14"),
							LOGIN.getProperty("DRAfbpw14"));
					pf.getDraPageInstance(ob).clickLoginDRA();

					pf.getDraPageInstance(ob).clickOnSignInWithFBOnDRAModal();
					pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.DRA_SEARCH_BOX_CSS);
					test.log(LogStatus.FAIL, "Linking is happened");

					pf.getHFPageInstance(ob).clickOnAccountLink();
					accountType = "Neon";

					try {
						// validating two accounts are linked or not
						validateLinkedAccounts(2, accountType);
						String winingAccountProfileName = pf.getLinkingModalsInstance(ob).getProfileName();
						test.log(LogStatus.INFO, "After merging account profile name: " + winingAccountProfileName);

						// Verifying that Profile name is same as winning
						// account after merging
						Assert.assertEquals(winingAccountProfileName, secondAccountProfileName);
						test.log(LogStatus.PASS, "Automated Merge is happened");

						if (winingAccountProfileName.contains(secondAccountProfileName)) {
							test.log(LogStatus.PASS, "Winning account is facebook account");
						} else
							throw new Exception("Winning account is cannot be determined");

						pf.getDraPageInstance(ob).getProfileNameDRA();
						pf.getDraPageInstance(ob).getProfileNameDRA();
						// pf.getProfilePageInstance(ob).clickProfileLink();
						waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.NEON_OK_BUTTON_XPATH.toString()), 30);
						ob.findElement(By.xpath(OnePObjectMap.NEON_OK_BUTTON_XPATH.toString())).click();

						BrowserWaits.waitTime(4);

						pf.getBrowserActionInstance(ob)
								.scrollToElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_TAB_WATCHLIST_CSS);
						int WinningAccount_WatclistCount = pf.getLinkingModalsInstance(ob).getWatchlistCount();
						System.out.println(WinningAccount_WatclistCount);
						if (WinningAccount_WatclistCount == watchlistCount) {
							test.log(LogStatus.PASS,
									"User is able to see the same watchlist count in profile page in Social account as in Neon");
						}

						else {

							test.log(LogStatus.FAIL,
									"User is not able to see the same watchlist count in Social account");
						}

					}

					catch (Throwable t) {

						test.log(LogStatus.FAIL, "Automated Merge is not happened");// extent
						// reports
						status = 2;// excel
						test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this
								.getClass().getSimpleName()
								+ "_more_search_results_do_not_get_displayed_when_user_scrolls_down_in_ALL_search_results_page")));// screenshot
						ErrorUtil.addVerificationFailure(t);

					}

				} catch (Throwable t) {

					test.log(LogStatus.FAIL, "User is not able to link");// extent
					// reports
					status = 2;// excel
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this
							.getClass().getSimpleName()
							+ "_more_search_results_do_not_get_displayed_when_user_scrolls_down_in_ALL_search_results_page")));// screenshot
					ErrorUtil.addVerificationFailure(t);

				}

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

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");

	}

	private void validateAccounts(int accountCount, String linkName) throws Exception {
		try {

			Assert.assertTrue(
					pf.getAccountPageInstance(ob).verifyLinkedAccount(linkName, LOGIN.getProperty("sru_fbusername11")));
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

	private void validateLinkedAccounts(int accountCount, String linkName) throws Exception {
		try {

			Assert.assertTrue(pf.getAccountPageInstance(ob).verifyLinkedAccount("Facebook",
					LOGIN.getProperty("sru_fbusername11")));
			Assert.assertTrue(
					pf.getAccountPageInstance(ob).verifyLinkedAccount(linkName, LOGIN.getProperty("sru_steam11")));
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

}
