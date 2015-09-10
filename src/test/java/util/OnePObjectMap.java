package util;

/**
 * Enum for OneP Object Repository
 * @author UC202376
 *
 */
public enum OnePObjectMap {

	HOME_ONEP_APPS_CSS("i[class='webui-icon webui-icon-app']"),
	HOME_PROJECT_NEON_LOGIN_BUTTON_CSS("button[class='btn webui-btn-primary unauth-login-btn']"),
	HOME_PROJECT_NEON_APP_FOOTER_LINKS_CSS("ul[class='list-unstyled footer-link-list']"),
	HOME_PROJECT_NEON_APP_RECORD_VIEW_DETALIS_XPATH("//a[contains(text(),'Details')]"),
	HOME_PROJECT_NEON_APP_RECORD_VIEW_DETALIS_BACKTOPN_CSS("a[title='Back to Project Neon']");
	
	
	private String locator;

	OnePObjectMap(String locator) {
	    this.locator = locator;
	  }
	
	  public String toString() {
	    return this.locator;
	  }
}
