package watpages;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

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
	boolean hilightedTab=false;
	List<WebElement> namesCount;

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
	 * Method for click Search tab
	 * @param test
	 * @throws Exception
	 */
	public void clickSearchTab(ExtentTest test) throws Exception{
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_SEARCH_LINK_XPATH);
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
	
	public void checkForAlternativeNames() throws Exception {
		String altName=pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_ALTERNATIVE_NAME_CSS)
				.getText();
		logger.info("Actual Value : "+altName);
		Assert.assertTrue(altName.equals("Alternative names"));
		//test.log(LogStatus.PASS, "Alternative names tab displayed in author record page");

	}

	public void clickAlternativeNamesTab() throws Exception {
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_ALTERNATIVE_NAME_CSS);
		waitForAjax(ob);
		hilightedTab = pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_ALTERNATIVE_NAME_TAB_HILIGHTED_CSS).isDisplayed();
		if (!hilightedTab) {
			throw new Exception("Alternative tab is not hilighted");
		}
		
		
	}

	public void checkAlternativeNamesCount(ExtentTest test) throws Exception {
		namesCount=pf.getBrowserActionInstance(ob)
				.getElements(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_ALTERNATIVE_NAME_COUNT_CSS);
		if(namesCount.size()<=5){
			test.log(LogStatus.INFO, "Below five Alternative names are displyed");
		}else{
			throw new Exception("Below five Alternative names are not displyed");
		}
	}

	public void checkForAuthorNames(String lastName,ExtentTest test) {
		if(namesCount.size()<=5){
			String[] names=lastName.split(" ");
		for(int i=0;i<namesCount.size();i++){
			String name=namesCount.get(i).getText();
			if(name.contains(names[0])||name.contains(names[1])){
				test.log(LogStatus.INFO, "Alternative names matching with last name");
			}
		}
		}
	}

	/**
	 * Entering curation mode and verify
	 * 
	 * @param LastName
	 * @author UC225218
	 * @throws Exception
	 * 
	 */
	public void enterCurationMode(ExtentTest test)
	{
		try {
			pf.getBrowserWaitsInstance(ob).waitForAllElementsToBePresent(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_AUTHOR_PROFILE_ICON_XPATH);
			if(pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_AUTHOR_PROFILE_ICON_XPATH).isDisplayed())
			{
				pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_SUGGEST_UPDATE_BTN_XPATH);
				test.log(LogStatus.INFO, "Entering curation mode");
				Assert.assertTrue(pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_CANCEL_UPDATE_BTN_XPATH).isDisplayed());
				test.log(LogStatus.PASS, "Entered curation mode, Checking for confirmation");
				scrollElementIntoView(ob, pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_PUBLICATION_REMOVE_CHKBOX_XPATH));
				Assert.assertTrue(pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_PUBLICATION_REMOVE_CHKBOX_XPATH).isDisplayed());
				test.log(LogStatus.PASS, "Entered curation mode and confirmed successfully");
			}
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Didnt entered curation mode");
			e.printStackTrace();
		}
	}
	
	/**
	 * @throws Exception
	 * @throws InterruptedException
	 */
	@SuppressWarnings("static-access")
	public void orcidFunctionality(ExtentTest test) throws Exception, InterruptedException {
		pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_CARD_1_XPATH).click();
		pf.getBrowserWaitsInstance(ob).waitTime(3);
		String ORCID =pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_ORCID_LINK_XPATH).getText();
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_ORCID_LINK_XPATH);
		pf.getBrowserActionInstance(ob).switchToNewWindow(ob);
		Assert.assertTrue(pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_ORCID_ID).getText().contains(ORCID), "User not taken to the ORCID page of the Author");
		test.log(LogStatus.PASS, "User is taken to the ORCID page of the Author successfully");
	}
}
