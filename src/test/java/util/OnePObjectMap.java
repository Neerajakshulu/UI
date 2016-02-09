package util;

/**
 * Enum for OneP Object Repository
 * @author UC202376
 *
 */
public enum OnePObjectMap {

	HOME_ONEP_APPS_LINK("Apps"),
	HOME_ONEP_APPS_PAGE_TITLE_HEADER_CSS("h1[class='heading-1']"),
	HOME_PROJECT_NEON_LOGIN_BUTTON_CSS("button[class='webui-btn-primary unauth-login-btn unauth-ne-btn']"),
	HOME_PROJECT_NEON_PROFILE_COOKIE_POLICY_LINK("Cookie Policy"),
	HOME_PROJECT_NEON_PROFILE_PRIVACY_STATEMENT_LINK("Privacy Statement"),
	HOME_PROJECT_NEON_PROFILE_TERMS_OF_USE_LINK("Terms of Use"),
	HOME_PROJECT_NEON_APP_RECORD_VIEW_DETALIS_XPATH("//a[contains(text(),'Details')]"),
	HOME_PROJECT_NEON_APP_RECORD_VIEW_DETALIS_BACKTOPN_CSS("a[title='Back to Project Neon']"),
	HOME_PROJECT_NEON_OWN_PROFILE_COMMENTS_LIKE_XPATH("//span[@class='webui-icon webui-icon-like']/following-sibling::span"),
	HOME_PROJECT_NEON_APP_PROFILE_COMMENTS_CSS("li[class='search-heading tabs ng-isolate-scope active']"),
	HOME_PROJECT_NEON_OWN_PROFILE_INTEREST_SKILLS_CSS("a[class='remove-button ng-binding ng-scope']"),
	HOME_PROJECT_NEON_AUTHORING_PREVENT_BOT_COMMENT_CSS("div[class='comment-error-msg ng-binding']"),
	HOME_PROJECT_NEON_PROFILE_FOLLOWING_CSS("a[class='ng-binding']"),
	HOME_PROJECT_NEON_PROFILE_NAME_CSS("span[class='ne-profile-object-title ng-scope']"),
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
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_USERNAME_CSS("input#session_key-oauthAuthorizeForm"),	
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_PASSWORD_CSS("input#session_password-oauthAuthorizeForm"),
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_LOGIN_CSS("input[name='authorize']"),
	
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_SHARE_CSS("input[class='btn-primary'][value='Share']"),
	
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_ON_FB_LINK("Share on Facebook"),
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_FB_USERNAME_CSS("input#email"),	
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_FB_PASSWORD_CSS("input#pass"),
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_FB_LOGIN_CSS("input[value='Log In']"),
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_FB_SHARE_LINK_CSS("button[name*='CONFIRM'][type='submit']"),
	
	HOME_PROJECT_NEON_ARTICLE_PROFILE_METADATA_TAG("h6"),
	HOME_PROJECT_NEON_PROFILE_INTEREST_AND_SKILLS_CSS("li[class='interest-or-skill ng-binding ng-scope']"),
	HOME_PROJECT_NEON_SEARCH_BOX_CSS("input[placeholder='Search']"),
	HOME_PROJECT_NEON_SEARCH_CLICK_CSS("i[class='webui-icon webui-icon-search']"),
	
