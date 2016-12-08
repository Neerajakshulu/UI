package pages;

import org.openqa.selenium.WebDriver;

import util.OnePObjectMap;
import base.TestBase;

public class IPApage extends TestBase {

	PageFactory pf;

	public IPApage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}

	public void loginToIPA(String username,
			String password) throws Exception {
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
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(
				OnePObjectMap.NEON_IPA_DASH_BOARD_SAVE_MODAL_TITLE_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.NEON_IPA_DASH_BOARD_SAVE_MODAL_TITLE_CSS);
		pf.getBrowserActionInstance(ob).clear(OnePObjectMap.NEON_IPA_DASH_BOARD_SAVE_MODAL_TITLE_CSS);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.NEON_IPA_DASH_BOARD_SAVE_MODAL_TITLE_CSS,
				datatiltle);
	}

	public void enterSavedatadesc(String datadesc) throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilText("Save as");
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.NEON_IPA_DASH_BOARD_SAVE_MODAL_TITLE_CSS);
		pf.getBrowserActionInstance(ob).clear(OnePObjectMap.NEON_IPA_DASH_BOARD_SAVE_MODAL_DESC_CSS);
		pf.getBrowserActionInstance(ob)
				.enterFieldValue(OnePObjectMap.NEON_IPA_DASH_BOARD_SAVE_MODAL_DESC_CSS, datadesc);
	}

	public void SaveDataInfo(String title,
			String desc) throws Exception {
		enterSavedatatitle(title);
		enterSavedatadesc(desc);

	}

	public void clickOnSaveData() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(
				OnePObjectMap.NEON_IPA_DASH_BOARD_SAVE_MODAL_SAVE_BUTTON_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.NEON_IPA_DASH_BOARD_SAVE_MODAL_SAVE_BUTTON_CSS);
	}

	public void clickOnCancelData() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(
				OnePObjectMap.NEON_IPA_DASH_BOARD_SAVE_MODAL_CANCEL_BUTTON_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.NEON_IPA_DASH_BOARD_SAVE_MODAL_CANCEL_BUTTON_CSS);
	}

}