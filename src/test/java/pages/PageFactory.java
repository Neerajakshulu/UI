package pages;

import org.openqa.selenium.WebDriver;

import suiteC.Authoring;
import suiteC.LoginTR;
import util.BrowserAction;
import util.BrowserWaits;

public class PageFactory {


	private  SearchProfile searchProfilePage = null;
	private  ProfilePage profilePage = null;
	private  PostRecordViewPage postRVPage = null;
	private  SearchResultsPage searchResultsPage = null;
	private  Watchlist WatchlistPage = null;
	private  HeaderFooterLinksPage hfPage = null;
	private  BrowserAction browserAction = null;
	private  BrowserWaits browserWaits = null;
	private  LoginTR loginTR = null;
	private  Authoring authoring = null;

	public   SearchProfile getSearchProfilePageInstance(WebDriver ob) {
		if (searchProfilePage == null) {
			searchProfilePage = new SearchProfile(ob);
		}

		return searchProfilePage;

	}
	
	

	public  ProfilePage getProfilePageInstance(WebDriver ob) {
		if (profilePage == null) {
			profilePage = new ProfilePage(ob);
		}

		return profilePage;

	}
	
	

	public  PostRecordViewPage getpostRVPageInstance(WebDriver ob) {
		if (postRVPage == null) {
			postRVPage = new PostRecordViewPage(ob);
		}

		return postRVPage;

	}
	
	

	public SearchResultsPage getSearchResultsPageInstance(WebDriver ob) {
		if (searchResultsPage == null) {
			searchResultsPage = new SearchResultsPage(ob);
		}

		return searchResultsPage;

	}
	
	

	public Watchlist getWatchlistPageInstance(WebDriver ob) {
		if (WatchlistPage == null) {
			WatchlistPage = new Watchlist(ob);
		}

		return WatchlistPage;

	}
	

	
	public HeaderFooterLinksPage getHFPageInstance(WebDriver ob){
		if(hfPage == null){
			hfPage = new HeaderFooterLinksPage(ob);
		}
		
		return hfPage;
		
	}
	

	
	public BrowserAction getBrowserActionInstance(WebDriver ob){
		if(browserAction == null){
			browserAction = new BrowserAction(ob);
		}
		
		return browserAction;
		
	}
	
	

	public BrowserWaits getBrowserWaitsInstance(WebDriver ob) {
		if (browserWaits == null) {
			browserWaits = new BrowserWaits(ob);
		}

		return browserWaits;

	}
	
	public Authoring getAuthoringInstance(WebDriver ob) {
		if (authoring == null) {
			authoring = new Authoring(ob);
		}

		return authoring;

	}
	
	public  LoginTR getLoginTRInstance(WebDriver ob) {
			if (loginTR == null) {
				loginTR = new LoginTR(ob);
			}
		

		return loginTR;

	}
	
	
}
