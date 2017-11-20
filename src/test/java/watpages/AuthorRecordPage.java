package watpages;

import org.apache.commons.lang3.StringUtils;
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
	String metaTitle;
	String metaOrg;

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
					"The following details are available for this author.","This author record is algorithmically generated and may not be complete.","All information is derived from the publication metadata.");
			pf.getBrowserWaitsInstance(ob).waitUntilText("Future iterations of author search will allow you to claim and","edit your own profile to create a complete and accurate record of your work.");
			pf.getBrowserWaitsInstance(ob).waitUntilText("in Web of Science","Sorted by");
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
	 * Method for default avatar 
	 * @param test
	 * @throws Exception
	 */
	public void defaultAvatar() throws Exception{
		isDefaultAvatarPresent=pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_RECORD_DEFAULT_AVATAR_CSS).isDisplayed();
		if(!isDefaultAvatarPresent){
			throw new Exception("No Default Avatar/Author Profile Pic is not displayed in Author Record page");
		}
    }
	
	
	/**
	 * Method for author record meta title 
	 * @param test
	 * @throws Exception
	 */
	public void authorRecordMetaTitle() throws Exception{
		metaTitle=pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_RECORD_META_TITLE_CSS).getText();
		String[] title=metaTitle.split(" ");
		if(!(title.length>=2)){
			throw new Exception("Profile Title in Author Record page should the form form First name, last name");
		}
    }
	
	
	/**
	 * Method for author record meta affiliation/organization 
	 * @param test
	 * @throws Exception
	 */
	public void authorRecordMetaOrganization() throws Exception{
		metaOrg=pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_RECORD_META_AFFILIATION_CSS).getText();
		if(!StringUtils.isEmpty(metaOrg)){
			throw new Exception("Profile metadata doesn't have any Organization");
		}
    }
	
	
}
