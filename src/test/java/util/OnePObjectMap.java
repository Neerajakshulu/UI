package util;

/**
 * Enum for OneP Object Repository
 * 
 * @author UC202376
 *
 */
public enum OnePObjectMap {

	HOME_ONEP_APPS_CSS("div[class='dropdown app-dropdown-wrap'] a i"),
	HOME_ONEP_APPS_PAGE_TITLE_HEADER_CSS("h1[class='heading-1']"),
	HOME_PROJECT_NEON_LOGIN_BUTTON_CSS("button[class='wui-btn wui-btn--primary login-button button-color-primary']"),
	HOME_PROJECT_NEON_PROFILE_COOKIE_POLICY_LINK("Cookie Policy"),
	HOME_PROJECT_NEON_PROFILE_PRIVACY_STATEMENT_LINK("Privacy Statement"),
	HOME_PROJECT_NEON_PROFILE_TERMS_OF_USE_LINK("Terms of Use"),
	HOME_PROJECT_NEON_APP_RECORD_VIEW_DETALIS_XPATH("//a[contains(text(),'View in Web of Science')]"),
	HOME_PROJECT_NEON_APP_RECORD_VIEW_DETALIS_BACKTOPN_CSS("a[title='Back to Project Neon']"),
	HOME_PROJECT_NEON_OWN_PROFILE_COMMENTS_LIKE_XPATH("//span[@class='webui-icon webui-icon-like']/following-sibling::span"),
	HOME_PROJECT_NEON_APP_PROFILE_COMMENTS_CSS("li[class='search-heading tabs ng-isolate-scope active']"),
	HOME_PROJECT_NEON_OWN_PROFILE_INTEREST_SKILLS_CSS("a[class='remove-button ng-binding ng-scope']"),
	HOME_PROJECT_NEON_AUTHORING_PREVENT_BOT_COMMENT_CSS("div[class='ne-create-comment__content'] div[class*='wui-textarea__error']"),
	HOME_PROJECT_NEON_PROFILE_FOLLOWING_CSS("a[class='ng-binding']"),
	HOME_PROJECT_NEON_PROFILE_NAME_CSS("span[class='ne-profile-object-title ng-scope']"),
	HOME_PROJECT_NEON_RECORD_VIEW_TITLE_CSS("h3[class='ng-binding']"),
	HOME_PROJECT_NEON_RECORD_VIEW_ARTICLE_WATCH_CSS("button[class^='btn btn-default pub-action-btn']"),
	HOME_PROJECT_NEON_WATCHLIST_DOCINFO_CSS("span[class^='doc-info']"),
	HOME_PROJECT_NEON_WATCHLIST_ARTICLE_COUNT_CSS("h2.headline span"),
	HOME_PROJECT_NEON_WATCHLIST_MORE_BUTTON_XPATH("//button[@class='btn webui-btn-primary']"),
	HOME_PROJECT_NEON_ARTICLE_SEARCH_MORE_BUTTON_CSS("button[class='btn webui-btn-primary ng-binding']"),
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_CSS("button[class='btn btn-default pub-action-btn dropdown-toggle']"),
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_ON_TWITTER_CSS("i[class='fa fa-twitter share-menu-icon']"),
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_TWITTER_USERNAME_CSS("input#username_or_email"),
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_TWITTER_PASSWORD_CSS("input#password"),
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_TWITTER_LOGIN_CSS("input[value='Log in and Tweet']"),
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_TWEET_DESC_CSS("textarea#status"),
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_TWEET_CSS("input[value='Tweet']"),

	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_ON_LI_CSS("i[class='fa fa-linkedin share-menu-icon']"),
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_USERNAME_CSS("input#session_key-oauthAuthorizeForm"),
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_PASSWORD_CSS("input#session_password-oauthAuthorizeForm"),
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_LOGIN_CSS("input[name='authorize']"),

	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_SHARE_CSS("input[class='btn-primary'][value='Share']"),

	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_ON_FB_CSS("i[class='fa fa-facebook share-menu-icon']"),
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_FB_USERNAME_CSS("input#email"),
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_FB_PASSWORD_CSS("input#pass"),
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_FB_LOGIN_CSS("input[value='Log In']"),
	HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_FB_SHARE_LINK_CSS("*[type='submit'][name*='CONFIRM']"),

