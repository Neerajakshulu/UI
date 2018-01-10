package ipaiam;

import java.io.PrintWriter;
import java.io.StringWriter;

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

public class IPAIAM053 extends TestBase {

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
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("IPAIAM");
	}

	/**
	 * Method for login into Neon application using TR ID
	 * 
	 * @throws Exception
	 *             , When TR Login is not done
	 */
	@Test
	public void testcaseIPA4() throws Exception {
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
			String statuCode = deleteUserAccounts(LOGIN.getProperty("USERIPA053"));

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

			ob.navigate().to(host + CONFIG.getProperty("appendIPAAppUrl"));
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.IPA_BRANDING_NAME_CSS);
			pf.getLoginTRInstance(ob).enterTRCredentials(LOGIN.getProperty("USERIPA053"),
					LOGIN.getProperty("USERPWDIPA053"));
			pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.LOGIN_PAGE_SIGN_IN_BUTTON_CSS);
//			BrowserWaits.waitTime(5);
//			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.NEON_IPA_SEARCH_TEXTBOX_CSS);
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.NEON_IPA_SEARCH_TEXTBOX_CSS, 5);
			test.log(LogStatus.PASS, "user has logged in with Steam account in ipa to make it Activated");
			String firstAccountProfileName = pf.getDraPageInstance(ob).getProfileNameDRA();
			test.log(LogStatus.INFO, "Steam account profile name: " + firstAccountProfileName);
			//BrowserWaits.waitTime(5);
			pf.getDraPageInstance(ob).clickOnProfileImageDRA();
			pf.getDraPageInstance(ob).clickOnAccountLinkDRA();
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.IPA_ACCOUNTSETTINGS_CLOSEBUTTON_CSS);
			//BrowserWaits.waitTime(2);
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.NEON_IPA_APP_SWITCHER_CSS);
			pf.getIpaPage(ob).clickOnAppswitcher();
			//BrowserWaits.waitTime(2);
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.NEON_PROJECTNEON_APP_SWITCHER_CSS);
			pf.getIpaPage(ob).clickOnNeonfromAppswitcher();
			switchToNewWindow(ob);
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_BOX_CSS);
			pf.getLoginTRInstance(ob).closeOnBoardingModal();
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_BOX_CSS);
			String accountType = "Facebook";
			logout();

			// ob.navigate().to(host);
			pf.getLoginTRInstance(ob).loginWithFBCredentials(LOGIN.getProperty("USERIPA053"),
					LOGIN.getProperty("USERPWDIPA053"));
			test.log(LogStatus.PASS, "user has logged in with social account in Neon to make it Activated");
			pf.getLinkingModalsInstance(ob).clickOnNotNowButton();
			pf.getLoginTRInstance(ob).logOutApp();
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.LOGIN_PAGE_FB_SIGN_IN_BUTTON_CSS);
			//BrowserWaits.waitTime(5);

			try {
				ob.navigate().to(host + CONFIG.getProperty("appendIPAAppUrl"));
				ob.navigate().refresh();
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.IPA_BRANDING_NAME_CSS);
				pf.getLoginTRInstance(ob).enterTRCredentials(LOGIN.getProperty("USERIPA053"),
						LOGIN.getProperty("USERPWDIPA053"));
				pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.LOGIN_PAGE_SIGN_IN_BUTTON_CSS);
				//BrowserWaits.waitTime(2);
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_LINKINGMODAL_FB_SIGN_IN_BUTTON_CSS);
				pf.getDraPageInstance(ob).clickOnSignInWithFBOnDRAModal();
				//BrowserWaits.waitTime(2);
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.NEON_IPA_SEARCH_TEXTBOX_CSS);
				test.log(LogStatus.PASS, "User is able to link steam account with facebook account");

				pf.getDraPageInstance(ob).clickOnAccountLinkDRA();
				validateAccounts(2, accountType);
				String winingAccountProfileName = pf.getIpaPage(ob).getProfileNameIPA();
				test.log(LogStatus.INFO, "After merging account profile name: " + winingAccountProfileName);

				// Verifying that Profile name is same as winning
				// account after merging
				Assert.assertEquals(winingAccountProfileName, firstAccountProfileName);
				test.log(LogStatus.PASS, "Random Merge is happened");

				if (winingAccountProfileName.contains(firstAccountProfileName)) {
					test.log(LogStatus.PASS, "Winning account is steam account");
				} else
					throw new Exception("Winning account is cannot be determined");

			} catch (Throwable t) {
				closeBrowser();
				t.printStackTrace();
				test.log(LogStatus.FAIL, "User is not able to merge accounts");
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test
						.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "_Not_able_to_link")));
			}

			//BrowserWaits.waitTime(2);
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

	private void validateAccounts(int accountCount, String linkName) throws Exception {
		try {
			Assert.assertTrue(
					pf.getDraPageInstance(ob).verifyLinkedAccountInDRA(linkName, LOGIN.getProperty("USERIPA053")));
			Assert.assertTrue(pf.getAccountPageInstance(ob).validateAccountsCount(accountCount));
			// test.log(LogStatus.PASS, " account is available and is not
			// linked");
			test.log(LogStatus.PASS,
					"Linked accounts are available in accounts page : Facebook and " + linkName + " accounts");

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
