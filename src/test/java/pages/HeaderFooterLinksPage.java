package pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import base.TestBase;
import util.BrowserAction;
import util.BrowserWaits;
import util.OnePObjectMap;

public class HeaderFooterLinksPage extends TestBase {
	
	
	/**
	 * Method for Validate Application Header and Footer links
	 * Header Link - Help link
	 * Footer Links - Cookie Policy,Privacy Statement,Terms of Use 
	 * @throws Exception, When validation not done and Element Not found 
	 */
	public static void validateLinks(String appHeadFooterLinks) throws Exception {
		String headerFooterLinks[]=appHeadFooterLinks.split("\\|");
		for(int i=0;i<headerFooterLinks.length;i++) {
			BrowserAction.scrollToElement(OnePObjectMap.HOME_PROJECT_NEON_APP_FOOTER_LINKS_CSS);
		
			if (headerFooterLinks[i].equalsIgnoreCase("Cookie Policy")) {
				BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_APP_FOOTER_LINKS_CSS).get(0).click();
				BrowserWaits.waitTime(4);
				BrowserWaits.waitUntilText(headerFooterLinks[i]);
				String cookieText=ob.findElement(By.tagName("h3")).getText();
				//System.out.println("cookie text-->"+cookieText);
				Assert.assertEquals(headerFooterLinks[i], cookieText);
			}
			
			else if (headerFooterLinks[i].equalsIgnoreCase("Privacy Statement")) {
				BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_APP_FOOTER_LINKS_CSS).get(1).click();
				BrowserWaits.waitTime(4);
				BrowserWaits.waitUntilText(headerFooterLinks[i]);
				String psText=ob.findElement(By.tagName("h3")).getText();
				//System.out.println("ps text-->"+psText);
				Assert.assertEquals(headerFooterLinks[i], psText);
			}
			
			else {
				BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_APP_FOOTER_LINKS_CSS).get(2).click();
				BrowserWaits.waitTime(4);
				BrowserWaits.waitUntilText(headerFooterLinks[i]);
				String tcText=ob.findElement(By.tagName("h3")).getText();
				//System.out.println("TC text-->"+tcText);
				Assert.assertEquals(headerFooterLinks[i], tcText);
			}
		}
	}
	
	/**
	 * Method for validate Help link Content
	 * @throws Exception
	 */
	public static void helpLinkValidation() throws Exception {
		clickProfileImage();
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_HELP_LINK);
		BrowserWaits.waitTime(4);
		BrowserWaits.waitUntilText("Help","Feedback");
		BrowserWaits.waitUntilText("We welcome your feedback.");
		
	}
	/**
	 * Method for validate Account link Content
	 * @throws Exception
	 */
	public static void accountLinkValidation() throws Exception {
		clickProfileImage();
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_ACCOUNT_LINK);
		BrowserWaits.waitTime(4);
		BrowserWaits.waitUntilText("Account","SETTINGS","EMAIL","PASSWORD");
	}
	
	/**
	 * Method for click profile image
	 * @throws Exception, When Profile image not available
	 */
	public static void clickProfileImage() throws Exception {
			BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_IMAGE_CSS);
			BrowserWaits.waitUntilText("Profile","Account","Help","Sign out");
			
	}
	
	/**
	 * Method to click on WatchList link in header
	 */
	public static void clickOnWatchLink() {
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_HEADER_WATCHLIST_LINK.toString()), 180);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_HEADER_WATCHLIST_LINK.toString())).click();

	}

	/**
	 * Method to click on Home link in header
	 */
	public static void clickOnHomeLink() {
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_HEADER_HOME_LINK.toString()), 180);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_HEADER_HOME_LINK.toString())).click();
	}

	/**
	 * Method to click on Publish A Post link in header
	 */
	public static void clickOnPublishAPostLink() {

		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_HEADER_PUBLISH_A_POST_LINK.toString()), 180);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_HEADER_PUBLISH_A_POST_LINK.toString())).click();
	}
	
	/**
	 * Method to click on Profile link under profile dropdown in header
	 */
	public static void clickOnProfileLink() throws Exception {
		clickProfileImage();
		waitForElementTobeVisible(ob,
				By.linkText(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_LINK.toString()), 180);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_LINK.toString())).click();
	}

	/**
	 * Method to click on Account link under profile dropdown in header
	 */
	public static void clickOnAccountLink() throws Exception {
		clickProfileImage();
		waitForElementTobeVisible(ob,
				By.linkText(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_ACCOUNT_LINK.toString()), 180);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_ACCOUNT_LINK.toString())).click();
	}

	/**
	 * Method to click on SignOut link under profile dropdown in header
	 */
	public static void clickOnSignOutLink() throws Exception {
		clickProfileImage();
		waitForElementTobeVisible(ob,
				By.linkText(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_SIGNOUT_LINK.toString()), 180);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_SIGNOUT_LINK.toString())).click();
	}

	/**
	 * Method to click on Help link under profile dropdown in header
	 */
	public static void clickOnHelpLink() throws Exception {
		clickProfileImage();
		waitForElementTobeVisible(ob,
				By.linkText(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_HELP_LINK.toString()), 180);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_HELP_LINK.toString())).click();
	}

	
	/**
	 * Method to enter search text in search box
	 * @param searchText
	 */
	public static void enterSearchText(String searchText) {
		waitForElementTobeVisible(ob,
				By.linkText(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_BOX_CSS.toString()), 180);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_BOX_CSS.toString())).clear();
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_BOX_CSS.toString())).sendKeys(searchText);
	}
	
	
	/**
	 * Method to click on search icon
	 */
	public static void clickOnSearchIcon() {
		
		waitForElementTobeVisible(ob,
				By.linkText(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_CLICK_CSS.toString()), 180);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_CLICK_CSS.toString())).click();
	}
	
	
	/**
	 * Method to search for the given text.
	 * @param text
	 */
	public static void searchForText(String text){
		enterSearchText(text);
		clickOnSearchIcon();
		
	}

}