	HOME_PROJECT_NEON_ARTICLE_PROFILE_METADATA_TAG("h6"),
	HOME_PROJECT_NEON_PROFILE_INTEREST_AND_SKILLS_CSS("li[class='interest-or-skill ng-binding ng-scope']"),
	HOME_PROJECT_NEON_SEARCH_BOX_CSS("div[class='ne-main-nav'] input[class^='wui-search-bar__input']"),
	HOME_PROJECT_NEON_SEARCH_CLICK_CSS("div[class='ne-main-nav'] button[title='Search'] i[class='fa fa-search']"),
	HOME_PROJECT_NEON_SEARCH_PEOPLE_CSS("li[class^='wui-side-menu__list-item']"),
	HOME_PROJECT_NEON_PROFILE_IMAGE_CSS("div[class='ne-user-profile-image-wrapper']"),
	HOME_PROJECT_NEON_PROFILE_LINK("Profile"),
	HOME_PROJECT_NEON_PROFILE_SIGNOUT_LINK("Sign out"),
	HOME_PROJECT_NEON_PROFILE_ACCOUNT_LINK("Account"),
	HOME_PROJECT_NEON_PROFILE_HELP_LINK("Help"),
	HOME_PROJECT_NEON_SEARCH_PROFILE_TICKMARK_CSS("ne-user-follow-unfollow button span"),
	HOME_PROJECT_NEON_SEARCH_PROFILE_TOOLTIP_CSS("ne-user-follow-unfollow button"),
	HOME_PROJECT_NEON_SEARCH_PROFILE_TITLE_CSS("div[class*='ne-user-profile-object-name ng-scope'] a"),
	HOME_PROJECT_NEON_SEARCH_PROFILE_METADATA_CSS("div[class='wui-descriptor wui-descriptor__profile--medium ng-binding']"),
	HOME_PROJECT_NEON_PROFILE_TITLE_CSS("h2[class*='user-profile-name']"),
	HOME_PROJECT_NEON_PROFILE_ROLE_METADATA_CSS("span[ng-if*='user.role']"),
	HOME_PROJECT_NEON_PROFILE_PRIMARYINSTITUTION_METADATA_CSS("span[ng-if*='user.primaryInstitution']"),
	HOME_PROJECT_NEON_PROFILE_LOCATION_METADATA_CSS("span[ng-if*='user.location']"),
	HOME_PROJECT_NEON_PROFILE_EDIT_CSS("button[ng-show='vm.shouldShowEditButton()']"),
	HOME_PROJECT_NEON_PROFILE_EDIT_FIRST_NAME_CSS("input[name='firstName']"),
	HOME_PROJECT_NEON_PROFILE_EDIT_LAST_NAME_CSS("input[name='lastName']"),
	HOME_PROJECT_NEON_PROFILE_EDIT_ROLE_CSS("input[ng-model*='role']"),
	HOME_PROJECT_NEON_PROFILE_EDIT_PI_CSS("input[ng-model*='vm.field']"),
	HOME_PROJECT_NEON_PROFILE_EDIT_COUNTRY_CSS("input[placeholder='+ Country']"),
	HOME_PROJECT_NEON_PROFILE_EDIT_CANCEL_CSS("button[ng-click*='cancelEditing()']"),
	HOME_PROJECT_NEON_PROFILE_EDIT_UPDATE_CSS("button[ng-click*='saveUserData()']"),
	HOME_PROJECT_NEON_PROFILE_EDIT_FIRST_NAME_ERROR_MESSAGE_CSS("div[ng-messages='userForm.firstName.$error']"),
	HOME_PROJECT_NEON_PROFILE_EDIT_LAST_NAME_ERROR_MESSAGE_CSS("div[ng-messages='userForm.lastName.$error']"),

