package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.BrowserWaits;
import util.OnePObjectMap;

/**
 * Class for Perform Browser Actions
 * 
 * @author UC225218
 *
 */
public class SearchAuthorClusterPage extends TestBase {

	public SearchAuthorClusterPage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}

	OnePObjectMap lastname_typeahead_first_value = OnePObjectMap.WAT_LASTNAME_FIRST_TYPEAHEAD_SUGGESTION_XPATH;
	OnePObjectMap firstname_typeahead_first_value = OnePObjectMap.WAT_FIRSTNAME_FIRST_TYPEAHEAD_SUGGESTION_XPATH;
	static String lastname_path = OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH.toString();
	static String lastname_typeahead = OnePObjectMap.WAT_AUTHOR_LASTNAME_TYPEAHEAD_XPATH.toString();
	static String firstname_path = OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH.toString();
	static String firstname_typeahead = OnePObjectMap.WAT_AUTHOR_FIRSTNAME_TYPEAHEAD_XPATH.toString();

	/**
	 * Common method to search for an author cluster with only LastName
	 * 
	 * @param LastName
	 * @author UC225218
	 * 
	 */
	public void SearchAuthorCluster(String LastName, ExtentTest test) {
		try {
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH);
			enterAuthorName(lastname_typeahead_first_value, LastName, lastname_path, test);
			pf.getBrowserWaitsInstance(ob)
					.waitUntilElementIsDisplayed(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH);
			test.log(LogStatus.INFO, "Clicking find button... ");
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH);
			if (ob.findElement(By.xpath(OnePObjectMap.WAT_AUTHOR_COUNTRY_DROPDOWN_XPATH.toString())).isDisplayed()) {
				selectCountryofAuthor(test);
				BrowserWaits.waitTime(2);
				selectOrgofAuthor(test);
			} else {
				test.log(LogStatus.INFO,
						"Country name selection is not required as the searched user resulted in less than 50 clusters... ");
			}
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.WAT_SEARCH_RESULTS_TEXT_XPATH);
			Assert.assertEquals(
					(pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_SEARCH_RESULTS_TEXT_XPATH).getText()),
					"Search Results", "Unable to search for an author and landed in Author search result page.");
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Unable to search for an author and landed in Author search result page.");
		}
	}

	/**
	 * Common method to search for an author cluster with LastName and FirstName
	 * 
	 * @throws @author
	 *             UC225218
	 * @param LastName
	 *            FirstName
	 */
	public void SearchAuthorCluster(String LastName, String FirstName, ExtentTest test) {
		try {
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH);
			enterAuthorName(lastname_typeahead_first_value, LastName, lastname_path, test);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH);
			enterAuthorName(firstname_typeahead_first_value, FirstName, firstname_path, test);
			pf.getBrowserWaitsInstance(ob)
					.waitUntilElementIsDisplayed(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH);
			test.log(LogStatus.INFO, "Clicking find button... ");
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH);
			try {
				ob.findElement(By.xpath(OnePObjectMap.WAT_AUTHOR_COUNTRY_DROPDOWN_XPATH.toString()));
				selectCountryofAuthor(test);
				BrowserWaits.waitTime(2);
				selectOrgofAuthor(test);
			} catch (Exception e) {
				test.log(LogStatus.INFO,
						"Country name selection is not required as the searched user resulted in less than 50 clusters... ");
			}
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.WAT_SEARCH_RESULTS_TEXT_XPATH);
			Assert.assertEquals(
					(pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_SEARCH_RESULTS_TEXT_XPATH).getText()),
					"Search Results", "Unable to search for an author and landed in Author search result page.");
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Unable to search for an author and landed in Author search result page.");
		}
	}

	/**
	 * Common method to search for an author cluster with LastName, First name
	 * and Country name
	 * 
	 * @param LastName
	 *            FirstName CountryName
	 * @author UC225218
	 * 
	 */
	public void SearchAuthorCluster(String LastName, String FirstName, String CountryName, ExtentTest test) {
		try {
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH);
			enterAuthorName(lastname_typeahead_first_value, LastName, lastname_path, test);
			BrowserWaits.waitTime(2);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH);
			enterAuthorName(firstname_typeahead_first_value, FirstName, firstname_path, test);
			BrowserWaits.waitTime(1);
			selectCountryofAuthor(test);
			BrowserWaits.waitTime(2);
			selectOrgofAuthor(test);

			// TODO Yet to be finished

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Common method to search for an author cluster with LastName, First
	 * name,Country name and Org Name
	 * 
	 * @param LastName
	 *            FirstName CountryName OrgName
	 * @author UC225218
	 * 
	 */
	public void SearchAuthorCluster(String LastName, String FirstName, String CountryName, String OrgName,
			ExtentTest test) {
		// TODO Yet to be coded
	}

	/**
	 * Method to enter the author search name (Can be First name or Last name)
	 * character by character.
	 * 
	 * @throws Exception
	 * @param Name
	 *            element Typeahead
	 * 
	 */
	public void enterAuthorName(OnePObjectMap Typeahead, String Name, String element, ExtentTest test)
			throws Exception {

		for (int i = 0; i < Name.length(); i++) {
			char c = Name.charAt(i);
			String s = new StringBuilder().append(c).toString();
			ob.findElement(By.xpath(element)).sendKeys(s);
			BrowserWaits.waitTime(0.5);
		}
		BrowserWaits.waitTime(3);
		selectFromTypeahead(Typeahead, Name, test);
	}

	/**
	 * Method to select value from typeahead, This will loop for 5 times incase
	 * if typeahead suggestions are not displayed.(Configurable)
	 * 
	 * @throws Exception
	 * 
	 * @param lnTypeahead
	 *            selectionText
	 * 
	 */
	public void selectFromTypeahead(OnePObjectMap Typeahead, String selectionText, ExtentTest test) throws Exception {
		int i = 1;
		while (i < 5) {
			try {
				if (ob.findElement(By.xpath(OnePObjectMap.WAT_AUTHOR_LASTNAME_TYPEAHEAD_XPATH.toString()))
						.isEnabled()) {
					pf.getBrowserActionInstance(ob).click(Typeahead);
					break;
				}

			} catch (Exception e) {
				test.log(LogStatus.INFO, "Typeahead not displayed... Count is " + i);
				BrowserWaits.waitTime(1);
				pf.getBrowserActionInstance(ob).clear(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH);
				BrowserWaits.waitTime(1);
				enterAuthorName(lastname_typeahead_first_value, selectionText, lastname_path, test);
				test.log(LogStatus.INFO, "Typeahead not displayed... Count is " + i);
				i++;
			}
		}
	}

	/**
	 * Method to select Country value for further filtering of author cluster.
	 * 
	 * @author UC225218
	 * 
	 * 
	 */
	public void selectCountryofAuthor(ExtentTest test) {
		waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.WAT_AUTHOR_COUNTRY_DROPDOWN_XPATH.toString()), 5);
		try {
			pf.getBrowserActionInstance(ob).moveToElement(OnePObjectMap.WAT_AUTHOR_COUNTRY_DROPDOWN_XPATH);
			test.log(LogStatus.INFO,
					"Country name selected as the searched user resulted in more than 50 clusters... ");
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_COUNTRY_XPATH);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Method to select organization value for further filtering of author
	 * cluster.
	 * 
	 * @author UC225218
	 * 
	 * 
	 */
	public void selectOrgofAuthor(ExtentTest test) {
		waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.WAT_AUTHOR_ORG_DROPDOWN_XPATH.toString()), 5);
		try {
			test.log(LogStatus.INFO, "Selecting relavent organization... ");
			pf.getBrowserActionInstance(ob).moveToElement(OnePObjectMap.WAT_AUTHOR_ORG_DROPDOWN_XPATH);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_ORG_XPATH);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
