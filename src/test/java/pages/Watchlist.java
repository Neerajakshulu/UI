package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import util.BrowserWaits;
import util.OnePObjectMap;
import base.TestBase;

public class Watchlist extends TestBase {

	PageFactory pf;

	public Watchlist(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();

	}

	public void clearWatchlist() throws Exception {
		pf.getHFPageInstance(ob).clickOnWatchLink();
		List<WebElement> watchLists = pf.getBrowserActionInstance(ob).getElements(
				OnePObjectMap.HOME_PROJECT_NEON_WATCHLIST_RECORDS_CSS);
		if (watchLists.size() > 0) {
			for (WebElement watchList : watchLists) {
				watchList.click();
				BrowserWaits.waitTime(2);
			}
		}
	}

	public void validateWatchlist(String watchlist) throws Exception {
		String expWatchlistPost = pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.HOME_PROJECT_NEON_WATCHLIST_CSS).getText();
		// System.out.println("postwatch-->"+expWatchlistPost);
		if (!expWatchlistPost.equalsIgnoreCase(watchlist)) {
			throw new Exception("Post is not adding into my watchlist");
		}
	}
}