	HOME_PROJECT_NEON_PROFILE_TAB_POSTS_CSS("a[data-event-category='profileposts']"),
	HOME_PROJECT_NEON_PROFILE_TAB_COMMENTS_CSS("a[data-event-category='profilecomments']"),
	HOME_PROJECT_NEON_PROFILE_TAB_FOLLOWERS_CSS("a[data-event-category='profilefollowers']"),
	HOME_PROJECT_NEON_PROFILE_TAB_FOLLOWING_CSS("a[data-event-category='profilefollowing']"),
	HOME_PROJECT_NEON_PROFILE_TAB_WATCHLIST_CSS("a[data-event-category='profilewatchlists']"),
	HOME_PROJECT_NEON_PROFILE_TAB_COMMENT_APPRECIATE_CSS("button[ng-click*='appreciateThis']"),
	HOME_PROJECT_NEON_PROFILE_PUBLISH_A_POST_BUTTON_CSS("a[redirect-state='profile.post'] button[id='posting']"),
	HOME_PROJECT_NEON_PROFILE_CREATE_POST_MODAL_CSS("div[class='modal-content']"),
	HOME_PROJECT_NEON_PROFILE_CREATE_POST_MODAL_DRAFTS_LINK_XPATH("//div[@class='modal-content']/descendant::a[@class='ng-binding']"),
	HOME_PROJECT_NEON_PROFILE_CREATE_POST_TITLE_CSS("div[class='modal-content'] input[placeholder='Add a title']"),
	HOME_PROJECT_NEON_PROFILE_CREATE_POST_CONTENT_CSS("div[class='modal-content'] div[id*='taTextElement']"),
	HOME_PROJECT_NEON_PROFILE_CREATE_POST_ERROR_CSS("span[class^='wui-textarea__error']"),
	HOME_PROJECT_NEON_PROFILE_CREATE_POST_CANCEL_CSS("div[class='modal-content'] button[data-event-action='cancel'][class^='wui-btn wui-btn']"),
	HOME_PROJECT_NEON_PROFILE_CREATE_POST_PUBLISH_CSS("div[class='modal-content'] button[data-event-action='publish']"),
	// HOME_PROJECT_NEON_PROFILE_POST_TITLE_CSS("div[ng-show='vm.hasPosts'] div[class='ng-scope'] h2 a"),
	HOME_PROJECT_NEON_PROFILE_POST_TITLE_CSS("div[class^='wui-content-title']"),
	HOME_PROJECT_NEON_PROFILE_POST_COUNT_CSS("ul[class='wui-side-menu__list'] li[class^='wui-side-menu__list-item']:nth-child(1) span[class='wui-side-menu__badge ng-binding']"),
	HOME_PROJECT_NEON_PROFILE_DRAFT_POST_COUNT_CSS("ul[class='wui-side-menu__list'] li[class^='wui-side-menu__list-item']:nth-child(2) span[class='wui-side-menu__badge ng-binding']"),
	HOME_PROJECT_NEON_VIEW_POST_EDIT_CSS("div[modal-controller='PostDialogController'] button[id='editing']"),
	HOME_PROJECT_NEON_VIEW_POST_SHARE_CSS("i[class^='webui-icon webui-icon-share']"),
	HOME_PROJECT_NEON_VIEW_POST_WATCH_CSS("i[class^='webui-icon webui-icon-watch']"),
	HOME_PROJECT_NEON_VIEW_POST_STOP_WATCH_CSS("i[class^='webui-icon webui-icon-watch'][class$='watching']"),
	HOME_PROJECT_NEON_VIEW_POST_SHARE_FACEBOOK_CSS("a[label='Share on Facebook']"),
	HOME_PROJECT_NEON_VIEW_POST_SHARE_LINKEDIN_CSS("a[label='Share on LinkedIn']"),
	HOME_PROJECT_NEON_VIEW_POST_SHARE_TWITTER_CSS("a[event-category='share-twitter-posts']"),
	HOME_PROJECT_NEON_HEADER_WATCHLIST_CSS("a[href='#/watchlist']"),
	HOME_PROJECT_NEON_HEADER_NEWSFEED_CSS("a[href='#/home"),
	HOME_PROJECT_NEON_HEADER_PUBLISH_A_POST_CSS("a[redirect-state='profile.post']"),

	HOME_PROJECT_NEON_PROFILE_REMOVE_TOPIC_CSS("a[ng-click='$removeTag()']"),
	HOME_PROJECT_NEON_PROFILE_ADD_TOPIC_CSS("input[placeholder='Add a Topic']"),
	HOME_PROJECT_NEON_PROFILE_ADD_TOPIC_TYPEAHEAD_CSS("div[class='autocomplete ng-scope'] ul li"),
	HOME_PROJECT_NEON_VIEW_POST_APPRECIATION_COUNT_CSS("div[class='ne-publication-sidebar__metrics'] button[class*='wui-icon-metric'] span[class='wui-icon-metric__value ng-binding']"),

	HOME_PROJECT_NEON_VIEW_POST_APPRECIATION_CSS("div[class='ne-publication-sidebar__metrics']  button[class*='wui-icon-metric'] i"),

