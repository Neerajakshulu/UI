package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.BrowserAction;
import util.BrowserWaits;
import util.OnePObjectMap;

public class PostRecordViewPage extends TestBase{

	/**
	 * Method to click on SHARE button in post record view
	 */
	public static void clickOnShareButton() {
		
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_CREATE_POST_PUBLISH_CSS.toString()), 180);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_CREATE_POST_PUBLISH_CSS.toString()))
				.click();

	}
	
	/**
	 * Method to click on Share on Facebook link under share menu in post record view
	 */
	public static void clickOnFacebookUnderShareMenu() {
		clickOnShareButton();
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_SHARE_FACEBOOK_CSS.toString()), 180);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_SHARE_FACEBOOK_CSS.toString()))
				.click();
	}

	/**
	 * Method to click on Share on LinkedIn Link under share menu in post record view
	 */
	public static void clickOnLinkedInUnderShareMenu() {
		clickOnShareButton();
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_SHARE_LINKEDIN_CSS.toString()), 180);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_SHARE_LINKEDIN_CSS.toString()))
				.click();
	}

	/**
	 * Method to click on Share on Twitter link under share menu in post record view
	 */
	public static void clickOnTwitterUnderShareMenu() {
		clickOnShareButton();
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_SHARE_TWITTER_CSS.toString()), 180);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_SHARE_TWITTER_CSS.toString()))
				.click();
	}

	/**
	 * Method to click on Watch in post record view
	 */
	public static void clickOnWatchButton() {
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_WATCH_CSS.toString()), 180);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_WATCH_CSS.toString()))
				.click();
	}
	
	/**
	 * Method to click on EDIT post icon in post record view
	 */
	public static void clickOnEditButton(){
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_EDIT_CSS.toString()), 180);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_EDIT_CSS.toString()))
				.click();
	}
	
	/**
	 * Method to click on Stop Watching button in post record view
	 */
	public static void clickOnStopWatchingButton() {
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_STOP_WATCH_CSS.toString()), 180);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_STOP_WATCH_CSS.toString()))
				.click();
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
			Thread.sleep(5000);
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
			Thread.sleep(5000);
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
	 * @throws Exception, When Validation not done
	 */
	public static void validatePostTitleAndProfileMetadata(String post,List<String> profileInfo) throws Exception {
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_TITLE_CSS);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_PROFILE_METADATA_CSS);
		String postTitle=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_TITLE_CSS).getText();
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_PROFILE_METADATA_CSS.toString()), 180);
		BrowserWaits.waitTime(10);
		Assert.assertEquals(post, postTitle);
		String postRVProfileTitle=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_PROFILE_TILE_CSS).getText();
		String profileData=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_PROFILE_METADATA_CSS).getText();
		
		if(!(profileInfo.toString().contains(profileData) && profileInfo.toString().contains(postRVProfileTitle))){
			throw new Exception("Profile info mismatching in Record view page of a Post");
		}
		
	}

	public static boolean verifyPostCreationDate() throws Exception {
		boolean isDisplayed=false;
		waitForAllElementsToBePresent(ob, By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_TIMESTAMP_CSS.toString()), 180);
		BrowserWaits.waitTime(6);
		List<WebElement> timestamp=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_TIMESTAMP_CSS);
		String time;
		for(WebElement we: timestamp){
			time=we.getText();
			if(time.contains("POSTED") && time.contains("|") && (time.contains("AM")||time.contains("PM"))){
				isDisplayed=true;
				break;
			}
			
		}
		
		return isDisplayed;
	}
	
	public static boolean verifyPostEditedDate() throws Exception {
		boolean isDisplayed=false;
		waitForAllElementsToBePresent(ob, By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_TIMESTAMP_CSS.toString()), 180);
		BrowserWaits.waitTime(6);
		List<WebElement> timestamp=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_TIMESTAMP_CSS);
		String time;
		for(WebElement we: timestamp){
			time=we.getText();
			if(time.contains("EDITED") && time.contains("|") && (time.contains("AM")||time.contains("PM"))){
				isDisplayed=true;
				break;
			}
			
		}
		
		return isDisplayed;
	}
	
	public static String  getPostTitle() throws Exception{
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
		WebElement content=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_CONTENT_CSS);
		if(content.findElements(By.linkText(string)).size()!=0)
			return true;
		else return false;
	
	}
	
	public static void clickExternalLinkInPostContent(String url) throws Exception {
		BrowserWaits.waitTime(6);
		BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_CONTENT_CSS);
		WebElement content=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_CONTENT_CSS);
		content.findElement(By.linkText(url)).click();
		BrowserWaits.waitTime(6);
	}
	
	
	public static boolean validateURL(String url){
		
		if(ob.getCurrentUrl().toLowerCase().contains(url.toLowerCase()))return true;
		else return false;
	}

	
public static void clickOnAuthorName() throws Exception{
	BrowserWaits.waitTime(6);
	BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_AUTHORNAME_CSS);
	BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_AUTHORNAME_CSS);
	}

public static boolean isCommentCountDisplayed(){
	boolean result=false;
	try{
		
		waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_COMMENTS_COUNT_XPATH.toString()), 60);
		if(ob.findElement(By.xpath(OnePObjectMap.HOME_PROJECT_NEON_RECORD_VIEW_POST_COMMENTS_COUNT_XPATH.toString())).isDisplayed())
			result=true;	
	}catch(Exception e){
		return false;
	}
	
	return result;
}

public static boolean isLikeCountDisplayed(){
	boolean result=false;
	try{
		
		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_APPRECIATION_COUNT_CSS.toString()), 60);
		if(ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_APPRECIATION_COUNT_CSS.toString())).isDisplayed())
			result=true;	
	}catch(Exception e){
		return false;
	}
	
	return result;
}

public static boolean isLikeButtonDisplayed(){
	boolean result=false;
	try{
		
		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_APPRECIATION_CSS.toString()), 60);
		if(ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_APPRECIATION_CSS.toString())).isDisplayed())
			result=true;	
	}catch(Exception e){
		return false;
	}
	
	return result;
}

}
