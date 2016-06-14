package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import util.BrowserWaits;
import util.OnePObjectMap;
import base.TestBase;

public class SearchProfile extends TestBase {

	/**
	 * Search results people count
	 */
	static int peopleCount = 0;
	static String followUnfollowLableBefore;
	static String followUnfollowLableAfter;

	PageFactory pf;

	public SearchProfile(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();

	}

	public void enterSearchKeyAndClick(String searchKey) throws Exception {
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_BOX_CSS, searchKey);
		BrowserWaits.waitTime(2);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_CLICK_CSS);
		waitForAjax(ob);
		waitForElementTobeVisible(ob, By.xpath(OR.getProperty("searchResults_links")), 90);
	}

	/**
	 * Method for Click People after searching an profile
	 * 
	 * @throws Exception, When People are not present/Disabled
	 */
	public int getPeopleCount() throws Exception {
		String listPeople = pf.getBrowserActionInstance(ob)
				.getElements(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_PEOPLE_CSS).get(3).findElement(By.tagName("span"))
				.getText();
		peopleCount = Integer.parseInt(listPeople);
		logger.info("Total People search results-->" + peopleCount);
		return peopleCount;
	}

	/**
	 * Method for Click People after searching an profile
	 * 
	 * @throws Exception, When People are not present/Disabled
	 */
	public int validatePeopleSearchResults() throws Exception {
		String listPeople = pf.getBrowserActionInstance(ob)
				.getElements(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_PEOPLE_CSS).get(2).findElement(By.tagName("span"))
				.getText();
		peopleCount = Integer.parseInt(listPeople);
		System.out.println("Total People search results-->" + peopleCount);
		return peopleCount;
	}

	/**
	 * Method for Click People after searching an profile
	 * 
	 * @throws Exception, When People are not present/Disabled
	 */
	public void clickPeople() throws Exception {
		BrowserWaits.waitTime(10);
		pf.getBrowserActionInstance(ob).getElements(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_PEOPLE_CSS).get(3).click();
		waitForAjax(ob);
		waitForElementTobeClickable(ob,
				By.cssSelector(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_PROFILE_TICKMARK_CSS.toString()), 90);
	}

	/**
	 * Follow/UnFollow other Profile from Search Page itself
	 * 
	 * @throws Exception
	 */
	public void followProfileFromSeach() throws Exception {
		WebElement followUnFollowCheck = pf.getBrowserActionInstance(ob).getElement(
				OnePObjectMap.HOME_PROJECT_NEON_SEARCH_PROFILE_TICKMARK_CSS);
		followUnfollowLableBefore = pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_PROFILE_TOOLTIP_CSS).getAttribute("data-tooltip");
		System.out.println("Follow/Unfollow Label Before-->" + followUnfollowLableBefore);
		followUnFollowCheck.click();
		BrowserWaits.waitTime(4);
		followUnfollowLableAfter = pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.HOME_PROJECT_NEON_SEARCH_PROFILE_TOOLTIP_CSS).getAttribute("data-tooltip");
		System.out.println("Follow/Unfollow Label After-->" + followUnfollowLableAfter);

		if (followUnfollowLableBefore.equalsIgnoreCase(followUnfollowLableAfter)) {
			throw new Exception("unable to follow other profile from search screen");
		}
	}

	/**
	 * Method for Click People after searching an profile
	 * 
	 * @throws Exception, When People are not present/Disabled
	 */
	public void hcrProfileBadgeValidation() throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(
				OnePObjectMap.HOME_PROJECT_NEON_PROFILE_HCR_BADGE_CSS);
		String hcrAttr = pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_HCR_BADGE_CSS).findElement(By.tagName("span"))
				.getAttribute("class");
		// System.out.println("hcr profile badge-->"+hcrAttr);
		if (!hcrAttr.contains("hcr")) {
			throw new Exception("HCR Profile should display with Badge");
		}
	}

}
