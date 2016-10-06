package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import base.TestBase;
import util.OnePObjectMap;

public class LinkingModalsPage extends TestBase {

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

}