	HOME_PROJECT_NEON_PROFILE_TAGLIST_PUBLISH_A_POST_BUTTON_CSS("button[id='posting']"),
	HOME_PROJECT_NEON_RECORD_VIEW_POST_TITLE_CSS("h2[class^='wui-content-title']"),
	HOME_PROJECT_NEON_RECORD_VIEW_POST_PROFILE_TILE_CSS("div[class='wui-subtitle ne-user-profile-object-name ng-scope'] a"),
	HOME_PROJECT_NEON_RECORD_VIEW_POST_PROFILE_METADATA_CSS("div[class='ne-user-profile-object-info'] div[class^='wui-descriptor']"),
	HOME_PROJECT_NEON_RECORD_VIEW_POST_CONTENT_CSS("div[class*='body ne-publication__body--post']"),
	HOME_PROJECT_NEON_RECORD_VIEW_POST_TIMESTAMP_CSS("div[ng-if*='PUBLISHED'] span[class^='ne-publication__metadata']"),

	//HOME_PROJECT_NEON_PROFILE_POST_DETAILS_TIMESTAMP_CSS("div[data-ng-show='vm.hasPosts'] p"),
	HOME_PROJECT_NEON_PROFILE_POST_DETAILS_LIKE_XPATH("//div[@data-ng-show='vm.hasPosts']/div[2]/div[1]"),
	HOME_PROJECT_NEON_PROFILE_POST_DETAILS_COMMENTS_XPATH("//div[@data-ng-show='vm.hasPosts']/div[2]/div[2]"),
	HOME_PROJECT_NEON_PROFILE_POST_DETAILS_WATCH_CSS("div[data-ng-show='vm.hasPosts'] button[data-event-category='watchlist-watch-item']"),

	HOME_PROJECT_NEON_WATCHLIST_RECORDS_CSS("span[class='webui-icon-btn-text']"),
	HOME_PROJECT_NEON_WATCHLIST_CSS("h2[class='search-results-title']"),
	HOME_PROJECT_NEON_PROFILE_PUBLISH_A_POST_DISCARD_CSS("button[ng-click='vm.close(vm.buttons.DISCARD)']"),
	HOME_PROJECT_NEON_PROFILE_CREATE_POST_INSERT_LINK_BUTTON_CSS("div[class*='modal-dialog'] button[name='insertLink']"),
	HOME_PROJECT_NEON_RECORD_VIEW_POST_AUTHORNAME_CSS("div[class='wui-subtitle ne-user-profile-object-name ng-scope'] a"),
	HOME_PROJECT_NEON_POST_WATCH_CSS("button[class='pull-left btn webui-icon-btn watchlist-toggle-button']"),
	HOME_PROJECT_NEON_POST_WATCH_CLOSE_CSS("button[ng-click='WatchlistModal.close()']"),
	HOME_PROJECT_NEON_PROFILE_PI_TYPEAHEAD_CSS("ul[ng-show='isOpen() && !moveInProgress']"),
	HOME_PROJECT_NEON_PROFILE_CREATE_POST_CANCEL_DISCARD_XPATH("//div[@class='modal-dialog']/descendant::button[@event-action='cancel' and contains(.,'Discard')]"),
	HOME_PROJECT_NEON_PROFILE_CREATE_POST_CANCEL_KEEP_DRAFT_XPATH("//div[@class='modal-dialog']/descendant::button[@event-action='cancel' and contains(.,'Keep draft')]"),
	HOME_PROJECT_NEON_PROFILE_COUNTRY_METADATA_CSS("span[ng-if*='location']"),
	HOME_PROJECT_NEON_RECORD_VIEW_POST_COMMENTS_COUNT_XPATH("//div[contains(@class,'wui-icon-metric') and contains(.,'Comments')]/descendant::span[@class='wui-icon-metric__value ng-binding']"),
	HOME_PROJECT_NEON_VIEW_POST_FOLLOW_BUTTON_CSS("div[class='ne-publication__header'] button[class*='ne-user-follow-unfollow']"),
	HOME_PROJECT_NEON_VIEW_POST_COMMENT_CSS("div[class='ne-comment-list__comment-content']"),
	HOME_PROJECT_NEON_PROFILE_COMMENTS_COUNT_CSS("a[data-event-category='profilecomments'] span[class*='ng-binding']"),
	HOME_PROJECT_NEON_PROFILE_FOLLOWERS_COUNT_CSS("a[data-event-category='profilefollowers'] span[class='ng-binding']:nth-child(2)"),
	HOME_PROJECT_NEON_PROFILE_FOLLOWING_COUNT_CSS("a[data-event-category='profilefollowing'] span[class='ng-binding']:nth-child(2)"),
	HOME_PROJECT_NEON_PROFILE_TABS_CSS("li[ng-repeat='tab in vm.detailTabs']"),
	HOME_PROJECT_NEON_PROFILE_TABS_RECORDS_CSS("div[class='wui-card__content']"),

