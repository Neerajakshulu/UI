package pages;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.OnePObjectMap;

/**
 * Class for Perform Browser Actions
 * 
 * @author UC225218
 *
 */
public class SearchAuthorClusterResultsPage extends TestBase {
	
	List<WebElement> pubDetailsList;

	public SearchAuthorClusterResultsPage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}

	
	/**
	 * Wait for Author Cluster Search Results page
	 * @param test
	 * @throws Exception
	 */
	public void waitForauthorClusterSearchResults(ExtentTest test) throws Exception{
		pf.getBrowserActionInstance(ob).waitForAjax(ob);
		pf.getBrowserWaitsInstance(ob).waitUntilText("Search terms","results"," Sorted by ","Relevance");
		pf.getBrowserWaitsInstance(ob).waitForAllElementsToBePresent(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_CSS);
		test.log(LogStatus.INFO, "Author Cluster Search Results page is displayed");
	}
	
	/**
	 * Verify publication details if publication count is 1
	 * @throws Exception 
	 */
	public void validatePublicationCount1Details(String lastName,String countryName,String orgName,ExtentTest test) throws Exception {
		waitForauthorClusterSearchResults(test);
		pubDetailsList=pf.getBrowserActionInstance(ob).getElements(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_CSS);
		String authorMetadata=pf.getBrowserActionInstance(ob).getElements(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_CSS).get(pubDetailsList.size()-1).getText();
		
		pf.getBrowserActionInstance(ob).scrollingToElement(pf.getBrowserActionInstance(ob).getElements(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_PUBLICATION_TITLE_CSS).get(pubDetailsList.size()-1));
		
		pubDetailsList.get(pubDetailsList.size()-1).findElement(By.cssSelector(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_PUBLICATION_AUTHORS_CSS.toString())).getText();
		
		String pubTitle=pubDetailsList.get(pubDetailsList.size()-1).findElement(By.cssSelector(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_PUBLICATION_TITLE_CSS.toString())).getText();;
		String pubSource=pubDetailsList.get(pubDetailsList.size()-1).findElement(By.cssSelector(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_PUBLICATION_SOURCE_CSS.toString())).getText();
		String pubAuthors=pubDetailsList.get(pubDetailsList.size()-1).findElement(By.cssSelector(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_PUBLICATION_AUTHORS_CSS.toString())).getText();
		
		if(!(StringUtils.containsIgnoreCase(authorMetadata, lastName) && StringUtils.containsIgnoreCase(authorMetadata, orgName) && 
				StringUtils.containsIgnoreCase(authorMetadata, countryName) &&StringUtils.isNotEmpty(pubTitle)&&StringUtils.containsIgnoreCase(pubSource, "PUBLISHED") && StringUtils.isNotEmpty(pubAuthors))){
				throw new Exception("Publication details are not displyaed for count 1");
		 }
		
	}
	
	/**
	 * Method for match author search results count with publications count
	 * @param test
	 * @throws Exception
	 */
	public void matchSearchResultsCountWithPublicationsCount(ExtentTest test) throws Exception{
		waitForauthorClusterSearchResults(test);
		pubDetailsList=pf.getBrowserActionInstance(ob).getElements(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_CSS);
		String[] results=pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_COUNT_CSS).getText().split(" ");
		int resultsCount=Integer.parseInt(results[0]);
		if(!(pubDetailsList.size() == resultsCount)){
			throw new Exception("Author Search Results count doesn't match Publications Count");
		}
		
	}
	
	/**
	 * Method for Default avatar/profile image available for each publication cart
	 * @param test
	 * @throws Exception
	 */
	public void defaultAvatarEachPublication(ExtentTest test) throws Exception{
		waitForauthorClusterSearchResults(test);
		List<WebElement> defaultAvatars=pf.getBrowserActionInstance(ob).getElements(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_AUTHOR_DEFAULT_AVATAR_CSS);
		for(WebElement defaultAvatar:defaultAvatars){
			if(!defaultAvatar.isDisplayed()){
				throw new Exception("Default Avatar not displayed for Each publication");
			}
			
		}
	}

}
