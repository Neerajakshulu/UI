package pages;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.BrowserWaits;
import util.ErrorUtil;
import util.OnePObjectMap;

public class LinkingModalsPage extends TestBase {
static int i=0;
	/**
	 * This class contains methods related to Linking modal
	 * 
	 * @author uc206198
	 *
	 */

	public LinkingModalsPage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}

	public String getMessageOnDidYouKnowModal() throws Exception {

		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.MATCHING_STEAM_MODALTITLE_CSS);
		WebElement text_on_modal = ob
				.findElement(By.cssSelector(OnePObjectMap.MATCHING_STEAM_MODALTITLE_CSS.toString()));
		String actual_text_onmodal = text_on_modal.getText();

		return actual_text_onmodal;

	}

	public String getMessageOnNotActivatedAccountModal() throws Exception {

		WebElement message_displayed = ob
				.findElement(By.xpath(OnePObjectMap.ACCOUNT_NOTACTIVATED_MSG_XPATH.toString()));

		String actual_msg = message_displayed.getText();
		return actual_msg;

	}

	/**
	 * Method for close linking modal
	 * 
	 * @throws Exception,
	 *             When modal not closed
	 */
	public void clickOnCloseButton() throws Exception {

		pf.getBrowserWaitsInstance(ob)
				.waitUntilElementIsDisplayed(OnePObjectMap.SEARCH_RESULTS_PAGE_LINKIINGMODAl_CLOSE_BUTTON_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.SEARCH_RESULTS_PAGE_LINKIINGMODAl_CLOSE_BUTTON_CSS);
	}

	/**
	 * Mehthod for clicking outside linking modal
	 * 
	 * @throws Exception,
	 *             When not clicked
	 */

	public void clickOutsideTheModal() throws Exception {

		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.DID_YOU_KNOW_LETS_GO_BUTTON_XPATH);

		Dimension dimesions = pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.DID_YOU_KNOW_LETS_GO_BUTTON_XPATH).getSize();
		logger.info("Width : " + dimesions.width);
		logger.info("Height : " + dimesions.height);
		int x = dimesions.width;
		int y = dimesions.height;

		Actions builder = new Actions(ob);
		builder.moveToElement(
				pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.DID_YOU_KNOW_LETS_GO_BUTTON_XPATH), x + 150, y)
				.build().perform();
		builder.click().build().perform();
	}
	
	public void clickOnSignInUsingFB() throws Exception{
		
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.SIGNIN_USING_FB_BUTTON_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.SIGNIN_USING_FB_BUTTON_CSS);
		
	}
	
	public String getProfileName() throws Exception{
		pf.getHFPageInstance(ob).clickProfileImage();
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.PROFILENAME_CSS);
		String ProfileName=pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.PROFILENAME_CSS).getText();
		return ProfileName;
	}
	

	public void toMakeAccountNeonActive() throws Exception {
		
		String newWatchlistName = this.getClass().getSimpleName() + "_" + getCurrentTimeStamp();
		createWatchList("public", newWatchlistName, "This is my test watchlist.");				
	}
	
	
	public void clickOnNotNowButton() throws Exception {

		pf.getBrowserWaitsInstance(ob)
				.waitUntilElementIsDisplayed(OnePObjectMap.NOT_NOW_BUTTON_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.NOT_NOW_BUTTON_CSS);
	}
	
	public String getCustomerSupportMsg() throws Exception{
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.CONTACT_CUSTOMER_SUPPORT_MSG_XPATH);
		String actual=pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.CONTACT_CUSTOMER_SUPPORT_MSG_XPATH).getText();
		return actual;
		
		
	}
	
	public void clickOnOkButton() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.OK_BUTTON_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.OK_BUTTON_CSS);
	}
}