	HOME_PROJECT_NEON_RECORD_VIEW_POST_LIKE_COUNT_XPATH("//div[contains(@class,'post-stat')]/descendant::div[contains(@class,'doc-info')][2]/span[contains(@class,'stat-count')][1]"),
	//HOME_PROJECT_NEON_RECORD_VIEW_POST_LIKE_XPATH("//div[contains(@class,'post-stat')]/descendant::button[contains(@tooltip,'Post')]"),
	HOME_PROJECT_NEON_RECORD_VIEW_POST_LIKE_XPATH("//div[@class='ne-publication-sidebar__metrics']/button/i"),
	HOME_PROJECT_NEON_PROFILE_POST_LIKE_CSS("span[class='wui-icon-metric__value ng-binding']"),
	//HOME_PROJECT_NEON_PROFILE_POST_TIMESTAMP_XPATH("//h2[@class='profile-tab-heading']/following::p"),
	HOME_PROJECT_NEON_PROFILE_POST_TIMESTAMP_XPATH("//wui-timestamp[@date='post.keydate']/span"),
	HOME_PROJECT_NEON_PROFILE_SUMMARY_CSS("div[ng-hide='vm.shouldShowSummaryInput()'] p"),
	HOME_PROJECT_NEON_PROFILE_DRAFT_POST_FIRST_TITLE_CSS("a[class='ng-binding'][ng-click*='titleClicked']"),
	HOME_PROJECT_VIEW_POST_FLAG_BUTTON_CSS("button[event-category='post-flag'] i"),
	HOME_PROJECT_VIEW_POST_DELETE_BUTTON_CSS("button[id='deleting']"),
	HOME_PROJECT_VIEW_POST_DELETE_CONFIRMATION_BUTTON_CSS("div[class='modal-content'] button[ng-click='close()']"),
	HOME_PROJECT_NEON_PROFILE_COMMENT_TIMESTAMP_CSS("span[class*='time-stamp']"),
	HOME_PROJECT_NEON_PROFILE_HCR_BADGE_CSS("div[class*='ne-user-profile-object-name']"),
	HOME_PROJECT_NEON_PROFILE_WATCHLIST_CSS("button[class*='wui-icon-only-btn ne-watchlist-dropdown__add-button']"),

	HOME_PROJECT_SECTION_HEADING_LABEL("//button[@class='btn dropdown-toggle ne-search-dropdown-btn ng-binding']"),
	HOME_PROJECT_SELECT_PEOPLE_FOR_SEARCH_IN_DROPDOWN_XPATH("//div[@class='input-group-btn open']//ul[@class='dropdown-menu']//li[4]//a"),
	HOME_PROJECT_SEARCH_TEXTBOX_XPATH("//input[@placeholder='Search Articles, Patents, People and Posts']"),
	HOME_PROJECT_SEARCH_BUTTON_XPATH("//div[@class='ne-main-nav']/descendant:: button[@title='Search']/descendant::i[@class='fa fa-search']"),
	HOME_PROJECT_PEOPLE_LINK("//a[@class='ng-binding ng-scope']"),
	HOME_PROJECT_NEON_RECORD_VIEW_DRAFT_POST_DELETE_XPATH("//div[contains(@class,'user-drafts-display')]/descendant::div[@class='row ng-scope' and contains(.,'TITLE')]/descendant::button[@id='deleting']"),
	HOME_PROJECT_NEON_RECORD_VIEW_DRAFT_POST_EDIT_XPATH("//div[contains(@class,'user-drafts-display')]/descendant::div[@class='row ng-scope' and contains(.,'TITLE')]/descendant::button[@id='editing']"),
	HOME_PROJECT_NEON_RECORD_VIEW_DRAFT_POST_DELETE_CONFIRMATION_CSS("div[class='modal-content'] button[ng-click='close()']"),
	HOME_PROJECT_SEARCH_RESULTS_ARTICLES_LINK("//ne-article-results//a[@class='ng-binding']"),
	HOME_PROJECT_SEARCH_RESULTS_PATENTS_LINK("//ne-patent-results//a[@class='ng-binding']"),
	HOME_PROJECT_SEARCH_RESULTS_POSTS_LINK("//ne-post-results//a[@class='ng-binding']"),
	HOME_PROJECT_COMMENTS_INSERT_LINK_CSS("button[name='insertLink']"),
	HOME_PROJECT_RECORD_COMMENTS_DIV_CSS("div[class='ne-comment-list__comment-content'] div[class^='ne-comment-list__comment-text'] a"),

