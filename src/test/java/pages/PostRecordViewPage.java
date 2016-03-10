package pages;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
		waitForPageLoad(ob);
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_PROFILE_METADATA_CSS.toString()), 180);
		//BrowserWaits.waitTime(10);
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
		waitForPageLoad(ob);
		waitForAllElementsToBePresent(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_TIMESTAMP_CSS.toString()), 180);
		
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
		waitForPageLoad(ob);
		waitForAllElementsToBePresent(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_TIMESTAMP_CSS.toString()), 180);
		//BrowserWaits.waitTime(6);
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
		//BrowserWaits.waitTime(6);
		waitForPageLoad(ob);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_TITLE_CSS);
		return BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_TITLE_CSS).getText();
	}

	public static String getPostContent() throws Exception {
		//BrowserWaits.waitTime(6);
		
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
		//BrowserWaits.waitTime(6);
		waitForPageLoad(ob);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_CONTENT_CSS);
		WebElement content = BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_CONTENT_CSS);
		content.findElement(By.linkText(url)).click();
		//BrowserWaits.waitTime(6);
		waitForPageLoad(ob);
	}

	public static boolean validateURL(String url) throws Exception {
		waitForPageLoad(ob);
		if (ob.getCurrentUrl().toLowerCase().contains(url.toLowerCase()))
			return true;
		else
			return false;
	}

	public static void clickOnAuthorName() throws Exception {
		waitForPageLoad(ob);
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
		waitForPageLoad(ob);

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
		//Thread.sleep(3000);
		
		String PARENT_WINDOW=ob.getWindowHandle();
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_ON_FB_LINK);
		//Thread.sleep(6000);
		waitForNumberOfWindowsToEqual(ob, 2);
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
				// Thread.sleep(6000);
				 BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_FB_SHARE_LINK_CSS);
				 BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_FB_SHARE_LINK_CSS);
				// Thread.sleep(3000);
				
				 ob.switchTo().window(PARENT_WINDOW);
			 }
		 }
	}
	
	
	public static void shareOnLI(String liusername, String lipassword) throws Exception {
			test.log(LogStatus.INFO,"Sharing Article on LinkedIn");
			waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_CSS.toString()), 80);
			jsClick(ob, ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_CSS.toString())));
			//BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_CSS);
			//Thread.sleep(3000);
			
			String PARENT_WINDOW=ob.getWindowHandle();
			BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_ON_LI_LINK);
			//Thread.sleep(6000);
			waitForElementTobeVisible(ob, By.cssSelector("div[class='modal-dialog']"), 40);
			ob.findElement(By.cssSelector("div[class='modal-footer ng-scope'] button[data-ng-click='shareModal.close()']")).click();
			waitForNumberOfWindowsToEqual(ob, 2);
			//Thread.sleep(6000);
			Set<String> child_window_handles= ob.getWindowHandles();
			System.out.println("window hanles-->"+child_window_handles.size());
			 for(String child_window_handle:child_window_handles) {
				 if(!child_window_handle.equals(PARENT_WINDOW)) {
					 ob.switchTo().window(child_window_handle);
					// Thread.sleep(6000);
					// ob.manage().window().maximize();
					// Thread.sleep(6000);
					 System.out.println("child window--->"+ob.getTitle());
					 BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_USERNAME_CSS);
					 BrowserAction.enterFieldValue(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_USERNAME_CSS, liusername);
					 BrowserAction.enterFieldValue(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_PASSWORD_CSS, lipassword);
					 BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_LI_LOGIN_CSS);
					 //Thread.sleep(10000);
					ob.switchTo().window(PARENT_WINDOW);
					//Thread.sleep(8000);
					waitForElementTobeVisible(ob, By.cssSelector("div[class='modal-footer ng-scope'] button[data-ng-click='shareModal.cancel()']"), 40);
					 jsClick(ob,ob.findElement(By.cssSelector("div[class='modal-footer ng-scope'] button[data-ng-click='shareModal.cancel()']")));
				 }
			 }
			
		}
	
	public static void shareOnTwitter(String tusername,String tpassword) throws Exception {
		
			test.log(LogStatus.INFO,"Sharing Article on Twitter");
			waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_CSS.toString()), 80);
			jsClick(ob, ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_CSS.toString())));
			//BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_CSS);
			//Thread.sleep(3000);
			
			String PARENT_WINDOW=ob.getWindowHandle();
			String rvPageurl=ob.getCurrentUrl();
			BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_SHARE_ON_TWITTER_LINK);
			//Thread.sleep(3000);
			ob.manage().window().maximize();
			waitForNumberOfWindowsToEqual(ob, 2);
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
					 //Thread.sleep(20000);
					 waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_TWEET_DESC_CSS.toString()), 180);
					 String articleDesc=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_TWEET_DESC_CSS).getText();
					 System.out.println("Article Desc-->"+articleDesc+"page url-->"+rvPageurl);
					 if(!articleDesc.contains(rvPageurl)){
						 throw new Exception("Sharing Article Description not populated on Twitter Page");
					 }
					 
					 BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_RECORD_VIEW_TWEET_CSS);
					 //Thread.sleep(3000);

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
	
	
	public  static void validateAppreciationComment() throws Exception  {
		
		waitForAllElementsToBePresent(ob, By.cssSelector("div[class='col-xs-12 watching-article-comments']"), 90);
		List<WebElement> apprDivs=ob.findElements(By.cssSelector("div[class='col-xs-12 watching-article-comments']"));
		System.out.println("size of total elemntes-->"+apprDivs.size());
		WebElement apprSubDivs = apprDivs.get(0).findElement(By.cssSelector("div[class='comment-content']"))
				.findElement(By.cssSelector("div[class='comment-timestamp-wrapper']"));
		
		//List<WebElement> apprSubDivs=apprDivs.get(0).findElements(By.cssSelector("div.row")).get(0).findElements(By.cssSelector("div[class^='col-xs-']"));
		System.out.println("app sub divs-->"+apprSubDivs.findElement(By.cssSelector("span[class='award ng-binding']")).getText());
		scrollingToElementofAPage();
		int apprEarCount=Integer.parseInt(apprSubDivs.findElement(By.cssSelector("span[class='award ng-binding']")).getText());
		System.out.println("Before count-->"+apprEarCount);
		
		String attrStatus=apprSubDivs.findElement(By.tagName("button")).getAttribute("ng-click");
		System.out.println("Attribute Status-->"+attrStatus);
		
		if(attrStatus.contains("DOWN")) {
			scrollingToElementofAPage();
			JavascriptExecutor exe= (JavascriptExecutor)ob;
			exe.executeScript("arguments[0].click();", apprSubDivs.findElement(By.tagName("button")));
			Thread.sleep(4000);
			int apprAftCount=Integer.parseInt(apprSubDivs.findElement(By.cssSelector("span[class='award ng-binding']")).getText());
			System.out.println("Already liked  After count-->"+apprAftCount);
			   if(!(apprAftCount<apprEarCount)) {
				   throw new Exception("Comment Appreciation not happended");
			   }else{
				   test.log(LogStatus.PASS, "Apreciate functionality working fine for comments");
			   }
		} 
		else if (attrStatus.contains("UP")) {
			scrollingToElementofAPage();
			JavascriptExecutor exe= (JavascriptExecutor)ob;
			exe.executeScript("arguments[0].click();", apprSubDivs.findElement(By.tagName("button")));
			Thread.sleep(4000);
			int apprAftCount=Integer.parseInt(apprSubDivs.findElement(By.cssSelector("span[class='award ng-binding']")).getText());
			System.out.println("Not liked --After count-->"+apprAftCount);
			   if(!(apprAftCount>apprEarCount)) {
				   throw new Exception("Comment Appreciation not happended");
			   }else{
				   test.log(LogStatus.PASS, "Un- apreciate functionality working fine for comments");
			   }
			}
	}
	
	public static void validateFlagAndUnflagActionOnPost() throws Exception {
		waitForAjax(ob);
		String attribute=ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_VIEW_POST_FLAG_BUTTON_CSS.toString())).getAttribute("class");
		if(attribute.contains("flag-inactive")){
			flagOrUnflagAPost();
			BrowserWaits.waitTime(6);
			attribute=ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_VIEW_POST_FLAG_BUTTON_CSS.toString())).getAttribute("class");
			Assert.assertTrue(attribute.contains("flag-active"));
			test.log(LogStatus.PASS, "User is able to flag the post");
			
		}else{
			flagOrUnflagAPost();
			BrowserWaits.waitTime(6);
			attribute=ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_VIEW_POST_FLAG_BUTTON_CSS.toString())).getAttribute("class");
			Assert.assertTrue(attribute.contains("flag-inactive"));
			test.log(LogStatus.PASS, "User is able to Unflag the post");
			
		}
		
		
	}

	private static void flagOrUnflagAPost() {
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_VIEW_POST_FLAG_BUTTON_CSS.toString())).click();
		waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_authoring_comments_flag_reason_modal_css")),
				40);
		jsClick(ob,ob.findElement(By.cssSelector(OR.getProperty("tr_authoring_comments_flag_reason_chkbox_css"))));
		jsClick(ob,ob.findElement(By.cssSelector(OR.getProperty("tr_authoring_comments_flag_button_modal_css"))));
	}

	public static void deletePost() throws Exception {
		waitForAjax(ob);
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_VIEW_POST_DELETE_BUTTON_CSS.toString()), 180);
		jsClick(ob,ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_VIEW_POST_DELETE_BUTTON_CSS.toString())));
		
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_VIEW_POST_DELETE_CONFIRMATION_BUTTON_CSS.toString()), 180);
		jsClick(ob,ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_VIEW_POST_DELETE_CONFIRMATION_BUTTON_CSS.toString())));
		waitForAjax(ob);
	}
	
	public static void addExternalLinkComments(String url) throws Exception{
		//BrowserWaits.waitTime(5);	
		waitForElementTobeVisible(ob, By.cssSelector("div[id^='taTextElement']"), 40);
		WebElement commentArea=ob.findElement(By.cssSelector("div[id^='taTextElement']"));
			commentArea.click();
			BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_COMMENTS_INSERT_LINK_CSS);
			BrowserAction.click(OnePObjectMap.HOME_PROJECT_COMMENTS_INSERT_LINK_CSS);
			waitForAlertToBePresent(ob, 40);
			Alert alert=ob.switchTo().alert();
			alert.sendKeys(url);
			alert.accept();
			BrowserWaits.waitTime(5);
	}

	public static boolean validateCommentForExternalLink(String url) throws Exception {
		//BrowserWaits.waitTime(6);
		BrowserWaits.waitForAllElementsToBePresent(ob, By.cssSelector(OnePObjectMap.HOME_PROJECT_RECORD_COMMENTS_DIV_CSS.toString()), 30);
		String link=ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_RECORD_COMMENTS_DIV_CSS.toString())).getText();
		if (link.equalsIgnoreCase(url))
			return true;
		else
			return false;
	}

	public static void clickExternalLinkInComments(String url) throws Exception {
		//BrowserWaits.waitTime(6);
		BrowserWaits.waitForAllElementsToBePresent(ob, By.cssSelector(OnePObjectMap.HOME_PROJECT_RECORD_COMMENTS_DIV_CSS.toString()), 30);
		String link=ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_RECORD_COMMENTS_DIV_CSS.toString())).getText();
		if (link.equalsIgnoreCase(url))
			ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_RECORD_COMMENTS_DIV_CSS.toString())).click();
	}
	
	}	

