package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import base.TestBase;
import util.BrowserWaits;
import util.OnePObjectMap;

public class PUBLONSPage extends TestBase {

	public PUBLONSPage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}

	public void loginPublonsAccont(String email, String pass) {
		ob.findElement(By.cssSelector(OnePObjectMap.LOGIN_PAGE_EMAIL_TEXT_BOX_CSS.toString())).sendKeys(email);
		ob.findElement(By.cssSelector(OnePObjectMap.LOGIN_PAGE_PASSWORD_TEXT_BOX_CSS.toString())).sendKeys(pass);
		jsClick(ob, ob.findElement(By.cssSelector(OnePObjectMap.NEON_LOGIN_PAGE_FORGOT_PASSWORD_OK_CSS.toString())));
	}
 //===================================================================================
	/**
	 * Method for Login shared platform application using FB valid login credentials
	 * 
	 * @param username
	 * @param pwd
	 * @throws InterruptedException
	 * @throws Exception,
	 *             When user is not able to login using FB
	 */
	public void loginWithFBCredentialsWithOutLinking(String username, String pwd) throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.LOGIN_PAGE_FB_SIGN_IN_BUTTON_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.LOGIN_PAGE_FB_SIGN_IN_BUTTON_CSS);
		
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.LOGIN_PAGE_FB_EMAIL_TEXT_BOX_NAME);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.LOGIN_PAGE_FB_EMAIL_TEXT_BOX_NAME, username);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.LOGIN_PAGE_FB_PASSWORD_TEXT_BOX_NAME, pwd);
		pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.LOGIN_PAGE_FB_LOGIN_BUTTON_NAME);
			
}
	//===================================================================================
		/**
		 * Method for Login shared platform application using Gmail valid login credentials
		 * 
		 * @param username
		 * @param pwd
		 * @throws InterruptedException
		 * @throws Exception,
		 *             When user is not able to login using FB
		 */
		public void loginWithGmailCredentialsWithOutLinking(String username, String pwd) throws Exception {
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.LOGIN_PAGE_GMAIL_SIGN_IN_BUTTON_CSS);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.LOGIN_PAGE_GMAIL_SIGN_IN_BUTTON_CSS);
			
			fluentwaitforElement(ob, By.name(OnePObjectMap.LOGIN_PAGE_GMAIL_SIGN_IN_EMAIL_TEXT_NAME.toString()), 60);
			pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.LOGIN_PAGE_GMAIL_SIGN_IN_EMAIL_TEXT_NAME, username);
			
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.LOGIN_PAGE_GMAIL_SIGN_IN_NEXT_BUTTON_ID);
			pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.LOGIN_PAGE_GMAIL_SIGN_IN_NEXT_BUTTON_ID);
			
			fluentwaitforElement(ob, By.name(OnePObjectMap.LOGIN_PAGE_GMAIL_SIGN_IN_PASSWORD_TEXT_NAME.toString()), 60);
			pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.LOGIN_PAGE_GMAIL_SIGN_IN_PASSWORD_TEXT_NAME, pwd);
			
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.LOGIN_PAGE_GMAIL_SIGN_IN_SIGNIN_BUTTON_NAME);
			pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.LOGIN_PAGE_GMAIL_SIGN_IN_SIGNIN_BUTTON_NAME);
			
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.LOGIN_PAGE_GMAIL_SIGN_IN_ACCESS_BUTTON_ID);
			pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.LOGIN_PAGE_GMAIL_SIGN_IN_ACCESS_BUTTON_ID);
						
	}
		public void checkBlockedUserErrorMessage() {
			pf.getBrowserWaitsInstance(ob).waitUntilText("Your account has been locked.");
			String errorMessage1 = ob
					.findElement(By.xpath(OnePObjectMap.DRA_SSO_SUSPEND_PAGE_ERROR_MESSAGE_XPATH.toString())).getText();
			String errorMessage2 = ob
					.findElement(By.xpath(OnePObjectMap.PUBLONS_BLOCKED_PAGE_ERROR_MESSAGE_AFTER30MIN_XPATH.toString()))
					.getText();
			Assert.assertEquals(errorMessage1, "Your account has been locked.");
			Assert.assertEquals(errorMessage2, "Please try again in 30 minutes.");

		}

		public void clickRegisterLink() throws Exception {
			
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.PUBLONS_LOGIN_PAGE_REGISTER_LINK_CSS);
			pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.PUBLONS_LOGIN_PAGE_REGISTER_LINK_CSS);
		}

		public void emailTextBox() throws Exception {
			waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.PUBLONS_REGISTER_PAGE_EMAIL_TEXTBOX_CSS.toString()),30);
			ob.findElement(By.name(OnePObjectMap.PUBLONS_REGISTER_PAGE_EMAIL_TEXTBOX_CSS.toString())).click();
			
			/*pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.PUBLONS_REGISTER_PAGE_EMAIL_TEXTBOX_CSS);
			pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.PUBLONS_REGISTER_PAGE_EMAIL_TEXTBOX_CSS);*/
			
		}

		public void clickPasswordTextBox() throws Exception {
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.ENW_LINK_ACCOUNTS_PASSWORD_XPATH);
			pf.getBrowserActionInstance(ob).jsClick(OnePObjectMap.ENW_LINK_ACCOUNTS_PASSWORD_XPATH);
			
		}
}
