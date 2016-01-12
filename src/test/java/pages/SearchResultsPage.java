package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import base.TestBase;
import util.BrowserAction;
import util.BrowserWaits;
import util.OnePObjectMap;

public class SearchResultsPage extends TestBase{

	public static void clickOnPostTab() throws Exception {
		waitForAjax(ob);
		BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_PEOPLE_CSS).get(3).click();
		waitForAjax(ob);
		BrowserWaits.waitTime(6);
	}

	public static void viewOtherUsersPost(String currentUserName) {

		waitForElementTobePresent(ob, By.cssSelector(OR.getProperty("tr_search_results_item_css")), 180);
		List<WebElement> records;

		while (true) {
			records = ob.findElements(By.cssSelector(OR.getProperty("tr_search_results_item_css")));
			int itr = 1;
			String profileName;
			boolean isFound = false;
			for (int i = (itr - 1) * 10; i < records.size(); i++) {
				profileName = records.get(i)
						.findElement(By.cssSelector(OR.getProperty("tr_search_results_item_post_author_css")))
						.getText();
				if (!profileName.equalsIgnoreCase(currentUserName)) {
					jsClick(ob, records.get(i)
							.findElement(By.cssSelector(OR.getProperty("tr_search_results_item_title_css"))));
					isFound = true;
					break;
				}

			}

			if (isFound)
				break;
			itr++;
			((JavascriptExecutor) ob).executeScript("javascript:window.scrollBy(0,document.body.scrollHeight-150)");
			waitForAjax(ob);
		}
	}
	
	
}
