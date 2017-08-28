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
		List<WebElement> pubDetailsList=pf.getBrowserActionInstance(ob).getElements(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_CSS);
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
	
	

}
