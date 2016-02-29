package pages;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.BrowserAction;
import util.BrowserWaits;
import util.OnePObjectMap;

public class PostRecordViewPage extends TestBase {

	/**
	 * Method to click on SHARE button in post record view
	 */
	public static void clickOnShareButton() {
		waitForAjax(ob);
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_CSS.toString()), 180);
		jsClick(ob,ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_CSS.toString())));
				

	}

	/**
	 * Method to click on Share on Facebook link under share menu in post record
	 * view
	 */
	public static void clickOnFacebookUnderShareMenu() {
		clickOnShareButton();
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_SHARE_FACEBOOK_CSS.toString()), 180);
		jsClick(ob,ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_SHARE_FACEBOOK_CSS.toString())));
	}

	/**
	 * Method to click on Share on LinkedIn Link under share menu in post record
	 * view
	 */
	public static void clickOnLinkedInUnderShareMenu() {
		clickOnShareButton();
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_SHARE_LINKEDIN_CSS.toString()), 180);
		jsClick(ob,ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_SHARE_LINKEDIN_CSS.toString())));
	}

	/**
	 * Method to click on Share on Twitter link under share menu in post record
	 * view
	 */
	public static void clickOnTwitterUnderShareMenu() {
		clickOnShareButton();
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_SHARE_TWITTER_CSS.toString()), 180);
		jsClick(ob,ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_SHARE_TWITTER_CSS.toString())));
	}

	/**
	 * Method to click on Watch in post record view
	 */
	public static void clickOnWatchButton() {
		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_WATCH_CSS.toString()),
				180);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_WATCH_CSS.toString())).click();
	}

	/**
	 * Method to click on EDIT post icon in post record view
	 */
	public static void clickOnEditButton() {
		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_EDIT_CSS.toString()),
				180);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_EDIT_CSS.toString())).click();
	}

	/**
	 * Method to click on Stop Watching button in post record view
	 */
	public static void clickOnStopWatchingButton() {
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_STOP_WATCH_CSS.toString()), 180);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_STOP_WATCH_CSS.toString())).click();
	}

	public static boolean validateLikeAndUnlikePostActions() throws InterruptedException {
		boolean result;
		WebElement appreciationButton;
		int countBefore, countAfter;
		waitForAjax(ob);
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_APPRECIATION_COUNT_CSS.toString()), 180);
		countBefore = Integer.parseInt(ob
				.findElement(
						By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_APPRECIATION_COUNT_CSS.toString()))
				.getText());
		appreciationButton = ob
				.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_APPRECIATION_CSS.toString()));

		if (appreciationButton.getAttribute("event-action").equalsIgnoreCase("like")) {
			appreciationButton.click();
			Thread.sleep(10000);
			countAfter = Integer.parseInt(ob
					.findElement(
							By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_APPRECIATION_COUNT_CSS.toString()))
					.getText());
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
			Thread.sleep(10000);
			countAfter = Integer.parseInt(ob
					.findElement(
							By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_APPRECIATION_COUNT_CSS.toString()))
					.getText());
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
	 * @throws Exception,
	 *             When Validation not done
	 */
	public static void validatePostTitleAndProfileMetadata(String post, List<String> profileInfo) throws Exception {
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_TITLE_CSS);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_PROFILE_METADATA_CSS);
		String postTitle = BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_TITLE_CSS)
				.getText();
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_PROFILE_METADATA_CSS.toString()), 180);
		BrowserWaits.waitTime(10);
		Assert.assertEquals(post, postTitle);
		String postRVProfileTitle = BrowserAction
				.getElement(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_PROFILE_TILE_CSS).getText();
		String profileData = BrowserAction
				.getElement(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_PROFILE_METADATA_CSS).getText();

		if (!(profileInfo.toString().contains(profileData) && profileInfo.toString().contains(postRVProfileTitle))) {
			throw new Exception("Profile info mismatching in Record view page of a Post");
		}

	}

	public static boolean verifyPostCreationDate() throws Exception {
		boolean isDisplayed = false;
		waitForAllElementsToBePresent(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_TIMESTAMP_CSS.toString()), 180);
		BrowserWaits.waitTime(6);
		List<WebElement> timestamp = BrowserAction
				.getElements(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_TIMESTAMP_CSS);
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

	public static boolean verifyPostEditedDate() throws Exception {
		boolean isDisplayed = false;
		waitForAllElementsToBePresent(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_TIMESTAMP_CSS.toString()), 180);
		BrowserWaits.waitTime(6);
		List<WebElement> timestamp = BrowserAction
				.getElements(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_TIMESTAMP_CSS);
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

	public static String getPostTitle() throws Exception {
		BrowserWaits.waitTime(6);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_TITLE_CSS);
		return BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_TITLE_CSS).getText();
	}

	public static String getPostContent() throws Exception {
		BrowserWaits.waitTime(6);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_CONTENT_CSS);
		return BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_CONTENT_CSS).getText();
	}

	public static boolean validatePostContentForExternalLink(String string) throws Exception {
		BrowserWaits.waitTime(6);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_CONTENT_CSS);
		WebElement content = BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_CONTENT_CSS);
		if (content.findElements(By.linkText(string)).size() != 0)
			return true;
		else
			return false;

	}

	public static void clickExternalLinkInPostContent(String url) throws Exception {
		BrowserWaits.waitTime(6);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_CONTENT_CSS);
		WebElement content = BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_CONTENT_CSS);
		content.findElement(By.linkText(url)).click();
		BrowserWaits.waitTime(6);
	}

	public static boolean validateURL(String url) {

		if (ob.getCurrentUrl().toLowerCase().contains(url.toLowerCase()))
			return true;
		else
			return false;
	}

	public static void clickOnAuthorName() throws Exception {
		BrowserWaits.waitTime(6);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_AUTHORNAME_CSS);
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_AUTHORNAME_CSS);
	}

	public static boolean isCommentCountDisplayed() {
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

	public static boolean isLikeCountDisplayed() {
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

	public static boolean isLikeButtonDisplayed() {
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

	public static boolean IsFollowOrUnfollowButtonDispayed() {
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

	public static void validateFollowOrUnfollow() throws InterruptedException {
		BrowserWaits.waitTime(10);

		String attribute = ob
				.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_FOLLOW_BUTTON_CSS.toString()))
				.getAttribute("tooltip");

		if (attribute.equalsIgnoreCase("Follow this person")) {
			ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_FOLLOW_BUTTON_CSS.toString()))
					.click();

			BrowserWaits.waitTime(10);
			attribute = ob
					.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_FOLLOW_BUTTON_CSS.toString()))
					.getAttribute("tooltip");

			Assert.assertTrue(attribute.equalsIgnoreCase("Unfollow this person"));
			test.log(LogStatus.PASS, "Follow functionality is working fine in view post record page");

		} else {
			ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_FOLLOW_BUTTON_CSS.toString()))
					.click();
			BrowserWaits.waitTime(10);
			attribute = ob
					.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_FOLLOW_BUTTON_CSS.toString()))
					.getAttribute("tooltip");

			Assert.assertTrue(attribute.equalsIgnoreCase("Follow this person"));
			test.log(LogStatus.PASS, "UnFollow functionality is working fine in view post record page");

		}
	}

	public static int getCommentCount() throws Exception {
		//BrowserWaits.waitTime(10);
		waitForAjax(ob);
		waitForElementTobeVisible(ob,
				By.xpath(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_COMMENTS_COUNT_XPATH.toString()), 90);
		String count=ob.findElement(
				By.xpath(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_COMMENTS_COUNT_XPATH.toString())).getText();	
		
		return Integer.parseInt(count);
	}

	
	public static void validateCommentNewlyAdded(String comment){
		
		waitForAllElementsToBePresent(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_COMMENT_CSS.toString()), 180);
		
		String actComment=ob.findElements(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_COMMENT_CSS.toString())).get(0).getText();
		
		Assert.assertTrue(actComment.contains(comment) );
		test.log(LogStatus.PASS, "Newly added comment on post is available");
		
		
	}
	
	
	public static void shareRecordOnFB(String fbusername,String fbpassword) throws Exception{
		test.log(LogStatus.INFO,"Sharing Article on Facebook");
		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_CSS.toString()), 80);
		jsClick(ob, ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_CSS.toString())));
		//BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_CSS);
		Thread.sleep(3000);
		
		String PARENT_WINDOW=ob.getWindowHandle();
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_ON_FB_LINK);
		Thread.sleep(6000);
		
		Set<String> child_window_handles= ob.getWindowHandles();
		//System.out.println("window hanles-->"+child_window_handles.size());
		 for(String child_window_handle:child_window_handles) {
			 if(!child_window_handle.equals(PARENT_WINDOW)) {
				 ob.switchTo().window(child_window_handle);
				// maximizeWindow();
				// System.out.println("child window--->"+ob.getTitle());
				 BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_FB_USERNAME_CSS);
				 BrowserAction.enterFieldValue(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_FB_USERNAME_CSS, fbusername);
				 BrowserAction.enterFieldValue(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_FB_PASSWORD_CSS, fbpassword);
				 BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_FB_LOGIN_CSS);
				 Thread.sleep(6000);
				 BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_FB_SHARE_LINK_CSS);
				 BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_FB_SHARE_LINK_CSS);
				 Thread.sleep(3000);
				
				 ob.switchTo().window(PARENT_WINDOW);
			 }
		 }
	}
	
	
	public static void shareOnLI(String liusername, String lipassword) throws Exception {
			test.log(LogStatus.INFO,"Sharing Article on LinkedIn");
			waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_CSS.toString()), 80);
			jsClick(ob, ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_CSS.toString())));
			//BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_CSS);
			Thread.sleep(3000);
			
			String PARENT_WINDOW=ob.getWindowHandle();
			BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_ON_LI_LINK);
			Thread.sleep(6000);
			waitForElementTobeVisible(ob, By.cssSelector("div[class='modal-dialog']"), 40);
			ob.findElement(By.cssSelector("div[class='modal-footer ng-scope'] button[data-ng-click='shareModal.close()']")).click();
			Thread.sleep(6000);
			Set<String> child_window_handles= ob.getWindowHandles();
			System.out.println("window hanles-->"+child_window_handles.size());
			 for(String child_window_handle:child_window_handles) {
				 if(!child_window_handle.equals(PARENT_WINDOW)) {
					 ob.switchTo().window(child_window_handle);
					 Thread.sleep(6000);
					// ob.manage().window().maximize();
					// Thread.sleep(6000);
					 System.out.println("child window--->"+ob.getTitle());
					 BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_USERNAME_CSS);
					 BrowserAction.enterFieldValue(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_USERNAME_CSS, liusername);
					 BrowserAction.enterFieldValue(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_PASSWORD_CSS, lipassword);
					 BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_LOGIN_CSS);
					 Thread.sleep(10000);
					ob.switchTo().window(PARENT_WINDOW);
					Thread.sleep(8000);
					 jsClick(ob,ob.findElement(By.cssSelector("div[class='modal-footer ng-scope'] button[data-ng-click='shareModal.cancel()']")));
				 }
			 }
			
		}
	
	public static void shareOnTwitter(String tusername,String tpassword) throws Exception {
		
			test.log(LogStatus.INFO,"Sharing Article on Twitter");
			waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_CSS.toString()), 80);
			jsClick(ob, ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_CSS.toString())));
			//BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_CSS);
			Thread.sleep(3000);
			
			String PARENT_WINDOW=ob.getWindowHandle();
			String rvPageurl=ob.getCurrentUrl();
			BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_ON_TWITTER_LINK);
			Thread.sleep(3000);
			ob.manage().window().maximize();
			
			Set<String> child_window_handles= ob.getWindowHandles();
			//System.out.println("window hanles-->"+child_window_handles.size());
			 for(String child_window_handle:child_window_handles) {
				 if(!child_window_handle.equals(PARENT_WINDOW)) {
					 ob.switchTo().window(child_window_handle);
					 ob.manage().window().maximize();
					 BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_TWITTER_USERNAME_CSS);
					 BrowserAction.enterFieldValue(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_TWITTER_USERNAME_CSS, tusername);
					 BrowserAction.enterFieldValue(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_TWITTER_PASSWORD_CSS, tpassword);
					 jsClick(ob, ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_TWITTER_LOGIN_CSS.toString())));
					 Thread.sleep(20000);
					 waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_TWEET_DESC_CSS.toString()), 180);
					 String articleDesc=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_TWEET_DESC_CSS).getText();
					 System.out.println("Article Desc-->"+articleDesc+"page url-->"+rvPageurl);
					 if(!articleDesc.contains(rvPageurl)){
						 throw new Exception("Sharing Article Description not populated on Twitter Page");
					 }
					 
					 BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_TWEET_CSS);
					 Thread.sleep(3000);

					 ob.switchTo().window(PARENT_WINDOW);
				 }
			 }
	}	

	
	/**
	 * click post link
	 * @throws Exception
	 */
	public static void clickPostLike() throws Exception {
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_LIKE_XPATH);
		BrowserWaits.waitTime(2);
	}
	
	}	

