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
			enterAuthorLastName(LastName, test);
			pf.getBrowserWaitsInstance(ob)
					.waitUntilElementIsDisplayed(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH);
			test.log(LogStatus.INFO, "Clicking find button... ");
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH);
			if (pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_COUNTRY_DROPDOWN_XPATH)
					.isDisplayed()) {
				selectCountryofAuthor(test);
				// BrowserWaits.waitTime(2);
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.WAT_AUTHOR_ORG_DROPDOWN_XPATH);
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
			enterAuthorLastName(LastName, test);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH);
			enterAuthorFirstName(FirstName, test);
			pf.getBrowserWaitsInstance(ob)
					.waitUntilElementIsDisplayed(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH);
			test.log(LogStatus.INFO, "Clicking find button... ");
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH);
			if (pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_COUNTRY_DROPDOWN_XPATH)
					.isDisplayed()) {
				selectCountryofAuthor(test);
				// BrowserWaits.waitTime(2);
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.WAT_AUTHOR_ORG_DROPDOWN_XPATH);
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
			enterAuthorLastName(LastName, test);
			BrowserWaits.waitTime(2);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH);
			enterAuthorFirstName(FirstName, test);
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
	 * Method to enter the author search name (Last name) character by
	 * character.
	 * 
	 * @throws Exception
	 * @param Name
	 *            element Typeahead
	 * 
	 */
	public void enterAuthorLastName(String LastName, ExtentTest test) throws Exception {

		for (int i = 0; i < LastName.length(); i++) {
			char c = LastName.charAt(i);
			String s = new StringBuilder().append(c).toString();
			pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH, s);
			BrowserWaits.waitTime(0.5);
		}
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.WAT_AUTHOR_LASTNAME_TYPEAHEAD_XPATH);
		selectLastNameFromTypeahead(LastName, test);
	}

	/**
	 * Method to enter the author search name (First name) character by
	 * character.
	 * 
	 * @throws Exception
	 * @param Name
	 * 
	 */
	public void enterAuthorFirstName(String FirstName, ExtentTest test) throws Exception {

		for (int i = 0; i < FirstName.length(); i++) {
			char c = FirstName.charAt(i);
			String s = new StringBuilder().append(c).toString();
			pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH, s);
			BrowserWaits.waitTime(0.5);
		}
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_TYPEAHEAD_XPATH);
		selectFirstNameFromTypeahead(FirstName, test);
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
	public void selectLastNameFromTypeahead(String selectionText, ExtentTest test) throws Exception {
		int i = 1;
		while (i < 5) {
			try {
				if (pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_LASTNAME_TYPEAHEAD_XPATH)
						.isEnabled()) {
					pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_LASTNAME_FIRST_TYPEAHEAD_SUGGESTION_XPATH);
					break;
				}

			} catch (Exception e) {
				test.log(LogStatus.INFO, "Typeahead not displayed... Count is " + i);
				BrowserWaits.waitTime(1);
				pf.getBrowserActionInstance(ob).clear(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH);
				// BrowserWaits.waitTime(1);
				enterAuthorLastName(selectionText, test);
				test.log(LogStatus.INFO, "Typeahead not displayed... Count is " + i);
				i++;
			}
		}
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
	public void selectFirstNameFromTypeahead(String selectionText, ExtentTest test) throws Exception {
		int i = 1;
		while (i < 5) {
			try {
				if (pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_TYPEAHEAD_XPATH)
						.isEnabled()) {
					pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_FIRSTNAME_FIRST_TYPEAHEAD_SUGGESTION_XPATH);
					break;
				}

			} catch (Exception e) {
				test.log(LogStatus.INFO, "Typeahead not displayed... Count is " + i);
				BrowserWaits.waitTime(1);
				pf.getBrowserActionInstance(ob).clear(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH);
				// BrowserWaits.waitTime(1);
				enterAuthorFirstName(selectionText, test);
				test.log(LogStatus.INFO, "Typeahead not displayed... Count is " + i);
				i++;
			}
		}
	}

	/**
	 * Method to select Country value for further filtering of author cluster.
	 * 
	 * @author UC225218
	 * @throws Exception
	 * 
	 * 
	 */
	public void selectCountryofAuthor(ExtentTest test) throws Exception {
		pf.getBrowserWaitsInstance(ob).waitForElementTobeVisible(ob,
				By.xpath(OnePObjectMap.WAT_AUTHOR_COUNTRY_DROPDOWN_XPATH.toString()), 5,
				"Country dropdown is not present in Author search page");
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
	 * @throws Exception
	 * 
	 * 
	 */
	public void selectOrgofAuthor(ExtentTest test) throws Exception {
		pf.getBrowserWaitsInstance(ob).waitForElementTobeVisible(ob,
				By.xpath(OnePObjectMap.WAT_AUTHOR_ORG_DROPDOWN_XPATH.toString()), 5,
				"Country dropdown is not present in Author search page");
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
