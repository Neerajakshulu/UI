package pages;

import org.openqa.selenium.WebDriver;

import util.BrowserAction;
import util.BrowserWaits;
import watpages.AuthorRecordPage;
import watpages.SearchAuthorClusterPage;
import watpages.SearchAuthorClusterResultsPage;
import watpages.WATLogInPage;
import watpages.WATProfilePage;

public class PageFactory {

	private SearchProfile searchProfilePage = null;
	private ProfilePage profilePage = null;
	private SearchResultsPage searchResultsPage = null;
	private HeaderFooterLinksPage hfPage = null;
	private BrowserAction browserAction = null;
	private BrowserWaits browserWaits = null;
	private LoginTR loginTR = null;
	private OnboardingModalsPage onboarding = null;
	private AccountPage accountPage = null;
	private LinkingModalsPage linkingModalsPage = null;
	private IAMPage iamPage = null;
	private CustomercarePage customercarePage = null;
	private CreatePostAndCommentPage postncommentpage = null;
	
	private WATLogInPage watLogInPage = null;
	private SearchAuthorClusterPage SearchAuthClusterPage = null;
	private SearchAuthorClusterResultsPage searchAuthorClusterResultsPage=null;
	private AuthorRecordPage authorRecordPage=null;
	private WATProfilePage watProfilePage=null;

	
	public IAMPage getIamPage(WebDriver ob) {

		if (iamPage == null) {
			iamPage = new IAMPage(ob);
		}
		return iamPage;
	}

	public CreatePostAndCommentPage getPostCommentPageInstance(WebDriver ob) {

		if (postncommentpage == null) {
			postncommentpage = new CreatePostAndCommentPage(ob);
		}
		return postncommentpage;
	}

	public SearchProfile getSearchProfilePageInstance(WebDriver ob) {
		if (searchProfilePage == null) {
			searchProfilePage = new SearchProfile(ob);
		}

		return searchProfilePage;

	}

	public ProfilePage getProfilePageInstance(WebDriver ob) {
		if (profilePage == null) {
			profilePage = new ProfilePage(ob);
		}

		return profilePage;

	}
	

	public SearchResultsPage getSearchResultsPageInstance(WebDriver ob) {
		if (searchResultsPage == null) {
			searchResultsPage = new SearchResultsPage(ob);
		}

		return searchResultsPage;

	}


	public HeaderFooterLinksPage getHFPageInstance(WebDriver ob) {
		if (hfPage == null) {
			hfPage = new HeaderFooterLinksPage(ob);
		}

		return hfPage;

	}

	public void setHFPageInstance(HeaderFooterLinksPage obj) {
		hfPage = obj;
	}

	public BrowserAction getBrowserActionInstance(WebDriver ob) {
		if (browserAction == null) {
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
	//
	// public Authoring getAuthoringInstance(WebDriver ob) {
	// if (authoring == null) {
	// authoring = new Authoring(ob);
	// }
	//
	// return authoring;
	//
	// }

	public LoginTR getLoginTRInstance(WebDriver ob) {
		if (loginTR == null) {
			loginTR = new LoginTR(ob);
		}

		return loginTR;

	}

	public void setLoginTRInstance(LoginTR obj) {
		loginTR = obj;
	}

	public OnboardingModalsPage getOnboardingModalsPageInstance(WebDriver ob) {
		if (onboarding == null) {
			onboarding = new OnboardingModalsPage(ob);
		}

		return onboarding;

	}

	public AccountPage getAccountPageInstance(WebDriver ob) {
		if (accountPage == null) {
			accountPage = new AccountPage(ob);
		}

		return accountPage;

	}

	public LinkingModalsPage getLinkingModalsInstance(WebDriver ob) {

		if (linkingModalsPage == null) {
			linkingModalsPage = new LinkingModalsPage(ob);
		}
		return linkingModalsPage;
	}

	public void setAccountPageInstance(AccountPage obj) {
		accountPage = obj;
	}

	public WATLogInPage getWatPageInstance(WebDriver ob) {

		if (watLogInPage == null) {
			watLogInPage = new WATLogInPage(ob);
		}
		return watLogInPage;
	}

	public CustomercarePage getCustomercarePage(WebDriver ob) {
		if (customercarePage == null) {
			customercarePage = new CustomercarePage(ob);
		}

		return customercarePage;

	}
	
	/**
	 * This Method provides Search author cluster page object to access the common methods related to search author
	 * cluster page.
	 * 
	 * @throws
	 * 
	 */
	public SearchAuthorClusterPage getSearchAuthClusterPage(WebDriver ob) {

		if (SearchAuthClusterPage == null) {
			SearchAuthClusterPage = new SearchAuthorClusterPage(ob);
		}
		return SearchAuthClusterPage;
	}
	
	/**
	 * Method for Provide Search Author Results page methods
	 * @param ob
	 * @return
	 */
	public SearchAuthorClusterResultsPage getSearchAuthClusterResultsPage(WebDriver ob) {

		if (searchAuthorClusterResultsPage == null) {
			searchAuthorClusterResultsPage = new SearchAuthorClusterResultsPage(ob);
		}
		return searchAuthorClusterResultsPage;
	}
	
	/**
	 * Method for Provide Author Record page methods
	 * @param ob
	 * @return
	 */
	public AuthorRecordPage getAuthorRecordPage(WebDriver ob) {

		if (authorRecordPage == null) {
			authorRecordPage = new AuthorRecordPage(ob);
		}
		return authorRecordPage;
	}


	/**
	 * Method for Provide WAT Profile page methods
	 * @param ob
	 * @return
	 */
	public WATProfilePage getWatProfilePage(WebDriver ob) {

		if (watProfilePage == null) {
			watProfilePage = new WATProfilePage(ob);
		}
		return watProfilePage;
	}
	
	public void clearAllPageObjects() {
		searchResultsPage = null;
		accountPage = null;
		loginTR = null;
		onboarding = null;
		browserWaits = null;
		browserAction = null;
		hfPage = null;
		searchProfilePage = null;
		profilePage = null;
		linkingModalsPage = null;
		postncommentpage = null;
		searchAuthorClusterResultsPage=null;
		authorRecordPage=null;
		watProfilePage=null;
	}
}
