package pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import base.TestBase;
import util.BrowserAction;
import util.BrowserWaits;
import util.OnePObjectMap;

public class ProfilePage  extends TestBase {
	
	/**
	 * Search results people count
	 */
	static int peopleCount=0;
	static String PARENT_WINDOW_HANDLE=null;
			
	public static void enterSearchKeyAndClick(String searchKey) throws Exception {
		BrowserAction.enterFieldValue(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_BOX_CSS, searchKey);
		Thread.sleep(2000);
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_CLICK_CSS);
		Thread.sleep(4000);
	}
	
	/**
	 * Method for Validate Profile Search with last name
	 * @param lastName
	 * @throws Exception
	 */
	public static void validateProfileLastName(String lastName) throws Exception {
		List<WebElement> profilesLastname=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_NAME_CSS);
		if(profilesLastname.size()>0){
			for(WebElement profileLastname:profilesLastname) {
				if(!StringUtils.containsIgnoreCase(profileLastname.getText(), lastName)) {
					throw new Exception("Profile serach not verifying with Last Name");
				}
			}
		}
		else
			System.out.println("Profile Search Results are not available with \t"+lastName+ "\t last Name");
		
	}
	
	/**
	 * Method for Validate Profile Search with Role/Primary Institution/Country
	 * @param metaData
	 * @throws Exception
	 */
	public static void validateProfileMetaData(String metaData) throws Exception {
		List<WebElement> profilesLastname=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_NAME_CSS);
		if(profilesLastname.size()>0){
			List<WebElement> profilesMetaData=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_PROFILE_METADATA_TAG);
			System.out.println("Profile metadata--->"+profilesMetaData.size());
			for(WebElement profileMetaData:profilesMetaData) {
				System.out.println("Meta Data-->"+profileMetaData.getText());
				if(!StringUtils.containsIgnoreCase(profileMetaData.getText(), metaData)) {
					throw new Exception("Profile search not verifying with Role/Primary Institution/Country	");
				}
			}
		}
		else
			System.out.println("No Profile Search Results are not available with \t"+metaData+ "\t role/Primary Institution/Country");
	}
	
	
	/**
	 * Method for Click People after searching an profile
	 * @throws Exception, When People are not present/Disabled
	 */
	public static int getPeopleCount() throws Exception {
			String listPeople=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_PEOPLE_CSS).get(2).findElement(By.tagName("span")).getText();
			peopleCount=Integer.parseInt(listPeople);
			System.out.println("Total People search results-->"+peopleCount);
			return peopleCount;
	}
	
	/**
	 * Method for Click People after searching an profile
	 * @throws Exception, When People are not present/Disabled
	 */
	public static void clickPeople() throws Exception {
			BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_PEOPLE_CSS).get(2).click();
			Thread.sleep(8000);
	}
	
	
	/**
	 * Method for Validate Profile Search with Interests and Skills
	 * @param lastName
	 * @throws Exception
	 */
	public static void validateProfileInterest(String interestAndSkill) throws Exception {
		List<WebElement> profilesname=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_NAME_CSS);
		if(profilesname.size()>0){
			for(WebElement profilename:profilesname) {
				profilename.findElement(By.tagName("a")).click();
				BrowserWaits.waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_INTEREST_AND_SKILLS_CSS);
				Thread.sleep(6000);
				break;
			}
			
			List<WebElement> interestsSkills=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_INTEREST_AND_SKILLS_CSS);
			List<String> interests=new ArrayList<String>();
			for(WebElement intSkill:interestsSkills) {
				interests.add(intSkill.getText());
			}
			
			System.out.println("interests and skills-->"+interests);
			
			if(!interests.contains(interestAndSkill)) {
				throw new Exception("Profile Search not happening with Interests and Skill "+interestAndSkill);
			}
		}
		else
			System.out.println("Profile Search Results are not available with \t"+interestAndSkill+ "\t Interests and Skills");
		
	}
	
	/**
	 * Method for Validate Apps links for redirecting different pages
	 * @param appLinks
	 * @throws Exception, When App links not present
	 */
	public static void validateAppsLinks(String appLinks) throws Exception  {
			String []totalAppLinks=appLinks.split("\\|");
			for(int i=0;i<totalAppLinks.length;i++) {
				BrowserAction.click(OnePObjectMap.HOME_ONEP_APPS_LINK);
				PARENT_WINDOW_HANDLE = ob.getWindowHandle();
				ob.findElement(By.linkText(totalAppLinks[i])).click();
				Thread.sleep(6000);
				ob.manage().window().maximize();
				Set<String> child_window_handles= ob.getWindowHandles();
				 for(String child_window_handle:child_window_handles) {
					 if(!child_window_handle.equals(PARENT_WINDOW_HANDLE)) {
						 ob.switchTo().window(child_window_handle);
						 String appLinkText=BrowserAction.getElement(OnePObjectMap.HOME_ONEP_APPS_PAGE_TITLE_HEADER_CSS).getText();
						 Assert.assertEquals(totalAppLinks[i], appLinkText);
						 ob.close();
						 ob.switchTo().window(PARENT_WINDOW_HANDLE);
				} //if
			 } //for
			} //for
		}
}
