package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import base.TestBase;
import util.BrowserAction;
import util.BrowserWaits;
import util.OnePObjectMap;

	public class DashboardPage extends TestBase {

	                PageFactory pf;
	                BrowserWaits browserWait;
	                BrowserAction browserAction;

	                public DashboardPage(WebDriver ob) {
	                                this.ob = ob;
	                                pf = new PageFactory();
	                                browserWait = new BrowserWaits(ob);
	                                browserAction = new BrowserAction(ob);

	                }

	public void clickOnPatentFoundIcon() throws Exception {

		pf.getBrowserWaitsInstance(ob)
				.waitUntilElementIsDisplayed(OnePObjectMap.NEON_IPA_DASH_BOARD_PATENT_FOUND_ICON_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.NEON_IPA_DASH_BOARD_PATENT_FOUND_ICON_CSS);

	}
	
	public List<String> getAllPatentRecords() throws Exception {
		List<String> recordList = new ArrayList<>();
		loadAllPatentRecords();
		List<WebElement> patentList = pf.getBrowserActionInstance(ob)
				.getElements(OnePObjectMap.NEON_IPA_RECORD_LIST_PAGE_PATENT_TITLE_CSS);

		for (WebElement we : patentList) {

			recordList.add(we.getText().trim());
		}

		return recordList;
	}
	
	public String clickOnNthPatentRecords(int index) throws Exception {
		waitForAllElementsToBePresent(ob,
				By.cssSelector(OnePObjectMap.NEON_IPA_RECORD_LIST_PAGE_PATENT_TITLE_CSS.toString()), 60);
		List<WebElement> patentList = pf.getBrowserActionInstance(ob)
				.getElements(OnePObjectMap.NEON_IPA_RECORD_LIST_PAGE_PATENT_TITLE_CSS);
		String title = patentList.get(index - 1).getText();
		patentList.get(index-1).click();
		return title;
	}
	
	public void loadAllPatentRecords() throws InterruptedException{
		scrollAndWait();
		while(isSpinnerDisplayed()){
			
			scrollAndWait();
		}
		
	}
	
	private void scrollAndWait() throws InterruptedException{
		JavascriptExecutor jse = (JavascriptExecutor) ob;
		jse.executeScript("scroll(0, 250);");
		Thread.sleep(1000);
		
	}
	
	private boolean isSpinnerDisplayed() {
		try {
			return pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.NEON_TO_ENW_BACKTOENDNOTE_PAGELOAD_CSS)
					.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
	
	
	public int getPatentCount() throws Exception {
		pf.getBrowserWaitsInstance(ob)
				.waitUntilElementIsDisplayed(OnePObjectMap.NEON_IPA_DASH_BOARD_PATENT_FOUND_COUNT_CSS);
		String count = pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.NEON_IPA_DASH_BOARD_PATENT_FOUND_COUNT_CSS).getText();
		count = count.replaceAll(",", "");
		return Integer.parseInt(count);

	}
}