	// HOME PAGE
	NEWSFEED_SHAREANIDEA_LINK_XPATH("(//button[@id='posting'])[2]"),
	NEWSFEED_MOST_VIEWED_ARTICLES_XPATH("//ne-most-viewed-documents"),
	NEWSFEED_FEATURED_POST_XPATH("//div[@class='wui-card wui-card--featured-post']"),
	NEWSFEED_TRENDINDING_MENU_XPATH("//ul[@class='nav nav-tabs']//tab-heading[contains(.,'FILTER_TYPE')]"),
	NEWSFEED_TRENDINDING_CATEGORIES_LINKS_XPATH("//ne-trending-categories[@class='ng-isolate-scope']//li"),
	NEWSFEED_TRENDINDING_TOPICS_LINKS_XPATH("//ne-trending-categories[@class='ng-isolate-scope']//li[2]//a"),
	NEWSFEED_RECOMMEND_PEOPLE_XPATH("//ne-recommend-people[@class='ng-scope']"),
	NEWSFEED_RECOMMEND_PEOPLE_LABEL_XPATH("//ne-recommend-people[@class='ng-scope']//div"),
	NEWSFEED_RECOMMEND_PEOPLE_IMAGE_XPATH("//ne-recommend-people[@class='ng-scope']//img"),
	NEWSFEED_RECOMMEND_ARTICLE_XPATH("//ne-recommend-articles[@class='ng-scope ng-isolate-scope']"),
	NEWSFEED_RECOMMEND_ARTICLE_LABEL_XPATH("//ne-recommend-articles[@class='ng-scope ng-isolate-scope']//div"),
	NEWSFEED_RECOMMEND_ARTICLE_A_XPATH("//ne-recommend-articles[@class='ng-scope ng-isolate-scope']//a"),
	NEWSFEED_ALL_NOTIFICATIONS_XPATH("//div[contains(@class,'notification-component')][2]"),
	NEWSFEED_NEW_POST_NOTIFICATIONS_XPATH("//ne-notification-post-creation"),
	NEWSFEED_TRENDINDING_DOCUMENT_TITLES_XPATH("//ul[@class='list-unstyled ne-trending__list']/li/div/div"),
	NEWSFEED_NEW_FOLLOWER_NOTITIFICATION_CSS("ne-notification-new-follower[class='ng-scope ng-isolate-scope'] div"),
	NEWSFEED_NEW_FREND_FOLLOW_NOTITIFICATION_CSS("ne-notify-friend-follows[class='ng-scope'] div"),
	NEWSFEED_TOP_COMMENTERS_XPATH("//div[contains(@ng-if,'TopUserCommenters')]"),
	NEWSFEED_NOTIFICATION_FOLLOWUSER_COMMENT_CSS("div[class='wui-card wui-card--comment-event']"),
	NEWSFEED_NOTIFICATION_FOLLOWUSER_NAME_XPATH("//ne-notification-comment-event[@class='ng-scope ng-isolate-scope']//div[@class='ne-profile-object-name wui-emphasis ng-scope']//a"),
	NEWSFEED_NOTIFICATION_PUBLIC_WATCHLIST_COMMENT_XPATH("//div[@class='wui-card wui-card--watchlist-event']"),
	NEWSFEED_NOTIFICATION_LIKE_POST_XPATH("//ne-notification-like[@class='ng-scope']"),
	NEWSFEED_NOTIFICATION_COMMENT_ON_OWN_POST_XPATH("//ne-notification-document-comment-event[@class='ng-scope']"),
	//Search Page Elements
	SEARCH_PAGE_ARTICLES_CSS("a[class='wui-side-menu__link'][ng-click*='ARTICLES']"),
	SEARCH_RESULTS_PAGE_ITEM_CSS("div[class='wui-card__content']"),
	SEARCH_RESULTS_PAGE_ITEM_COMMENTS_COUNT_CSS("div[class*='wui-icon-metric'][tooltip='Comments'] span[class='wui-icon-metric__value ng-binding']"),
	SEARCH_RESULTS_PAGE_ITEM_TITLE_CSS("a[class='ng-binding']"),
	SEARCH_RESULTS_PAGE_POST_TITLE_CSS("a[href^='#/posts']"),
	SEARCH_RESULTS_PAGE_ITEM_TITLE_XPATH("//div[@class='wui-content-title wui-content-title--medium']/a"),
	SEARCH_RESULT_PAGE_SORT_DROPDOWN_CSS("button[id='single-button']"),
	SEARCH_RESULT_PAGE_SORT_LEFT_NAV_PANE_CSS("a[class='wui-side-menu__link']"),
	SEARCH_RESULT_PAGE_POSTS_CSS("a[class='wui-side-menu__link'][ng-click*='POSTS']"),
	SEARCH_RESULT_PAGE_PEOPLE_CSS("a[class='wui-side-menu__link'][ng-click*='PEOPLE']"),
	SEARCH_RESULT_PAGE_ALL_CSS("a[class='wui-side-menu__link'][ng-click*='ALL']"),
	SEARCH_RESULT_PAGE_PATENTS_CSS("a[class='wui-side-menu__link'][ng-click*='PATENTS']"),
	
