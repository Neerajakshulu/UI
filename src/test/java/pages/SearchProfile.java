package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import base.TestBase;
import util.BrowserAction;
import util.BrowserWaits;
import util.OnePObjectMap;

public class SearchProfile  extends TestBase {
	
	/**
	 * Search results people count
	 */
	static int peopleCount=0;
	static String followUnfollowLableBefore;
	static String followUnfollowLableAfter;
			
	public static void enterSearchKeyAndClick(String searchKey) throws Exception {
		BrowserAction.enterFieldValue(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_BOX_CSS, searchKey);
		Thread.sleep(2000);
		BrowserAction.click(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_CLICK_CSS);
		Thread.sleep(4000);
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
	public static int validatePeopleSearchResults() throws Exception {
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
			BrowserWaits.waitTime(6);
	}
	
	/**
	 * Follow/UnFollow other Profile from Search Page itself
	 * @throws Exception
	 */
	public static void followProfileFromSeach() throws Exception {
		WebElement followUnFollowCheck=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_PROFILE_TICKMARK_CSS);
		followUnfollowLableBefore=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_PROFILE_TOOLTIP_CSS).getAttribute("tooltip");
		System.out.println("Follow/Unfollow Label Before-->"+followUnfollowLableBefore);
		followUnFollowCheck.click();
		Thread.sleep(4000);
		followUnfollowLableAfter=BrowserAction.getElement(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_PROFILE_TOOLTIP_CSS).getAttribute("tooltip");
		System.out.println("Follow/Unfollow Label After-->"+followUnfollowLableAfter);
		
		if(followUnfollowLableBefore.equalsIgnoreCase(followUnfollowLableAfter)){
			throw new Exception("unable to follow other profile from search screen");
		}
	}

}
