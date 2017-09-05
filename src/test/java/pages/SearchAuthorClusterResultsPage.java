package pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.OnePObjectMap;

/**
 * Class for Perform Browser Actions
 * 
 * @author UC225218
 *
 */
public class SearchAuthorClusterResultsPage extends TestBase {
	
	List<WebElement> pubDetailsList;
	List<WebElement> morePublications;
	List<WebElement> recentPublications;
	protected static final String REG_EXP="^[-+]?\\d+(\\ - \\d+)?$";
	String publications[] = null;
	String years[] = null;
	String topJournals = null;
	List<WebElement> journals;
	int beforeScroll;
	int afterScroll;
	List<Integer> sortByRelevance;
	

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
	
	/**
	 * Verify publication details if publication count is morethan 1
	 * 
	 * @throws Exception
	 */
	public void validateMorePublicationsCount(String lastName,
			String countryName,
			String orgName,
			ExtentTest test) throws Exception {
		waitForauthorClusterSearchResults(test);
		pubDetailsList = pf.getBrowserActionInstance(ob)
				.getElements(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_CSS);
		String authorMetadata = pf.getBrowserActionInstance(ob)
				.getElements(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_CSS)
				.get(0).getText();
		
		String primaryName=pf.getBrowserActionInstance(ob)
		.getElement(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_AUTHOR_NAME_CSS).getText();
		
		if (!(StringUtils.containsIgnoreCase(primaryName, lastName)
				&& StringUtils.containsIgnoreCase(authorMetadata, countryName)
				&& StringUtils.containsIgnoreCase(authorMetadata, orgName))){
			test.log(LogStatus.FAIL, "Author Metadata Mismatching with Search input content");
			ErrorUtil.addVerificationFailure(new Exception("Author Metadata details are mismatching with Search input"));
		}

		morePublications = pf.getBrowserActionInstance(ob).getElements(
				OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_AUTHOR_MORE_PUBLICATIONS_XPATH);
		if (morePublications.size() > 0) {
			publications = morePublications.get(0).getText().split("\n");
			years = morePublications.get(1).getText().split("\n");
			topJournals = morePublications.get(2).getText();
		}
		
		if (!(StringUtils.containsIgnoreCase(publications[0].trim(), "Publications")
				&& (publications[1].trim().matches(REG_EXP)))
				&& (years[0].trim().equalsIgnoreCase("Years") && (years[1].trim().matches(REG_EXP)))
				&& (StringUtils.isNotEmpty(topJournals) && topJournals.equalsIgnoreCase("Top Journals"))) {
			test.log(LogStatus.FAIL, "Publications doesn't have any count value, Years and Top Journals");
			throw new Exception("More Publications details fail");
		}
		
		recentPublications=pf.getBrowserActionInstance(ob).getElements(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_RECENT_PUBLICATIONS_CSS);
		pf.getBrowserActionInstance(ob).scrollingToElement(recentPublications.get(0));
		if(recentPublications.size()>0) {
			if(!StringUtils.equalsIgnoreCase(recentPublications.get(0).getText().trim(), "Recent publications")){
				test.log(LogStatus.FAIL, "Recent Publications link not present in Publication cart");
				throw new Exception("No Recent publicaion link present in cart");
			}

		}
	}
	