	// record view page
	RECORD_VIEW_PAGE_COMMENT_DELETE_BUTTON_CSS("button[class='wui-mini-btn wui-mini-btn--secondary'][ng-click='deleteThis(comment.id)']"),
	RECORD_VIEW_PAGE_COMMENT_DELETE_CONFIMATION_OK_BUTTON_CSS("div[class^='modal-footer'] button[ng-click='vm.close()']"), 
	RECORD_VIEW_PAGE_FLAG_REASON_MODAL_CSS("div[class='modal-dialog']")	,
	RECORD_VIEW_PAGE_FLAG_REASON_MODAL_CHECKBOX_CSS("span[class='wui-checkbox__visible']"),
	RECORD_VIEW_PAGE_FLAG_REASON_MODAL_CANCEL_BUTTON_CSS("div[class^='modal-footer'] button[ng-click='vm.cancel()']"),
	RECORD_VIEW_PAGE_FLAG_REASON_MODAL_FLAG_BUTTON_CSS("div[class^='modal-footer'] button[ng-click='vm.close()']"),
	RECORD_VIEW_PAGE_COMMENTS_FLAG_BUTTON_CSS("button[ng-click='reportThis()']"),
	RECORD_VIEW_PAGE_COMMENTS_SHOW_MORE_LINK_CSS("button[ng-click='grabMore()']"),
	RECORD_VIEW_PAGE_COMMENTS_EDIT_BUTTON_CSS("button[ng-click='editThis(comment.id)']"),
	RECORD_VIEW_PAGE_COMMENTS_DELETE_BUTTON_CSS("button[ng-click='deleteThis(comment.id)']"),
	RECORD_VIEW_PAGE_COMMENTS_DYNAMIC_XPATH("//div[@class='ne-comment-list__comment-content']"),
	RECORD_VIEW_PAGE_COMMENTS_DYNAMIC_FLAG_XPATH("descendant::button[@ng-click='reportThis()']/i"),
	RECORD_VIEW_PAGE_COMMENTS_TEXTBOX_CSS("div[id^='taTextElement']"),
	RECORD_VIEW_PAGE_COMMENTS_ADD_COMMENT_BUTTON_CSS("button[ng-click='createComment()']"),
	RECORD_VIEW_PAGE_COMMENTS_USER_PROFILE_LINK_XPATH("descendant::div[contains(@class,'ne-user-profile-object-name')]/descendant::a[contains(@href,'#/profile')]"),
	RECORD_VIEW_PAGE_COMMENTS_COUNT_CSS("h3[class*='comment-list'] span"),
	RECORD_VIEW_PAGE_COMMENTS_EDIT_SUBMIT_BUTTON_CSS("button[class='wui-btn wui-btn--primary']"),
	RECORD_VIEW_PAGE_COMMENTS_MATRICS_COUNT_CSS("span[class='wui-icon-metric__value ng-binding']"),
	RECORD_VIEW_PAGE_COMMENTS_BOLD_ICON_CSS("div[class='ne-create-comment'] button[name='bold']"),
	RECORD_VIEW_PAGE_COMMENTS_ITALIC_ICON_CSS("div[class='ne-create-comment'] button[name='italics']"),
	RECORD_VIEW_PAGE_LI_SHARE_MODAL_SHARE_BUTTON_CSS("div[class='modal-footer wui-modal__footer ng-scope'] button[data-ng-click='shareModal.close()']"),
	RECORD_VIEW_PAGE_LI_SHARE_MODAL_CANCEL_BUTTON_CSS("div[class='modal-footer wui-modal__footer ng-scope'] button[data-ng-click='shareModal.cancel()']"),
	RECORD_VIEW_PAGE_COMMENTS_EDIT_CANCEL_BUTTON_CSS("button[ng-click^='cancelEdit']"),
	RECORD_VIEW_PAGE_COMMENTS_EDIT_ERROR_MESSAGE_CSS("div[class=ne-comment-list__comment-content] div[class='wui-textarea__error']"),
	RECORD_VIEW_PAGE_COMMENTS_EDIT_TEXTBOX_CSS("div[class='ne-comment-list__comment-content'] div[id^='taTextElement']"),
	
