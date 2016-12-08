package pages;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

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
	
	public void validateDRAInactiveErrorMsg(ExtentTest test) throws Exception{
		try {
			String errormsg_title = pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.DRA_SUBSCRIPTION_INACTIVE_CSS).getText();
			String msg1= pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.DRA_SUBSCRIPTION_INACTIVE_MSG1_CSS).getText();
			String msg2= pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.DRA_SUBSCRIPTION_INACTIVE_MSG2_XPATH).getText();
			
			System.out.println(msg2);
			if(errormsg_title.contains("Thank you for your interest") && msg1.contains("Target Druggability is a subscription product.") && msg2.contains("Questions? Learn more or contact DRA.support@thomsonreuters.com.")){
				test.log(LogStatus.PASS,
						" correct msg displayed ");
			}
			else
				{test.log(LogStatus.FAIL,
						" incorrect msg displayed ");
				}
		
		}catch(Throwable t){
			test.log(LogStatus.FAIL, "Something unexpected happened");
			ErrorUtil.addVerificationFailure(t);// testng
			closeBrowser();
		}
		
	}
}