	HOME_PROJECT_NEON_SEARCH_PEOPLE_CSS("li[class='content-type-selector ng-scope']"),
	HOME_PROJECT_NEON_PROFILE_IMAGE_CSS("span[class='ne-profile-image-wrapper']"),
	HOME_PROJECT_NEON_PROFILE_LINK("Profile"),
	HOME_PROJECT_NEON_PROFILE_SIGNOUT_LINK("Sign out"),
	HOME_PROJECT_NEON_PROFILE_ACCOUNT_LINK("Account"),
	HOME_PROJECT_NEON_PROFILE_HELP_LINK("Help"),
	HOME_PROJECT_NEON_SEARCH_PROFILE_TICKMARK_CSS("ne-profile-follow-unfollow button span"), 
	HOME_PROJECT_NEON_SEARCH_PROFILE_TOOLTIP_CSS("ne-profile-follow-unfollow button"),
	HOME_PROJECT_NEON_SEARCH_PROFILE_TITLE_CSS("span[class='ne-profile-object-title ng-scope'] a"),
	HOME_PROJECT_NEON_SEARCH_PROFILE_METADATA_CSS("h6[class='ne-profile-object-metadata-wrapper ng-binding']"),
	HOME_PROJECT_NEON_PROFILE_TITLE_CSS("h2[class^='profile-name']"),
	HOME_PROJECT_NEON_PROFILE_ROLE_METADATA_CSS("h6[ng-show='role']"),
	HOME_PROJECT_NEON_PROFILE_PRIMARYINSTITUTION_METADATA_CSS("h6[ng-show='primaryInstitution']"),
	HOME_PROJECT_NEON_PROFILE_LOCATION_METADATA_CSS("button[ng-click^='startEditing']"),
	HOME_PROJECT_NEON_PROFILE_EDIT_CSS("span[class='webui-icon webui-icon-edit']"),
	HOME_PROJECT_NEON_PROFILE_EDIT_TITLE_CSS("input[ng-model='title']"),
	HOME_PROJECT_NEON_PROFILE_EDIT_ROLE_CSS("input[ng-model='role']"),
	HOME_PROJECT_NEON_PROFILE_EDIT_PI_CSS("input[ng-model='vm.field']"),
	HOME_PROJECT_NEON_PROFILE_EDIT_COUNTRY_CSS("input[placeholder='Add your country']"),
	HOME_PROJECT_NEON_PROFILE_EDIT_CANCEL_CSS("button[ng-click='cancelEditing()']"),
	HOME_PROJECT_NEON_PROFILE_EDIT_UPDATE_CSS("button[ng-click='saveUserData()']"),

	HOME_PROJECT_NEON_PROFILE_TAB_POSTS_CSS("a[data-event-category='profileposts']"),
	HOME_PROJECT_NEON_PROFILE_TAB_COMMENTS_CSS("a[data-event-category='profilecomments']"),
	HOME_PROJECT_NEON_PROFILE_TAB_FOLLOWERS_CSS("a[data-event-category='profilefollowers']"),
	HOME_PROJECT_NEON_PROFILE_TAB_FOLLOWING_CSS("a[data-event-category='profilefollowing']"),
	HOME_PROJECT_NEON_PROFILE_TAB_COMMENT_APPRECIATE_CSS("button[ng-click*='appreciateThis']"),
	HOME_PROJECT_NEON_PROFILE_PUBLISH_A_POST_BUTTON_CSS("div[class='post-button-wrapper'] button[id='posting']"),
	HOME_PROJECT_NEON_PROFILE_CREATE_POST_MODAL_CSS("div[class='modal-content']"),
	HOME_PROJECT_NEON_PROFILE_CREATE_POST_TITLE_CSS("input[class^='post-title-input']"),
	HOME_PROJECT_NEON_PROFILE_CREATE_POST_CONTENT_CSS("div[name='createdPostContent'] div[id^='taTextElement']"),
	HOME_PROJECT_NEON_PROFILE_CREATE_POST_ERROR_CSS("div[class='post-error-msg ng-binding'] p"),
	HOME_PROJECT_NEON_PROFILE_CREATE_POST_CANCEL_CSS("button[event-action='cancel']"),
	HOME_PROJECT_NEON_PROFILE_CREATE_POST_PUBLISH_CSS("button[event-action='publish']"),
	//HOME_PROJECT_NEON_PROFILE_POST_TITLE_CSS("div[ng-show='vm.hasPosts'] div[class='ng-scope'] h2 a"),
	HOME_PROJECT_NEON_PROFILE_POST_TITLE_CSS("h2[class='profile-tab-heading']"),
	HOME_PROJECT_NEON_PROFILE_POST_COUNT_CSS("a[data-event-category='profileposts'] span[ng-bind='vm.count']"),
	HOME_PROJECT_NEON_VIEW_POST_EDIT_CSS("div[modal-controller='PostDialogController'] button[id='editing']"),
	HOME_PROJECT_NEON_VIEW_POST_SHARE_CSS("i[class^='webui-icon webui-icon-share']"),
	HOME_PROJECT_NEON_VIEW_POST_WATCH_CSS("i[class^='webui-icon webui-icon-watch']"),
	HOME_PROJECT_NEON_VIEW_POST_STOP_WATCH_CSS("i[class^='webui-icon webui-icon-watch'][class$='watching']"),
	HOME_PROJECT_NEON_VIEW_POST_SHARE_FACEBOOK_CSS("a[label='Share on Facebook']"),
	HOME_PROJECT_NEON_VIEW_POST_SHARE_LINKEDIN_CSS("a[label='Share on LinkedIn']"),
	HOME_PROJECT_NEON_VIEW_POST_SHARE_TWITTER_CSS("a[event-category='share-twitter-posts']"),
	HOME_PROJECT_NEON_HEADER_WATCHLIST_CSS("a[href='#/watch-list']"),
	HOME_PROJECT_NEON_HEADER_HOME_LINK("Home"),
	HOME_PROJECT_NEON_HEADER_PUBLISH_A_POST_LINK("Publish a Post"),
	
