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
		ProfilePage.clickProfileImage();
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
		ProfilePage.clickProfileImage();
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_ACCOUNT_LINK);
		BrowserWaits.waitTime(4);
		BrowserWaits.waitUntilText("Account","SETTINGS","EMAIL","PASSWORD");
	}
}
