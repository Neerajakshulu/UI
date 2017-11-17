package watpages;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import pages.PageFactory;
import util.OnePObjectMap;

/**
 * Class for Author Record/Profile page related Operations
 * 
 * @author UC202376
 *
 */
public class AuthorRecordPage extends TestBase {
	boolean isDefaultAvatarPresent=false;

	public AuthorRecordPage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}
	/**
	 * Method for waiting Author Record page
	 * @param test
	 * @throws Exception
	 */
	public void waitForAuthorRecordPage(ExtentTest test) throws Exception{
			pf.getBrowserWaitsInstance(ob).waitUntilText("Search Results");
			pf.getBrowserWaitsInstance(ob).waitUntilText(
					"The following details are available for this author. This author record is algorithmically generated and may not be complete. All information is derived from the publication metadata.");
			pf.getBrowserWaitsInstance(ob).waitUntilText("Future iterations of author search will allow you to claim and edit your own profile to create a complete and accurate record of your work. ");
			pf.getBrowserWaitsInstance(ob).waitUntilText("publication in Web of Science");
			test.log(LogStatus.INFO, "User navigated to Author Record page");
	}
	
	/**
	 * Method for click Search Results tab
	 * @param test
	 * @throws Exception
	 */
	public void clickSearchResultsTab(ExtentTest test) throws Exception{
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_SEARCH_RESULTS_TEXT_XPATH);
		waitForAjax(ob);
    }
	
	/**
	 * Method for click Search Results tab
	 * @param test
	 * @throws Exception
	 */
	public void defaultAvatar() throws Exception{
		isDefaultAvatarPresent=pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_RECORD_DEFAULT_AVATAR_CSS).isDisplayed();
		if(!isDefaultAvatarPresent){
			throw new Exception("No Default Avatar/Author Profile Pic is not displayed in Author Record page");
		}
    }
	
	
}
