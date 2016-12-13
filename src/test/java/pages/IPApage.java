package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import util.BrowserWaits;
import util.ErrorUtil;
import util.OnePObjectMap;
import base.TestBase;

public class IPApage extends TestBase {

	PageFactory pf;

	public IPApage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}

	public void loginToIPA(String username, String password) throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.NEON_IPA_USERNAME_CSS);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.NEON_IPA_USERNAME_CSS, username);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.NEON_IPA_PASSWORD_CSS, password);
		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.NEON_IPA_SIGNIN_CSS);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsNotDisplayed(OnePObjectMap.NEON_IPA_SIGNIN_CSS);

	}

	public void clickOnSaveButton() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.NEON_IPA_DASH_BOARD_SAVE_BUTTON_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.NEON_IPA_DASH_BOARD_SAVE_BUTTON_CSS);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.NEON_IPA_DASH_BOARD_SAVE_MODAL_CSS);

	}

	public void enterSavedatatitle(String datatiltle) throws Exception {
		pf.getBrowserWaitsInstance(ob)
				.waitUntilElementIsDisplayed(OnePObjectMap.NEON_IPA_DASH_BOARD_SAVE_MODAL_TITLE_CSS);
		// pf.getBrowserActionInstance(ob).click(OnePObjectMap.NEON_IPA_DASH_BOARD_SAVE_MODAL_TITLE_CSS);
		pf.getBrowserActionInstance(ob).clear(OnePObjectMap.NEON_IPA_DASH_BOARD_SAVE_MODAL_TITLE_CSS);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.NEON_IPA_DASH_BOARD_SAVE_MODAL_TITLE_CSS,
				datatiltle);
	}

	public void enterSavedatadesc(String datadesc) throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilText("Save as");
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.NEON_IPA_DASH_BOARD_SAVE_MODAL_TITLE_CSS);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.NEON_IPA_DASH_BOARD_SAVE_MODAL_DESC_CSS,
				datadesc);
	}

	public void SaveDataInfo(String title, String desc) throws Exception {
		enterSavedatatitle(title);
		enterSavedatadesc(desc);

	}

	public void clickOnSaveData() throws Exception {
		pf.getBrowserWaitsInstance(ob)
				.waitUntilElementIsClickable(OnePObjectMap.NEON_IPA_DASH_BOARD_SAVE_MODAL_SAVE_BUTTON_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.NEON_IPA_DASH_BOARD_SAVE_MODAL_SAVE_BUTTON_CSS);
	}

	public void clickOnCancelData() throws Exception {
		pf.getBrowserWaitsInstance(ob)
				.waitUntilElementIsClickable(OnePObjectMap.NEON_IPA_DASH_BOARD_SAVE_MODAL_CANCEL_BUTTON_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.NEON_IPA_DASH_BOARD_SAVE_MODAL_CANCEL_BUTTON_CSS);
	}

	// Method to click on App switcher
	public void clickOnAppswitcher() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.NEON_IPA_APP_SWITCHER_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.NEON_IPA_APP_SWITCHER_CSS);

	}

	// Method to click on Project Neon
	public void clickOnNeonfromAppswitcher() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.NEON_PROJECTNEON_APP_SWITCHER_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.NEON_PROJECTNEON_APP_SWITCHER_CSS);
		BrowserWaits.waitTime(3);

	}

	public void changepwdIPA(String currentpw, String newpw) throws Exception {
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_CURRENTPW_FIELD_CSS,
				currentpw);
		pf.getBrowserWaitsInstance(ob)
				.waitUntilElementIsDisplayed(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.DRA_ACCOUNTSETTINGS_CHANGEPWD_NEWPW_FIELD_CSS,
				newpw);
		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.IPA_ACCOUNTSETTINGS_CHANGEPWD_SUBMIT_CSS);

	}
	
	
	/**
	 * Method for to check IPA landing screen displayed or not
	 * 
	 * @throws Exception,When
	 *             IPA landing screen not displayed
	 */
	public void landingScreenIPA() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilText("Thomson Reuters", "IP Analytics","Sign in");
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.LOGIN_PAGE_EMAIL_TEXT_BOX_CSS);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.LOGIN_PAGE_PASSWORD_TEXT_BOX_CSS);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.LOGIN_PAGE_SIGN_IN_BUTTON_CSS);
	}
	
	public void steamLockedIPA() throws Exception {

		ob.findElement(By.cssSelector(OnePObjectMap.LOGIN_PAGE_EMAIL_TEXT_BOX_CSS.toString())).clear();
		ob.findElement(By.cssSelector(OnePObjectMap.LOGIN_PAGE_EMAIL_TEXT_BOX_CSS.toString()))
				.sendKeys(LOGIN.getProperty("IPAUSER0051Locked"));

		for (int i = 0; i <= 9; i++) {
			ob.findElement(By.name("loginPassword")).sendKeys("asdfgh");
			ob.findElement(By.cssSelector(OnePObjectMap.LOGIN_PAGE_PASSWORD_TEXT_BOX_CSS.toString()))
					.sendKeys("asdfgh");
			pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.LOGIN_PAGE_SIGN_IN_BUTTON_CSS);
			Thread.sleep(2000);
		}

	}
	public void clickLoginIPA() throws Exception {
		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.LOGIN_PAGE_SIGN_IN_BUTTON_CSS);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.NEON_IPA_NEW_SEARCH_LINK_CSS);

	}
	
	public void validateIPAInactiveErrorMsg(ExtentTest test) throws Exception {
		try {
			String errormsg_title = pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.DRA_SUBSCRIPTION_INACTIVE_CSS).getText();
			String msg1 = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.DRA_SUBSCRIPTION_INACTIVE_MSG1_CSS)
					.getText();
			String msg2 = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.DRA_SUBSCRIPTION_INACTIVE_MSG2_XPATH)
					.getText();

			System.out.println(msg2);
			if (errormsg_title.contains("Thank you for your interest")
					&& msg1.contains("IP Analytics is currently available to customers in our early access program.")
					&& msg2.contains("Questions? Contact IPA.support@thomsonreuters.com.")) {
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



}