	// login page elements
	LOGIN_PAGE_EMAIL_TEXT_BOX_CSS("input[name='loginEmail']"),
	LOGIN_PAGE_PASSWORD_TEXT_BOX_CSS("input[name='loginPassword']"),
	LOGIN_PAGE_SIGN_IN_BUTTON_CSS("button[class='wui-btn wui-btn--primary login-button button-color-primary']"),
	SEARCH_RESULTS_PAGE_PEOPLE_TITLE_CSS("div[class='wui-card__content'] a[class='ng-binding ng-scope']"),
	SEARCH_RESULTS_PAGE_POST_AUTHOR_CSS("a[event-category='searchresult-ck-profile'][class='ng-binding ng-scope']"),
	WTCHLISH_LINK_XPATH("//span[contains(text(),'Watchlist')]"),
	NEWSFEED_RECOMMENDED_ARTICLES_SECTION_ARTICLE_CSS("div[class='wui-card__line-item ng-scope']"), 
	NEWSFEED_RECOMMENDED_ARTICLE_TITLE_CSS("div[class='wui-content-title ng-binding wui-content-title--small']"),
	ARTICLE_TITLE_IN_RECORD_VIEW_PAGE_CSS("div[class='ne-publication__header'] h2"),
	NEWSFEED_RECOMMENDED_PEOPLE_SECTION_FOLLOW_USER_CSS("div[class='wui-card ng-scope'] span[class='fa fa-check unfollow']"),
	NEWSFEED_RECOMMENDED_PEOPLE_SECTION_NUMBER_OF_USER_CSS("div[class='clearfix horizontal-wrapper'] div[class='ne-user-profile-image-wrapper']"),
	NEWSFEED_RECOMMENDED_PEOPLE_SECTION_COPY_USER_NAME_CSS("span[class='ne-user-profile-object-title'] a"),
	NEWSFEED_RECOMMENDED_ARTICLES_SECTION_XPATH("//div[@class='wui-card wui-card--recommended-articles']"),
	NEWSFEED_RECOMMENDED_ARTICLES_SECTION_ARTICLE_NAME_XPATH("//div[@class='wui-content-title ng-binding wui-content-title--small']"),
	NEWSFEED_RECOMMENDED_ARTICLES_SECTION_WATCHLIST_BUTTON_XPATH("//div[@class='wui-card wui-card--recommended-articles']//button"),
	NEWSFEED_RECOMMENDED_PEOPLE_SECTION_XPATH("//div[@class='wui-card ng-scope']"),
	PROFILE_PAGE_AUTOR_NAME_CSS("h2[class='wui-title wui-title--user-profile-name ng-binding']"),
	NEWSFEED_RECOMMENDED_PEOPLE_SECTION_CSS("div[class='wui-card ng-scope']"),
	WATCHLIST_WATCH_BUTTON_CSS("button[class='wui-icon-only-btn ne-watchlist-dropdown__add-button--inactive']"),
	WATCHLIST_WATCH_BUTTON_IN_SEACHPAGE_CSS("button[class='wui-icon-btn dropdown-toggle']"),
	DOCUMENT_TITILE_IN_SEARCHPAGE_XPATH("//div[@class='wui-content-title wui-content-title--medium ng-binding']"),
	DOCUMENT_URL_IN_SEARCHPAGE_XPATH("//ne-post-results[@class='ng-scope ng-isolate-scope']/a"),
	DOCUMENT_TITLE_IN_RECORDVIEW_PAGE_XPATH("//h2[@class='wui-content-title wui-content-title--ne-publication ng-binding']"),
	ADD_COMMENT_BUTTON_XPATH("//button[contains(text(),'Add Comment')]"),
	;

	
	
	private String locator;

	OnePObjectMap(String locator) {
		this.locator = locator;
	}

	public String toString() {
		return this.locator;
	}
}
