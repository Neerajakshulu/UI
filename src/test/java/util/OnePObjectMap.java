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
	HOME_PROJECT_NEON_OWN_PROFILE_COMMENTS_LIKE_CSS("span[class^='webui-icon webui-icon-like']"),
	HOME_PROJECT_NEON_APP_PROFILE_COMMENTS_CSS("li[class='search-heading tabs ng-isolate-scope active']"),
	HOME_PROJECT_NEON_OWN_PROFILE_INTEREST_SKILLS_CSS("a[class='remove-button ng-binding ng-scope']"),
	HOME_PROJECT_NEON_AUTHORING_PREVENT_BOT_COMMENT_CSS("div[class='comment-error-msg ng-binding']"),
	HOME_PROJECT_NEON_PROFILE_FOLLOWING_CSS("a[class='ng-binding']"),
	HOME_PROJECT_NEON_PROFILE_NAME_CSS("h4[class='webui-media-heading']");
	
	
	private String locator;

	OnePObjectMap(String locator) {
	    this.locator = locator;
	  }
	
	  public String toString() {
	    return this.locator;
	  }
}
