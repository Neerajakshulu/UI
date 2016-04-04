package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import base.TestBase;
import util.OnePObjectMap;

public class HeaderFooterLinksPage extends TestBase {
	
	 
	PageFactory pf;
	public HeaderFooterLinksPage(WebDriver ob){
		this.ob=ob;
		pf= new PageFactory();
	}
	
	
	
	/**
	 * Method for Validate Application Header and Footer links
	 * Header Link - Help link
	 * Footer Links - Cookie Policy,Privacy Statement,Terms of Use 
	 * @throws Exception, When validation not done and Element Not found 
	 */
	public void validateLinks(String appHeadFooterLinks) throws Exception {
		String headerFooterLinks[]=appHeadFooterLinks.split("\\|");
		
		for(int i=0;i<headerFooterLinks.length;i++) {
			
			clickProfileImage();
		
			if (headerFooterLinks[i].equalsIgnoreCase("Cookie Policy")) {
				pf.getBrowserActionInstance(ob).click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_COOKIE_POLICY_LINK);
				waitForElementTobePresent(ob, By.tagName("h3"), 90);
				pf.getBrowserWaitsInstance(ob).waitUntilText("Cookie Policy","Cookies","Cookie name","Description","Additional information");
				pf.getBrowserWaitsInstance(ob).waitUntilText(headerFooterLinks[i]);
				String cookieText=ob.findElement(By.tagName("h3")).getText();
				//System.out.println("cookie text-->"+cookieText);
				Assert.assertEquals(headerFooterLinks[i], cookieText);
			}
			
			else if (headerFooterLinks[i].equalsIgnoreCase("Privacy Statement")) {
				pf.getBrowserActionInstance(ob).click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_PRIVACY_STATEMENT_LINK);
				pf.getBrowserWaitsInstance(ob).waitUntilText("Scope","Highlights","Full Privacy Statement","Cookies","Third Party Services");
				pf.getBrowserWaitsInstance(ob).waitUntilText(headerFooterLinks[i]);
				String psText=ob.findElement(By.tagName("h3")).getText();
				//System.out.println("ps text-->"+psText);
				Assert.assertEquals(headerFooterLinks[i], psText);
			}
			
			else {
				pf.getBrowserActionInstance(ob).click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_TERMS_OF_USE_LINK);
				pf.getBrowserWaitsInstance(ob).waitUntilText("These Terms of Use shall govern your use of the online service known as");
				pf.getBrowserWaitsInstance(ob).waitUntilText(headerFooterLinks[i]);
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
	public void helpLinkValidation() throws Exception {
		clickProfileImage();
		waitForElementTobeClickable(ob, By.linkText(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_HELP_LINK.toString()), 90);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_HELP_LINK);
		waitForPageLoad(ob);
		pf.getBrowserWaitsInstance(ob).waitUntilText("Help","Feedback");
		pf.getBrowserWaitsInstance(ob).waitUntilText("We welcome your feedback.");
		
	}
	/**
	 * Method for validate Account link Content
	 * @throws Exception
	 */
	public void accountLinkValidation() throws Exception {
		clickProfileImage();
		waitForElementTobeClickable(ob, By.linkText(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_ACCOUNT_LINK.toString()), 90);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_ACCOUNT_LINK);
		//pf.waitTime(4);
		waitForPageLoad(ob);
		pf.getBrowserWaitsInstance(ob).waitUntilText("Account","SETTINGS","EMAIL","PASSWORD");
	}
	
	/**
	 * Method for click profile image
	 * @throws Exception, When Profile image not available
	 */
	public void clickProfileImage() throws Exception {
		waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_IMAGE_CSS.toString()), 180);	
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_IMAGE_CSS);
		pf.getBrowserWaitsInstance(ob).waitUntilText("Profile","Account","Help","Sign out");
			
	}
	
	/**
	 * Method to click on WatchList link in header
	 */
	public void clickOnWatchLink() throws Exception {
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_HEADER_WATCHLIST_CSS.toString()), 180);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.HOME_PROJECT_NEON_HEADER_WATCHLIST_CSS);
		//pf.waitTime(4);
		waitForPageLoad(ob);

	}

	/**
	 * Method to click on Home link in header
	 */
	public void clickOnHomeLink() {
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_HEADER_HOME_LINK.toString()), 180);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_HEADER_HOME_LINK.toString())).click();
	}

	/**
	 * Method to click on Publish A Post link in header
	 */
	public void clickOnPublishAPostLink() {

		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_HEADER_PUBLISH_A_POST_LINK.toString()), 180);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_HEADER_PUBLISH_A_POST_LINK.toString())).click();
	}
	
	/**
	 * Method to click on Profile link under profile dropdown in header
	 */
	public void clickOnProfileLink() throws Exception {
		clickProfileImage();
		waitForElementTobeVisible(ob,
				By.linkText(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_LINK.toString()), 180);
		jsClick(ob,ob.findElement(By.linkText(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_LINK.toString())));
		//pf.waitTime(4);
				waitForPageLoad(ob);
	}

	/**
	 * Method to click on Account link under profile dropdown in header
	 */
	public void clickOnAccountLink() throws Exception {
		clickProfileImage();
		waitForElementTobeVisible(ob,
				By.linkText(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_ACCOUNT_LINK.toString()), 180);
		ob.findElement(By.linkText(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_ACCOUNT_LINK.toString())).click();
	}

	/**
	 * Method to click on SignOut link under profile dropdown in header
	 */
	public void clickOnSignOutLink() throws Exception {
		clickProfileImage();
		waitForElementTobeVisible(ob,
				By.linkText(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_SIGNOUT_LINK.toString()), 180);
		ob.findElement(By.linkText(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_SIGNOUT_LINK.toString())).click();
	}

	/**
	 * Method to click on Help link under profile dropdown in header
	 */
	public void clickOnHelpLink() throws Exception {
		clickProfileImage();
		
		waitForElementTobeVisible(ob,
				By.linkText(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_HELP_LINK.toString()), 180);
		ob.findElement(By.linkText(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_HELP_LINK.toString())).click();
	}

	
	/**
	 * Method to enter search text in search box
	 * @param searchText
	 */
	public void enterSearchText(String searchText) {
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_BOX_CSS.toString()), 180);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_BOX_CSS.toString())).clear();
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_BOX_CSS.toString())).sendKeys(searchText);
	}
	
	
	/**
	 * Method to click on search icon
	 */
	public void clickOnSearchIcon() {
		
		waitForElementTobeVisible(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_CLICK_CSS.toString()), 180);
		ob.findElement(By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_CLICK_CSS.toString())).click();
	}
	
	
	/**
	 * Method to search for the given text.
	 * @param text
	 */
	public void searchForText(String text){
		enterSearchText(text);
		clickOnSearchIcon();
		
	}

}
