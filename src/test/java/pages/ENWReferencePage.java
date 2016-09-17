package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.BrowserWaits;
import util.ErrorUtil;
import util.OnePObjectMap;

/**
 * This class contains all the method related to account page
 * @author uc205521
 *
 */
public class ENWReferencePage extends TestBase {

	PageFactory pf;

	public ENWReferencePage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}



	/**
	 * Method to delete record in  Endnote from Neon
	 * 
	 * 
	 */
	public void clearRecordEndnote() {
		
		ob.findElement(By.xpath("//a[@class='ne-app-switcher-flyout__toggle']/i")).click();
		
		ob.findElement(By.xpath("//li[@class='ne-app-switcher-flyout__list-item'][1]/a")).click();
		ob.findElement(By.xpath("//div[@id='idFolderLink_1']/a")).click();
		ob.findElement(By.cssSelector("input[id='idCheckAllRef']")).click();
		ob.findElement(By.cssSelector("input[id='idDeleteTrash']")).click();
		
		
	}
	
	
	//ENW FB sign in 
	public void loginWithENWFBCredentials(String username, String pwd) throws InterruptedException, Exception {

		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.LOGIN_PAGE_FB_SIGN_IN_BUTTON_CSS.toString()), 30);
		ob.findElement(By.cssSelector(OnePObjectMap.LOGIN_PAGE_FB_SIGN_IN_BUTTON_CSS.toString())).click();

		signInToFacebook(username, pwd);
		closeOnBoardingModal();
	}
	public void signInToFacebook(String username, String pwd) throws InterruptedException {
		waitForElementTobeVisible(ob, By.name(OnePObjectMap.LOGIN_PAGE_FB_EMAIL_TEXT_BOX_ID.toString()), 30);

		// Verify that existing LI user credentials are working fine
		ob.findElement(By.name(OnePObjectMap.LOGIN_PAGE_FB_EMAIL_TEXT_BOX_ID.toString())).sendKeys(username);
		ob.findElement(By.name(OnePObjectMap.LOGIN_PAGE_FB_PASSWORD_TEXT_BOX_ID.toString())).sendKeys(pwd);
		BrowserWaits.waitTime(2);
		ob.findElement(By.xpath(OR.getProperty("ENW_FB_LOGIN_BUTTON"))).click();
		BrowserWaits.waitTime(2);
		ob.findElement(By.xpath(OR.getProperty("ENW_CONTINUE_BUTTON"))).click();
	}
	
		public void clickAccount() throws InterruptedException
		{
			try {
				BrowserWaits.waitTime(3);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ob.findElement(By.xpath(OR.getProperty("ENW_FB_PROFILE_IMGCIRCLE"))).click();
			BrowserWaits.waitTime(3);
			ob.findElement(By.xpath(OR.getProperty("ENW_FB_PROFILE_IMGCIRCLE_ACCOUNT"))).click();
			BrowserWaits.waitTime(3);
			//validateLinkedAccounts(2, accountType);
		}
		
		
		// ENW LinkedIn Login
		public void loginWithENWLinkedInCredentials(String username, String pwd) throws InterruptedException, Exception {

			waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.LOGIN_PAGE_LI_SIGN_IN_BUTTON_CSS.toString()), 30);
			ob.findElement(By.cssSelector(OnePObjectMap.LOGIN_PAGE_LI_SIGN_IN_BUTTON_CSS.toString())).click();

			signInToENWLinkedIn(username, pwd);
			closeOnBoardingModal();
		}

		public void signInToENWLinkedIn(String username, String pwd) throws InterruptedException {
			waitForElementTobeVisible(ob, By.name(OnePObjectMap.LOGIN_PAGE_LI_EMAIL_TEXT_BOX_ID.toString()), 30);

			// Verify that existing LI user credentials are working fine
			ob.findElement(By.name(OnePObjectMap.LOGIN_PAGE_LI_EMAIL_TEXT_BOX_ID.toString())).sendKeys(username);
			ob.findElement(By.name(OnePObjectMap.LOGIN_PAGE_LI_PASSWORD_TEXT_BOX_ID.toString())).sendKeys(pwd);
			// BrowserWaits.waitTime(2);
			ob.findElement(By.name(OnePObjectMap.LOGIN_PAGE_LI_ALLOW_ACCESS_BUTTON_ID.toString())).click();
			BrowserWaits.waitTime(2);
			ob.findElement(By.xpath(OR.getProperty("ENW_CONTINUE_BUTTON"))).click();
		}
		
		
		// ENW Back to Endnot button +  Logout
		public void logout1() throws InterruptedException
		{
			BrowserWaits.waitTime(3);
			ob.findElement(By.xpath(OR.getProperty("ENW_BACK_TO_ENDNOTE_BUTTON"))).click();
			try {
				closeOnBoardingModal();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BrowserWaits.waitTime(3);
			ob.findElement(By.xpath(OR.getProperty("ENW_FB_PROFILE_IMGCIRCLE"))).click();
			BrowserWaits.waitTime(3);
			ob.findElement(By.xpath(OR.getProperty("ENW_FB_PROFILE_FLYOUT_SIGNOUT"))).click();
		}
		
	//Closing the onboarding modal 
	public void closeOnBoardingModal() throws Exception, InterruptedException {
		List<WebElement> onboardingStatus = pf.getBrowserActionInstance(ob)
				.getElements(OnePObjectMap.HOME_PROJECT_NEON_ONBOARDING_MODAL_CSS);
		logger.info("onboarding status-->" + onboardingStatus.size());

		if (onboardingStatus.size() == 1) {

			String header = pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.HOME_PROJECT_NEON_ONBOARDING_WELCOME_MODAL_TEXT_CSS).getText();
			if (header.contains("Hello")) {
				pf.getBrowserWaitsInstance(ob)
						.waitUntilElementIsClickable(OnePObjectMap.HOME_PROJECT_NEON_ONBOARDING_WELCOME_MODAL_CSS);
				pf.getBrowserActionInstance(ob).click(OnePObjectMap.HOME_PROJECT_NEON_ONBOARDING_WELCOME_MODAL_CSS);
				pf.getBrowserWaitsInstance(ob)
						.waitUntilElementIsClickable(OnePObjectMap.HOME_PROJECT_NEON_ONBOARDING_PROFILE_MODAL_CSS);
				pf.getBrowserActionInstance(ob).click(OnePObjectMap.HOME_PROJECT_NEON_ONBOARDING_PROFILE_MODAL_CSS);
				BrowserWaits.waitTime(4);
				pf.getBrowserWaitsInstance(ob)
						.waitUntilElementIsClickable(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_BOX_CSS);
			}
		}
	}

}