	/**
	 * Method for Click Recent Publications
	 * @param test
	 * @throws Exception
	 */
	public void clickRecentPublications(ExtentTest test) throws Exception{
		waitForauthorClusterSearchResults(test);
		pubDetailsList = pf.getBrowserActionInstance(ob)
				.getElements(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_CSS);
		recentPublications=pubDetailsList.get(0).findElements(By.cssSelector(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_RECENT_PUBLICATIONS_CSS.toString()));
		if(recentPublications.size()>0) {
			test.log(LogStatus.INFO, "Recent Publications link available in Publication cart");
			pf.getBrowserActionInstance(ob).scrollingToElement(recentPublications.get(0));
			recentPublications.get(0).click();
			test.log(LogStatus.INFO, "Click Recent Publications link and it turns into Hide Publications");
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_HIDE_PUBLICATIONS_CSS);
			List<WebElement> topPubs=pubDetailsList.get(0).findElements(By.cssSelector(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_TOP_PUBLICATIONS_CSS.toString()));
			
			logger.info("top publications-->"+topPubs.size());
			
			if (!(topPubs.size() == 3 && StringUtils.isNotEmpty(topPubs.get(0).getText())
					&& StringUtils.isNotEmpty(topPubs.get(1).getText())
					&& StringUtils.isNotEmpty(topPubs.get(2).getText()))) {
				test.log(LogStatus.FAIL, "Top 3 Recent Publications displayed");
				throw new Exception("Top 3 publications are not displayed while click Recent Publications");
			}
			
		}
	}

	/**
	 * Method for Author Cluster Search Results page fields in each cart
	 * @param test
	 * @throws Exception
	 */
	public void eachAuthorClusterSearchResultsFields(String lastName,String countryName,String orgName,ExtentTest test) throws Exception{
		waitForauthorClusterSearchResults(test);
		pubDetailsList=pf.getBrowserActionInstance(ob).getElements(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_CSS);
		
		for(WebElement eachCart:pubDetailsList) {
			pf.getBrowserActionInstance(ob).scrollingToElement(eachCart);
			String primaryName=eachCart.findElement(By.cssSelector(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_AUTHOR_PRIMARY_NAME_CSS.toString())).getText();
			String altName=eachCart.findElement(By.cssSelector(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_AUTHOR_ALTERNATIVE_NAME_CSS.toString())).getText();
			String affiliation=eachCart.findElement(By.cssSelector(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_AUTHOR_AFFILIATION_CSS.toString())).getText();
			String location=eachCart.findElement(By.cssSelector(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_AUTHOR_LOCATION_CSS.toString())).getText();
			
			logger.info("Details-->"+primaryName+","+altName+","+affiliation+","+location);
			
			if (!(StringUtils.containsIgnoreCase(primaryName, lastName)
					&& StringUtils.containsIgnoreCase(altName, lastName)
					&& StringUtils.containsIgnoreCase(affiliation, orgName)
					&& StringUtils.containsIgnoreCase(location, countryName))){
				test.log(LogStatus.FAIL, "Author Metadata Mismatching with Search input content");
				throw new Exception("Author Metadata Mismatching with Search input content");
			}
			
		}
		
		recentPublications=pubDetailsList.get(0).findElements(By.cssSelector(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_RECENT_PUBLICATIONS_CSS.toString()));
		if(recentPublications.size()>0) {
			for(WebElement recentPub:pubDetailsList){
				recentPub.findElement(By.cssSelector(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_RECENT_PUBLICATIONS_CSS.toString()));
				logger.info("Rec pub link name-->"+recentPub.getText());
				if (!(StringUtils.containsIgnoreCase(recentPub.getText(), "Recent publications")
						&& StringUtils.containsIgnoreCase(recentPub.getText(), "Publications")
						&& StringUtils.containsIgnoreCase(recentPub.getText(), "Years")
						&& StringUtils.containsIgnoreCase(recentPub.getText(), "Top Journals"))) {
					test.log(LogStatus.FAIL, "Recent publications link, Publications,Years and Top Journals not displayed if author have morethan 1 publications");
					throw new Exception("Recent publications link, Publications,Years and Top Journals not displayed if author have morethan 1 publications");
			  }
			}
			
		} else {
				for(WebElement eachCart:pubDetailsList) {
					String pubTitle=eachCart.findElement(By.cssSelector(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_PUBLICATION_TITLE_CSS.toString())).getText();;
					String pubSource=eachCart.findElement(By.cssSelector(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_PUBLICATION_SOURCE_CSS.toString())).getText();
					String pubAuthors=eachCart.findElement(By.cssSelector(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_PUBLICATION_AUTHORS_CSS.toString())).getText();
			
					if(!(StringUtils.isNotEmpty(pubTitle)&&StringUtils.containsIgnoreCase(pubSource, "PUBLISHED") && StringUtils.isNotEmpty(pubAuthors))){
						test.log(LogStatus.FAIL, "Publication Details are not displayed for count 1");
						throw new Exception("Publication details are not displyaed for count 1");
					}
					
			 }
		}
	}
	
	/**
	 * Method for Author Cluster Search Results Top Journals should contain max 3
	 * @param test
	 * @throws Exception
	 */
	public void searchResultsTopJournals(ExtentTest test) throws Exception{
		waitForauthorClusterSearchResults(test);
		pubDetailsList=pf.getBrowserActionInstance(ob).getElements(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_AUTHOR_PUB_YEARS_JOURNALS_CSS);
		logger.info("total pub_years_journals-->"+pubDetailsList.size());
		if(pubDetailsList.size()>0) {
			for(WebElement topJournals:pubDetailsList){
				pf.getBrowserActionInstance(ob).scrollingToElement(topJournals);
				journals=topJournals.findElements(By.cssSelector(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_AUTHOR_TOP_JOURNALS_CSS.toString()));
				logger.info("Top journals size-->"+journals.size());
				if(!(journals.size()<=3 && journals.size()==0)){
					test.log(LogStatus.FAIL, "Top Journals section should contain max of 3 journal titles");
					throw new Exception("Top Journals section should contain max of 3 journal titles");
				}
			}
		}
	}
	
	/**
	 * Method for Scroll down for author search results
	 * @param test
	 * @throws Exception
	 */
	public void searchResultsScrollDown(ExtentTest test) throws Exception{
		waitForauthorClusterSearchResults(test);
		pubDetailsList=pf.getBrowserActionInstance(ob).getElements(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_CSS);
		beforeScroll=pubDetailsList.size();
		logger.info("beforeScroll-->"+beforeScroll);
		if(beforeScroll>10){
			pf.getBrowserActionInstance(ob).scrollingToElement(pubDetailsList.get(pubDetailsList.size()-1));
			pf.getBrowserWaitsInstance(ob).waitForAjax(ob);
			pubDetailsList=pf.getBrowserActionInstance(ob).getElements(OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_CSS);
			afterScroll=pubDetailsList.size();
			logger.info("afterScroll-->"+afterScroll);
			if(beforeScroll==afterScroll){
				test.log(LogStatus.FAIL, "Search Results count not increase while page scroll down");
				throw new Exception("Search Results count not increase while page scroll down");
			}
		}
	}
	
	
	/**
	 * Method for Verify sorted by relevance count
	 * @param test
	 * @throws Exception
	 */
	public void sortByRelevance(ExtentTest test) throws Exception{
		waitForauthorClusterSearchResults(test);
		pubDetailsList = pf.getBrowserActionInstance(ob).getElements(
				OnePObjectMap.WAT_AUTHOR_SEARCH_RESULTS_PAGE_PUBLICATIONS_DETAILS_AUTHOR_PUB_YEARS_JOURNALS_PUB_COUNT_CSS);

		sortByRelevance = new ArrayList<Integer>();
		for (WebElement pubCount : pubDetailsList) {
			sortByRelevance.add(Integer.parseInt(pubCount.getText()));
		}
		logger.info("Sort by Relevance data-->"+sortByRelevance);
		int val1 = sortByRelevance.get(0);
		int val2 = sortByRelevance.get(1);
		for (int i = 2; i < sortByRelevance.size(); i++) {
			if ( !(val1 >= val2)) {
				test.log(LogStatus.FAIL, "Publicaiton count is not in Sort order");
				throw new Exception("Publicaiton count is not in Sort order");
			}
			val1=val2;
			val2=sortByRelevance.get(i);
		}
	}
}