	HOME_PROJECT_NEON_PROFILE_REMOVE_TOPIC_CSS("a[ng-click='$removeTag()']"),
	HOME_PROJECT_NEON_PROFILE_ADD_TOPIC_CSS("input[placeholder='Add a Topic']"),
	HOME_PROJECT_NEON_PROFILE_ADD_TOPIC_TYPEAHEAD_CSS("div[class='autocomplete ng-scope'] ul li"), 
	HOME_PROJECT_NEON_VIEW_POST_APPRECIATION_COUNT_CSS("div[class*='post-stat'] div[class='doc-info']:nth-child(2) span[class*='stat-count']"),
	
	HOME_PROJECT_NEON_VIEW_POST_APPRECIATION_CSS("div[class*='post-stat'] button"),
	
	HOME_PROJECT_NEON_PROFILE_TAGLIST_PUBLISH_A_POST_BUTTON_CSS("button[id='posting']"),
	HOME_PROJECT_NEON_RECORD_VIEW_POST_TITLE_CSS("div[class='full-record'] h2[class='ng-binding']"),
	HOME_PROJECT_NEON_RECORD_VIEW_POST_PROFILE_TILE_CSS("h3[class$='ne-profile-object-title-wrapper']"),
	HOME_PROJECT_NEON_RECORD_VIEW_POST_PROFILE_METADATA_CSS("h6[class='ne-profile-object-metadata-wrapper ng-binding']"),
	HOME_PROJECT_NEON_RECORD_VIEW_POST_CONTENT_CSS("div[class='full-record'] div[class='ng-binding']"),
	HOME_PROJECT_NEON_RECORD_VIEW_POST_TIMESTAMP_CSS("div[class='full-record'] div[class*='timestamp-wrapper'] div"),

	HOME_PROJECT_NEON_PROFILE_POST_DETAILS_TIMESTAMP_CSS("div[ng-show='vm.hasPosts'] div[class='ng-scope'] p"),
	HOME_PROJECT_NEON_PROFILE_POST_DETAILS_LIKE_XPATH("//div[@ng-show='vm.hasPosts']/div[@class='ng-scope']/div[1]"),
	HOME_PROJECT_NEON_PROFILE_POST_DETAILS_COMMENTS_XPATH("//div[@ng-show='vm.hasPosts']/div[@class='ng-scope']/div[2]"),
	HOME_PROJECT_NEON_PROFILE_POST_DETAILS_WATCH_CSS("div[ng-show='vm.hasPosts'] div[class='ng-scope'] ne-watch-icon"),
	
	HOME_PROJECT_NEON_WATCHLIST_RECORDS_CSS("span[class='webui-icon-btn-text']"),
	HOME_PROJECT_NEON_WATCHLIST_CSS("h2[class='search-results-title']"),
	HOME_PROJECT_NEON_PROFILE_PUBLISH_A_POST_DISCARD_CSS("button[ng-click='vm.close(vm.buttons.DISCARD)']");

	
	
	private String locator;

	OnePObjectMap(String locator) {
	    this.locator = locator;
	  }
	
	  public String toString() {
	    return this.locator;
	  }
}
