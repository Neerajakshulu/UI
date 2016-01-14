package pages;

import java.util.List;

import org.openqa.selenium.WebElement;

import base.TestBase;
import util.BrowserAction;
import util.BrowserWaits;
import util.OnePObjectMap;

public class Watchlist extends TestBase {

	public static void clearWatchlist() throws Exception {
		HeaderFooterLinksPage.clickOnWatchLink();
		List<WebElement> watchLists = BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_WATCHLIST_RECORDS_CSS);
		if (watchLists.size() > 0) {
			for (WebElement watchList : watchLists) {
				watchList.click();
				BrowserWaits.waitTime(2);
			}
		}
	}
	
	public static void validateWatchlist(String watchlist) throws Exception {
		String expWatchlistPost=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_WATCHLIST_CSS).getText();
		//System.out.println("postwatch-->"+expWatchlistPost);
		if(!expWatchlistPost.equalsIgnoreCase(watchlist)){
			throw new Exception("Post is not adding into my watchlist");
		}
	}
}
