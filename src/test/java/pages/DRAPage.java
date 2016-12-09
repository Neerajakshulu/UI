package pages;

import java.util.List;

import org.openqa.selenium.By;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.BrowserWaits;
import util.ErrorUtil;
import util.OnePObjectMap;

public class DRAPage extends TestBase {

	public DRAPage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}

	/**
	 * Method for logout DRA application
	 * 
	 * @throws Exception,When
	 *             not able to login
	 */
	public void logoutDRA() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.DRA_PROFILE_FLYOUT_IMAGE_CSS);
		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.DRA_PROFILE_FLYOUT_IMAGE_CSS);
		waitForAjax(ob);
		pf.getBrowserWaitsInstance(ob)
				.waitUntilElementIsClickable(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_SIGNOUT_LINK);
		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_SIGNOUT_LINK);
		BrowserWaits.waitTime(3);
	}

	public void clickLoginDRA() throws Exception {
		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.LOGIN_PAGE_SIGN_IN_BUTTON_CSS);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.DRA_SEARCH_BOX_CSS);

	}

	public void clickOnAccountLinkDRA() throws Exception {
		pf.getProfilePageInstance(ob).clickProfileFlyout();
		pf.getBrowserWaitsInstance(ob)
				.waitUntilElementIsClickable(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_ACCOUNT_LINK);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_ACCOUNT_LINK);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_PROFILEFLYOUT_ACCOUNTLINK_CSS);
	}

	public void clickOnChangePwLinkDRA() throws Exception {
		pf.getBrowserWaitsInstance(ob)
				.waitUntilElementIsClickable(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_LINK_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_LINK_CSS);
		pf.getBrowserWaitsInstance(ob)
				.waitUntilElementIsDisplayed(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_CSS);
	}

	public void changepwd(String currentpw, String newpw) throws Exception {
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_CSS,
				currentpw);
		pf.getBrowserWaitsInstance(ob)
				.waitUntilElementIsDisplayed(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS,
				newpw);
		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_SUBMIT_CSS);

	}

	public void clickCancelOnChangepwd() throws Exception {
		pf.getBrowserWaitsInstance(ob)
				.waitUntilElementIsClickable(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CANCEL_CSS);
		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CANCEL_CSS);
		pf.getBrowserWaitsInstance(ob)
				.waitUntilElementIsClickable(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_LINK_CSS);
	}

	public void clickOnSignInWithFBOnDRAModal() throws Exception {
		pf.getBrowserWaitsInstance(ob)
				.waitUntilElementIsClickable(OnePObjectMap.DRA_LINKINGMODAL_FB_SIGN_IN_BUTTON_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.DRA_LINKINGMODAL_FB_SIGN_IN_BUTTON_CSS);
	}

	public void clickOutsideTheDRAModal() throws Exception {

		pf.getBrowserWaitsInstance(ob)
				.waitUntilElementIsDisplayed(OnePObjectMap.DRA_LINKINGMODAL_FB_SIGN_IN_BUTTON_CSS);

		Dimension dimesions = pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.DRA_LINKINGMODAL_FB_SIGN_IN_BUTTON_CSS).getSize();
		logger.info("Width : " + dimesions.width);
		logger.info("Height : " + dimesions.height);
		int x = dimesions.width;
		int y = dimesions.height;

		Actions builder = new Actions(ob);
		builder.moveToElement(
				pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.DRA_LINKINGMODAL_FB_SIGN_IN_BUTTON_CSS),
				x + 150, y).build().perform();
		builder.click().build().perform();
	}

	/**
	 * Method for to check DRA landing screen displayed or not
	 * 
	 * @throws Exception,When
	 *             DRA landing screen not displayed
	 */
	public void landingScreenDRA() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_LOGO_CSS);
		pf.getBrowserWaitsInstance(ob).waitUntilText("Target Druggability", "Drug Research Advisor");
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.LOGIN_PAGE_EMAIL_TEXT_BOX_CSS);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.LOGIN_PAGE_PASSWORD_TEXT_BOX_CSS);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.LOGIN_PAGE_SIGN_IN_BUTTON_CSS);
	}

	/**
	 * Method for login DRA Application with valid login credentials
	 * 
	 * @param userName
	 * @param password
	 * @throws Exception,
	 *             When login not happend
	 */
	public void loginToDRAApplication(String userName, String password) throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_LOGO_CSS);
		pf.getBrowserWaitsInstance(ob).waitUntilText("Target Druggability", "Drug Research Advisor");

		pf.getBrowserActionInstance(ob).clickAndClear(OnePObjectMap.LOGIN_PAGE_EMAIL_TEXT_BOX_CSS);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.LOGIN_PAGE_EMAIL_TEXT_BOX_CSS, userName);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.LOGIN_PAGE_PASSWORD_TEXT_BOX_CSS, password);

		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.LOGIN_PAGE_SIGN_IN_BUTTON_CSS);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.DRA_SEARCH_BOX_CSS);

	}

	public void validateInactiveaccount(ExtentTest test) throws Exception {
		try {
			WebElement element = pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.DRA_SUBSCRIPTION_INACTIVE_CSS);

			if (!(element.isDisplayed())) {
				test.log(LogStatus.FAIL,
						"user is not allowed to access DRA as the STeAM account is not tied to an active subscription");
			}
			test.log(LogStatus.PASS,
					"user is not allowed to access DRA as the STeAM account is not tied to an active subscription with the 'DRA_TARGET_DRUG' entitlement (SKU).");
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something unexpected happened");
			ErrorUtil.addVerificationFailure(t);// testng
			closeBrowser();
		}
	}

	/**
	 * Method for to check invalid error message displayed or not
	 * 
	 * @throws Exception,When
	 *             entered invalid credentials
	 */
	public void validateInvalidCredentialsErrorMsg(ExtentTest test) throws Exception {
		try {
			if (!checkElementPresence_id("login_error")) {
				test.log(LogStatus.FAIL, "Unexpected login happened");// extent
																		// //
																		// report
				closeBrowser();

			} else {
				test.log(LogStatus.PASS,
						" 'Invalid Email/Password. Please try again.' message is displayed correctly ");// extent
																										// report
			}
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something unexpected happened");
			ErrorUtil.addVerificationFailure(t);// testng
			closeBrowser();
		}
	}

	public void validateDRAInactiveErrorMsg(ExtentTest test) throws Exception {
		try {
			String errormsg_title = pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.DRA_SUBSCRIPTION_INACTIVE_CSS).getText();
			String msg1 = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.DRA_SUBSCRIPTION_INACTIVE_MSG1_CSS)
					.getText();
			String msg2 = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.DRA_SUBSCRIPTION_INACTIVE_MSG2_XPATH)
					.getText();

			System.out.println(msg2);
			if (errormsg_title.contains("Thank you for your interest")
					&& msg1.contains("Target Druggability is a subscription product.")
					&& msg2.contains("Questions? Learn more or contact DRA.support@thomsonreuters.com.")) {
				test.log(LogStatus.PASS, " correct msg displayed ");
			} else {
				test.log(LogStatus.FAIL, " incorrect msg displayed ");
			}
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something unexpected happened");
			ErrorUtil.addVerificationFailure(t);// testng
			closeBrowser();
		}
	}

	public void validateAccount(int accountCount, String linkName, String accountId, ExtentTest test) throws Exception {
		try {
			Assert.assertTrue(pf.getAccountPageInstance(ob).verifyLinkedAccount(linkName, accountId));
			Assert.assertTrue(pf.getAccountPageInstance(ob).validateAccountsCount(accountCount));
			test.log(LogStatus.PASS, "Single Steam account is available and is not linked to Social account");

		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Linked accounts are available in accounts page");
			ErrorUtil.addVerificationFailure(t);// testng
			test.log(LogStatus.INFO, "Snapshot below: "
					+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "_failed")));// screenshot
		}
	}

	public void validateIncorrectPwdErrorMsg(ExtentTest test) throws Exception {

		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_CHANGEPW_ERROR_MSG_CSS);
		WebElement errormsg = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.DRA_CHANGEPW_ERROR_MSG_CSS);

		if (!(errormsg.isDisplayed())) {
			test.log(LogStatus.FAIL, "Incorrect password error message is not displayed");
		} else {
			String actualerrortext = errormsg.getText();
			String expectederrortext = "Incorrect password. Please try again.";
			Assert.assertEquals(actualerrortext, expectederrortext);
			test.log(LogStatus.PASS, "Incorrect password error message is displayed");
		}
	}

	public void validateCurrentPwdErrorMsg(ExtentTest test) throws Exception {
		try {
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_CHANGEPW_ERROR_MSG_CSS);
			WebElement errormsg = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.DRA_CHANGEPW_ERROR_MSG_CSS);
			String actualerrortext1 = errormsg.getText();
			String expectederrortext1 = "New password should not match current password";
			Assert.assertEquals(actualerrortext1, expectederrortext1);
			test.log(LogStatus.PASS, "'New password should not match current password' error message is displayed");
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Current password error message is not displayed");
		}
	}

	public void validateNewPwdErrorMsg(ExtentTest test) throws Exception {
		try {
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_CHANGEPW_ERROR_MSG_CSS);
			WebElement errormsg = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.DRA_CHANGEPW_ERROR_MSG_CSS);
			String actualerrortext2 = errormsg.getText();
			String expectederrortext2 = "New password should not match previous 4 passwords";
			Assert.assertEquals(actualerrortext2, expectederrortext2);
			test.log(LogStatus.PASS, "'New password should not match previous 4 passwords' error message is displayed");
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "New password error message is not displayed");
		}
	}

	public void clickProfileLink() throws Exception {
		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.DRA_PROFILE_FLYOUT_NAME_CSS);
		// pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_EDIT_CSS);
		// pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_TITLE_CSS);
		pf.getBrowserWaitsInstance(ob).waitUntilText("Update", "Cancel", "Profile");
	}

	public String getProfileNameDRA() throws Exception {
		clickOnProfileImageDRA();
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_PROFILE_FLYOUT_INFO_CSS);
		String ProfileName = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.DRA_PROFILE_FLYOUT_INFO_CSS)
				.getText();
		return ProfileName;
	}
	
	public void clickOnProfileImageDRA() throws Exception{
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DRA_PROFILE_FLYOUT_IMAGE_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.DRA_PROFILE_FLYOUT_IMAGE_CSS);
	}
	public void steamLockedDRA() throws Exception {

		ob.findElement(By.cssSelector(OnePObjectMap.LOGIN_PAGE_EMAIL_TEXT_BOX_CSS.toString())).clear();
		ob.findElement(By.cssSelector(OnePObjectMap.LOGIN_PAGE_EMAIL_TEXT_BOX_CSS.toString()))
				.sendKeys(LOGIN.getProperty("DRAUSER0012Locked"));

		for (int i = 0; i <= 9; i++) {
			ob.findElement(By.name("loginPassword")).sendKeys("asdfgh");
			ob.findElement(By.cssSelector(OnePObjectMap.LOGIN_PAGE_PASSWORD_TEXT_BOX_CSS.toString()))
					.sendKeys("asdfgh");
			pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.LOGIN_PAGE_SIGN_IN_BUTTON_CSS);
			Thread.sleep(2000);
		}

	}

	
	public void loginTofbSuspended() throws Exception {

		pf.getLoginTRInstance(ob).enterTRCredentials(LOGIN.getProperty("ENWIAM00015UserSuspended"),
				LOGIN.getProperty("ENWIAM00015SuspendedPWD"));
		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.LOGIN_PAGE_SIGN_IN_BUTTON_CSS);

	}

	public boolean verifyLinkedAccountInDRA(String accountType, String emailId) {
		boolean result = false;
		waitForAllElementsToBePresent(ob, By.cssSelector("div[class='account-option-item ng-scope']"), 60);
		List<WebElement> list = ob.findElements(By.cssSelector("div[class='account-option-item ng-scope']"));

		for (WebElement element : list) {
						
			String type1 = null;
			List<WebElement> elementList1 = element
					.findElements(By.cssSelector("span[class='wui-subtitle ng-binding']"));	
			for (WebElement we1 : elementList1) {
			
				if (we1.isDisplayed()) {
					type1 = we1.getText();
					break;
				}
			
			}
			
			
			if ((accountType.equalsIgnoreCase("Steam") && type1.contains("Thomson Reuters | Project Neon"))
					|| accountType.equalsIgnoreCase(type1.trim())) {
				String emailid = null;
				emailid = element.findElement(By.cssSelector("span[class='ng-binding']")).getText();
				if (emailid.equalsIgnoreCase(emailId))
					result = true;
				break;
			}

		}
		return result;
	}

}