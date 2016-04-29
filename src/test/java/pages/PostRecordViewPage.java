package pages;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import util.BrowserWaits;
import util.OnePObjectMap;
import base.TestBase;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * This class contains all the methods related to Post record view page
 * @author uc205521
 *
 */
public class PostRecordViewPage extends TestBase {

	PageFactory pf;

	public PostRecordViewPage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}

	/**
	 * Method to click on SHARE button in post record view
	 */
	public void clickOnShareButton() {
		waitForAjax(ob);
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_CSS.toString()), 180);
		jsClick(ob, ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_CSS
				.toString())));

	}

	/**
	 * Method to click on Share on Facebook link under share menu in post record view
	 */
	public void clickOnFacebookUnderShareMenu() {
		clickOnShareButton();
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_SHARE_FACEBOOK_CSS.toString()), 180);
		jsClick(ob,
				ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_SHARE_FACEBOOK_CSS.toString())));
	}

	/**
	 * Method to click on Share on LinkedIn Link under share menu in post record view
	 */
	public void clickOnLinkedInUnderShareMenu() {
		clickOnShareButton();
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_SHARE_LINKEDIN_CSS.toString()), 180);
		jsClick(ob,
				ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_SHARE_LINKEDIN_CSS.toString())));
	}

	/**
	 * Method to click on Share on Twitter link under share menu in post record view
	 */
	public void clickOnTwitterUnderShareMenu() {
		clickOnShareButton();
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_SHARE_TWITTER_CSS.toString()), 180);
		jsClick(ob,
				ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_SHARE_TWITTER_CSS.toString())));
	}

	/**
	 * Method to click on Watch in post record view
	 */
	public void clickOnWatchButton() {
		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_WATCH_CSS.toString()),
				180);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_WATCH_CSS.toString())).click();
	}

	/**
	 * Method to click on EDIT post icon in post record view
	 */
	public void clickOnEditButton() {
		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_EDIT_CSS.toString()),
				180);
		jsClick(ob, ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_EDIT_CSS.toString())));
	}

	/**
	 * Method to click on Stop Watching button in post record view
	 */
	public void clickOnStopWatchingButton() {
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_STOP_WATCH_CSS.toString()), 180);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_STOP_WATCH_CSS.toString())).click();
	}

	/**
	 * This method performs like or Un-like operations on posts and validate the count increment. 
	 * @param test
	 * @return 
	 * @throws InterruptedException
	 */
	public boolean validateLikeAndUnlikePostActions(ExtentTest test) throws InterruptedException {
		boolean result;
		WebElement appreciationButton;
		int countBefore, countAfter;
		waitForAjax(ob);
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_APPRECIATION_COUNT_CSS.toString()), 180);
		countBefore = Integer.parseInt(ob
				.findElement(
						By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_APPRECIATION_COUNT_CSS.toString()))
				.getText().replaceAll(",", "").trim());
		appreciationButton = ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_APPRECIATION_CSS
				.toString()));

		if (appreciationButton.getAttribute("event-action").equalsIgnoreCase("like")) {
			appreciationButton.click();
			Thread.sleep(15000);// After clicking on like button wait for status to change and count update
			countAfter = Integer
					.parseInt(ob
							.findElement(
									By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_APPRECIATION_COUNT_CSS
											.toString())).getText().replaceAll(",", "").trim());
			try {
				Assert.assertEquals(countAfter, countBefore + 1);
				test.log(LogStatus.PASS, "Appreciation action on post is working as expected");
				result = true;
			} catch (Throwable e) {
				result = false;
				test.log(LogStatus.FAIL, "Appreciation action on post is not working as expected");
			}

		} else {
			appreciationButton.click();
			Thread.sleep(15000);// After clicking on unlike button wait for status to change and count update
			countAfter = Integer
					.parseInt(ob
							.findElement(
									By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_APPRECIATION_COUNT_CSS
											.toString())).getText().replaceAll(",", "").trim());
			try {
				Assert.assertEquals(countAfter, countBefore - 1);
				test.log(LogStatus.PASS, "un Appreciation action on post is working as expected");
				result = true;
			} catch (Throwable e) {
				result = false;
				test.log(LogStatus.FAIL, "un Appreciation action on post is not working as expected");
			}

		}

		return result;

	}

	/**
	 * Method to Validate Post title and Profile meta data in record view page
	 * 
	 * @throws Exception, When Validation not done
	 */
	public void validatePostTitleAndProfileMetadata(String post,
			List<String> profileInfo) throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(
				OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_TITLE_CSS);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(
				OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_PROFILE_METADATA_CSS);
		String postTitle = pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_TITLE_CSS).getText();
		waitForPageLoad(ob);
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_PROFILE_METADATA_CSS.toString()), 180);
		Assert.assertEquals(post, postTitle);
		String postRVProfileTitle = pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_PROFILE_TILE_CSS).getText();
		String profileData = pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_PROFILE_METADATA_CSS).getText();

		if (!(profileInfo.toString().contains(profileData) && profileInfo.toString().contains(postRVProfileTitle))) {
			throw new Exception("Profile info mismatching in Record view page of a Post");
		}

	}

	/**
	 * This method is to validate the Post Creation date in Post record view page.
	 * @return
	 * @throws Exception
	 */
	public boolean verifyPostCreationDate() throws Exception {
		boolean isDisplayed = false;
		waitForPageLoad(ob);
		waitForAllElementsToBePresent(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_TIMESTAMP_CSS.toString()), 180);

		List<WebElement> timestamp = pf.getBrowserActionInstance(ob).getElements(
				OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_TIMESTAMP_CSS);
		String time;
		for (WebElement we : timestamp) {
			time = we.getText();
			if (time.contains("POSTED") && time.contains("|") && (time.contains("AM") || time.contains("PM"))) {
				isDisplayed = true;
				break;
			}

		}

		return isDisplayed;
	}
	
	/**
	 * This method is to validate the Post modification date in Post record view page.
	 * @return
	 * @throws Exception
	 */
	public boolean verifyPostEditedDate() throws Exception {
		boolean isDisplayed = false;
		BrowserWaits.waitTime(5);
		waitForPageLoad(ob);
		waitForAllElementsToBePresent(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_TIMESTAMP_CSS.toString()), 180);
		List<WebElement> timestamp = pf.getBrowserActionInstance(ob).getElements(
				OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_TIMESTAMP_CSS);
		String time;
		for (WebElement we : timestamp) {
			time = we.getText();
			if (time.contains("EDITED") && time.contains("|") && (time.contains("AM") || time.contains("PM"))) {
				isDisplayed = true;
				break;
			}

		}

		return isDisplayed;
	}

	/**
	 * This method captures and returns the post title from Post record view page
	 * @return
	 * @throws Exception
	 */
	public String getPostTitle() throws Exception {
		waitForPageLoad(ob);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(
				OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_TITLE_CSS);
		return pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_TITLE_CSS)
				.getText();
	}
	/**
	 * This method captures and returns the post content from Post record view page
	 * @return
	 * @throws Exception
	 */
	public String getPostContent() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(
				OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_CONTENT_CSS);
		return pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_CONTENT_CSS)
				.getText();
	}

	/**
	 * This method takes the link as as argument and validates that the post content
	 * contains the specified link.
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public boolean validatePostContentForExternalLink(String string) throws Exception {
		BrowserWaits.waitTime(6);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(
				OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_CONTENT_CSS);
		WebElement content = pf.getBrowserActionInstance(ob).getElement(
				OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_CONTENT_CSS);
		if (content.findElements(By.linkText(string)).size() != 0)
			return true;
		else
			return false;

	}

	/**
	 * This method takes the link as as argument and click on it.
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public void clickExternalLinkInPostContent(String url) throws Exception {
		waitForPageLoad(ob);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(
				OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_CONTENT_CSS);
		WebElement content = pf.getBrowserActionInstance(ob).getElement(
				OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_CONTENT_CSS);
		content.findElement(By.linkText(url)).click();
		waitForPageLoad(ob);
	}

	/**
	 * This method validates the page URL
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public boolean validateURL(String url) throws Exception {
		waitForPageLoad(ob);
		if (ob.getCurrentUrl().toLowerCase().contains(url.toLowerCase()))
			return true;
		else
			return false;
	}

	/**
	 * Method to click on post author name in Post record view page.
	 * @throws Exception
	 */
	public void clickOnAuthorName() throws Exception {
		waitForPageLoad(ob);
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(
				OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_AUTHORNAME_CSS);
		jsClick(ob, ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_AUTHORNAME_CSS
				.toString())));
	}

	/**
	 * Method to check whether comment count is displayed in Post record view page
	 * @return
	 */
	public boolean isCommentCountDisplayed() {
		boolean result = false;
		try {

			waitForElementTobeVisible(ob,
					By.xpath(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_COMMENTS_COUNT_XPATH.toString()), 60);
			if (ob.findElement(
					By.xpath(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_COMMENTS_COUNT_XPATH.toString()))
					.isDisplayed())
				result = true;
		} catch (Exception e) {
			return false;
		}

		return result;
	}
	/**
	 * Method to check whether like count is displayed in Post record view page
	 * @return
	 */
	public boolean isLikeCountDisplayed() {
		boolean result = false;
		try {

			waitForElementTobeVisible(ob,
					By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_APPRECIATION_COUNT_CSS.toString()), 60);
			if (ob.findElement(
					By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_APPRECIATION_COUNT_CSS.toString()))
					.isDisplayed())
				result = true;
		} catch (Exception e) {
			return false;
		}

		return result;
	}
	/**
	 * Method to check whether Like button is displayed in Post record view page
	 * @return
	 */
	public boolean isLikeButtonDisplayed() {
		boolean result = false;
		try {

			waitForElementTobeVisible(ob,
					By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_APPRECIATION_CSS.toString()), 60);
			if (ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_APPRECIATION_CSS.toString()))
					.isDisplayed())
				result = true;
		} catch (Exception e) {
			return false;
		}

		return result;
	}
	/**
	 * Method to check whether Follow/Un-follow button is displayed in Post record view page
	 * @return
	 */
	public boolean IsFollowOrUnfollowButtonDispayed() {
		boolean result = false;
		try {

			waitForElementTobeVisible(ob,
					By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_FOLLOW_BUTTON_CSS.toString()), 60);
			if (ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_FOLLOW_BUTTON_CSS.toString()))
					.isDisplayed())
				result = true;
		} catch (Exception e) {
			return false;
		}

		return result;
	}

	
	/**
	 * This method validates the follow or un follow function in Post record view page.
	 * @param test
	 * @throws InterruptedException
	 */
	public void validateFollowOrUnfollow(ExtentTest test) throws InterruptedException {
		BrowserWaits.waitTime(10);
		waitForPageLoad(ob);
		waitForAjax(ob);
		String attribute = ob.findElement(
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_FOLLOW_BUTTON_CSS.toString())).getAttribute(
				"data-tooltip");

		if (attribute.equalsIgnoreCase("Follow this person")) {
			ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_FOLLOW_BUTTON_CSS.toString()))
					.click();

			BrowserWaits.waitTime(10);
			attribute = ob.findElement(
					By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_FOLLOW_BUTTON_CSS.toString()))
					.getAttribute("data-tooltip");

			Assert.assertTrue(attribute.equalsIgnoreCase("Unfollow this person"));
			test.log(LogStatus.PASS, "Follow functionality is working fine in view post record page");

		} else {
			ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_FOLLOW_BUTTON_CSS.toString()))
					.click();
			BrowserWaits.waitTime(10);
			attribute = ob.findElement(
					By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_FOLLOW_BUTTON_CSS.toString()))
					.getAttribute("data-tooltip");

			Assert.assertTrue(attribute.equalsIgnoreCase("Follow this person"));
			test.log(LogStatus.PASS, "UnFollow functionality is working fine in view post record page");

		}
	}

	/**
	 * Metod returns the count of the comments on post record view page.
	 * @return
	 * @throws Exception
	 */
	public int getCommentCount() throws Exception {
		BrowserWaits.waitTime(5);
		waitForAjax(ob);
		waitForElementTobeVisible(ob,
				By.xpath(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_COMMENTS_COUNT_XPATH.toString()), 90);
		String count = ob
				.findElement(By.xpath(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_COMMENTS_COUNT_XPATH.toString()))
				.getText().replaceAll(",", "").trim();

		return Integer.parseInt(count);
	}

	/**
	 * Method validates that specified comment is displayed in Post record view page. 
	 * @param comment
	 * @param test
	 */
	public void validateCommentNewlyAdded(String comment,
			ExtentTest test) {

		waitForAllElementsToBePresent(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_COMMENT_CSS.toString()), 180);

		String actComment = ob
				.findElements(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_COMMENT_CSS.toString())).get(0)
				.getText();

		Assert.assertTrue(actComment.contains(comment));
		test.log(LogStatus.PASS, "Newly added comment on post is available");

	}

	/**
	 * Method to share the records on Facebook
	 * @param fbusername
	 * @param fbpassword
	 * @throws Exception
	 */
	public void shareRecordOnFB(String fbusername,
			String fbpassword) throws Exception {
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_CSS.toString()), 80);
		jsClick(ob, ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_CSS
				.toString())));
		
		String PARENT_WINDOW = ob.getWindowHandle();
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_ON_FB_LINK);
		waitForNumberOfWindowsToEqual(ob, 2);
		Set<String> child_window_handles = ob.getWindowHandles();
		for (String child_window_handle : child_window_handles) {
			if (!child_window_handle.equals(PARENT_WINDOW)) {
				ob.switchTo().window(child_window_handle);
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(
						OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_FB_USERNAME_CSS);
				pf.getBrowserActionInstance(ob).enterFieldValue(
						OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_FB_USERNAME_CSS, fbusername);
				pf.getBrowserActionInstance(ob).enterFieldValue(
						OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_FB_PASSWORD_CSS, fbpassword);
				pf.getBrowserActionInstance(ob).click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_FB_LOGIN_CSS);
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(
						OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_FB_SHARE_LINK_CSS);
				pf.getBrowserActionInstance(ob).click(
						OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_FB_SHARE_LINK_CSS);
				ob.switchTo().window(PARENT_WINDOW);
			}
		}
	}

	/**
	 * Method to share the records on LinkedIn
	 * @param liusername
	 * @param lipassword
	 * @throws Exception
	 */
	public void shareOnLI(String liusername,
			String lipassword) throws Exception {
			waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_CSS.toString()), 80);
		jsClick(ob, ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_CSS
				.toString())));
		String PARENT_WINDOW = ob.getWindowHandle();
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_ON_LI_LINK);
		waitForElementTobeVisible(ob, By.cssSelector("div[class='modal-dialog']"), 40);
		ob.findElement(By.cssSelector("div[class='modal-footer ng-scope'] button[data-ng-click='shareModal.close()']"))
				.click();
		waitForNumberOfWindowsToEqual(ob, 2);
		Set<String> child_window_handles = ob.getWindowHandles();
		System.out.println("window hanles-->" + child_window_handles.size());
		for (String child_window_handle : child_window_handles) {
			if (!child_window_handle.equals(PARENT_WINDOW)) {
				ob.switchTo().window(child_window_handle);
				System.out.println("child window--->" + ob.getTitle());
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(
						OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_USERNAME_CSS);
				pf.getBrowserActionInstance(ob).enterFieldValue(
						OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_USERNAME_CSS, liusername);
				pf.getBrowserActionInstance(ob).enterFieldValue(
						OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_PASSWORD_CSS, lipassword);
				pf.getBrowserActionInstance(ob).click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_LOGIN_CSS);
				ob.switchTo().window(PARENT_WINDOW);
				waitForElementTobeVisible(
						ob,
						By.cssSelector("div[class='modal-footer ng-scope'] button[data-ng-click='shareModal.cancel()']"),
						40);
				jsClick(ob, ob.findElement(By
						.cssSelector("div[class='modal-footer ng-scope'] button[data-ng-click='shareModal.cancel()']")));
			}
		}

	}

	/**
	 * Method to share the records on Twitter
	 * @param tusername
	 * @param tpassword
	 * @throws Exception
	 */
	public void shareOnTwitter(String tusername,
			String tpassword) throws Exception {
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_CSS.toString()), 80);
		jsClick(ob, ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_CSS
				.toString())));
		String PARENT_WINDOW = ob.getWindowHandle();
		String rvPageurl = ob.getCurrentUrl();
		pf.getBrowserActionInstance(ob)
				.click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_ON_TWITTER_LINK);
		ob.manage().window().maximize();
		waitForNumberOfWindowsToEqual(ob, 2);
		Set<String> child_window_handles = ob.getWindowHandles();
		for (String child_window_handle : child_window_handles) {
			if (!child_window_handle.equals(PARENT_WINDOW)) {
				ob.switchTo().window(child_window_handle);
				ob.manage().window().maximize();
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(
						OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_TWITTER_USERNAME_CSS);
				pf.getBrowserActionInstance(ob).enterFieldValue(
						OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_TWITTER_USERNAME_CSS, tusername);
				pf.getBrowserActionInstance(ob).enterFieldValue(
						OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_TWITTER_PASSWORD_CSS, tpassword);
				jsClick(ob, ob.findElement(By
						.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_TWITTER_LOGIN_CSS.toString())));
				//waitForPageLoad(ob);
				BrowserWaits.waitTime(10);
				// waitForElementTobeVisible(ob,
				// By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_TWEET_DESC_CSS.toString()), 180);
				// String
				// articleDesc=pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_TWEET_DESC_CSS).getText();
				// System.out.println("Article Desc-->"+articleDesc+"page url-->"+rvPageurl);
				// if(!articleDesc.contains(rvPageurl)){
				// throw new Exception("Sharing Article Description not populated on Twitter Page");
				// }
				/*waitForElementTobeVisible(ob,
						By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_TWEET_CSS.toString()), 180);
				pf.getBrowserActionInstance(ob).click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_TWEET_CSS);*/
				BrowserWaits.waitTime(10);
				ob.switchTo().window(PARENT_WINDOW);
				BrowserWaits.waitTime(10);
			}
		}
	}

	/**
	 * click post link
	 * 
	 * @throws Exception
	 */
	public void clickPostLike() throws Exception {
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_LIKE_XPATH);
		BrowserWaits.waitTime(2);
	}

	/**
	 * Method to validate the appreciate or un appreciate functionality of the comments.
	 * @param test
	 * @throws Exception
	 */
	public void validateAppreciationComment(ExtentTest test) throws Exception {

		waitForAllElementsToBePresent(ob, By.cssSelector("div[class='col-xs-12 watching-article-comments']"), 90);
		List<WebElement> apprDivs = ob.findElements(By.cssSelector("div[class='col-xs-12 watching-article-comments']"));
		System.out.println("size of total elemntes-->" + apprDivs.size());
		WebElement apprSubDivs = apprDivs.get(0).findElement(By.cssSelector("div[class='comment-content']"))
				.findElement(By.cssSelector("div[class='comment-timestamp-wrapper']"));
		System.out.println("app sub divs-->"
				+ apprSubDivs.findElement(By.cssSelector("span[class='award ng-binding']")).getText());
		scrollingToElementofAPage();
		int apprEarCount = Integer.parseInt(apprSubDivs.findElement(By.cssSelector("span[class='award ng-binding']"))
				.getText().replaceAll(",", "").trim());
		System.out.println("Before count-->" + apprEarCount);

		String attrStatus = apprSubDivs.findElement(By.tagName("button")).getAttribute("ng-click");
		System.out.println("Attribute Status-->" + attrStatus);

		if (attrStatus.contains("DOWN")) {
			scrollingToElementofAPage();
			JavascriptExecutor exe = (JavascriptExecutor) ob;
			exe.executeScript("arguments[0].click();", apprSubDivs.findElement(By.tagName("button")));
			Thread.sleep(4000);// After clicking on unlike button wait for status to change and count update
			int apprAftCount = Integer
					.parseInt(apprSubDivs.findElement(By.cssSelector("span[class='award ng-binding']")).getText()
							.replaceAll(",", "").trim());
			System.out.println("Already liked  After count-->" + apprAftCount);
			if (!(apprAftCount < apprEarCount)) {
				throw new Exception("Comment Appreciation not happended");
			} else {
				test.log(LogStatus.PASS, "Apreciate functionality working fine for comments");
			}
		} else if (attrStatus.contains("UP")) {
			scrollingToElementofAPage();
			JavascriptExecutor exe = (JavascriptExecutor) ob;
			exe.executeScript("arguments[0].click();", apprSubDivs.findElement(By.tagName("button")));
			Thread.sleep(4000);// After clicking on like button wait for status to change and count update
			int apprAftCount = Integer
					.parseInt(apprSubDivs.findElement(By.cssSelector("span[class='award ng-binding']")).getText()
							.replaceAll(",", "").trim());
			System.out.println("Not liked --After count-->" + apprAftCount);
			if (!(apprAftCount > apprEarCount)) {
				throw new Exception("Comment Appreciation not happended");
			} else {
				test.log(LogStatus.PASS, "Un- apreciate functionality working fine for comments");
			}
		}
	}

	/**
	 * Method to validate whether a commented is flagged or unflagged.
	 * @param test
	 * @throws Exception
	 */
	public void validateFlagAndUnflagActionOnPost(ExtentTest test) throws Exception {
		waitForAjax(ob);
		String attribute = ob.findElement(
				By.cssSelector(OnePObjectMap.HOME_PROJECT_VIEW_POST_FLAG_BUTTON_CSS.toString())).getAttribute("class");
		if (attribute.contains("flag-inactive")) {
			flagOrUnflagAPost();
			BrowserWaits.waitTime(6);
			attribute = ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_VIEW_POST_FLAG_BUTTON_CSS.toString()))
					.getAttribute("class");
			Assert.assertTrue(attribute.contains("flag-active"));
			test.log(LogStatus.PASS, "User is able to flag the post");

		} else {
			flagOrUnflagAPost();
			BrowserWaits.waitTime(6);
			attribute = ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_VIEW_POST_FLAG_BUTTON_CSS.toString()))
					.getAttribute("class");
			Assert.assertTrue(attribute.contains("flag-inactive"));
			test.log(LogStatus.PASS, "User is able to Unflag the post");

		}

	}

	/**
	 * Method to click on FLAG or UNFLAG on the comment
	 */
	private void flagOrUnflagAPost() {
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_VIEW_POST_FLAG_BUTTON_CSS.toString())).click();
		waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_authoring_comments_flag_reason_modal_css")), 40);
		jsClick(ob, ob.findElement(By.cssSelector(OR.getProperty("tr_authoring_comments_flag_reason_chkbox_css"))));
		jsClick(ob, ob.findElement(By.cssSelector(OR.getProperty("tr_authoring_comments_flag_button_modal_css"))));
	}

	/**
	 * Method to delete the post from Post record view page
	 * @throws Exception
	 */
	public void deletePost() throws Exception {
		waitForAjax(ob);
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_VIEW_POST_DELETE_BUTTON_CSS.toString()), 180);
		jsClick(ob, ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_VIEW_POST_DELETE_BUTTON_CSS.toString())));

		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_VIEW_POST_DELETE_CONFIRMATION_BUTTON_CSS.toString()), 180);
		jsClick(ob, ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_VIEW_POST_DELETE_CONFIRMATION_BUTTON_CSS
				.toString())));
		waitForAjax(ob);
	}

	/**
	 * Method to add links to the comments.
	 * @param url
	 * @throws Exception
	 */
	public void addExternalLinkComments(String url) throws Exception {
		BrowserWaits.waitTime(15);
		waitForElementTobeVisible(ob, By.cssSelector("div[id^='taTextElement']"), 40);
		WebElement commentArea = ob.findElement(By.cssSelector("div[id^='taTextElement']"));
		commentArea.click();
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_COMMENTS_INSERT_LINK_CSS);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.HOME_PROJECT_COMMENTS_INSERT_LINK_CSS);
		waitForAlertToBePresent(ob, 40);
		Alert alert = ob.switchTo().alert();
		alert.sendKeys(url);
		alert.accept();
		BrowserWaits.waitTime(5);
	}
	/**
	 * Method to validate a specified link added in the comment.
	 * @param url
	 * @throws Exception
	 */
	public boolean validateCommentForExternalLink(String url) throws Exception {
		BrowserWaits.waitTime(6);
		pf.getBrowserWaitsInstance(ob).waitForPageLoad(ob);
		waitForAllElementsToBePresent(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_RECORD_COMMENTS_DIV_CSS.toString()), 90);
		String link = ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_RECORD_COMMENTS_DIV_CSS.toString()))
				.getText();
		if (link.equalsIgnoreCase(url))
			return true;
		else
			return false;
	}

	/**
	 * Method to click on specified link added in the comment.
	 * @param url
	 * @throws Exception
	 */
	public void clickExternalLinkInComments(String url) throws Exception {
		BrowserWaits.waitTime(6);
		waitForAllElementsToBePresent(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_RECORD_COMMENTS_DIV_CSS.toString()), 30);
		String link = ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_RECORD_COMMENTS_DIV_CSS.toString()))
				.getText();
		if (link.equalsIgnoreCase(url))
			jsClick(ob, ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_RECORD_COMMENTS_DIV_CSS.toString())));
		waitForPageLoad(ob);
	}

	/**
	 * Method loads up to 40 comments in a record view page.
	 */
	public void loadComments() {
		boolean isPresent = true;
		WebElement more;
		int count = 0;
		while (isPresent && count < 4) {
			try {
				waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_authoring_comments_more_css")), 40);

				more = ob.findElement(By.cssSelector(OR.getProperty("tr_authoring_comments_more_css")));
				Point point = more.getLocation();
				int y = point.getY() + 100;
				String script = "scroll(0," + y + ");";
				((JavascriptExecutor) ob).executeScript(script);
				jsClick(ob, more);
				waitForAjax(ob);
				count++;
			} catch (Exception e) {
				e.printStackTrace();
				isPresent = false;
			}
		}
	}

	/**
	 * Method to click FlAG button on the comments in record view page
	 * @param currentuser
	 */
	public void clickOnFlagOfOtherUserComments(String currentuser) {
		waitForAllElementsToBePresent(ob, By.xpath(OR.getProperty("tr_authoring_comments_xpath")), 80);
		List<WebElement> commentsList = ob.findElements(By.xpath(OR.getProperty("tr_authoring_comments_xpath")));
		String commentText;
		WebElement flagWe;
		for (int i = 0; i < commentsList.size(); i++) {
			commentText = commentsList.get(i).getText();
			if (!commentText.contains(currentuser) && !commentText.contains("Comment deleted")) {
				flagWe = commentsList.get(i).findElement(
						By.xpath(OR.getProperty("tr_authoring_comments_flag_dynamic_xpath")));
				if (flagWe.getAttribute("class").contains("flag-inactive")) {
					jsClick(ob,
							commentsList.get(i).findElement(
									By.xpath(OR.getProperty("tr_authoring_comments_flag_dynamic_xpath"))));
					break;
				}

			}
		}
	}

	/**
	 * Method to access the article which has comments added to it.
	 */
	public void searchForArticleWithComments() {
		waitForAllElementsToBePresent(ob, By.xpath(OR.getProperty("tr_search_results_item_xpath")), 180);
		List<WebElement> itemList;

		while (true) {
			itemList = ob.findElements(By.cssSelector(OR.getProperty("tr_search_results_item_css")));
			int commentsCount, itr = 1;
			String strCmntCt;
			boolean isFound = false;
			for (int i = (itr - 1) * 10; i < itemList.size(); i++) {
				strCmntCt = itemList.get(i)
						.findElement(By.cssSelector(OR.getProperty("tr_search_results_item_comments_count_css")))
						.getText().replaceAll(",", "").trim();
				commentsCount = Integer.parseInt(strCmntCt);
				if (commentsCount != 0) {
					jsClick(ob,
							itemList.get(i).findElement(
									By.cssSelector(OR.getProperty("tr_search_results_item_title_css"))));

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

	
		public boolean isFlagButtonDispalyedForOthersPost() {
			boolean result = false;
			try {

				waitForElementTobeVisible(ob,
						By.xpath(OnePObjectMap.HOME_PROJECT_VIEW_POST_FLAG_BUTTON_CSS.toString()), 60);
				if (ob.findElement(
						By.xpath(OnePObjectMap.HOME_PROJECT_VIEW_POST_FLAG_BUTTON_CSS.toString()))
						.isDisplayed())
					result = true;
			} catch (Exception e) {
				return false;
			}

			return result;
		}

		public boolean validateCommentForLink(String url) throws InterruptedException {
			BrowserWaits.waitTime(10);
			
			waitForElementTobeVisible(ob, By.cssSelector("div[id^='taTextElement']"), 40);
			WebElement commentArea = ob.findElement(By.cssSelector("div[id^='taTextElement']"));
			
			if (commentArea.findElements(By.linkText(url)).size() != 0)
				return true;
			else
				return false;
			
		}

		public boolean validateFormatOptions(ExtentTest test) {
		boolean result=true;
			try {

				waitForElementTobeVisible(ob,
						By.cssSelector("div[class='comment-add-comment-wrapper'] button[name='bold']"),40);
				if (!ob.findElement(
						By.cssSelector("div[class='comment-add-comment-wrapper'] button[name='bold']"))
						.isEnabled()){
					test.log(LogStatus.FAIL, "Bold format option is not enable for comments");
					result=false;
				}
				waitForElementTobeVisible(ob,
						By.cssSelector("div[class='comment-add-comment-wrapper'] button[name='italics']"),40);
				if (!ob.findElement(
						By.cssSelector("div[class='comment-add-comment-wrapper'] button[name='italics']"))
						.isEnabled()){
					test.log(LogStatus.FAIL, "Italics format option is not enable for comments");
					result=false;
				}
				
				waitForElementTobeVisible(ob,
						By.cssSelector("div[class='comment-add-comment-wrapper'] button[name='insertLink']"),40);
				if (!ob.findElement(
						By.cssSelector("div[class='comment-add-comment-wrapper'] button[name='insertLink']"))
						.isEnabled()){
					test.log(LogStatus.FAIL, "Insert link format option is not enable for comments");
					result=false;
				}
				
				
					
			} catch (Exception e) {
				result= false;
			}
			return result;
		}

		
	
}
