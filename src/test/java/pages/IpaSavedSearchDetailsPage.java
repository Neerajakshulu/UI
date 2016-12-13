package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import util.OnePObjectMap;
import base.TestBase;

public class IpaSavedSearchDetailsPage extends TestBase {

	PageFactory pf;

	public IpaSavedSearchDetailsPage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();

	}

	public void clickOnSavedWork() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(
				OnePObjectMap.NEON_IPA_HOMOE_PAGE_SAVED_WORK_BUTTON_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.NEON_IPA_HOMOE_PAGE_SAVED_WORK_BUTTON_CSS);
	}

	private WebElement getSavedRecord(String recordTitle) throws Exception {
		waitForAllElementsToBePresent(ob,
				By.cssSelector(OnePObjectMap.NEON_IPA_SAVED_DATA_PAGE_SEARCH_RECORDS_CSS.toString()), 60);
		List<WebElement> recordname = ob.findElements(By
				.cssSelector(OnePObjectMap.NEON_IPA_SAVED_DATA_PAGE_SEARCH_RECORDS_CSS.toString()));
		String actTitle;
		for (WebElement we : recordname) {
			actTitle = we.findElement(
					By.cssSelector(OnePObjectMap.NEON_IPA_SAVED_DATA_PAGE_SEARCH_TITLE_CSS.toString())).getText();
			if (actTitle.equalsIgnoreCase(recordTitle)) {
				return we;
			}
		}
		throw new Exception("Saved record not found in Saved work page");
	}

	public void clickOnEditButton(String datatitle) throws Exception {
		WebElement record = getSavedRecord(datatitle);
		record.findElement(By.cssSelector(OnePObjectMap.NEON_IPA_SAVED_DATA_PAGE_EDIT_BUTTON_CSS.toString())).click();

	}

	public void clickOnDeleteButton(String datatitle) throws Exception {
		WebElement record = getSavedRecord(datatitle);
		record.findElement(By.cssSelector(OnePObjectMap.NEON_IPA_SAVED_DATA_PAGE_DELETE_BUTTON_CSS.toString())).click();
		String text = "Are you sure you want to delete this saved item?";
		pf.getBrowserWaitsInstance(ob).waitUntilText("Delete Saved item");
		String popuptext = ob.findElement(
				By.cssSelector(OnePObjectMap.IPA_DELETE_SAVED_ITEM_BUTTON_POPUP_TEXT_CSS.toString())).getText();
		if (text.equalsIgnoreCase(popuptext))
			ob.findElement(By.cssSelector(OnePObjectMap.IPA_DELETE_BUTTON_IN_SAVED_CONFIRMATION_MODAL_CSS.toString()))
					.click();

	}

	public boolean validateSavedDataInfo(String title,
			String type) throws Exception {
		WebElement record = getSavedRecord(title);
		String stitle = record.findElement(
				By.cssSelector(OnePObjectMap.NEON_IPA_SAVED_DATA_PAGE_SEARCH_TITLE_CSS.toString())).getText();
		String stype = record.findElement(
				By.cssSelector(OnePObjectMap.NEON_IPA_SAVED_DATA_PAGE_SEARCH_TERM_TYPE_CSS.toString())).getText();
		if (title.equalsIgnoreCase(stitle) && stype.equalsIgnoreCase(type))
			return true;
		else
			return false;
	}

	public void clickOnTitle(String stitle) throws Exception {
		waitForAjax(ob);
		waitForAllElementsToBePresent(ob,
				By.cssSelector(OnePObjectMap.NEON_IPA_SAVED_DATA_PAGE_SEARCH_TITLE_CSS.toString()), 30);
		List<WebElement> list = pf.getBrowserActionInstance(ob).getElements(
				OnePObjectMap.NEON_IPA_SAVED_DATA_PAGE_SEARCH_TITLE_CSS);
		for (WebElement we : list) {
			if (we.getText().equalsIgnoreCase(stitle)) {
				we.click();
				return;
			}
		}

	}

	public void clickOnSaveButtonInTile() throws Exception {
		waitForAllElementsToBePresent(ob,
				By.cssSelector(OnePObjectMap.IPA_SAVE_BUTTON_IN_SAVED_DATA_TILE_CSS.toString()), 60);
		List<WebElement> list = pf.getBrowserActionInstance(ob).getElements(
				OnePObjectMap.IPA_SAVE_BUTTON_IN_SAVED_DATA_TILE_CSS);
		for (WebElement we : list) {
			if (we.isDisplayed()) {
				we.click();
				return;
			}
		}
		throw new Exception("Submit button is not displayed in Saved tile page");
	}

	public void clickOnCancelButtonInTile() throws Exception {
		waitForAllElementsToBePresent(ob,
				By.cssSelector(OnePObjectMap.IPA_CANCEL_BUTTON_IN_SAVED_DATA_TILE_CSS.toString()), 60);
		List<WebElement> list = pf.getBrowserActionInstance(ob).getElements(
				OnePObjectMap.IPA_CANCEL_BUTTON_IN_SAVED_DATA_TILE_CSS);
		for (WebElement we : list) {
			if (we.isDisplayed()) {
				we.click();
				return;
			}
		}
		throw new Exception("Cancel button is not displayed in Saved tile page");
	}

	public void randomUpdate(int val) throws Exception {
		waitForAllElementsToBePresent(ob,
				By.cssSelector(OnePObjectMap.NEON_IPA_SAVED_DATA_PAGE_EDIT_BUTTON_CSS.toString()), 30);
		List<WebElement> list = ob.findElements(By.cssSelector(OnePObjectMap.NEON_IPA_SAVED_DATA_PAGE_EDIT_BUTTON_CSS
				.toString()));
		if (val <= list.size()) {
		list.get(val).click();
			

		} else
			throw new Exception("Saved Data  edit button in the title is not selected randomly");
	}
}
