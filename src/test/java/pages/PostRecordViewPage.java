package pages;

import org.openqa.selenium.By;

import base.TestBase;
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
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_WATCH_CSS.toString()), 180);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_VIEW_POST_WATCH_CSS.toString()))
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
	
	
	
}
