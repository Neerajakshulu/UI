package util;

/**
 * Enum for OneP Object Repository
 * @author UC202376
 *
 */
public enum OnePObjectMap {

	HOME_ONEP_APPS_CSS("i[class='webui-icon webui-icon-app']"),
	HOME_PROJECT_NEON_LOGIN_BUTTON_CSS("button[class='webui-btn-primary unauth-login-btn unauth-ne-btn']"),
	HOME_PROJECT_NEON_APP_FOOTER_LINKS_CSS("ul[class='list-unstyled footer-link-list']"),
	HOME_PROJECT_NEON_APP_RECORD_VIEW_DETALIS_XPATH("//a[contains(text(),'Details')]"),
	HOME_PROJECT_NEON_APP_RECORD_VIEW_DETALIS_BACKTOPN_CSS("a[title='Back to Project Neon']"),
	HOME_PROJECT_NEON_OWN_PROFILE_COMMENTS_LIKE_XPATH("//span[@class='webui-icon webui-icon-like']/following-sibling::span"),
	HOME_PROJECT_NEON_APP_PROFILE_COMMENTS_CSS("li[class='search-heading tabs ng-isolate-scope active']"),
	HOME_PROJECT_NEON_OWN_PROFILE_INTEREST_SKILLS_CSS("a[class='remove-button ng-binding ng-scope']"),
	HOME_PROJECT_NEON_AUTHORING_PREVENT_BOT_COMMENT_CSS("div[class='comment-error-msg ng-binding']"),
	HOME_PROJECT_NEON_PROFILE_FOLLOWING_CSS("a[class='ng-binding']"),
	HOME_PROJECT_NEON_PROFILE_NAME_CSS("h4[class='webui-media-heading']"),
	HOME_PROJECT_NEON_RECORD_VIEW_TITLE_CSS("h3[class='ng-binding']"),
	HOME_PROJECT_NEON_RECORD_VIEW_ARTICLE_WATCH_CSS("button[class^='btn btn-default pub-action-btn']"),
	HOME_PROJECT_NEON_WATCHLIST_DOCINFO_CSS("span[class^='doc-info']"),
	HOME_PROJECT_NEON_WATCHLIST_ARTICLE_COUNT_CSS("h2.headline span"),
	HOME_PROJECT_NEON_WATCHLIST_MORE_BUTTON_XPATH("//button[@class='btn webui-btn-primary']"),
	HOME_PROJECT_NEON_ARTICLE_SEARCH_MORE_BUTTON_CSS("button[class='btn webui-btn-primary ng-binding']"),
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_CSS("button[class='btn btn-default pub-action-btn dropdown-toggle']"),
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_ON_TWITTER_LINK("Share on Twitter"),
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_TWITTER_USERNAME_CSS("input#username_or_email"),	
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_TWITTER_PASSWORD_CSS("input#password"),
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_TWITTER_LOGIN_CSS("input[value='Log in and Tweet']"),
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_TWEET_DESC_CSS("textarea#status"),
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_TWEET_CSS("input[value='Tweet']"),
	
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_ON_LI_LINK("Share on LinkedIn"),
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_USERNAME_CSS("input#session_key-login"),	
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_PASSWORD_CSS("input#session_password-login"),
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_LOGIN_CSS("input[id='btn-primary']"),
	
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_SHARE_CSS("input[class='btn-primary'][value='Share']"),
	
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_ON_FB_LINK("Share on Facebook"),
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_FB_USERNAME_CSS("input#email"),	
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_FB_PASSWORD_CSS("input#pass"),
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_FB_LOGIN_CSS("input[value='Log In']"),
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_FB_SHARE_LINK_CSS("button[name='share'][type='submit']");
	
	
	private String locator;

	OnePObjectMap(String locator) {
	    this.locator = locator;
	  }
	
	  public String toString() {
	    return this.locator;
	  }
}
